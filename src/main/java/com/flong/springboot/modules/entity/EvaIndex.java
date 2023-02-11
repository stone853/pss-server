package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_eva_index")
public class EvaIndex {
    private Integer id;

    private String schemeCode;

    @Length(max = 30,message = "名称长度不能大于30")
    private String indexName;

    private Double indexWeight;

    private Double indexScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode == null ? null : schemeCode.trim();
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName == null ? null : indexName.trim();
    }


    public Double getIndexWeight() {
        return indexWeight;
    }

    public void setIndexWeight(Double indexWeight) {
        this.indexWeight = indexWeight;
    }

    public void setIndexScore(Double indexScore) {
        this.indexScore = indexScore;
    }

    public Double getIndexScore() {
        return indexScore;
    }
}