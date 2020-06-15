<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết sinh viên</title>
</head>
<body>
<table>
    <tbody>
    <tr>
        <td>ID</td>
        <td>${data.id}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${data.name}</td>
    </tr>
    <tr>
        <td>Phone</td>
        <td>${data.phone}</td>
    </tr>
    <tr>
        <td>Address</td>
        <td>${data.address}</td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <form action="/student">
                <button>Back to List</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
