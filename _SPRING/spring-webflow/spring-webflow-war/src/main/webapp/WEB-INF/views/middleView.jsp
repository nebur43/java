<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>Middle View</h1>
    <form:form method="post">
    	<input type="submit" name="_eventId_cancel" value="BACK"/>
        <input type="submit" name="_eventId_proceed" value="NEXT"/>
    </form:form>
</body>
</html>
