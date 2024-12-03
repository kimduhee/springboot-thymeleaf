package com.framework.app.common.handler;

import com.framework.app.common.exception.BizException;
import com.framework.app.common.factory.ErrorMessageSourceFactory;
import com.framework.app.common.util.BeanUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @Autowired
    private ContentNegotiatingViewResolver contentNegotiatingViewResolver;

    @Autowired
    ErrorMessageSourceFactory errorMessageSourceFactory;

    /**
     * 404 not found 처리
     * @param e
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNotFoundException(NoHandlerFoundException e, HttpServletRequest httpServletRequest) {

        if(!isAjax(httpServletRequest)) {
            //httpServletRequest.setAttribute("test","test", WebRequest.SCOPE_REQUEST);
            ModelAndView mav = new ModelAndView("forward:/v1/view/comm/404");
            return mav;
        }

        ErrorMessageSourceFactory errorMessageSourceFactory = BeanUtil.getBean(ErrorMessageSourceFactory.class);

        CommonApiResponse res = CommonApiResponse.fail(HttpStatus.NOT_FOUND,"ERRCM000404", errorMessageSourceFactory.getMessage("ERRCM000404"));

        if(log.isInfoEnabled()) {
            log.info("NoHandlerFoundException : [ERRCM000404]{}", errorMessageSourceFactory.getMessage("ERRCM000404"));
        }

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    /**
     * Controller input validation check
     * @param e
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {

        if(!isAjax(httpServletRequest)) {
            //httpServletRequest.setAttribute("test","test", WebRequest.SCOPE_REQUEST);
            ModelAndView mav = new ModelAndView("forward:/v1/view/comm/400");
            return mav;
        }

        ErrorMessageSourceFactory errorMessageSourceFactory = BeanUtil.getBean(ErrorMessageSourceFactory.class);

        CommonApiResponse res = CommonApiResponse.fail(HttpStatus.BAD_REQUEST, "ERRCM000400", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        if(log.isInfoEnabled()) {
            log.info("BizException : [ERRCM000400]{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    /**
     * 커스텀 BizException 처리
     * @param e
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Object handleBizException(BizException e, HttpServletRequest httpServletRequest) {

        if(!isAjax(httpServletRequest)) {
            //httpServletRequest.setAttribute("test","test", WebRequest.SCOPE_REQUEST);
            ModelAndView mav = new ModelAndView("forward:/v1/view/comm/500");
            return mav;
        }

        ErrorMessageSourceFactory errorMessageSourceFactory = BeanUtil.getBean(ErrorMessageSourceFactory.class);

        if(StringUtils.isEmpty(e.getMessage())) {
            e.setErrMessage(errorMessageSourceFactory.getMessage(e.getErrCode()));
        }

        CommonApiResponse res = CommonApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrCode(), e.getMessage());

        if(log.isInfoEnabled()) {
            log.info("BizException : [{}]{}",e.getErrCode(), e.getMessage());
        }

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeExceptiontion(RuntimeException e, HttpServletRequest httpServletRequest) {

        if(!isAjax(httpServletRequest)) {
            //httpServletRequest.setAttribute("test","test", WebRequest.SCOPE_REQUEST);
            ModelAndView mav = new ModelAndView("forward:/v1/view/comm/500");
            return mav;
        }

        ErrorMessageSourceFactory errorMessageSourceFactory = BeanUtil.getBean(ErrorMessageSourceFactory.class);

        CommonApiResponse res = CommonApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR,"ERRCM000000", errorMessageSourceFactory.getMessage("ERRCM000000"));

        if(log.isInfoEnabled()) {
            log.info("RuntimeException : [ERRCM000000]{}", errorMessageSourceFactory.getMessage("ERRCM000000"));
        }

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * request 건이 화면인지 ajax인지 구별
     *
     * @param httpServletRequest
     * @return
     */
    public boolean isAjax(HttpServletRequest httpServletRequest) {

        boolean isViewResponse = false;

        //Request Content-type 에 application/json 이 포함될 경우 ajax 통신
        if(httpServletRequest.getContentType() != null && httpServletRequest.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
            return true;
        }

        //Request Headers 의 Accept에 application/json 이 포함될 경우 ajax 통신
        if(httpServletRequest.getHeader("accept") != null && httpServletRequest.getHeader("accept").indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
            return true;
        }

//        if(nativeWebRequest != null) {
//
//            List<MediaType> mediaTypeList;
//            try {
//                mediaTypeList = contentNegotiatingViewResolver.getContentNegotiationManager().resolveMediaTypes(nativeWebRequest);
//            } catch(HttpMediaTypeNotAcceptableException e) {
//                mediaTypeList = new ArrayList<MediaType>();
//            }
//
//            for(MediaType mt : mediaTypeList) {
//                if(mt.getType().indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
//                    return false;
//                }
//            }
//        }

//        if(handlerMethod != null) {
//
//            if(handlerMethod.getMethodAnnotation(RequestMapping.class) != null) {
//                String[] mtList = handlerMethod.getMethodAnnotation(RequestMapping.class).produces();
//                for(String mt : mtList) {
//                    if(mt.indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
//                        return true;
//                    }
//                }
//            }
//
//            if(handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class) != null) {
//                String[] mtList = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class).produces();
//                for(String mt : mtList) {
//                    if(mt.indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
//                        return true;
//                    }
//                }
//            }
//        }

        return isViewResponse;
    }
}
