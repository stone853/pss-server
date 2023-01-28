package com.flong.springboot.modules.entity.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.ProcessLog;
import com.flong.springboot.modules.entity.PssProcessTask;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProcessInfo {
    List<ProcessLog> processLogList;

    PssProcessTask processTask;

    public void setProcessLogList(List<ProcessLog> processLogList) {
        this.processLogList = processLogList;
    }

    public List<ProcessLog> getProcessLogList() {
        return processLogList;
    }

    public void setProcessTask(PssProcessTask processTask) {
        this.processTask = processTask;
    }

    public PssProcessTask getProcessTask() {
        return processTask;
    }


}