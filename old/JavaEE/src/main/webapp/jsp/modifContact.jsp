<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="cn.hunk.learn.web.contact.entity.Contact" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>修改联系人信息</title>
    <%!
        Contact contact = null;

    %>

    <%
       contact =  (Contact)session.getAttribute("contact");
    %>
</head>
<body>
<center><h3>修改联系人</h3></center>
<form action="UpdateContactServlet" method="post">
    <input type="hidden" name="id" value="${contact.id}"/>
    <table align="center" border="1" width="300px">
        <tr>
            <th>姓名</th>
            <td><input type="text" name="name" value="${contact.name}"/></td>
        </tr>
        <tr>
            <th>性别</th>
            <td>
                <input type="radio" name="gender" value="男" <c:if test="${contact.gender == '男'}" > checked="checked" </c:if>/>男
                <input type="radio" name="gender" value="女" <c:if test="${contact.gender == '女'}" > checked="checked" </c:if> />女
            <%--
                <%
                    if ("男".equals(contact.getGender())){
                %>
                男<br/>
                <input type="radio" name="gender" value="男" checked="checked"/>男
                <input type="radio" name="gender" value="女" />女
                <%
                    }else {
                %>
                女<br/>
                <input type="radio" name="gender" value="男" />男
                <input type="radio" name="gender" value="女" checked="checked"/>女
                <%
                    }
                %>
                --%>
            </td>
        </tr>
        <tr>
            <th>年龄</th>
            <td><input type="text" name="age" value="${contact.age}"/></td>
        </tr>
        <tr>
            <th>电话</th>
            <td><input type="text" name="phone" value="${contact.phone}"/></td>
        </tr>
        <tr>
            <th>邮箱</th>
            <td><input type="text" name="email" value="${contact.email}"/></td>
        </tr>
        <tr>
            <th>QQ</th>
            <td><input type="text" name="qq" value="${contact.qq}"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="保存"/>&nbsp;
                <input type="reset" value="重置"/></td>
        </tr>
    </table>
</form>
</body>
</html>
