package com.framework.app.controller.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleUpdateCInDto {

    @Schema(description = "그룹명", example = "A그룹")
    @NotEmpty(message="그룹을 입력하세요.")
    private String sampleGroup;

    @Schema(description = "이름", example = "홍길동")
    @NotEmpty(message="이름을 입력하세요.")
    private String sampleName;

    @Schema(description = "샘플키값", example = "1")
    @NotEmpty(message="키값이 존재하지 않습니다.")
    private String sampleId;
}
