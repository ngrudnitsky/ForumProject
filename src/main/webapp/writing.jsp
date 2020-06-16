<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="/include/head.htm" %>
<body>
<%@ include file="include/menu.htm" %>
<form class="form-horizontal" action="do?command=writing" method="POST">
<fieldset>

<!-- Form Name -->
<legend>Create new Post</legend>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="username">Title</label>
  <div class="col-md-4">
  <input id="title" name="title" value="" type="text" placeholder="" class="form-control input-md" required="">
  </div>
</div>

<!-- Password input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="content">Content</label>
  <div class="col-md-4">
    <input id="content" name="content" value="" type="text" placeholder="" class="form-control input-md" required="">

  </div>
</div>

<!-- Button -->
<div class="form-group">
  <label class="col-md-4 control-label" for="singlebutton"></label>
  <div class="col-md-4">
    <button id="singlebutton" name="singlebutton" class="btn btn-success">Publish</button>
  </div>
</div>

</fieldset>
</form>

</body>
</html>

