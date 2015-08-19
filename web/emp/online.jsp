<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/14
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>online用户列表</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
    <!--  引入头部页面  -->
    <jsp:include page="public/head.jsp"/>
    <!--  在线用户  -->
    <table align="center" border="1" width="80%" cellpadding="3" cellspacing="0">
        <tr>
            <td colspan="2" align="center"><h3>在线列表展示</h3></td>
        </tr>
        <tr>
            <td>编号</td>
            <td>名称</td>
        </tr>
        <c:if test="${not empty applicationScope.onlineList}">
            <c:forEach var="admin" items="${applicationScope.onlineList}">
                <tr>
                    <td>${admin.id}</td>
                    <td>${admin.userName}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</body>
</html>
