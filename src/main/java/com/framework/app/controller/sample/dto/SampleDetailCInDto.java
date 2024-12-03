package com.framework.app.controller.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SampleDetailCInDto {

    @Schema(description = "샘플키값", example = "1")
    @NotEmpty(message="키값이 존재하지 않습니다.")
    private String sampleId;
}
