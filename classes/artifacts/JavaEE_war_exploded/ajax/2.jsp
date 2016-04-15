<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/17
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>检测用户名是否可用</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${basePath}ajax/util.js" ></script>
    <script type="text/javascript">
        window.onload= function () {

            document.getElementById("username").onblur=function(){
                if(this.value==""){
                    alert("请输入用户名");
                    this.focus(); // 恢复焦点
                    return;
                }
                // 发出异步请求
                var xhr = getXHR();
                xhr.onreadystatechange= function(){
                    if(xhr.readyState==4){
                        if(xhr.status==200){
                            document.getElementById("msg").innerHTML=xhr.responseText;
                        }
                    }
                }
                xhr.open("POST","${basePath}ajax/demo2?time="+new Date().getTime());
                // 设置请求消息头，告知客户端提交正文的MIME类型
                xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xhr.send("username="+this.value);
            }
        }
    </script>
</head>
<body>
<form action="" method="post">
    用户名:<input type="text" id="username" name="username"/><span id="msg"></span><br/>
    密  码:<input type="password" id="password" name="password"/><br/>
    <input type="submit" value="注册"/>
</form>
</body>
</html>
