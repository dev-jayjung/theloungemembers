const TreeUtil = (() => {
  /**
   * [내부 헬퍼] 원본 데이터 배열을 jsTree 규격 객체 배열로 변환
   * @private
   */
  const _convertMapping = (list, mapping) => {
    const map = mapping || { id: 'id', parent: 'parent', text: 'text' };

    return list.map(item => {
      // 부모 ID 추출 및 최상위(#) 예외 처리
      let parentVal = item[map.parent];
      if (!parentVal || parentVal === '0' || parentVal === '') {
        parentVal = '#';
      }

      return {
        id: String(item[map.id]),
        parent: String(parentVal),
        text: String(item[map.text] || ''),
        state: item.state || {},       // opened, selected, disabled 등의 상태값 유지가능
        icon: item.icon || true,       // 커스텀 아이콘 설정 유지
        originalData: item             // DB에서 내려온 원본 DTO 전체를 보존해둠 (필요시 꺼내 쓰기 위함)
      };
    });
  };

  return {
    /**
     * jsTree 공통 생성 함수
     * @param {string|jQuery} target - 트리가 그려질 엘리먼트 (예: '#categoryTree')
     * @param {object} options - 생성 옵션
     * @returns {object|null} jsTree 인스턴스
     */
    init(target, options) {
      const $target = $(target);
      if (!$target.length) {
        console.warn('[JsTree] 대상 엘리먼트를 찾을 수 없습니다:', target);
        return null;
      }

      // 이미 인스턴스가 존재하면 기존 트리 파괴(destroy) 후 재생성
      if ($.jstree.reference($target)) {
        $target.jstree('destroy');
      }

      // 기본 옵션 세팅
      const defaults = {
        url: '',                  // 데이터 조회 API URL
        data: null,               // 프론트 단독 테스트용 로컬 배열(더미 데이터)
        dataParams: null,         // API 호출 시 추가 파라미터 (객체 또는 함수)
        responseField: 'data',    // API 응답에서 실제 데이터 배열이 담긴 필드명
        mapping: {
          id: 'id',
          parent: 'parent',
          text: 'text'
        },
        plugins: ['types', 'search'], // 기본 사용 플러그인
        autoOpenAll: false,       // 로드 완료 후 전체 펼침 여부
        onSelect: null,           // 노드 선택 시 콜백 함수 (node, data)
        onReady: null             // 트리 로드 완료 시 콜백 함수
      };

      // 기본 설정과 커스텀 설정 병합
      const config = $.extend(true, {}, defaults, options);

      // jsTree에 전달할 'data' 옵션 구성
      let treeDataConfig;

      if (config.data && Array.isArray(config.data)) {
        // [CASE A] 로컬 배열(더미데이터)이 들어온 경우 매핑 변환 적용
        treeDataConfig = _convertMapping(config.data, config.mapping);
      } else {
        // [CASE B] 서버 API 호출 방식인 경우
        treeDataConfig = {
          'url': config.url,
          'type': 'GET',
          'dataType': 'json',
          'data': node => {
            // 기본 파라미터(현재 노드 ID) 세팅
            const param = { 'id': node.id };

            // 동적/정적 파라미터 병합
            if (typeof config.dataParams === 'function') {
              return $.extend(param, config.dataParams(node));
            } else if (config.dataParams) {
              return $.extend(param, config.dataParams);
            }

            return param;
          },
          // 서버 응답 데이터를 jsTree 표준 규격({id, parent, text})으로 데이터 변환
          'dataFilter': rawResponse => {
            try {
              // JSON 문자열 파싱
              const jsonResponse = (typeof rawResponse === 'string') ? JSON.parse(rawResponse) : rawResponse;
              let responseList = [];

              // ApiResponse 객체 검증 및 data 배열 추출
              if (jsonResponse && typeof jsonResponse === 'object' && !Array.isArray(jsonResponse)) {
                if (!jsonResponse.success) {
                  console.error('[JsTree] API 실패 응답:', jsonResponse.code, jsonResponse.message);
                  return JSON.stringify([]);
                }

                // responseField('data') 추출
                responseList = jsonResponse[config.responseField] || [];
              } else if (Array.isArray(jsonResponse)) {
                responseList = jsonResponse;
              }

              // jsTree 규격 변환 ({id, parent, text})
              const formattedList = _convertMapping(responseList, config.mapping);

              // 로그 확인용
              // console.log('[JsTree] 변환 완료된 트리 데이터:', formattedList);

              return JSON.stringify(formattedList);
            } catch (e) {
              console.error('[JsTree] dataFilter 변환 중 에러 발생:', e);
              return JSON.stringify([]);
            }
          }
        };
      }

      // jsTree 초기화
      $target.jstree({
        'core': {
          'data': treeDataConfig,
          'check_callback': true,
          'themes': {
            'responsive': false,
            'dots': true,
            'icons': true
          }
        },
        'plugins': config.plugins
      });

      // 인스턴스 조회
      const treeInstance = $target.jstree(true);

      // 노드 선택 이벤트
      $target.off('changed.jstree').on('changed.jstree', (e, data) => {
        if (data && data.selected.length && typeof config.onSelect === 'function') {
          const selectedNode = data.instance.get_node(data.selected[0]);
          config.onSelect(selectedNode, data);
        }
      });

      // 로드 완료 이벤트
      $target.off('ready.jstree').on('ready.jstree', (e, data) => {
        if (config.autoOpenAll) {
          $target.jstree('open_all');
        }
        if (typeof config.onReady === 'function') {
          config.onReady(treeInstance, data);
        }
      });

      return treeInstance; // 생성된 jsTree 인스턴스 반환
    }
  };
})();