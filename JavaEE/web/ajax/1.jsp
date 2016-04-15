<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/17
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath",basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>AJAX的编码步骤：测试异步交互是可行的</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${basePath}ajax/util.js" ></script>
    <script type="text/javascript">
        window.onload=function(){
            document.getElementById("bt1").onclick=function(){
                // 发出异步请求：步骤

                // 1、得到xhr(XMLHttpRequest)对象
                var xhr = getXHR();

                // 2、注册状态变化监听器
                xhr.onreadystatechange=function(){
                    // 做出具体的处理
                        console.info("readyState:  " + xhr.readyState);
                        console.info("xhr.status:  " + xhr.status);
                    if(xhr.readyState==4){
                        if(xhr.status==200){
                            alert("服务器已经相应结束了，去看看吧")
                        }
                    }
                }

                // 3、建立与服务器的连接
                // 如果访问的地址相同，浏览器不会真正的发出请求
                xhr.open("GET","${basePath}ajax/demo1?time=" + new Date().getTime());

                // 4、向服务器发出请求
                xhr.send(null); // 没有正文
            }
        }
    </script>
</head>
<body>
    <input id="bt1" type="button" value="点我啊"/>
</body>
</html>
