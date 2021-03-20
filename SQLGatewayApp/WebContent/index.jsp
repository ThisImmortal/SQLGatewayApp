<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>
  <meta charset="utf-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
  <title>SQL Gateway Interface</title>
</head>

<body>

<c:if test="${sqlStatement == null}">
  <c:set var="sqlStatement" value="Select * from person"/>
</c:if>

<h1>The SQL Gateway</h1>
<p>Enter an SQL statement and click the Execute button</p>

<p><b>SQL statement:</b></p>
<form action="sqlGateway" method="post">
  <textarea name="sqlStatement" rows="8" cols="60">${sqlStatement}</textarea>
  <input type="submit" value="Execute"/>
</form>

<p><b>SQL result:</b></p>
${sqlResult}
</body>

</html>