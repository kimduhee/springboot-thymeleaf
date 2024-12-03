package com.framework.app.controller.sample.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleDeleteCInDto {

    @NotEmpty(message="키값이 존재하지 않습니다.")
    private String sampleId;
}
