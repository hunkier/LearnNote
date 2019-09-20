package cn.hunkier.switchyomega.controller;

import cn.hunkier.switchyomega.SwitchyOmegaUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
public class IndexController {

    private static DateTime lastTime = new DateTime().minusDays(1);
    private static JSONObject result = null;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        return "hello ! This is a SwitchyOmega configure server!\n Use: "+basePath+"switchyOmega.json";
    }

    @GetMapping("/switchyOmega.json")
    public Object switchyOmega(){
        if (lastTime.plusSeconds(60*10).isBeforeNow()) {
            final JSONObject jsonConfigure = SwitchyOmegaUtil.getJsonConfigure();
            lastTime = new DateTime();
            result =  jsonConfigure;
        }else {
            log.info("read data from cache, last update time: " + lastTime.toString("yyyy-MM-dd HH:mm:ss"));
        }
        return  result;
    }
}
