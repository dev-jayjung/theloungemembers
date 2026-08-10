Tabulator.extendModule('format', 'formatters', {
    rowNum: function(cell, formatterParams, onRendered) {
        const table = cell.getTable();
        const page = table.getPage() || 1;
        const size = table.getPageSize() || 15;
        const index = cell.getRow().getPosition(true) - 1;
        const total = table.totalElements || 0;
        const rowNum = total - (((page - 1) * size) + index);

        return rowNum > 0 ? rowNum : '';
    }
});

const COMMON_COLUMNS = Object.freeze({
    ROW_NUM: { title: '순서', field: 'rowNum', hozAlign: 'center', formatter: 'rowNum', download: false, headerSort: false },
    CREATED_AT: { title: '등록일', field: 'regDate', hozAlign: 'center' },
    UPDATED_AT: { title: '수정일', field: 'updateDate', hozAlign: 'center' }
});

/**
 * 프로젝트 공통 Tabulator 그리드 생성 함수
 * @param {string|HTMLElement} elementSelector - 그리드가 그려질 DOM 셀렉터 (예: "#apiMemberList")
 * @param {Object} customOptions - 화면별 개별 옵션 (url, columns, params 등)
 */
function initTabulatorGrid(elementSelector, customOptions) {
    let totalElements = 0;

    const prefixColumns = [];
    const suffixColumns = [];

    // 옵션 기본값: useRowNum은 기본 true, 나머지는 필요시 화면에서 true로 켬
    const useRowNum = customOptions?.useRowNum !== false; 
    const useCreatedAt = customOptions?.useCreatedAt === true;
    const useUpdatedAt = customOptions?.useUpdatedAt === true;

    // 맨 앞에 붙을 공통 컬럼
    if (useRowNum) {
        prefixColumns.push(COMMON_COLUMNS.ROW_NUM)
    };

    // 맨 뒤에 붙을 공통 컬럼
    if (useCreatedAt) {
        suffixColumns.push(COMMON_COLUMNS.CREATED_AT);
    }

    if (useUpdatedAt) {
        suffixColumns.push(COMMON_COLUMNS.UPDATED_AT);
    }

    // 컬럼 합치기
    const userColumns = customOptions?.columns || [];
    customOptions.columns = [...prefixColumns, ...userColumns, ...suffixColumns];

    // 공통 기본 옵션 정의
    const defaultOptions = {
        height: '600px',
        layout: 'fitDataFill',
        elementAttributes: {
            class: 'table table-striped table-bordered table-sm' // 부트스트랩 클래스 적용
        },
        columnDefaults: {
            resizable: true,
            headerHozAlign: 'center',
            headerMenu: headerMenuValue,
            accessorDownload: function(value, data, type, params, column) {
                // 값이 없거나 null/undefined 인 경우
                if (value === null || value === undefined) {
                    return '';
                }

                const colDef = column.getDefinition();

                // formatter가 사용자 정의 함수가 아니면 기존 원본 값 반환
                if (typeof colDef.formatter != 'function') {
                    return value;
                }

                // 가상 셀 및 함수
                const mockCell = {
                    getValue: () => value,
                    getData: () => data,
                    getRow: () => ({ getData: () => data })
                };

                // 사용자 정의 formatter 실행
                const resultValue = colDef.formatter(mockCell) || value;

                // 리턴된 값/HTML이 문자열인 경우 HTML 태그 및 개행 정제
                if (typeof resultValue === 'string') {
                    return resultValue
                        .replace(/<br\s*\/?>/gi, '\n')       // <br> -> 줄바꿈(\n)
                        .replace(/<[^>]*>?/gm, '')           // 모든 HTML 태그 제거 (<p>, <span> 등)
                        .replace(/&nbsp;/g, ' ')             // &nbsp; -> 공백
                        .trim();
                }

                // 사용자 정의 formatter 적용 및 정제 완료 값 반환
                return resultValue;
            }
        },
        pagination: true,
        paginationMode: 'remote', // 서버 사이드 페이징 기본
        paginationSize: 15,       // PageRequest 기본 size(15)
        paginationCounter: function(pageSize, currentRow, currentPage, totalRows, totalPages) {
            return `전체 ${this.table.totalElements.toLocaleString()}건`;
        },

        sortMode: 'remote',
        initialSort: [
            { column: 'uid', dir: 'desc' }
        ],

        locale: 'ko-kr',
        langs: {
            'ko-kr': {
                'pagination': {
                    'first': '<<',        // 또는 '«'
                    'first_title': '첫 페이지',
                    'last': '>>',      // 또는 '»'
                    'last_title': '마지막 페이지',
                    'prev': '<',        // 또는 '‹'
                    'prev_title': '이전 페이지',
                    'next': '>',        // 또는 '›'
                    'next_title': '다음 페이지'
                }
            }
        },

        ajaxConfig: 'GET',
        ajaxContentType: 'json',
        placeholder: '조회된 데이터가 없습니다.',

        ajaxURLGenerator: function(url, config, params) {
            let queryParams = new URLSearchParams();

            delete params.sort;

            Object.keys(params).forEach(key => {
                if (key === 'page') {
                    // Tabulator가 1로 보낸 page 값을 Spring용 0으로 변환 (1 -> 0, 2 -> 1)
                    let pageNum = parseInt(params[key], 10);
                    queryParams.append('page', pageNum ? Math.max(pageNum - 1, 0) : 0);
                } else {
                    // 나머지 size, sortBy, direction, searchKeyword 등은 그대로 전달
                    if (params[key] !== undefined && params[key] !== null) {
                        queryParams.append(key, params[key]);
                    }
                }
            });

            return url + '?' + queryParams.toString();
        },

        // [공통 처리 2] 백엔드 ApiResponse<PageResponse<T>> -> Tabulator 규격 변환
        ajaxResponse: function(url, params, response) {
            if (response && response.success && response.data) {
                const pageData = response.data;

                this.totalElements = pageData.totalElements || 0;

                return {
                    last_page: pageData.totalPages || 1,
                    data: pageData.content || []
                };
            }

            this.totalElements = 0;
            return { last_page: 1, data: [] };
        },
//        renderComplete: function() {
//            const total = this.tableDataTotal || 0;
//            const counterEl = this.element.querySelector(".tabulator-page-counter");
//            if (counterEl) {
//                counterEl.textContent = `전체 ${total.toLocaleString()}건`;
//            }
//        }
//        // 데이터 로드가 끝난 후 하단 카운터 & 셀 포맷터 강제 갱신
//        dataLoaded: function(data) {
//            const total = this.tableDataTotal || 0;
//            
//            // 1) 하단 카운터 엘리먼트 텍스트 직접 변경
//            const counterEl = this.element.querySelector(".tabulator-page-counter");
//            if (counterEl) {
//                counterEl.textContent = `전체 ${total.toLocaleString()}건`;
//            }
//
//            // 2) 행 번호(rowNumDesc) 컬럼 재렌더링
//            this.redraw(true);
//        }
    };

    // 화면별 추가 파라미터(ajaxParams)가 전달된 경우 병합 처리
    const userAjaxParams = customOptions.ajaxParams;
    if (userAjaxParams) {
        customOptions.ajaxParams = function() {
            // Tabulator의 기본 정렬(Sorters) 추출
            const sorters = this.getSorters();
            let sorterParams = {};
            if (sorters && sorters.length > 0) {
                sorterParams.sortBy = sorters[0].field;
                sorterParams.direction = sorters[0].dir.toUpperCase();
            }

            // 개별 화면에서 전달한 파라미터 함수/객체 실행
            const extraParams = typeof userAjaxParams === 'function' ? userAjaxParams.call(this) : userAjaxParams;

            return Object.assign({}, sorterParams, extraParams);
        };
    }

    // 기본 옵션과 화면별 customOptions 덮어쓰기/병합
    const finalOptions = Object.assign({}, defaultOptions, customOptions);

    // Tabulator 인스턴스 생성 및 반환
    const e = new Tabulator(elementSelector, finalOptions);
    e.totalElements = totalElements;

    return e;
}

function headerMenuValue() {
    const menu = [];
    const columns = this.getColumns();

    for (let column of columns) {
        let field = column.getField();
        let def = column.getDefinition();

        // 순서 컬럼 제외
        if (def.formatter === 'rowNum') {
            continue;
        }

        let label = document.createElement('span');
        label.style.cursor = 'pointer';

        // 특수문자(☑, ☐)로 체크 상태 표기
        const updateLabel = (visible) => {
            const checkIcon = visible ? '☑' : '☐';
            const iconColor = visible ? '#0d6efd' : '#999999';
            const titleText = def.title || field;

            label.innerHTML = `<span style="color: ${iconColor}; font-weight: bold; margin-right: 6px;">${checkIcon}</span><span>${titleText}</span>`;
        };

        // 초기 상태 렌더링
        updateLabel(column.isVisible());

        menu.push({
            label: label,
            action: function(e) {
                e.stopPropagation(); // 팝업 닫힘 방지

                // 컬럼 보이기/숨기기 토글
                column.toggle();

                // 텍스트/체크 표시 즉시 갱신
                updateLabel(column.isVisible());
            }
        });
    }

    menu.push({ separator: true });

    let closeLabel = document.createElement('span');
    closeLabel.innerHTML = '<span style="color: #dc3545; font-weight: bold; margin-right: 6px;">✕</span><b style="color: #dc3545;">닫기</b>';
    closeLabel.style.cursor = 'pointer';

    menu.push({
        label: closeLabel,
        action: function(e) {}
    });

    return menu;
}

const SERVICE_STATUS = Object.freeze({
    IN_SERVICE: { code: '1', label: 'O', text: '사용' },
    STOPPED:    { code: '0', label: '<p style="color:red;">X</p>', excelLabel: 'X', text: '미사용' }
});

// Tabulator 전용 헬퍼 함수
const ServiceStatusUtil = {
    // 화면용 Formatter
    formatter: function(cell) {
        return cell.getValue() === SERVICE_STATUS.IN_SERVICE.code
            ? SERVICE_STATUS.IN_SERVICE.label
            : SERVICE_STATUS.STOPPED.label;
    },

    // 엑셀 다운로드용 Accessor
    accessor: function(value) {
        return value === SERVICE_STATUS.IN_SERVICE.code
            ? SERVICE_STATUS.IN_SERVICE.label
            : SERVICE_STATUS.STOPPED.excelLabel;
    }
};