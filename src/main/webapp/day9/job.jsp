<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String jid = request.getParameter("job_id");
String title = request.getParameter("job_title");
String max = request.getParameter("max_salary");
String min = request.getParameter("min_salary");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>JOB 등록</h1>
<p>jobid는 <%=jid %></p>
<p>job title는 <%=title %></p>
<p>job max는 <%=max %></p>
<p>job min는 <%=min %></p>
</body>
</html>