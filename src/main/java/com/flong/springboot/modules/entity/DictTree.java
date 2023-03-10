package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_dict_tree")
public class DictTree {
    private Long id;


    private String code;

    @Length(max = 30,message = "名称长度不能大于30")
    private String note;

    private String type;

    @Length(max = 300,message = "备注长度不能大于300")
    private String remark;

    private Long parentId;

    private Byte nodeType;

    private Integer sort;

    private Integer level;

    private String path;

    @TableField(exist = false)
    private List<DictTree> childDictTree;


    public void setChildDictTree(List<DictTree> childDictTree) {
        this.childDictTree = childDictTree;
    }

    public List<DictTree> getChildDictTree() {
        return childDictTree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public DictTree setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getNodeType() {
        return nodeType;
    }

    public DictTree setNodeType(Byte nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}