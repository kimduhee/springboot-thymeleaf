package com.framework.app.controller.sample;

import com.framework.app.common.exception.BizException;
import com.framework.app.common.handler.CommonApiResponse;
import com.framework.app.controller.sample.dto.*;
import com.framework.app.service.sample.SampleService;
import com.framework.app.service.sample.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "샘플 API", description = "샘플 테스트용 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/v1/api/sample")
@RestController
public class SampleController {

    private final SampleService service;

    @PostMapping(value="/sample-list")
    @Operation(summary="샘플 리스트 조회", description = "샘플리스트를 조회한다.")
    public ResponseEntity<CommonApiResponse> sampleList(@RequestBody @Validated SampleListCInDto cInDto) {
        SampleListCOutDto cOutDto = new SampleListCOutDto();

        GetSampleListSInDto sInDto = new GetSampleListSInDto();
        //BeanUtils.copyProperties(cInDto, sInDto);
        List<GetSampleListSOutDto> sampleList =  service.getSampleList(sInDto);
        cOutDto.setSampleList(sampleList);

        return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);
    }

    @PostMapping(value="/sample-detail")
    @Operation(summary="샘플 상세 조회", description = "샘플상세를 조회한다.")
    public ResponseEntity<CommonApiResponse> sampleDetail(@RequestBody @Validated SampleDetailCInDto cInDto) {
        SampleDetailCOutDto cOutDto = new SampleDetailCOutDto();

        GetSampleSInDto sInDto = new GetSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        GetSampleSOutDto sample = service.getSample(sInDto);
        cOutDto.setSample(sample);

        return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);
    }

    @PostMapping(value="/sample-save")
    @Operation(summary="샘플 저장", description = "샘플을 저장한다.")
    public ResponseEntity<CommonApiResponse> SampleSave(@RequestBody @Validated SampleSaveCInDto cInDto) {

        SaveSampleSInDto sInDto = new SaveSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        service.saveSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }

    @PostMapping(value="/sample-update")
    @Operation(summary="샘플 수정", description = "샘플을 수정한다.")
    public ResponseEntity<CommonApiResponse> sampleUpdate(@RequestBody @Validated SampleUpdateCInDto cInDto) {

        UpdateSampleSInDto sInDto = new UpdateSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        service.updateSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }

    @PostMapping(value="/sample-delete")
    @Operation(summary="샘플 삭제", description = "샘플을 삭제한다.")
    public ResponseEntity<CommonApiResponse> sampleDelete(@RequestBody @Validated SampleDeleteCInDto cInDto) {

        DeleteSampleSInDto sInDto = new DeleteSampleSInDto();
        BeanUtils.copyProperties(cInDto, sInDto);
        service.deleteSample(sInDto);

        return new ResponseEntity<>(CommonApiResponse.ok("success"), HttpStatus.OK);
    }
}
