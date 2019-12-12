package cn.hunkier.switchyomega.controller;

import cn.hunkier.switchyomega.SwitchyOmegaUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.net.URL;

@Slf4j
//@RestController
@Controller
public class IndexController {


    @GetMapping("/")

//    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        String res =  "hello ! This is a SwitchyOmega configure server!\n " +
//                "<br/>" +
                "Use: "+basePath+"switchyOmega.json?c=中国  use for chrome/firefox plugin SwitchyOmega https://github.com/FelisCatus/SwitchyOmega/releases \n  " +
//                "</br>" +
                "Use: "+basePath+"server   use for ios shadowrocket \n  " +
//                "</br>" +
                "Use: "+basePath+"proxy  use for linux terminal \n  " +
//                "</br>" +
                "pac.txt: "+basePath+"pac.txt \n  "
//                +"</br>"
                ;

        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(res);
        return null;

    }

    @GetMapping("/switchyOmega.json")
    @ResponseBody
    public Object switchyOmega(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
    ){
        final JSONObject jsonConfigure = SwitchyOmegaUtil.getSwitchyOmegaConfigure(country,update);
        return  jsonConfigure;
    }
    @GetMapping("/server")
    public void server(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
            ,HttpServletResponse response
    )throws  Exception{
        String serverConfigure = SwitchyOmegaUtil.getServerConfigure(country,update);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(serverConfigure);
    }
    @GetMapping("/proxy")
    public void proxy(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
            ,HttpServletResponse response
    )throws  Exception{
        String result =  SwitchyOmegaUtil.getProxyConfigure(country,update);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);
    }
    @GetMapping("/pac.txt")
    public void pac(HttpServletResponse response) throws Exception {
        final URL url = ResourceUtils.getURL("classpath:pac.txt");
       String  ruleList = FileUtil.readString(url,"UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(ruleList);
    }
}
