package net.evecom.rd.ie.baseline.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: ResPropVerify
 * @Description: 资源属性校验对象 @author： zhengc @date： 2017年10月25日
 */
@Table(name = "DB_RESOURCE_PROP_VERIFY_T")
@ToString
public class ResPropVerify extends DataEntity<ResPropVerify> implements Serializable {


    @ApiModelProperty(value = "字段id")
    private Long propId;

    @ApiModelProperty(value = "验证规则")
    private String verifyRule;

    @ApiModelProperty(value = "错误提示")
    private String errorTips;

    @Column(name = "PROP_ID")
    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    @Column(name = "VERIFY_RULE")
    public String getVerifyRule() {
        return verifyRule;
    }

    public void setVerifyRule(String verifyRule) {
        this.verifyRule = verifyRule;
    }

    @Column(name = "error_tips")
    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

}
