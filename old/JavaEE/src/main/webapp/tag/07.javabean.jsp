<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/5
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="cn.hunk.learn.tag.Student" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>jsp页面使用javabean</title>
</head>
<body>
This is my first jsp page.<br/>
<%
    /*Student stu = new Student();
    stu.setName("rose");
    stu.setAge(20);
    stu.getName();*/
%>
<%--创建对象--%>
<jsp:useBean id="stu" class="cn.hunk.learn.tag.Student"/>
<%--赋值--%>
<jsp:setProperty name="stu" property="name" value="jacky"/>
<jsp:getProperty name="stu" property="name"/>

</body>
</html>
