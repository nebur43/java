<html>
<head>
<title>Spring REST File Upload</title>
</head>
<body>
<!-- 	<form method="POST" action="/SpringRestExample/api/rest/employee-management/employees/1/photo"
http://localhost:8080/multipart/upload
 -->
		<form method="POST" action="/multipart/upload"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>Select a file to upload</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>