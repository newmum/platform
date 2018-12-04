package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * @ClassName: UserExtra
 * @Description: 用户详细信息实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "RM_USER_EXTRA_T")
public class UserExtra extends DataEntity<UserExtra> {

	@Column(name = "USER_ID")
	@ApiModelProperty(value = "用户id", hidden = true)
	private Long crmUserId;
	@Column(name = "USER_NAME")
	@ApiModelProperty(value = "姓名")
	private String name;
	@Column(name = "USER_IMG")
	@ApiModelProperty(value = "头像")
	private String userImg;
	@Column(name = "QQ")
	@ApiModelProperty(value = "qq")
	private String qq;
	@Column(name = "ADDRESS")
	@ApiModelProperty(value = "地址")
	private String address;
	@Column(name = "SEX")
	@ApiModelProperty(value = "性别")
	private int sex;
	@Column(name = "BIRTHDAY")
	@ApiModelProperty(value = "生日")
	private String birthday;
	@Column(name = "REMARK")
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
		return "UserExtra [" + "crmUserId=" + crmUserId + "," + "name=" + name + "," + "userImg=" + userImg + ","
				+ "qq=" + qq + "," + "address=" + address + "," + "sex=" + sex + "," + "birthday=" + birthday + ","
				+ "remarks=" + remarks + "]" + "Address [" + getClass().getName() + "@"
				+ Integer.toHexString(hashCode()) + "]";
	}

}
