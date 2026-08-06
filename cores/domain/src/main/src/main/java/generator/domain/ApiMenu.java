package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* API 메뉴
* @TableName api_menu
*/
public class ApiMenu implements Serializable {

    /**
    * 일련번호
    */
    @NotNull(message="[일련번호]不能为空")
    @ApiModelProperty("일련번호")
    private Long uid;
    /**
    * 그룹 코드
    */
    @NotBlank(message="[그룹 코드]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("그룹 코드")
    @Length(max= 100,message="编码长度不能超过100")
    private String groupCode;
    /**
    * API 코드
    */
    @NotBlank(message="[API 코드]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("API 코드")
    @Length(max= 100,message="编码长度不能超过100")
    private String code;
    /**
    * API 이름
    */
    @NotBlank(message="[API 이름]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("API 이름")
    @Length(max= 255,message="编码长度不能超过255")
    private String name;
    /**
    * 표시 순서
    */
    @NotNull(message="[표시 순서]不能为空")
    @ApiModelProperty("표시 순서")
    private Integer displayOrdinal;
    /**
    * 사용여부 1: 사용 0, 0:사용 X
    */
    @NotNull(message="[사용여부 1: 사용 0, 0:사용 X]不能为空")
    @ApiModelProperty("사용여부 1: 사용 0, 0:사용 X")
    private String onService;
    /**
    * API 경로
    */
    @NotBlank(message="[API 경로]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("API 경로")
    @Length(max= 255,message="编码长度不能超过255")
    private String linkUrl;
    /**
    * 메모
    */
    @NotBlank(message="[메모]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("메모")
    @Length(max= 255,message="编码长度不能超过255")
    private String memo;
    /**
    * 등록일
    */
    @NotNull(message="[등록일]不能为空")
    @ApiModelProperty("등록일")
    private Date regDate;
    /**
    * 수정일
    */
    @NotNull(message="[수정일]不能为空")
    @ApiModelProperty("수정일")
    private Date updateDate;

    /**
    * 일련번호
    */
    private void setUid(Long uid){
    this.uid = uid;
    }

    /**
    * 그룹 코드
    */
    private void setGroupCode(String groupCode){
    this.groupCode = groupCode;
    }

    /**
    * API 코드
    */
    private void setCode(String code){
    this.code = code;
    }

    /**
    * API 이름
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 표시 순서
    */
    private void setDisplayOrdinal(Integer displayOrdinal){
    this.displayOrdinal = displayOrdinal;
    }

    /**
    * 사용여부 1: 사용 0, 0:사용 X
    */
    private void setOnService(String onService){
    this.onService = onService;
    }

    /**
    * API 경로
    */
    private void setLinkUrl(String linkUrl){
    this.linkUrl = linkUrl;
    }

    /**
    * 메모
    */
    private void setMemo(String memo){
    this.memo = memo;
    }

    /**
    * 등록일
    */
    private void setRegDate(Date regDate){
    this.regDate = regDate;
    }

    /**
    * 수정일
    */
    private void setUpdateDate(Date updateDate){
    this.updateDate = updateDate;
    }


    /**
    * 일련번호
    */
    private Long getUid(){
    return this.uid;
    }

    /**
    * 그룹 코드
    */
    private String getGroupCode(){
    return this.groupCode;
    }

    /**
    * API 코드
    */
    private String getCode(){
    return this.code;
    }

    /**
    * API 이름
    */
    private String getName(){
    return this.name;
    }

    /**
    * 표시 순서
    */
    private Integer getDisplayOrdinal(){
    return this.displayOrdinal;
    }

    /**
    * 사용여부 1: 사용 0, 0:사용 X
    */
    private String getOnService(){
    return this.onService;
    }

    /**
    * API 경로
    */
    private String getLinkUrl(){
    return this.linkUrl;
    }

    /**
    * 메모
    */
    private String getMemo(){
    return this.memo;
    }

    /**
    * 등록일
    */
    private Date getRegDate(){
    return this.regDate;
    }

    /**
    * 수정일
    */
    private Date getUpdateDate(){
    return this.updateDate;
    }

}
