package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
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
@ToString
public class UserExtra extends DataEntity<UserExtra> {

	@ApiModelProperty(value = "用户id", hidden = true)
	private String userId;

	@ApiModelProperty(value = "姓名")
	private String userName;

	@ApiModelProperty(value = "头像")
	private String userImg;

	@ApiModelProperty(value = "qq")
	private String qq;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "性别")
	private int sex;

	@ApiModelProperty(value = "生日")
	private String birthday;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_IMG")
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "SEX")
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column(name = "BIRTHDAY")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "REMARK")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
