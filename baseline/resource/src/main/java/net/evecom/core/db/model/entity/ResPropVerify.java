package net.evecom.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: ResPropVerify
 * @Description: 资源属性校验对象 @author： zhengc @date： 2017年10月25日
 */
@Table(name = "res_prop_verify")
public class ResPropVerify extends DataEntity<ResPropVerify> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "res_prop_id")
    @ApiModelProperty(value = "字段id")
    private Long resPropId;
    @Column(name = "verify_rule")
    @ApiModelProperty(value = "验证类型")
    private String verifyRule;
    @Column(name = "error_tips")
    @ApiModelProperty(value = "错误提示")
    private String errorTips;

    public Long getResPropId() {
        return resPropId;
    }

    public void setResPropId(Long resPropId) {
        this.resPropId = resPropId;
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
                "resPropId=" + resPropId + "," +
                "verifyRule=" + verifyRule + "," +
                "errorTips=" + errorTips +
                "]" + "Address [" +
                getClass().getName() + "@" +
                Integer.toHexString(hashCode()) +
                "]";
    }

}
