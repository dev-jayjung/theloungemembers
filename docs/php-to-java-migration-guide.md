# PHP → Java 관리자 화면 마이그레이션 가이드

`the-lounge-php` (레거시 PHP 관리자) 화면을 `theloungemembers` (Java/Spring) 관리자 화면으로 옮길 때
참고할 절차와 규칙. `API 이용 로그 조회` 화면(`/api-usage-logs`) 마이그레이션 작업에서 정리함.

## 0. 시작 전 확인할 것

1. PHP 소스 파일 경로 확인 (예: `the-lounge-php/dev.theloungemembers.com/admin_work/api/xxx_list.html`)
2. Java 쪽에 이미 만들어진 파츠가 있는지 먼저 검색 (엔티티/매퍼/서비스/REST 컨트롤러가 이전 커밋에서
   먼저 만들어져 있는 경우가 많음). 아래 순서로 `find`/`grep`:
   - `cores/domain/.../entity/*Entity.java` (JPA 엔티티)
   - `cores/domain/.../mapper/*Mapper.java`, `.xml` (MyBatis)
   - `cores/model/.../*Query.java`, `*Result.java`, `*Command.java`
   - `cores/admin/.../*Service.java`
   - `webs/admin/.../dto/*Request.java`, `*Response.java`
   - `webs/admin/.../controller/*RestController.java`, `*ViewController.java`
3. 가장 비슷한 기존 화면(도메인이 유사하거나 CRUD 패턴이 같은 화면)을 하나 골라서 그대로 참고.
   이번 작업에서는 `api/accessible_ip_list.html` + `ApiAccessibleIpViewController` +
   `ApiMenuViewController`(그룹 셀렉트박스 패턴), `member_list.html`(계정 표시 방식)을 참고함.

## 1. 레이어 구조 (하나의 목록 화면 = 아래 7개 파츠)

| 레이어 | 위치 | 역할 |
|---|---|---|
| JPA Entity | `cores/domain/.../entity/XxxEntity.java` | 테이블 매핑 (등록/수정용, 조회는 MyBatis 사용) |
| MyBatis Mapper 인터페이스 | `cores/domain/.../mapper/XxxMapper.java` | `BaseMapper<Query, Result, Id>` 상속 |
| MyBatis Mapper XML | `cores/domain/src/main/resources/mapper/.../XxxMapper.xml` | `selectList`/`selectCount` + `<sql id="where">` |
| Query/Result/Command | `cores/model/.../Xxx{Query,Result,Command}.java` | Query는 `PageRequest` 상속 |
| Service | `cores/admin/.../XxxService.java` | `AbstractBaseService<Command, Query, Result, Id>` 상속 |
| REST Controller | `webs/admin/.../controller/XxxRestController.java` | `/api/xxx-list` 형태, `ApiResponse<PageResponse<T>>` 반환 |
| View Controller + Template | `webs/admin/.../controller/XxxViewController.java` + `templates/views/xxx/xxx_list.html` | HTML 뼈대 + 검색조건 select 옵션 데이터 |

이번 작업 시점에는 1~6번(Entity/Mapper/Query/Result/Command/Service/RestController)이 이미
구현되어 있었고, View Controller와 Thymeleaf 템플릿만 비어 있었음 — **항상 어디까지 되어 있는지
먼저 확인**.

## 2. PHP → Java 매핑 규칙

### 검색조건 (`select_search_target`, `where_clause`)
- PHP의 `$select_search_target` 배열 → Mapper XML의 `<sql id="where">`의
  `<choose><when test="searchTarget == '...'">` 분기와 1:1 대응.
- 자유 텍스트 검색(같은 필드를 여러 조건으로 검색)은 `searchTarget`/`searchValue` 콤보 하나로 처리.
- 콤보박스(select)로 고정된 값 검색(계정, 카테고리 등)은 `Query`의 별도 필드
  (`accountId`, `apiCode` 등)로 처리 — `where`에서 `<if test="field != null and field != ''">`.
- **두 방식이 같은 컬럼에 대해 동시에 존재할 수도 있음** (예: `api_code`는 searchTarget 검색과
  전용 select 필터가 둘 다 있었음). PHP 원본이 그렇게 되어 있으면 그대로 유지.

### 날짜 범위 검색 (`arr_search_date`, `period_clause`)
- `PageRequest`에 이미 `startDtm`/`endDtm` (`LocalDateTime`) 필드가 있음 — 화면별 Query/Request
  DTO에 별도로 선언할 필요 없음.
- **주의**: 프로젝트 공통 `serializeJson()` (`custom.js`)은
  `input[type=hidden|text|password|checkbox|radio], textarea, select`만 수집하고
  `input[type="date"]`는 수집하지 않음. 따라서 date input은 `name` 속성을 주지 말고
  `id`만 부여한 뒤, `ajaxParams` 함수 안에서 직접 `startDtm`/`endDtm`을 계산해서 넣어야 함:
  ```js
  ajaxParams: function() {
      const params = $('#searchForm').serializeJson();
      const startDate = $('#searchStartDate').val();
      const endDate = $('#searchEndDate').val();
      if (startDate) params.startDtm = startDate + 'T00:00:00';
      if (endDate)   params.endDtm   = endDate + 'T23:59:59';
      return params;
  }
  ```
- Spring Boot는 `LocalDateTime` 파라미터를 별도 `@DateTimeFormat` 없이도 기본
  `ISO_LOCAL_DATE_TIME`(`yyyy-MM-ddTHH:mm:ss`) 포맷으로 바인딩하므로 위 포맷 그대로 보내면 됨.

### 콤보박스 데이터 소스 (계정목록/카테고리목록 등)
- PHP의 `AdminModule::get_revised_arr(...Get_name_arr(...))` 같은 코드로 만든 select 옵션은
  Java에서는 **ViewController의 `GetMapping`에서 관련 Service의 `getList(new XxxQuery())`를 호출**해
  `model.addAttribute`로 넘기고, 템플릿에서 `th:each`로 렌더링.
- 재사용 가능한 단순 select는 `fragments/tag.html :: select(name=, target=, valueKey=, textKey=)`
  프래그먼트 사용 (기본 `valueKey='code'`, `textKey='description'`).
- "이름(코드)" 같이 값 두 개를 합쳐 보여줘야 하면(PHP의 `$arr[1] = $arr[1]."(".$arr[0].")"` 패턴)
  프래그먼트로는 표현이 안 되므로 템플릿에 직접 `<select>`를 쓰고 Thymeleaf 리터럴 치환 문법
  (`th:text="|${item.name}(${item.code})|"`)으로 조합.
- **드롭다운용 목록을 조회할 때는 `Query`에 `onService = ServiceStatus.IN_SERVICE`를 반드시
  설정**해서 사용 중인 항목만 옵션으로 노출할 것 (`ApiMemberViewController`가
  `ApiMenuQuery.setOnService(ServiceStatus.IN_SERVICE)`로 API 메뉴 목록을 채우는 것과 동일한 패턴).
  `new XxxQuery()`만 넘기고 끝내면 중지된 계정/메뉴까지 검색 옵션에 섞여 나오므로 빠뜨리기 쉬운
  부분 — 리뷰 시 꼭 확인.

  ```java
  final ApiMemberQuery memberQuery = new ApiMemberQuery();
  memberQuery.setOnService(ServiceStatus.IN_SERVICE);
  final List<ApiMemberResult> memberList = apiMemberService.getList(memberQuery);
  ```

### 정렬 (`select_order_by_info`)
- `PageRequest.getOrderBy()`가 camelCase → snake_case 자동 변환 + `sortBy`/`direction` 처리.
- Tabulator 그리드가 헤더 클릭 정렬 시 자동으로 `sortBy`/`direction`을 보냄
  (`tabulator_custom.js`의 `ajaxParams` 공통 로직). 화면별로 추가 코드 불필요.
- 기본 정렬은 `initialSort: [{ column: 'uid', dir: 'desc' }]`가 공통 기본값이라
  PHP의 `default_column=uid, default_sort=DESC`와 대개 일치함 — 다르면 화면별로 override.

### 목록 렌더링 (`echo("<tr>...")` 반복문)
- 전부 **Tabulator 그리드**로 대체 (`initTabulatorGrid(selector, options)` 공통 헬퍼,
  `webs/admin/.../static/js/tabulator/tabulator_custom.js`).
- 컬럼은 REST 응답(`XxxResponse`) 필드명(camelCase)과 `field`가 일치해야 함.
- 성공/실패, 사용/미사용 같은 2값 컬럼은 `formatter` 함수로 텍스트/색상 처리
  (`cell.getValue() === 'S' ? '성공' : '<p style="color:red;">실패</p>'` 형태).
- 등록일/수정일 컬럼은 직접 선언하지 말고 `useCreatedAt: true` / `useUpdatedAt: true` 옵션 사용
  (내부적으로 `regDate`/`updateDate` 필드로 매핑된 공통 컬럼을 자동으로 붙여줌).
- 행 번호는 기본 `useRowNum`(true)로 자동 처리됨.

### 등록/수정/상세 이동
- PHP의 `move_to_xxx` 링크 이동 → `createTargetFormSubmit({ id: 'xxxView', url: '/xxx/detail?id=...' })`
  공통 함수 사용. 목록이 읽기 전용(로그 조회 등)이면 등록 버튼(`등록하기`) 자체를 생략해도 됨
  (PHP 원본에 등록 UI가 없으면 굳이 만들지 않음).
- PHP에서 다른 화면으로 넘어가는 링크(예: 에러로그 상세로 이동)가 있는데 **대상 화면이 아직
  Java 쪽에 마이그레이션되지 않았다면, 해당 기능은 생략**하고 나중에 그 화면을 만들 때 같이 연결.
  (이번 작업의 `move_to_error_log` 링크가 해당 사례 — API 에러 로그 화면이 아직 없어서 제외함)

## 3. 템플릿 뼈대 (list 화면 공통)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layouts/default_layout}">
<head><title>...</title></head>

<th:block layout:fragment="script">
  <script th:inline="javascript">
    let xxxGrid;
    $(document).ready(function() {
        createXxxGrid();
        // searchTarget 변경 시 searchValue enable/disable
        $('#searchForm').on('change', '#searchTarget', function () { ... });
    });
    function createXxxGrid() {
        xxxGrid = initTabulatorGrid('#xxxList', {
            ajaxURL: '/api/xxx',
            ajaxParams: function() { return $('#searchForm').serializeJson(); },
            useCreatedAt: true,
            columns: [ ... ]
        });
    }
    function reloadGrid() { ... }         // 검색 버튼
    function resetSearchForm() { resetForm('searchForm'); }
    function downloadExcel() { xxxGrid.download('xlsx', '파일명.xlsx'); }
  </script>
</th:block>

<body>
  <div layout:fragment="content">
    <div class="table_type1">
      <form id="searchForm" name="searchForm" method="get"> ... </form>
    </div>
    <div class="btn_area_center">
      <button type="button" onclick="reloadGrid();" class="btn_type2">검색</button>
      <button type="button" onclick="resetSearchForm();" class="btn_type1">초기화</button>
    </div>
    <div class="mTitle gTitle"><h2>제목</h2></div>
    <div class="mButton typeBorad">
      <div class="leftInner">
        <button type="button" onclick="submitAddForm();" class="btn_h25_type1">등록하기</button> <!-- 읽기전용이면 생략 -->
        <button type="button" onclick="downloadExcel();" class="btn_type1"><span class="icoExl">엑셀 다운로드</span></button>
      </div>
    </div>
    <div class="mModule"><div id="xxxList"></div></div>
  </div>
</body>
</html>
```

## 4. 체크리스트

- [ ] Java 쪽에 이미 있는 파츠 먼저 확인 (Entity/Mapper/Query/Result/Command/Service/RestController)
- [ ] Mapper XML `where` 절과 PHP `select_search_target`/select 필터가 1:1로 대응하는지 확인
- [ ] ViewController에서 검색조건용 select 데이터(계정/카테고리 목록 등) 채워서 model에 추가
      (이때 `Query.onService = ServiceStatus.IN_SERVICE` 필터 빠뜨리지 않았는지 확인)
- [ ] 템플릿: 검색폼 + Tabulator 그리드 + (필요시) 날짜범위 검색 JS 변환 로직
- [ ] 날짜 범위 검색을 쓰면 `type="date"` input은 `name` 없이 `id`만 부여했는지 확인
- [ ] 등록/수정 UI가 필요 없는 읽기 전용 화면인지 PHP 원본과 대조
- [ ] 아직 마이그레이션 안 된 화면으로의 링크는 생략(주석 남기지 말고 그냥 제외)
- [ ] `./gradlew :web-admin:compileJava` 로 컴파일 확인
- [ ] 가능하면 브라우저로 실제 화면 열어서 검색/페이징/엑셀다운로드 동작 확인
      (DB 연결이 필요해 로컬에서 직접 확인 못하면, 사용자에게 수동 확인 요청)

## 참고: 이번에 완성한 화면

- PHP: `the-lounge-php/dev.theloungemembers.com/admin_work/api/api_usage_log_list.html`
- Java: `/api-usage-logs` →
  `webs/admin/.../controller/ApiUsageLogViewController.java` +
  `webs/admin/.../templates/views/api/usage_log_list.html`
  (REST 쪽은 이전 커밋에서 이미 구현되어 있었음: `ApiUsageLogRestController`,
  `ApiUsageLogMapper.xml`, `ApiUsageLogService`)
