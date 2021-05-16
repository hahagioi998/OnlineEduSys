package com.huixian.service.base.config.exceptionHandler;



import com.huixian.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常会执行这个方法
    @ExceptionHandler(Exception.class)
    //因为他不在Controller中。没有@RestController，所以数据不会返回，需要加@ResponeseBody返回数据
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。。");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }

    @ExceptionHandler(AchangException.class)
    @ResponseBody
    public R error(AchangException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return R.error().message(e.getMsg()).code(e.getCode());
    }


}
