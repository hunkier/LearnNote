<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/14
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%--引入jstl国际化和格式化标签--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <!-- 一、设置本地化对象 -->
    <fmt:setLocale value="${pageContext.request.locale}" />
    <!-- 一、设置工具类 -->
    <fmt:setBundle basename="com.hunk.learn.listener.i18n.msg" var="bundle" />
    <base href="<%=basePath%>">
    <title><fmt:message key="title" bundle="${bundle}"/> </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<form name="frmLogin" action="admin?method=login" method="post">
    <table align="center" border="1">
        <tr>
            <td><fmt:message bundle="${bundle}" key="username" /> </td>
            <td>
                <input type="text" name="userName">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="pwd" bundle="${bundle}" /> </td>
            <td>
                <input type="password" name="pwd">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="<fmt:message key="submit" bundle="${bundle}"/> ">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
