<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="include/head.htm" %>
<body>
<%@ include file="include/menu.htm" %>
<div class="row">
    <div class="col-md-1">Articles</div>
</div>
<c:forEach items="${posts}" var="post">
    <br>
    <div class="col-md-1">${post.title} </div>
    <br>
    <div class="col-md-1">${post.content} </div>
    <br>
</c:forEach>
</body>
</html>
