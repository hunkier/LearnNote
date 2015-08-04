<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>添加联系人</title>
</head>
<body>
<center><h3>添加联系人</h3></center>
<form action="AddContactServlet" method="post">
    <table align="center" border="1" width="300px">
        <tr>
            <th>姓名</th>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <th>性别</th>
            <td>
                <input type="radio" checked="checked" name="gender" value="男">男
                <input type="radio" name="gender" value="女">女
            </td>
        </tr>
        <tr>
            <th>年龄</th>
            <td><input type="text" name="age"></td>
        </tr>
        <tr>
            <th>电话</th>
            <td><input type="text" name="phone"></td>
        </tr>
        <tr>
            <th>邮箱</th>
            <td><input type="text" name="email"></td>
        </tr>
        <tr>
            <th>QQ</th>
            <td><input type="text" name="qq"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" name="保存">&nbsp;
                <input type="reset" name="重置">&nbsp;
            </td>
        </tr>
    </table>
</form>
</body>
</html>
