package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_eva_index")
public class EvaIndex {
    private Integer id;

    private String schemeCode;

    private String indexName;

    private Long indexWeight;

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

    public Long getIndexWeight() {
        return indexWeight;
    }

    public void setIndexWeight(Long indexWeight) {
        this.indexWeight = indexWeight;
    }
}