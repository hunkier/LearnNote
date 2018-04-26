<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/18
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>5</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${basePath}ajax/util.js" ></script>
    <script type="text/javascript">
        var xmlDoc;
        window.onload=function(){
            // 发出异步请求
            var xhr=getXHR();
            xhr.onreadystatechange=function(){
                if(xhr.readyState==4){
                    if(xhr.status==200){
                        xmlDoc=xhr.responseXML;

                        // 给省份赋值：XML DOM和HTML DOM
                        // -------------------------------------
                        // 1、解析XML文档，得到所有的province元素
                        var xmlProvinces = xmlDoc.getElementsByTagName("province");
                        // 2、遍历province元素，得到他的属性code和name值
                        for(var i=0;i<xmlProvinces.length;i++){
                            // 找到了：把他们的子元素city取出来
                            var codeValue = xmlProvinces[i].getAttribute("code");
                            var nameValue = xmlProvinces[i].getAttribute("name");
                            // 创建HTML中的option对象，给id=p1的对象添加子元素
                            var o =  new Option(nameValue,codeValue);
                            document.getElementById("p1").add(o);
                        }
                    }
                }
            }
            xhr.open("GET","${basePath}ajax/demo4?time="+new Date().getTime());
            xhr.send(null);
        }

        // 省份变化，引起城市变化
        function selectCity(provinceObj){

            // 取到当前的选项的值
            var selectValue = provinceObj.value;
           // alert(selectValue);
            // 清除城市下拉内容
            document.getElementById("c1").length=1;
            // 遍历xml文档中的省份元素，比对code的值
            var xmlProvinces = xmlDoc.getElementsByTagName("province");
            for(var i=0;i<xmlProvinces.length;i++){
                // 找到了：把他的子元素city取出来
                var code = xmlProvinces[i].getAttribute("code");
               // alert(code);
                if(selectValue == code){
                    var xmlCitys = xmlProvinces[i].getElementsByTagName("city");
                   // alert(xmlCitys);
                    for(var j=0;j<xmlCitys.length;j++){
                        var nameValue = xmlCitys[j].getAttribute("name");
                        var cityValue = xmlCitys[j].getAttribute("city");
                       // alert(nameValue + "  " + cityValue);
                        var o = new Option(nameValue,cityValue);
                        document.getElementById("c1").add(o);
                    }
                    return;
                }
            }
        }
    </script>
</head>
<body>
This is my first jsp page.<br/>
省份：<select id="p1" onchange="selectCity(this)">
    <option value="">--请选择--</option>
</select><br/>
城市：<select id="c1" >
    <option value="">--请选择--</option>
</select>
</body>
</html>
