package com.framework.app.mapper.sample;

import com.framework.app.service.sample.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface
SampleMapper {

    public List<GetSampleListSOutDto> getSampleList(GetSampleListSInDto getSampleListSInDto);

    public GetSampleSOutDto getSample(GetSampleSInDto getSampleSInDto);

    public int saveSample(SaveSampleSInDto saveSampleSInDto);

    public int updateSample(UpdateSampleSInDto updateSampleSInDto);

    public int deleteSample(DeleteSampleSInDto deleteSampleSInDto);

}
