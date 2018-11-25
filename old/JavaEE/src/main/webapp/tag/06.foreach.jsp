<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/5
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="cn.hunk.learn.tag.Student" %>
<%@ taglib prefix="hunk" uri="http://hunk.com.cn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>forEach标签</title>
</head>
<body>
<%
    //保存数据
    List<Student> list = new ArrayList<Student>();
    list.add(new Student("rose", 18));
    list.add(new Student("jack", 28));
    list.add(new Student("lucy", 38));
    // 放入域中
    pageContext.setAttribute("list", list);

    //Map
    Map<String, Student> map = new HashMap<String, Student>();
    map.put("100", new Student("mark", 20));
    map.put("101", new Student("maxwell", 30));
    map.put("102", new Student("narci", 40));
    //放入域中
    pageContext.setAttribute("map", map);
%>
This is my first jsp page.<br/>
<hunk:forEach items="${list}" var="student">
    姓名:${student.name}  -  年龄：${student.age}<br/>
</hunk:forEach>
<hr/>
<hunk:forEach items="${map}" var="entry">
   编号：${entry.key} 姓名:${entry.value.name}  -  年龄：${entry.value.age}<br/>
</hunk:forEach>
</body>
</html>
