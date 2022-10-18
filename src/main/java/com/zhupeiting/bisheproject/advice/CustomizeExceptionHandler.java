package com.zhupeiting.bisheproject.advice;

import com.alibaba.fastjson2.JSON;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zhupeiting.bisheproject.dto.ResultDto;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//统一处理controller接到的异常，也有可能是其他层throw的
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Throwable e, Model model) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回 JSON
            ResultDto resultDto;
            if(e instanceof CustomizeException){
                resultDto = ResultDto.errorOf((CustomizeException)e);
            }else{
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException ex) {
            }
            return null;
        }else{
            // 错误页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR);
            }
            return new ModelAndView("error");
        }
    }
}
