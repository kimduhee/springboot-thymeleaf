package com.framework.app.controller.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleSaveCInDto {

    @Schema(description = "그룹명", example = "A그룹")
    @NotEmpty(message="그룹명을 입력해 주세요.")
    private String sampleGroup;

    @Schema(description = "이름", example = "홍길동")
    @NotEmpty(message="이름 입력해 주세요.")
    private String sampleName;
}
