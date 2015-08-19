<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/13
  Time: 17:27
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
    <title>list</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<%--<h1>欢迎你，${sessionScope.loginInfo.userName}</h1>--%>
<!--  引入头部页面  -->
<jsp:include page="public/head.jsp"></jsp:include>
<!--  列表展示数据  -->
<table align="center" border="1" width="80%" cellpadding="3" cellspacing="0">
    <tr>
        <td>序号</td>
        <td>编号</td>
        <td>员工名称</td>
    </tr>
    <c:if test="${not empty requestScope.listEmp}">
        <c:forEach var="emp" items="${requestScope.listEmp}" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${emp.empId}</td>
                <td>${emp.empName}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
