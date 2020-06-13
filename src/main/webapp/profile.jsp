<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="include/head.htm" %>
<body>
<p> ${user} profile!</p>
<p> name - ${user.firstName}</p>
<p> last name - ${user.lastName}</p>
<p> email - ${user.email}</p>
<form class="form-horizontal" action="do?command=profile" method="POST">
<!-- Button -->
<div class="form-group">
  <label class="col-md-4 control-label" for="singlebutton"></label>
  <div class="col-md-4">
    <button id="logout" name="logout" value="true" class="btn btn-success">Log Out</button>
 </div>
</div>
</body>
</html>
