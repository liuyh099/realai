package cn.realai.online.common.config;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 注册全局参数校验异常
     * @param exception
     * @param httpServletResponse
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object exceptionHandler(MethodArgumentNotValidException exception, HttpServletResponse httpServletResponse) {
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {

            String message = null;
            List<ObjectError> ls = result.getAllErrors();
            for (int i = 0; i < ls.size(); i++) {
                message = ls.get(i).getDefaultMessage();
                break;
            }
            if (StringUtils.isNotBlank(message)) {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                Result result1 = new Result(ResultCode.PARAM_ERROR.getCode(), message, null);
                PrintWriter out = null;
                try {
                    out = httpServletResponse.getWriter();
                    out.append(JSON.toJSONString(result1));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }
        return null;

    }
}
