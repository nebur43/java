<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Review Details</title>
</head>
<body>
    <h1>Review Your Details</h1>
    <table>
        <tr>
            <td>Name:</td>
            <td>${userDetails.name}</td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>${userDetails.email}</td>
        </tr>
    </table>
    <form method="post">
        <input type="submit" name="_eventId_confirm" value="Confirm"/>
        <input type="submit" name="_eventId_edit" value="Edit"/>
    </form>
</body>
</html>
