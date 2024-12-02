package com.framework.app.service.sample.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetSampleListSOutDto {

    private int sampleId;
    private String sampleGroup;
    private String sampleName;
    private String regId;
    private Date regDt;
}
