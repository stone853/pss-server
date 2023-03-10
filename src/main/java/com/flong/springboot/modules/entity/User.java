package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_user")
public class User extends Model<User> implements Serializable {

//    @TableId(type = IdType.ID_WORKER)
//    private Long userId;

    private Long id;

    private String userId;

    private String mobile;

    @Length(max = 30,message = "姓名长度不能大于30")
    private String name;

    private String password;

    private Byte isDelete;

    @Length(max = 300,message = "姓名长度不能大于300")
    private String remark;

    private String deptCode;

    private String userType;



    @TableField(exist = false)
    private String roleCodes;

    public User setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public User setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public User setDeptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password == null ? null : password.trim();
        return this;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public User setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
        return this;
    }
}
