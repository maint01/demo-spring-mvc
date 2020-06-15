<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Them moi</title>
</head>
<body>
<div>
  <h1>Them moi sinh vien</h1>
  <form:form modelAttribute="student" method="post" action="/save">
    <table>
      <tbody>
      <tr>
        <td>Name:</td>
        <td>
          <form:input path="name"/>
        </td>
      </tr>
      <tr>
        <td>Phone:</td>
        <td>
          <form:input path="phone"/>
        </td>
      </tr>
      <tr>
        <td>Address:</td>
        <td>
          <form:input path="address"/>
        </td>
      </tr>
      <tr>
        <td>Email:</td>
        <td>
          <form:input path="email"/>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <button type="submit">Save</button>
        </td>
      </tr>
      </tbody>
    </table>
  </form:form>
</div>
</body>
</html>
