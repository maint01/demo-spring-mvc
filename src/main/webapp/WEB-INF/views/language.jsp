<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Change Language</title>
</head>
<body>
<div>
  Language :
  <a href="${pageContext.request.contextPath}/language?language=en">English</a>|
  <a href="${pageContext.request.contextPath}/language?language=vi">Viet Nam</a>|

  <h2>hello :  <spring:message code="hello" text="default text" /></h2>

  Current Locale : ${pageContext.response.locale}
</div>
</body>
</html>
