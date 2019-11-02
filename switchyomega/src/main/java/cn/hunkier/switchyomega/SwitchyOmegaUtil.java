package cn.hunkier.switchyomega;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;


import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class SwitchyOmegaUtil {
    
    private static String ruleList = null;

    private final static String [] WHITE_HOSTS= {
            "10.30.13.250",
            "127.0.0.1",
            "10.*",
            "192.168.*",
            "172.20.*",
            "172.18.*",
            "mtech.wind-dev.net",
            "*.baidu.com",
            "*.wanzhongonline.com",
    };

    private static DateTime lastTime = new DateTime().minusDays(1);

    private static String result = null;



    public static void main(String[] args) throws Exception {


/*
    final JSONObject base_JsonObject = getSwitchyOmegaConfigure(null);
//        System.out.println(base_JsonObject.toJSONString());
        final File file = new File("target/backup.json");
//        System.out.println(file.getAbsolutePath());
        FileUtil.writeString(base_JsonObject.toJSONString(), file, HTTP.UTF_8);
*/


/*

        final File file = new File("target/backup.json");
        System.out.println(file.getAbsolutePath());
        FileUtil.writeString(getServerInfo(), file, HTTP.UTF_8);
*/


    }






    public static JSONObject getSwitchyOmegaConfigure(String country){
        Map<String,Integer> servers = new HashMap<>();
        String json = getServerInfo();
//        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
//        JSONObject json_data = JSON.parseObject(jsonObject.getString("data"));
        Map<String, String> retMap = getServerMapInfo(jsonObject);

        // System.out.println("username:" + jsonObject.getString("s_login"));
        // System.out.println("password:" + jsonObject.getString("s_token"));
        String baseJson = null;
        try {
            baseJson = FileUtil.readString(ResourceUtils.getURL("classpath:base.json"),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject base_JsonObject = JSONObject.parseObject(baseJson);

        List<String> matchProfileNames = new ArrayList<>();
        for (Map.Entry<String, String> string : retMap.entrySet()) {
            String serverProfileName = string.getValue().toString().split("#")[1];
            String serverType = string.getValue().toString().split("#")[2];
            serverProfileName += ("-"+serverType);
            serverProfileName = getServerProfileName(serverProfileName, servers, matchProfileNames);
            if (StrUtil.isNotBlank(country) && !StrUtil.containsAny(serverProfileName,country)){
                continue;
            }
            JSONObject serverProfileName_JSON = new JSONObject();
            {

                JSONObject auth = new JSONObject();
                {
                    JSONObject fallbackProxy = new JSONObject();
                    fallbackProxy.put("password", jsonObject.getString("s_token"));
                    fallbackProxy.put("username", jsonObject.getString("s_login"));
                    auth.put("fallbackProxy", fallbackProxy);
                }
                serverProfileName_JSON.put("auth", auth);
            }
            {
                JSONObject fallbackProxy = new JSONObject();
                fallbackProxy.put("host", string.getKey());
                fallbackProxy.put("port", Integer.parseInt(string.getValue().toString().split("#")[0]));
                fallbackProxy.put("scheme", "https");
                serverProfileName_JSON.put("fallbackProxy", fallbackProxy);
            }
            {
                JSONArray bypassList = new JSONArray();
                for (String whiteHost : WHITE_HOSTS) {
                    if (StrUtil.isBlank(whiteHost)){
                        continue;
                    }
                    JSONObject bypassListJSON = new JSONObject();
                    bypassListJSON.put("conditionType", "BypassCondition");
                   // bypassListJSON.put("pattern", "127.0.0.1");
                    bypassListJSON.put("pattern", whiteHost);
                    bypassList.add(bypassListJSON);
                }

                serverProfileName_JSON.put("bypassList", bypassList);
            }

            serverProfileName_JSON.put("color", "#d63");
//            serverProfileName_JSON.put("name", "SetupCrackByDM-" + serverProfileName);
            serverProfileName_JSON.put("name", "" + serverProfileName);
            serverProfileName_JSON.put("profileType", "FixedProfile");
            serverProfileName_JSON.put("revision", UUID.randomUUID().toString());

//            base_JsonObject.put("+SetupCrackByDM-" + serverProfileName, serverProfileName_JSON);


            serverProfileName_JSON.put("name",serverProfileName);
            base_JsonObject.put("+" + serverProfileName, serverProfileName_JSON);
        }

        final JSONObject autoSwitchRule = base_JsonObject.getJSONObject("+__ruleListOf_auto switch");
        String matchProfileName = matchProfileNames.get(0);
        autoSwitchRule.put("matchProfileName",matchProfileName);
        if (null==ruleList) {
            try {
                final URL url = ResourceUtils.getURL("classpath:pac.txt");
                ruleList = FileUtil.readString(url,"UTF-8");
                log.info(url.getPath());
//                log.info(ruleList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        autoSwitchRule.put("ruleList",ruleList);

        return  base_JsonObject;
    }

    private static String getServerProfileName(String serverProfileName, Map<String,Integer> servers, List<String> matchProfileNames){
        Integer count = servers.get(serverProfileName);
        if (null==count){
            count = 1 ;
        }else {
            count = count +1 ;
        }
        servers.put(serverProfileName, count);
        if (count>1){

            serverProfileName += ("-"+count);
        }
        if (StrUtil.containsAny(serverProfileName,"特别行政区")){
            matchProfileNames.add(serverProfileName);
        }
        return  serverProfileName;
    }

    public static String getServerConfigure(String country){
        final String json = getServerInfo();
        final JSONObject jsonObject = JSON.parseObject(json);
        final Map<String, String> serverMapInfo = getServerMapInfo(jsonObject);
        final String login = jsonObject.getString("s_login");
        final String token = jsonObject.getString("s_token");
        final Map<String,Integer> servers = new HashMap<>();
        final List<String> matchProfileNames = new ArrayList<>();
        final StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> string : serverMapInfo.entrySet()) {
            final String host = string.getKey();
            final String port = string.getValue().toString().split("#")[0];
            String serverProfileName = string.getValue().toString().split("#")[1];
            String serverType = string.getValue().toString().split("#")[2];
            serverProfileName += ("-"+serverType);
            serverProfileName = getServerProfileName(serverProfileName, servers, matchProfileNames);
            if (StrUtil.isNotBlank(country) && !StrUtil.containsAny(serverProfileName,country)){
                continue;
            }
           String content = new StringBuilder()
                   .append(login)
                   .append(":")
                   .append(token)
                   .append("@")
                   .append(host)
                   .append(":")
                   .append(port)
                   .toString();
            result.append(" \n" +
                    "https://")
                    .append(Base64.getEncoder().encodeToString(content.getBytes()))
            .append("?remarks=")
            .append(URLEncoder.encode(serverProfileName))
            .append("\r")
            .append("\n")
            .append("\r\n")
                    ;
        }
        return result.toString();
    }

    public static String getProxyConfigure(String country){
        final String json = getServerInfo();
        final JSONObject jsonObject = JSON.parseObject(json);
        final Map<String, String> serverMapInfo = getServerMapInfo(jsonObject);
        final String login = jsonObject.getString("s_login");
        final String token = jsonObject.getString("s_token");
        final Map<String,Integer> servers = new HashMap<>();
        final List<String> matchProfileNames = new ArrayList<>();
        final StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> string : serverMapInfo.entrySet()) {
            final String host = string.getKey();
            final String port = string.getValue().toString().split("#")[0];
            String serverProfileName = string.getValue().toString().split("#")[1];
            String serverType = string.getValue().toString().split("#")[2];
            serverProfileName += ("-"+serverType);
            serverProfileName = getServerProfileName(serverProfileName, servers, matchProfileNames);
            if (StrUtil.isNotBlank(country) && !StrUtil.containsAny(serverProfileName,country)){
                continue;
            }
            result.append(serverProfileName)
                    .append("\r")
                    .append("\n")
                    .append("\r\n")
            ;
            result.append("export https_proxy=https://")
                    .append(URLEncoder.encode(login))
                    .append(":")
                    .append(URLEncoder.encode(token))
                    .append("@")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("\r")
                    .append("\n")
                    .append("\r\n")
                    .append("</br>")
            ;
        }
        return result.toString();
    }

    private static Map<String, String> getServerMapInfo(JSONObject jsonObject){
        Map<String,String> retMap = new HashMap<>();
        JSONObject json_data = JSON.parseObject(jsonObject.getString("data"));
        JSONObject json_PUBLICSERVERS = JSON.parseObject(json_data.getString("PUBLICSERVERS"));
        for (Map.Entry<String, Object> string : json_PUBLICSERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country")+ "#" + serverInfo.getString("Type"));
        }
        JSONObject json_SERVERS = JSON.parseObject(json_data.getString("SERVERS"));
        for (Map.Entry<String, Object> string : json_SERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country") + "#" + serverInfo.getString("Type"));
        }
        JSONObject json_PREMIUMSERVERS = JSON.parseObject(json_data.getString("PREMIUMSERVERS"));
        for (Map.Entry<String, Object> string : json_PREMIUMSERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country")+ "#" + serverInfo.getString("Type"));
        }
        return retMap;
    }

    private static String getServerInfo(){
        if (StrUtil.isNotBlank(result) && lastTime.plusSeconds(60*10).isAfterNow()) {
            log.info("read data from cache, last update time: " + lastTime.toString("yyyy-MM-dd HH:mm:ss"));
            return  result;
        }
        Map<String, String> post = new HashMap<>();
        post.put("u", "qmvlei@hi2.in");
        post.put("hpassword", "ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413");
        post.put("cv", "3.7.7");
        post.put("lan", "zh-CN");
        post.put("lser", "qmvlei@hi2.in");
        post.put("lang", "zh");
        post.put("h", "login");
        post.put("platform", "chrome");
        post.put("base", "https://base4-sv.diltwo.com/client/");
        post.put("ua", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        post.put("lid", "c77963fa-d7f8-4eb2-8786-667a3075f018");
        post.put("os", "win");
        String json = sendPost("https://base4-sv.diltwo.com/client/rest/config/extension", post);
        result = json;
        lastTime = new DateTime();
        return  json ;
    }


    private static String sendPost(String url, Map<String, String> map) {
        String result = "";
        try {
            System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
            System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
            System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "info");
            System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire.header", "info");
            System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "info");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (!CollectionUtils.isEmpty(map)) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(url);

            httppost.setEntity(entity);
            CloseableHttpResponse response = null;
            response = httpclient.execute(httppost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
