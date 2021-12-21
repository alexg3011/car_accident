<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<form action="<c:url value='/update?id=${accident.id}'/>"
      method='POST'>
    <table style="font-size: larger">
        <tr>
            <td>Название:</td>
            <td>
                <input type='text' name='name' value="${accident.name}">
            </td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form>
</body>
</html>