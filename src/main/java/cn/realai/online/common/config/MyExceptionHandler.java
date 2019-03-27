package cn.realai.online.common.config;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class MyExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        //PrintWriter out = null;
        if (ex instanceof UnauthenticatedException || ex instanceof UnauthorizedException) {
            try {
                //Result result = new Result(ResultCode.NO_PERMISSION.getCode(), ResultMessage.NO_PERMISSION.getMsg(), null);
                attributes.put("code",ResultCode.NO_PERMISSION.getCode());
                attributes.put("msg",ResultMessage.NO_PERMISSION.getMsg());
                attributes.put("data",null);
                //out = httpServletResponse.getWriter();
                //out.append(JSON.toJSONString(result));
                if(ex instanceof UnauthenticatedException){
                    httpServletResponse.setStatus(401);
                }else {
                    httpServletResponse.setStatus(403);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //if (out != null) {
               //     out.flush();
               // }
            }
        } else {
            logger.error("系统异常",ex);
            try {
                attributes.put("code",ResultCode.DATA_ERROR.getCode());
                attributes.put("msg","系统异常");
                attributes.put("data",null);
               // Result result = new Result(ResultCode.DATA_ERROR.getCode(),"系统异常", null);
               // out = httpServletResponse.getWriter();
                //out.append(JSON.toJSONString(result));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //if (out != null) {
                 //   out.close();
                //}
            }
        }

        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
