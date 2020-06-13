<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="include/head.htm" %>
<body>
<div class="row">
    <div class="col-md-1">Articles</div>
</div>
<c:forEach items="${posts}" var="post">
    <br>
    <div class="row">
        <div class="col-md-1">${post.title} </div>
        <div class="col-md-1">${post.content} </div>
    </div>
</c:forEach>
</body>
</html>
