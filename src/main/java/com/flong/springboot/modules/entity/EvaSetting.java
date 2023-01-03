package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_eva_setting")
public class EvaSetting {
    private Integer id;

    private Integer afterDays;

    private String cycle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAfterDays() {
        return afterDays;
    }

    public void setAfterDays(Integer afterDays) {
        this.afterDays = afterDays;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }
}