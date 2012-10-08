<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-PE">
    <head>
        <meta name="charset" content="UTF-8">
        <title>Listado Veh&iacute;culos</title>
        <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.9.0/build/reset/reset-min.css">
        <link rel="stylesheet" type="text/css" href="css/default.css">
    </head>
    <body>
        <div class="main">
            <h1>Listado Veh&iacute;culos</h1>
            <ul class="listado">
                <c:forEach var="vehiculo" items="${requestScope.vehiculos}">
                    <li>
                        <span><a href="#${vehiculo.id}">${vehiculo.nombre}</a></span>
                        <span>${vehiculo.marca}</span>
                        <span>${vehiculo.modelo}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </body>
</html>
