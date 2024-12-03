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
### API Controller
+ API controller 명시를 위해 class상단에 <code>@RestController</code> 추가
+ lombok을 이용한 생성자 주입을 위한 <code>@RequiredArgsConstructor</code> 추가
+ <code>@RequiredArgsConstructor</code>를 이용한 주입을 위해 Service는 private final 로 지정
+ http method에 따라 @PostMapping 또는 @GetMapping 사용
+ swagger 사용을 위한 <code>@Tag(name = "샘플 API", description = "샘플 테스트용 API")</code>추가
+ swagger 사용을 위한 @Operation 정의 summary : api 제목, description : 설명
+ return type은 <code>ResponseEntity&lt;CommonApiResponse&gt;</code> 로 지정하고 리턴시
<code>return new ResponseEntity<>(CommonApiResponse.ok(cOutDto), HttpStatus.OK);</code>
의 ok인자값에 리턴 객체를 지정한다.

<pre><code>Get 방식
input Dto에 @RequestBody 제외

@Tag(name = "샘플 API", description = "샘플 테스트용 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/v1/api/sample")
@RestController
public class SampleController {

  private final SampleService service;

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
+ Service 명시를 위해 class상단에 <code>@Service</code> 추가
+ lombok을 이용한 생성자 주입을 위한 <code>@RequiredArgsConstructor</code> 추가
+ <code>@RequiredArgsConstructor</code>를 이용한 주입을 위해 Mapper는 private final 로 지정
<pre><code>Service 조회 예시

@RequiredArgsConstructor
@Slf4j
@Service
public class SampleService {
  private final SampleMapper sampleMapper;
  
  @Transactional(readOnly=true)
    public List<GetSampleListSOutDto> getSampleList(GetSampleListSInDto getSampleListSInDto) {
    return sampleMapper.getSampleList(getSampleListSInDto);
  }
}
</code></pre>
<pre><code>Service CUD 예시

@RequiredArgsConstructor
@Slf4j
@Service
public class SampleService {
  private final SampleMapper sampleMapper;
  
  @Transactional(propagation = Propagation.REQUIRED)
  public int updateSample(UpdateSampleSInDto updateSampleSInDto) {
      return sampleMapper.updateSample(updateSampleSInDto);
  }
}
</code></pre>
transaction을 위해 조회성 일 경우는 <code>@Transactional(readOnly=true)</code>을 
CUD의 경우는 <code>@Transactional(propagation = Propagation.REQUIRED)</code>을 기본적으로 기입해 준다
> ### Transaction propagation(전파옵션)
>+ REQUIRED<br/>
><code>디폴트 속성으로 부모 트랜젝션 내에서 실행, 부모 트랜젝션이 없을경우 새로운 트랜잭션 생성</code>
><br/><br/>
>+ SUPPORTS<br/>
><code>시작된 트랜잭션이 있으면 참여, 그렇지 않으면 트랜잭션 없이 진행</code>
><br/><br/>
>+ REQUIRES_NEW<br/>
><code>부모 트랜잭션을 무시하고 새로운 트랜잭션 생성</code>
><br/><br/>
>+ MANDATORY<br/>
><code>REQUIRED와 비슷하며 시작된 트랜잭션이 있으면 참여
>트랜잭션이 시작된 것이 없으면 예외를 발생
>독립적으로 트랜잭션을 진행하면 안 되는 경우 사용</code>
><br/><br/>
>+ NOT_SUPPORTED<br/>
><code>트랜잭션을 사용하지 않게 함
>이미 진행 중인 트랜잭션은 보류시킴</code>
><br/><br/>
>+ NEVER<br/>
><code>트랜잭션을 사용하지 않게 강제함
>이미 진행 중인 트랜잭션이 있으면 예외 발생</code>
><br/><br/>
>+ NESTED<br/>
><code>진행중인 트랜잭션이 있으면 중첩 트랜잭션 시작
>중첩 트랜잭션은 트랜잭션 안에 다시 트랜잭션을 만듬</code>
><br/><br/>
>+ readOnly<br/>
><code>트랜잭션을 읽기 전용으로 설정
>성능 최적화 및 특정 트랜잭션 안에서 쓰기 작업을 의도적으로 방지하기 위함
>readOnly 값은 기본 false이며, true일 경우 insert,update, delete 실행 시 예외발생</code>

> ### Transaction rollback
> + rollbackFor <br/>
><code>특정 예외가 발생 시 강제로 Rollback</br>
>예) @Transactional(rollbackFor=Exception.class)</code>
><br/><br/>
> + noRollbackFor <br/>
><code>특정 예외가 발생 시 Rollback 처리되지 않음</br>
>예) @Transactional(noRollbackFor=Exception.class)</code>

> ### Transaction timeout
> + rollbackFor <br/>
><code>지정된 시간 내에 해당 메소드 수행이 완료되지 않은 경우 rollback 수행</br>
>-1일경우 no timeout(Default=-1)</br>
>예) @Transactional(timeout=100)</code>
- - -
### mapper
내용 추가 예정
- - -
### DTO
+ swagger <code>필드정의 @Schema(description = "샘플키값", example = "1")</code>
<br/>description : 필드값, example  : 예시값
+ validation <code>@NotEmpty(message="키값이 존재하지 않습니다.")</code>
<br/>message : 해당 validation에 대한 노출 메세지
<pre><code>Controller input Dto

@Getter
public class SampleDetailCInDto {
    @Schema(description = "샘플키값", example = "1")
    @NotEmpty(message="키값이 존재하지 않습니다.")
    private String sampleId;
}
</code></pre>
POST Controller input DTO의 경우 <code>@Getter</code>만 추가해도 값들이 binding 되며 
GET Api의 경우 아래 사항 추가하여 binding 되도록 처리함
<pre><code>@ControllerAdvice
public class WebControllerHandler {

    /**
     * Get 요청시 setter가 아닌 Field에 직접 접근 방식으로 처리
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}
</code></pre>
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
