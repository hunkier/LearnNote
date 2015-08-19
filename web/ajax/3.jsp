<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/18
  Time: 12:41
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
    <title>显示所有的商品</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${basePath}ajax/util.js" ></script>
    <script type="text/javascript">
        window.onload=function(){
            document.getElementById("bt1").onclick=function(){
                // 发出异步请求
                var xhr = getXHR();
                xhr.onreadystatechange=function(){
                    if(xhr.readyState==4){
                        if(xhr.status==200){
                            document.getElementById("d1").innerHTML=xhr.responseText;
                        }
                    }
                }
                xhr.open("GET","${basePath}ajax/demo3?time="+new Date().getTime());
                xhr.send(null);
            }
        }
    </script>
</head>
<body>
<input type="button" id="bt1" value="显示商品"/>
<hr/>
<div id="d1"></div>
</body>
</html>
