package com.framework.app.controller.sample.dto;

import com.framework.app.service.sample.dto.GetSampleSOutDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSampleCInDto {
    private String sampleGroup;
    private String sampleName;
    private String userId;
    private String sampleId;
}
