package com.framework.app.service.sample.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class GetSampleSOutDto {
    private int sampleId;
    private String sampleGroup;
    private String sampleName;
    private String regId;
    private Date regDt;
}
