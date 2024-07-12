<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Enter Details</title>
</head>
<body>
    <h1>Enter Your Details</h1>
    <form:form method="post" modelAttribute="userDetails">
        <table>
            <tr>
                <td>Name:</td>
                <td><form:input path="name" /></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path="email" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="_eventId_submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>
