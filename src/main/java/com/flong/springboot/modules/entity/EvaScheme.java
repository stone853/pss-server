package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_eva_scheme")
public class EvaScheme {
    private Integer id;

    private String code;

    private String name;

    private String updUser;

    private String updTime;

    private String enableFlag;

    private String schemeDes;

    @TableField(exist = false)
    private List<EvaIndex> evaIndexList;


    public void setEvaIndexList(List<EvaIndex> evaIndexList) {
        this.evaIndexList = evaIndexList;
    }

    public List<EvaIndex> getEvaIndexList() {
        return evaIndexList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser == null ? null : updUser.trim();
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime == null ? null : updTime.trim();
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public String getSchemeDes() {
        return schemeDes;
    }

    public void setSchemeDes(String schemeDes) {
        this.schemeDes = schemeDes == null ? null : schemeDes.trim();
    }
}