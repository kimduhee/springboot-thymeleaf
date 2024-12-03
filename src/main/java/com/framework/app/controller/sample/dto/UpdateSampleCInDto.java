package com.framework.app.controller.sample.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSampleCInDto {

    @NotEmpty(message="그룹을 입력하세요.")
    private String sampleGroup;

    @NotEmpty(message="이름을 입력하세요.")
    private String sampleName;

    @NotEmpty(message="키값이 존재하지 않습니다.")
    private String sampleId;
}
