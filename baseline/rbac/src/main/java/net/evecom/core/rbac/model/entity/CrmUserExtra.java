package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "crm_user_extra")
public class CrmUserExtra extends DataEntity<CrmUserExtra> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "crm_user_id")
	@ApiModelProperty(value = "用户id", hidden = true)
	private Long crmUserId;
	@Column(name = "name")
	@ApiModelProperty(value = "姓名")
	private String name;
	@Column(name = "user_img")
	@ApiModelProperty(value = "头像")
	private String userImg;
	@Column(name = "qq")
	@ApiModelProperty(value = "qq")
	private String qq;
	@Column(name = "address")
	@ApiModelProperty(value = "地址")
	private String address;
	@Column(name = "sex")
	@ApiModelProperty(value = "性别")
	private int sex;
	@Column(name = "birthday")
	@ApiModelProperty(value = "生日")
	private String birthday;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "CrmUserExtra [" + "crmUserId=" + crmUserId + "," + "name=" + name + "," + "userImg=" + userImg + ","
				+ "qq=" + qq + "," + "address=" + address + "," + "sex=" + sex + "," + "birthday=" + birthday + ","
				+ "remarks=" + remarks + "]" + "Address [" + getClass().getName() + "@"
				+ Integer.toHexString(hashCode()) + "]";
	}

}
