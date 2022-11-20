package com.example.back_end.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebUtils {
    /**
     *  将字符串渲染到客户端
     * @param response
     * @param string
     * @return
     */
    public static String renderString(HttpServletResponse response, String string) {
        try{
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
