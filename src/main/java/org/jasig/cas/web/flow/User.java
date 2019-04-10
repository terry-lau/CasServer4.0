package org.jasig.cas.web.flow;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * 
 * @author Terry
 * 
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8253480111876133990L;

	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_LOCKED = "2";

	public static final String USER_TYPE_SUPER = "1";// 系统超级管理员
	public static final String USER_TYPE_NOMAL = "2";// 普通用户
	public static final String USER_TYPE_INTER = "3";// 接口用户
	protected Long id;
	protected String isRemoved = "0";//是否删除0：使用1：删除
	protected Integer version = 0;
	protected Date createDate;//创建时间
	protected String createBy;//创建人
	protected Date modifyDate;//修改时间
	protected String modifyBy;//修改人
	// 登录用户访问Token
	private String token;
	private String userCode;// 用户编码
	private String userName;// 用户名称
	private String loginName;// 登陆名称
	private String platformNo;// 平台编码
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
}
