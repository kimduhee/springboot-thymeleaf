package com.framework.app.controller.sample.dto;

import com.framework.app.service.sample.dto.GetSampleListSOutDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SampleListCOutDto {
    private List<GetSampleListSOutDto> sampleList;
}
