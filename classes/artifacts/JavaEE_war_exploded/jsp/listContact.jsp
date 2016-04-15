<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, com.hunk.learn.web.contact.entity.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>所有联系人信息</title>
</head>
<body>
<center><h3>查询所以联系人（jsp版本）</h3></center>
<table align="center" border="1" width="700px">
    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>年龄</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>QQ</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${contacts}" var="con" varStatus="varSta">
        <tr>
            <td>${varSta.count}</td>
            <td>${con.name}</td>
            <td>${con.gender}</td>
            <td>${con.age}</td>
            <td>${con.phone}</td>
            <td>${con.email}</td>
            <td>${con.qq}</td>
            <td>
                <a href="QueryContactServlet?id=${con.id}">修改</a>&nbsp;
                <a href="DeleteContactServlet?id=${con.id}">删除</a>
            </td>
        </tr>
    </c:forEach>

   <%-- <%
        // 从request域中接收数据
        List<Contact> list = (List<Contact>)request.getAttribute("contacts");
        for (Contact con : list) {

    %>
    <tr>
        <td><%=con.getId()%></td>
        <td><%=con.getName()%></td>
        <td><%=con.getGender()%></td>
        <td><%=con.getAge()%></td>
        <td><%=con.getPhone()%></td>
        <td><%=con.getEmail()%></td>
        <td><%=con.getQq()%></td>
        <td>
            <a href="QueryContactServlet?id=<%=con.getId()%>">修改</a>&nbsp;
            <a href="DeleteContactServlet?id=<%=con.getId()%>">删除</a>
        </td>
    </tr>
    <%
        }
    %>
    --%>
    <tr>
        <td colspan="8" align="center">
            <a href="jsp/addContact.jsp">[添加联系人]</a>
        </td>
    </tr>
</table>
</body>
</html>
