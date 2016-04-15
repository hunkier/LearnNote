<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/3
  Time: 14:43
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
    <title>Jsp语法</title>
</head>
<body>
    <%--jsp表达式--%>
    <%
        // 变量
        String name = "eric";
        int a = 10;
        int b =20;
    %>
    <%=name%>
    <br/>
    <%=(a-b)%>
    <hr/>
    <%--jsp脚本--%>
    <%
        // 生成随机数
        Random ran = new Random();
        float num = ran.nextFloat();
    %>
    随机小数：<%=num %>
    <hr/>
    <%--穿插html代码--%>
    <%
        for (int i = 0; i <= 6; i++) {
            %>
                <h<%=i%>>标题<%=i%></h>
            <%
        }
    %>
    <hr/>
    <%--练习：使用脚步和html代码显示99乘法表--%>
    <%
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (j <=i){
                    %>
                        <%=i %> X <%=j %>=<%=(i*j) %>&nbsp;
                    <%
                }
            }
            %>
                <br/>
            <%
        }
    %>

<%
    String age = "20";
    /*脚本中不能声明
    public String getAge(){
    return; age;
    }
    */
%>

<%--jsp声明--%>
<%!
    //变量
    String name = "jacky";
    public String getName(){
        return name;
    }
%>

    <!--html注释 -->
    <%--jsp注释--%>
    <%--<jsp:forward page="01.hello.jsp"></jsp:forward>--%>


</body>
</html>
