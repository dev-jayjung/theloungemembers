const GridUtil = (() => {
  // -----------------------------------------------------------------
  // 1. Private 변수 & 공통 컬럼 정의 (외부 노출 X)
  // -----------------------------------------------------------------
  const COMMON_COLUMNS = Object.freeze({
    ROW_NUM: { title: '순서', field: 'rowNum', hozAlign: 'center', formatter: 'rowNum', download: false, headerSort: false },
    CREATED_AT: { title: '등록일', field: 'regDate', hozAlign: 'center' },
    UPDATED_AT: { title: '수정일', field: 'updateDate', hozAlign: 'center' }
  });

  // Custom Formatter 등록 (순번 계산)
  if (typeof Tabulator !== 'undefined') {
    Tabulator.extendModule('format', 'formatters', {
      rowNum: cell => {
        const table = cell.getTable();
        const page = table.getPage() || 1;
        const size = table.getPageSize() || 15;
        const index = cell.getRow().getPosition(true) - 1;
        const total = table.totalElements || 0;
        const rowNum = total - (((page - 1) * size) + index);

        return rowNum > 0 ? rowNum : '';
      }
    });
  }

  // Header Menu 생성 헬퍼
  const buildHeaderMenu = function () {
    const menu = [];
    const columns = this.getColumns();

    for (let column of columns) {
      let field = column.getField();
      let def = column.getDefinition();

      if (def.formatter === 'rowNum') {
        continue;
      }

      let label = document.createElement('span');
      label.style.cursor = 'pointer';

      const updateLabel = visible => {
        const checkIcon = visible ? '☑' : '☐';
        const iconColor = visible ? '#0d6efd' : '#999999';
        const titleText = def.title || field;
        label.innerHTML = `<span style="color: ${iconColor}; font-weight: bold; margin-right: 6px;">${checkIcon}</span><span>${titleText}</span>`;
      };

      updateLabel(column.isVisible());

      menu.push({
        label: label,
        action: e => {
          e.stopPropagation();
          column.toggle();
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
      action: e => { }
    });

    return menu;
  }

  return {
    /**
      * 프로젝트 공통 Tabulator 그리드 생성
      */
    init(elementSelector, customOptions) {
      let totalElements = 0;

      const prefixColumns = [];
      const suffixColumns = [];

      const useRowNum = customOptions?.useRowNum !== false;
      const useCreatedAt = customOptions?.useCreatedAt === true;
      const useUpdatedAt = customOptions?.useUpdatedAt === true;

      if (useRowNum) {
        prefixColumns.push(COMMON_COLUMNS.ROW_NUM);
      }

      if (useCreatedAt) {
        suffixColumns.push(COMMON_COLUMNS.CREATED_AT);
      }

      if (useUpdatedAt) {
        suffixColumns.push(COMMON_COLUMNS.UPDATED_AT);
      }

      const userColumns = customOptions?.columns || [];
      customOptions.columns = [...prefixColumns, ...userColumns, ...suffixColumns];

      const defaultOptions = {
        height: '600px',
        layout: 'fitDataFill',
        elementAttributes: {
          class: 'table table-striped table-bordered table-sm'
        },
        columnDefaults: {
          resizable: true,
          headerHozAlign: 'center',
          headerMenu: buildHeaderMenu,
          accessorDownload: (value, data, type, params, column) => {
            if (value === null || value === undefined) {
              return '';
            }

            const colDef = column.getDefinition();

            if (typeof colDef.formatter != 'function') {
              return value;
            }

            const mockCell = {
              getValue: () => value,
              getData: () => data,
              getRow: () => ({ getData: () => data })
            };

            const resultValue = colDef.formatter(mockCell) || value;

            if (typeof resultValue === 'string') {
              return resultValue
                .replace(/<br\s*\/?>/gi, '\n')
                .replace(/<[^>]*>?/gm, '')
                .replace(/&nbsp;/g, ' ')
                .trim();
            }

            return resultValue;
          }
        },
        pagination: true,
        paginationMode: 'remote',
        paginationSize: 15,
        paginationCounter() {
          return `전체 ${(this.table.totalElements || 0).toLocaleString()}건`;
        },
        sortMode: 'remote',
        initialSort: [{ column: 'uid', dir: 'desc' }],
        locale: 'ko-kr',
        langs: {
          'ko-kr': {
            'pagination': {
              'first': '<<',
              'first_title': '첫 페이지',
              'last': '>>',
              'last_title': '마지막 페이지',
              'prev': '<',
              'prev_title': '이전 페이지',
              'next': '>',
              'next_title': '다음 페이지'
            }
          }
        },
        ajaxConfig: 'GET',
        ajaxContentType: 'json',
        placeholder: '조회된 데이터가 없습니다.',
        ajaxRequestFunc: (url, config, params) => {
          const processedParams = Object.assign({}, params);
          delete processedParams.sort;

          if (processedParams.page !== undefined && processedParams.page !== null) {
            let pageNum = parseInt(processedParams.page, 10);
            processedParams.page = pageNum ? Math.max(pageNum - 1, 0) : 0;
          }

          Object.keys(processedParams).forEach(key => {
            if (processedParams[key] === null || processedParams[key] === undefined) {
              delete processedParams[key];
            }
          });

          return new Promise((resolve, reject) => {
            AjaxUtil.call({
              url,
              type: config.method || 'GET',
              data: processedParams,
              callBack: data => resolve(data),
              fail: xhr => reject(xhr)
            });
          });
        },

        ajaxResponse: function (url, params, response) {
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
        }
      };

      // ajaxParams 병합
      const userAjaxParams = customOptions.ajaxParams;
      if (userAjaxParams) {
        customOptions.ajaxParams = function () {
          const sorters = this.getSorters();
          let sorterParams = {};
          if (sorters && sorters.length > 0) {
            sorterParams.sortBy = sorters[0].field;
            sorterParams.direction = sorters[0].dir.toUpperCase();
          }
          const extraParams = typeof userAjaxParams === 'function' ? userAjaxParams.call(this) : userAjaxParams;
          return Object.assign({}, sorterParams, extraParams);
        };
      }

      const finalOptions = Object.assign({}, defaultOptions, customOptions);
      const gridInstance = new Tabulator(elementSelector, finalOptions);
      gridInstance.totalElements = totalElements;

      // 페이지 사이즈 select 자동 바인딩 (기본 셀렉터: #pageSize)
      const targetSelector = customOptions?.pageSizeSelector || '#pageSize';
      if ($(targetSelector).length > 0) {
        $(targetSelector).on('change', e => {
          const $this = $(e.currentTarget);
          const newSize = parseInt($this.val(), 10);
          if (!isNaN(newSize) && newSize > 0) {
            gridInstance.setPageSize(newSize);
          }
        });
      }

      return gridInstance;
    },

    /**
      * 서비스 상태 등 자주 쓰는 Formatter/Accessor 헬퍼 모음
      */
    ServiceStatus: {
      formatter: cell => cell.getValue() === '1' ? 'O' : '<p style="color:red;">X</p>',
      accessor: value => value === '1' ? 'O' : 'X'
    }
  };
})();