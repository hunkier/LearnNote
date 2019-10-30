package cn.hunkier.switchyomega.controller;

import cn.hunkier.switchyomega.SwitchyOmegaUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
public class IndexController {


    @GetMapping("/")
    public String index(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        return "hello ! This is a SwitchyOmega configure server!\n </br> " +
                "Use: "+basePath+"switchyOmega.json  \n </br> " +
                "Use: "+basePath+"server.json  \n </br> " +
                "Use: "+basePath+"proxy.json  \n </br> ";

    }

    @GetMapping("/switchyOmega.json")
    public Object switchyOmega(@RequestParam(required = false)String country){
        final JSONObject jsonConfigure = SwitchyOmegaUtil.getSwitchyOmegaConfigure(country);
        return  jsonConfigure;
    }
    @GetMapping("/server.json")
    public Object server(@RequestParam(required = false)String country){
        String serverConfigure = SwitchyOmegaUtil.getServerConfigure(country);
        return  serverConfigure;
    }
    @GetMapping("/proxy.json")
    public Object proxy(@RequestParam(required = false)String country){
        String result =  SwitchyOmegaUtil.getProxyConfigure(country);
        return  result;
    }
}
