<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 17:10
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
    <title>百万富翁数字竞猜游戏</title>
</head>
<body>
<%
    //从request中取出信息
    String msg = (String)request.getAttribute("msg");
    if (null!=msg){
        out.write("<font color='red'>" +msg +"</font>");
    }
%>
<%
    //获取竞猜次数
    Integer times = (Integer)request.getAttribute("times");
    if (null!=times){
        out.write("你还有"+ (5 - times) + "次机会！ ");
    }

%>

<form action="GuessServlet" method="post">
    请输入30以下的整数：<input type="text" name="lucyNo"><br/>
    <%
        if (times!=null){
    %>
        <input type="hidden" name="times" value="<%=times%>">
    <%
        }
    %>
    <input type="submit" value="开始竞猜">
</form>


</body>
</html>
