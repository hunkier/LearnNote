<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/5
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="hunk" uri="http://hunk.com.cn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>第一个自定义标签</title>
</head>
<body>
This is my first jsp page.<br/>
<%
    // 获取当前客户的ip地址
    String ip = request.getRemoteHost();
    out.write("当前客户的IP是：" + ip);
%>
<br/>
<%--使用标签库中的标签--%>
<hunk:ShowIpTag/>
</body>
</html>
