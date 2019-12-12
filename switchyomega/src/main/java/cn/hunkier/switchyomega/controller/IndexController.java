package cn.hunkier.switchyomega.controller;

import cn.hunkier.switchyomega.SwitchyOmegaUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URL;

@Slf4j
@RestController
public class IndexController {


    @GetMapping("/")
    public String index(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        return "hello ! This is a SwitchyOmega configure server!\n </br> " +
                "Use: "+basePath+"switchyOmega.json?c=中国  use for chrome/firefox plugin SwitchyOmega https://github.com/FelisCatus/SwitchyOmega/releases \n </br> " +
                "Use: "+basePath+"server.json   use for ios shadowrocket \n </br> " +
                "Use: "+basePath+"proxy.json  use for linux terminal \n </br> ";

    }

    @GetMapping("/switchyOmega.json")
    public Object switchyOmega(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
    ){
        final JSONObject jsonConfigure = SwitchyOmegaUtil.getSwitchyOmegaConfigure(country,update);
        return  jsonConfigure;
    }
    @GetMapping("/server.json")
    public Object server(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
    ){
        String serverConfigure = SwitchyOmegaUtil.getServerConfigure(country,update);
        return  serverConfigure;
    }
    @GetMapping("/proxy.json")
    public Object proxy(
            @RequestParam(name = "c",required = false)String country
            ,@RequestParam(name = "u",required = false)String update
    ){
        String result =  SwitchyOmegaUtil.getProxyConfigure(country,update);
        return  result;
    }
    @GetMapping("/pac.txt")
    public Object pac() throws FileNotFoundException {
        final URL url = ResourceUtils.getURL("classpath:pac.txt");
       String  ruleList = FileUtil.readString(url,"UTF-8");
        return  ruleList;
    }
}
