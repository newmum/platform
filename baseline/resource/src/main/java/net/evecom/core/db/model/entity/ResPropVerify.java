package net.evecom.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: ResPropVerify
 * @Description: 资源属性校验对象 @author： zhengc @date： 2017年10月25日
 */
@Table(name = "DB_RESOURCE_PROP_VERIFY_T")
public class ResPropVerify extends DataEntity<ResPropVerify> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PROP_ID")
    @ApiModelProperty(value = "字段id")
    private Long propId;
    @Column(name = "VERIFY_RULE")
    @ApiModelProperty(value = "验证规则")
    private String verifyRule;
    @Column(name = "error_tips")
    @ApiModelProperty(value = "错误提示")
    private String errorTips;

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getVerifyRule() {
        return verifyRule;
    }

    public void setVerifyRule(String verifyRule) {
        this.verifyRule = verifyRule;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    @Override
    public String toString() {
        return "ResPropVerify [" +
                "resPropId=" + propId + "," +
                "verifyRule=" + verifyRule + "," +
                "errorTips=" + errorTips +
                "]" + "Address [" +
                getClass().getName() + "@" +
                Integer.toHexString(hashCode()) +
                "]";
    }

}
