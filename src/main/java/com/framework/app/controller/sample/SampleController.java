package com.framework.app.controller.sample;

import com.framework.app.common.handler.CommonApiResponse;
import com.framework.app.controller.sample.dto.*;
import com.framework.app.service.sample.SampleService;
import com.framework.app.service.sample.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api/sample")
@RestController
public class SampleController {

    private final SampleService service;

    @GetMapping("sample")
    public ResponseEntity<CommonApiResponse> getSampleList() {
        GetSampleListCOutDto cOutDto = new GetSampleListCOutDto();

        GetSampleListSInDto sInDto = new GetSampleListSInDto();
        //BeanUtils.copyProperties(cInDto, sInDto);
        List<GetSampleListSOutDto> sampleList =  service.getSampleList(sInDto);
        cOutDto.setSampleList(sampleList);

        return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);
    }

    @GetMapping("sample/{sampleId}")
    public ResponseEntity<CommonApiResponse> getSample(@PathVariable String sampleId) {
        GetSampleCOutDto cOutDto = new GetSampleCOutDto();

        GetSampleSInDto sInDto = new GetSampleSInDto();
        sInDto.setSampleId(sampleId);
        GetSampleSOutDto sample = service.getSample(sInDto);
        cOutDto.setSample(sample);

        return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);
    }

    @PostMapping("/save/sample")
    public ResponseEntity<CommonApiResponse> saveSample(@RequestBody SaveSampleCInDto cInDto) {

        SaveSampleSInDto sInDto = new SaveSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        service.saveSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }

    @PostMapping("/sample/update/{sampleId}")
    public ResponseEntity<CommonApiResponse> updateSample(@PathVariable String sampleId, @RequestBody @Validated UpdateSampleCInDto cInDto) {

        UpdateSampleSInDto sInDto = new UpdateSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        service.updateSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }

    @PostMapping("/sample/delete/{sampleId}")
    public ResponseEntity<CommonApiResponse> deleteSample(@PathVariable String sampleId) {

        DeleteSampleSInDto sInDto = new DeleteSampleSInDto();
        sInDto.setSampleId(sampleId);
        service.deleteSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }
}
