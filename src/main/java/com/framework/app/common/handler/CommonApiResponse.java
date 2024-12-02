package com.framework.app.common.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.MDC;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class CommonApiResponse<T> {

    //응답 상태값
    private Integer status;

    //거래 ID
    private String traceId;

    //응답 코드
    private String code;

    //응답 메세지
    private String message;

    //응답 데이터
    private Object data;

    //응답 시간
    private final LocalDateTime time;

    /**
     * 정상 처리에 대한 응답값
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> CommonApiResponse<T> ok(T data) {
        return CommonApiResponse.<T>builder()
                .status(200)
                .data(data)
                .code("00")
                .message("success")
                .time(LocalDateTime.now())
                .traceId(MDC.get("traceId"))
                .build();
    }

    /**
     * 실패 처리에 대한 응답값
     *
     * @param status
     * @param code
     * @param message
     * @return
     * @param <T>
     */
    public static <T> CommonApiResponse<T> fail(int status, String code, String message) {
        return CommonApiResponse.<T>builder()
                .status(status)
                .code(code)
                .message(message)
                .time(LocalDateTime.now())
                .traceId(MDC.get("traceId"))
                .build();
    }
}
