<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Upload file</title>
</head>
<body>
<h1>Upload file</h1>
<form action="/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="file"/>
  <button type="submit">Submit</button>
</form>
</body>
</html>
