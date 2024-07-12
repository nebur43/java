<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Calculator</title>
</head>
<body>
    <h1>Calculadora</h1>
    <form:form method="post" modelAttribute="calculadoraBean">
        <table>
            <tr>
                <td>numero1:</td>
                <td><form:input path="a" /></td>
            </tr>
            <tr>
                <td>numero2:</td>
                <td><form:input path="b" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="_eventId_sumar" value="Sumar"/>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>
