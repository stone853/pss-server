package com.flong.springboot.modules.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class DelDictTreeDto {

    private List<String> ids;

    private String dictType;
}
