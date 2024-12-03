# Validation
- - -
### Validation 사용방법
+ 컨트롤러의 각 mapping 매개변수(input 객체) 앞에 @Validated 삽입<br/>
<pre><code>public ResponseEntity<CommonApiResponse> sampleList(@RequestBody @Validated SampleListCInDto cInDto)</code></pre>

+ input 객체 필드값에 validation 필드 추가
<pre><code>@NotEmpty(message="그룹을 입력하세요.")
private String sampleGroup;</code></pre><br/>

### Validation 주의사항
+ 여러 필드에 대해서 validation이 순차적으로(위에서 아래로) 처리 되지 않는다
<br/><br/>
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
## BizException
- - -
### BizException 사용방법

/resource/message/error/error_ko.properties에 에러코드 등록
<pre><code>
ERRCM009998=테스트 에러입니다.
ERRCM009999=테스트 {0}번, {1}번 에러입니다.
</code></pre>
개발자 exception 발생
<pre><code>
if(1==1) {
    throw new BizException("ERRCM009998");
}
=> message : 테스트 에러입니다.
</code></pre>
<pre><code>
if(1==1) {
    String[] errTxt = {"1", "2"};
    throw new BizException("ERRCM009999");
}
=> message : 테스트 1번, 2번 에러입니다.
</code></pre>
<pre><code>
if(1==1) {
    throw new BizException("ERRCM009998", "다른 메세지입니다.");
}
=> message : 다른 메세지입니다.
</code></pre>
