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
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;


import java.net.URL;
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



    public static void main(String[] args) throws Exception {

        final JSONObject base_JsonObject = getJsonConfigure();

        System.out.println(base_JsonObject.toJSONString());


//        final String gfwlist = sendPost("https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt", null);
//        System.out.println(gfwlist);
    }





    public static JSONObject getJsonConfigure(){
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
        Map<String, String> retMap = new HashMap<>();
//        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject json_data = JSON.parseObject(jsonObject.getString("data"));
        JSONObject json_PUBLICSERVERS = JSON.parseObject(json_data.getString("PUBLICSERVERS"));
        for (Map.Entry<String, Object> string : json_PUBLICSERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country"));
        }
        JSONObject json_SERVERS = JSON.parseObject(json_data.getString("SERVERS"));
        for (Map.Entry<String, Object> string : json_SERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country"));
        }
        JSONObject json_PREMIUMSERVERS = JSON.parseObject(json_data.getString("PREMIUMSERVERS"));
        for (Map.Entry<String, Object> string : json_PREMIUMSERVERS.entrySet()) {
            JSONObject serverInfo = JSON.parseObject(string.getValue().toString());
            retMap.put(serverInfo.getString("Host"), serverInfo.getString("Port") + "#" + serverInfo.getString("Country"));
        }

        // System.out.println("username:" + jsonObject.getString("s_login"));
        // System.out.println("password:" + jsonObject.getString("s_token"));

//        String baseJson = "{\"+auto switch\":{\"color\":\"#99dd99\",\"defaultProfileName\":\"direct\",\"name\":\"auto switch\",\"profileType\":\"SwitchProfile\",\"rules\":[{\"condition\":{\"conditionType\":\"HostWildcardCondition\",\"pattern\":\"internal.example.com\"},\"profileName\":\"direct\"}],\"revision\":\"16937f2184a\"},\"-addConditionsToBottom\":false,\"-confirmDeletion\":true,\"-downloadInterval\":1440,\"-enableQuickSwitch\":false,\"-monitorWebRequests\":true,\"-quickSwitchProfiles\":[],\"-refreshOnProfileChange\":true,\"-revertProxyChanges\":true,\"-showExternalProfile\":true,\"-showInspectMenu\":true,\"-startupProfileName\":\"\",\"schemaVersion\":2,\"-showConditionTypes\":0}";
//        String baseJson = "{\"+auto switch\":{\"color\":\"#99dd99\",\"defaultProfileName\":\"__ruleListOf_auto switch\",\"name\":\"auto switch\",\"profileType\":\"SwitchProfile\",\"rules\":[],\"revision\":\"16937f2184a\"},\"-addConditionsToBottom\":false,\"-confirmDeletion\":true,\"-downloadInterval\":1440,\"-enableQuickSwitch\":false,\"-monitorWebRequests\":true,\"-quickSwitchProfiles\":[],\"-refreshOnProfileChange\":true,\"-revertProxyChanges\":true,\"-showExternalProfile\":true,\"-showInspectMenu\":true,\"-startupProfileName\":\"auto switch\",\"schemaVersion\":2,\"-showConditionTypes\":0,\"+__ruleListOf_auto switch\":{\"name\":\"__ruleListOf_auto switch\",\"defaultProfileName\":\"direct\",\"profileType\":\"RuleListProfile\",\"color\":\"#99dd99\",\"format\":\"AutoProxy\",\"matchProfileName\":\"SetupCrackByDM-美国\",\"ruleList\":\"\", \"revision\":\"16b53e71dfb\",\"sourceUrl\":\"https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt\",\"lastUpdate\":\"2019-07-14T19:40:46.794Z\"}}";
        String baseJson = "{\"+auto switch\":{\"color\":\"#99dd99\",\"defaultProfileName\":\"__ruleListOf_auto switch\",\"name\":\"auto switch\",\"profileType\":\"SwitchProfile\",\"rules\":[],\"revision\":\"16937f2184a\"},\"-addConditionsToBottom\":false,\"-confirmDeletion\":true,\"-downloadInterval\":1440,\"-enableQuickSwitch\":false,\"-monitorWebRequests\":true,\"-quickSwitchProfiles\":[],\"-refreshOnProfileChange\":true,\"-revertProxyChanges\":true,\"-showExternalProfile\":true,\"-showInspectMenu\":true,\"-startupProfileName\":\"auto switch\",\"schemaVersion\":2,\"-showConditionTypes\":0,\"+__ruleListOf_auto switch\":{\"name\":\"__ruleListOf_auto switch\",\"defaultProfileName\":\"direct\",\"profileType\":\"RuleListProfile\",\"color\":\"#99dd99\",\"format\":\"AutoProxy\",\"matchProfileName\":\"美国\",\"ruleList\":\"\", \"revision\":\"16b53e71dfb\",\"sourceUrl\":\"https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt\",\"lastUpdate\":\"2019-07-14T19:40:46.794Z\"}}";
        JSONObject base_JsonObject = JSONObject.parseObject(baseJson);

        for (Map.Entry<String, String> string : retMap.entrySet()) {
            String serverProfileName = string.getValue().toString().split("#")[1];
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
            base_JsonObject.put("+" + serverProfileName, serverProfileName_JSON);
        }

        final JSONObject autoSwitchRule = base_JsonObject.getJSONObject("+__ruleListOf_auto switch");
        if (null==ruleList) {
            try {
                final URL url = ResourceUtils.getURL("classpath:gfwlist.txt");
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


    public static String sendPost(String url, Map<String, String> map) {
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
