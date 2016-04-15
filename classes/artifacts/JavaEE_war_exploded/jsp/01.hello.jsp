<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/3
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>第一个jsp页面</title>
</head>
<body>
    <%
        //写java代码
        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String curTime = format.format(new Date());
        out.println("当前时间为：" + curTime);
    %>
</body>
</html>
