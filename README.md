# 기본설정
### 환경변수 설정
+ 기본적으로 [local], [dev], [stg], [prd] 로 구성되어 있으며 다음과 같은 환경변수를 적용해준다
<pre><code>-Dspring.profiles.active=[환경변수값]</code></pre>
- - -
### 환경 셋팅 파일
+ 환경변수에 따라 application-[환경변수값].yml을 읽어오게 되므로 해당파일에 환경에 맞게 적용 시켜 줘야 한다
+ 환경변수에 따른 logback-spring.xml 내의 정보를 변경해 줘야 한다.
- - -
### Swagger
+ local 및 dev 환경에는 적용되어 있으며, stg 및 prd 환경에서는 제외 되었다
<pre><code>application-[환경변수값].yml</br>
springdoc:
  swagger-ui:
    enabled: false  //true: 적용(local, dev), false: 제외(stg, prd)
</code></pre>
<br/><br/>
# 개발 가이드
### Controller
+ http method에 따라 @PostMapping 또는 @GetMapping 사용
+ swagger 사용을 위한 @Operation 정의 summary : api 제목, description : 설명
+ return type은 <code>ResponseEntity&lt;CommonApiResponse&gt;</code> 로 지정하고 리턴시
<code>return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);</code> 의 cOutDto에 리턴 객체를 지정한다.

<pre><code>Get 방식 
input Dto에 @RequestBody 제외

@GetMapping(value="/sample-list")
@Operation(summary="샘플 리스트 조회", description = "샘플리스트를 조회한다.")
public ResponseEntity<CommonApiResponse> sampleList(@Validated SampleListCInDto cInDto) {
    SampleListCOutDto cOutDto = new SampleListCOutDto();

    GetSampleListSInDto sInDto = new GetSampleListSInDto();
    //BeanUtils.copyProperties(cInDto, sInDto);
    List<GetSampleListSOutDto> sampleList =  service.getSampleList(sInDto);
    cOutDto.setSampleList(sampleList);

    return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);
}
</code></pre>

<pre><code>POST 방식
input Dto에 @RequestBody 포함

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
</code></pre>
<pre><code>
Response body

{
    "status": 200,
    "traceId": "c8acd608-118e-4fc2-a457-6392b2442b00",
    "code": "00",
    "message": "success",
    "data": {
        "sampleList": [
            {
                "sampleId": 1,
                "sampleGroup": "A그룹",
                "sampleName": "홍길동",
                "regId": "admin",
                "regDt": "2024-12-02T15:00:00.000+00:00"
            }
        ]
    },
    "time": "2024-12-03T14:54:33.3117478"
}
</code></pre>
- - -
### Service
내용 추가 예정
- - -
### mapper
내용 추가 예정
<br/><br/>

# Validation
### Validation 사용방법
+ 컨트롤러의 각 mapping 매개변수(input 객체) 앞에 @Validated 삽입<br/>
<pre><code>public ResponseEntity<CommonApiResponse> sampleList(@RequestBody @Validated SampleListCInDto cInDto)</code></pre>

+ input 객체 필드값에 validation 필드 추가
<pre><code>@NotEmpty(message="그룹을 입력하세요.")
private String sampleGroup;</code></pre>
- - -
### Validation 주의사항
+ 여러 필드에 대해서 validation이 순차적으로(위에서 아래로) 처리 되지 않는다
- - -
### Validation annotation 종류
+ @Null
<pre><code>null 값만 입력 가능<br/>
@Null
String userName;</code></pre>

+ @NotNull
<pre><code>null 불가능<br/>
@NotNull
String userName;</code></pre>

+ @NotEmpty
<pre><code>null 및 빈 문자열 불가능<br/>
@NotEmpty
String userName;</code></pre>

+ @NotBlank
<pre><code>null, 빈 문자열, 스페이스 불가능<br/>
@NotBlank
String userName;</code></pre>

+ @Size
<pre><code>해당 값에 대한 최소값과 최대값을 지정<br/>
@Size(min=2, max=4)
String userName;</code></pre>

+ @Max
<pre><code>해당 값의 최대값을 지정<br/>
@Max(4)
String userName;</code></pre>

+ @Min
<pre><code>해당 값의 최소값을 지정<br/>
@Min(2)
String userName;</code></pre>

+ @Pattern
<pre><code>해당 값의 유효성 패턴을 지정<br/>
@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}")
String phoneNumber;</code></pre>

+ @Future
<pre><code>입력된 날짜는 미래의 날짜여야 함<br/>
@Future
Date eventDate;</code></pre>

+ @Past
<pre><code>입력된 날짜는 과거의 날짜여야 함<br/>
@Past
Date birthday;</code></pre>
<br/><br/>

# BizException
### BizException 사용방법
+ /resource/message/error/error_ko.properties에 에러코드 등록
<pre><code>ERRCM009998=테스트 에러입니다.
ERRCM009999=테스트 {0}번, {1}번 에러입니다.
</code></pre>
+ 개발자 exception 발생
<pre><code>if(1==1) {
    throw new BizException("ERRCM009998");
}
=> message : 테스트 에러입니다.
</code></pre>
<pre><code>if(1==1) {
    String[] errTxt = {"1", "2"};
    throw new BizException("ERRCM009999");
}
=> message : 테스트 1번, 2번 에러입니다.
</code></pre>
<pre><code>if(1==1) {
    throw new BizException("ERRCM009998", "다른 메세지입니다.");
}
=> message : 다른 메세지입니다.
</code></pre>
