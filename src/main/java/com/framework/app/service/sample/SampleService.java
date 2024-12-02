package com.framework.app.service.sample;

import com.framework.app.mapper.sample.SampleMapper;
import com.framework.app.service.sample.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SampleService {

    private final SampleMapper sampleMapper;

    public List<GetSampleListSOutDto> getSampleList(GetSampleListSInDto getSampleListSInDto) {
        return sampleMapper.getSampleList(getSampleListSInDto);
    }

    public GetSampleSOutDto getSample(GetSampleSInDto getSampleSInDto) {
        return sampleMapper.getSample(getSampleSInDto);
    }

    public int saveSample(SaveSampleSInDto saveSampleSInDto) {
        return sampleMapper.saveSample(saveSampleSInDto);
    }

    public int updateSample(UpdateSampleSInDto updateSampleSInDto) {
        return sampleMapper.updateSample(updateSampleSInDto);
    }

    public int deleteSample(DeleteSampleSInDto deleteSampleSInDto) {
        return sampleMapper.deleteSample(deleteSampleSInDto);
    }
}
