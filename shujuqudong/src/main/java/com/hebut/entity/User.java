package com.hebut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.io.Serializable;

/**
 * (User)实体类
 */
public class User implements Serializable {
    private static final long serialVersionUID = -75901818015106145L;
    //用户ID，初始值100000
    private Integer uid;
    //微信openID
    private String wxopenid;
    //QQopenID
    private String qqopenid;
    //电话
    private String phone;
    //邮箱
    private String email;
    //昵称
    private String name;
    //性别，0-女，1-男
    private Integer sex;
    //头像，文件路径
    private String headimg;
    //个人简历
    private String profile;
    //姓名
    private String realname;
    //地址
    private String address;
    //身份证
    private String idcard;
    //注册时间
    private Date registertime;
    //登陆密码
    private String password;
    //角色，0-普通用户，1-学生，2-老师
    private Integer role;
    //令牌，token
    private String token;
    private Integer sum;

	public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public String getQqopenid() {
        return qqopenid;
    }

    public void setQqopenid(String qqopenid) {
        this.qqopenid = qqopenid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public User(Integer uid, String wxopenid, String qqopenid, String phone, String email, String name, Integer sex,
			String headimg, String profile, String realname, String address, String idcard, Date registertime,
			String password, Integer role, String token) {
		super();
		this.uid = uid;
		this.wxopenid = wxopenid;
		this.qqopenid = qqopenid;
		this.phone = phone;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.headimg = headimg;
		this.profile = profile;
		this.realname = realname;
		this.address = address;
		this.idcard = idcard;
		this.registertime = registertime;
		this.password = password;
		this.role = role;
		this.token = token;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", wxopenid=" + wxopenid + ", qqopenid=" + qqopenid + ", phone=" + phone
				+ ", email=" + email + ", name=" + name + ", sex=" + sex + ", headimg=" + headimg + ", profile="
				+ profile + ", realname=" + realname + ", address=" + address + ", idcard=" + idcard + ", registertime="
				+ registertime + ", password=" + password + ", role=" + role + ", token=" + token + "]";
	}

	public User() {
		super();
	}

}
