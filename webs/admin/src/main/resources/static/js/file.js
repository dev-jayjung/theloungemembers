/**
 * 파일 업로드 / 다운로드 유틸
 */
const FileUtil = (() => {
  /**
   * Target DOM 요소들의 값을 일괄 변경
   */
  const setTargetValues = (targets = {}, value = '') => {
    const { $key, $name, $display, $status, $btnDelete } = targets;

    if ($key?.length) $key.val(value);
    if ($name?.length) $name.val(value);
    if ($display?.length) $display.val(value);

    if (value === '') {
      if ($status?.length) $status.text('');
      $btnDelete?.hide();
    }
  };

  /**
   * 업로드 상태 텍스트 및 색상 변경
   */
  const setStatus = ($status, message, color) => {
    if ($status?.length) {
      $status.text(message ? ` ${message}` : '').css('color', color || '');
    }
  };

  /**
   * 파일 확장자 검증
   */
  const validateExtension = (file, fileExt) => {
    if (!fileExt) return true;

    const allowedExtensions = fileExt.split(/[,|]/).map(ext => ext.trim().toLowerCase().replace('.', ''));
    const fileExtension = file.name.split('.').pop().toLowerCase();

    if (!allowedExtensions.includes(fileExtension)) {
      alert(`허용되지 않은 파일 형식입니다.\n(허용 확장자: ${allowedExtensions.join(', ')})`);
      return false;
    }

    return true;
  };

  /**
 * 파일 용량 검증 (단위: MB)
 */
  const validateFileSize = (file, maxMb) => {
    if (!maxMb) {
      return true;
    }

    const maxBytes = maxMb * 1024 * 1024;
    if (file.size > maxBytes) {
      alert(`파일 용량이 초과되었습니다.\n(최대 허용 용량: ${maxMb}MB / 현재 파일: ${(file.size / (1024 * 1024)).toFixed(2)}MB)`);
      return false;
    }

    return true;
  };

  /**
   * 이미지 해상도(가로/세로 px) 검증
   */
  const validateImageDimension = (file, maxWidth, maxHeight) => {
    return new Promise(resolve => {
      if (!maxWidth && !maxHeight) {
        resolve(true);
        return;
      }

      const img = new Image();
      const objectUrl = URL.createObjectURL(file);

      img.onload = () => {
        URL.revokeObjectURL(objectUrl); // 메모리 해제
        const width = img.width;
        const height = img.height;

        if (maxWidth && width > maxWidth) {
          alert(`이미지 가로 크기가 초과되었습니다.\n(최대 가로: ${maxWidth}px / 현재: ${width}px)`);
          resolve(false);
          return;
        }

        if (maxHeight && height > maxHeight) {
          alert(`이미지 세로 크기가 초과되었습니다.\n(최대 세로: ${maxHeight}px / 현재: ${height}px)`);
          resolve(false);
          return;
        }

        resolve(true);
      };

      img.onerror = () => {
        URL.revokeObjectURL(objectUrl);
        alert('이미지 파일을 읽는 중 오류가 발생했습니다.');
        resolve(false);
      };

      img.src = objectUrl;
    });
  };

  return {
    /**
     * 파일 단건 업로드 함수
     * @param {File} file - input에서 선택된 File 객체
     * @param {string} path - S3FilePath Enum 코드 (예: 'BOARD', 'TEMP', 'NOTICE')
     * @param {object} options - 추가 옵션 (isBuildFileName 등)
     * @returns {Promise<{key: string, originalName: string, url: string}>}
     */
    async upload(file, options = {}) {
      if (!file) {
        throw new Error('업로드할 파일이 존재하지 않습니다.');
      }

      const isBuildFileName = options.isBuildFileName ?? true;

      // 백엔드에 Presigned Upload URL 요청
      const fileInfo = await new Promise((resolve, reject) => {
        AjaxUtil.call({
          url: '/api/files/upload-url',
          type: 'GET',
          data: { fileName: file.name, isBuildFileName },
          callBack: res => resolve(res.data),
          fail: xhr => reject(xhr)
        });
      });

      // S3로 직접 바이너리 파일 PUT 전송
      const res = await fetch(fileInfo.url, {
        method: 'PUT',
        headers: {
          'Content-Type': fileInfo.contentType || 'application/octet-stream'
        },
        body: file
      });

      if (!res.ok) {
        throw new Error('S3 Direct Upload Failed');
      }

      // 업로드 완료 후 업로드 정보(Key, 원본 파일명 등) 반환
      return {
        s3Key: fileInfo.s3Key,
        originalName: file.originalName || file.name,
        contentType: fileInfo.contentType
      };
    },

    // S3 Presigned Download URL 조회 API (미리보기용)
    async getDownloadUrl(key, fileName) {
      return new Promise((resolve, reject) => {
        AjaxUtil.call({
          url: '/api/files/download-url',
          type: 'GET',
          data: { key, fileName },
          callBack: res => resolve(res.data),
          fail: xhr => reject(xhr)
        });
      });
    },

    init() {
      // ==========================================
      // 일반 파일 버튼 이벤트
      // ==========================================
      $(document)
        .off('click', '.btn-s3-file-select')
        .on('click', '.btn-s3-file-select', e => $(e.currentTarget).siblings('.auto-s3-upload').click());

      // ==========================================
      // 일반 파일 업로드 이벤트
      // ==========================================
      $(document)
        .off('change', '.auto-s3-upload')
        .on('change', '.auto-s3-upload', async e => {
          const $input = $(e.currentTarget);
          const $wrapper = $input.closest('.s3-upload-wrapper');
          const file = e.currentTarget.files[0];

          // data-* 속성 읽기
          const fileExt = $input.data('target-ext');
          const maxSize = $input.data('max-size');

          const targets = {
            $key: $($input.data('target-key')),
            $name: $($input.data('target-name')),
            $display: $($input.data('target-display')),
            $status: $wrapper.find('.upload-status'),
            $btnDelete: $wrapper.find('.btn-s3-file-delete')
          };

          // 파일 선택 취소 시 초기화
          if (!file) {
            setTargetValues(targets, '');
            return;
          }

          // 확장자 및 용량 체크
          if (!validateExtension(file, fileExt) || !validateFileSize(file, maxSize)) {
            $input.val('');
            return;
          }

          // 로딩 상태 표출
          $input.prop('disabled', true);
          setStatus(targets.$status, '업로드 중...', 'blue');

          try {
            // S3 업로드 수행
            const result = await FileUtil.upload(file);

            // Target Hidden 필드들에 결과 자동 반영
            if (targets.$key.length) targets.$key.val(result.s3Key);
            if (targets.$name.length) targets.$name.val(result.originalName);
            if (targets.$display.length) targets.$display.val(result.originalName);

            setStatus(targets.$status, `완료 (${result.originalName})`, 'green');
            targets.$btnDelete.show();
          } catch (error) {
            console.error('자동 파일 업로드 실패:', error);
            setStatus(targets.$status, '업로드 실패', 'red');
            setTargetValues(targets, '');
            $input.val('');
            alert('파일 업로드에 실패했습니다.');
          } finally {
            $input.prop('disabled', false);
          }
        });

      // ==========================================
      // 일반 파일 삭제 버튼 클릭
      // ==========================================
      $(document)
        .off('click', '.btn-s3-file-delete')
        .on('click', '.btn-s3-file-delete', e => {
          const $this = $(e.currentTarget);
          const $wrapper = $this.closest('.s3-upload-wrapper');
          const $input = $wrapper.find('.auto-s3-upload');

          const targets = {
            $key: $($input.data('target-key')),
            $name: $($input.data('target-name')),
            $display: $($input.data('target-display')),
            $status: $wrapper.find('.upload-status'),
            $btnDelete: $this
          };

          // 값 초기화
          $input.val('');
          setTargetValues(targets, '');
        });

      // ==========================================
      // 이미지 파일 버튼 이벤트
      // ==========================================
      $(document)
        .off('click', '.btn-img-select')
        .on('click', '.btn-img-select', e => $(e.currentTarget).siblings('.auto-s3-upload-img').click());

      // ==========================================
      // 이미지 파일 선택 이벤트
      // ==========================================
      $(document)
        .off('change', '.auto-s3-upload-img')
        .on('change', '.auto-s3-upload-img', async e => {
          const $input = $(e.currentTarget);
          const file = e.currentTarget.files[0];

          if (!file) {
            return;
          }

          const fileExt = $input.data('target-ext');
          const maxSize = $input.data('max-size');
          const maxWidth = $input.data('max-width');
          const maxHeight = $input.data('max-height');

          const $targetKey = $($input.data('target-key'));
          const $targetName = $($input.data('target-name'));
          const $targetImg = $($input.data('target-img'));
          const $status = $input.siblings('.upload-status');

          // 이미지 타입 검증
          if (!file.type.startsWith('image/')) {
            alert('이미지 파일만 선택 가능합니다.');
            $input.val('');
            return;
          }

          // 확장자 및 용량 체크
          if (!validateExtension(file, fileExt) || !validateFileSize(file, maxSize)) {
            $input.val('');
            return;
          }

          // 이미지 사이즈 체크
          const isValidDimension = await validateImageDimension(file, maxWidth, maxHeight);
          if (!isValidDimension) {
            $input.val('');
            return;
          }

          setStatus($status, '업로드 중...', 'blue');

          try {
            // S3 업로드
            const result = await FileUtil.upload(file);

            //  Hidden S3 Key 저장
            if ($targetKey.length) {
              $targetKey.val(result.s3Key);
            }

            if ($targetName.length) {
              $targetName.val(result.originalName);
            }

            // Presigned Download URL 받아서 <img> 미리보기 src 교체
            const downloadUrl = await FileUtil.getDownloadUrl(result.s3Key, result.originalName);
            if ($targetImg.length) {
              $targetImg.attr('src', downloadUrl);
            }

            setStatus($status, `완료 (${result.originalName})`, 'green');
          } catch (error) {
            console.error('이미지 업로드 실패:', error);
            setStatus($status, '업로드 실패', 'red');
            alert('이미지 업로드에 실패했습니다.');
          } finally {
            $input.val(''); // 동일 파일 재선택 가능하도록 초기화
          }
        });

      // ==========================================
      // 이미지 미리보기 삭제/초기화 버튼 클릭
      // ==========================================
      $(document)
        .off('click', '.btn-s3-img-delete')
        .on('click', '.btn-s3-img-delete', e => {
          const $this = $(e.currentTarget);
          const $wrapper = $this.closest('.s3-image-wrapper');
          const $input = $wrapper.find('.auto-s3-upload-img');
          const $targetKey = $($input.data('target-key'));
          const $targetName = $($input.data('target-name'));
          const $targetImg = $($input.data('target-img'));
          const $status = $wrapper.find('.upload-status');

          // 기본 이미지 주소로 복원
          const defaultSrc = $targetImg.data('default-src') || '/images/noimage.png';

          $input.val('');
          if ($targetKey.length) {
            $targetKey.val(''); // S3 Key 비우기 (서버에 null 전송)
          }

          if ($targetName.length) {
            $targetName.val('');
          }

          if ($targetImg.length) {
            $targetImg.attr('src', defaultSrc);
          }

          setStatus($status, '', '');
        });
    },

    /**
     * 파일 다중 업로드 함수
     * @param {FileList|File[]} files - 선택된 파일 리스트
     * @param {string} path - S3FilePath Enum 코드
     * @returns {Promise<Array>}
     */
    async uploadMultiple(files, path = 'TEMP') {
      return Promise.all(Array.from(files).map(file => this.upload(file, path)));
    }
  };
})();

window.FileUtil = FileUtil; // 전역에서 접근 가능하도록 설정

$(() => {
  FileUtil.init();
});