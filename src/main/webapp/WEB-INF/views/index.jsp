<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <title>Нарушения</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
            </div>
            <div class="card-body">
                <div><a href="<c:url value='/create'/>">Добавить инцидент</a></div>
                <table class="table table-bordered" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th style="text-align: center">Название</th>
                        <th style="text-align: center">Описание</th>
                        <th style="text-align: center">Адрес</th>
                        <th style="text-align: center">Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${accidents}" var="acc">
                        <tr>
                            <td><c:out value="${acc.name}"/></td>
                            <td><c:out value="${acc.text}"/></td>
                            <td><c:out value="${acc.address}"/></td>
                            <td>
                                <a href="<c:url value='/update?id=${acc.id}'/>">Изменить</a>
                                <a href="<c:url value='/delete?id=${acc.id}'/>">Удалить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
