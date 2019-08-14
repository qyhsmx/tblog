package com.qyy.blog.blogweb.controller.common;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Controller
public class CommonController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;


    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse httpServletResponse){

        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();

        OutputStream out = null;
        try {
            //generate a text
            String verifyCode = defaultKaptcha.createText();
            //generate a image
            BufferedImage image = defaultKaptcha.createImage(verifyCode);
            //put in httpSession
            request.getSession().setAttribute("verifyCode",verifyCode);
            //write verifyCode to outputStream
            ImageIO.write(image,"jpg",imageOutputStream);

        // write out to response
        byte[] toByteArray = imageOutputStream.toByteArray();

        //set httpServletResponse
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");


        out = httpServletResponse.getOutputStream();

        out.write(toByteArray);

        out.flush();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }


}
