<%@page import="java.util.List"%>
<%@page import="aproject.vo.EmpVO"%>
<%@page import="aproject.vo.EmpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
EmpService eService = new EmpService();

List<EmpVO> emplist = eService.selectAll();

%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>직원목록</h1>
	<table>
	<thead>
	<tr>
		<th>직원번호</th>
		<th>이름</th>
		<th>성</th>
		<th>이메일</th>
		<th>급여</th>
		<th>입사일</th>
		<th>전화번호</th>
		<th>직책</th>
		<th>매니저</th>
		<th>커미션</th>
		<th>부서</th>
	</tr>
	</thead>
	<tbody>
	<% for(EmpVO emp:emplist){%>
	<tr>
		<td><%=emp.getEmployee_id() %></td>
		<td><%=emp.getFirst_name() %></td>
		<td><%=emp.getLast_name() %></td>
		<td><%=emp.getEmail() %></td>
		<td><%=emp.getSalary() %></td>
		<td><%=emp.getHire_date() %></td>
		<td><%=emp.getPhone_number() %></td>
		<td><%=emp.getJob_id() %></td>
		<td><%=emp.getManager_id() %></td>
		<td><%=emp.getCommission_pct() %></td>
		<td><%=emp.getDepartment_id() %></td>
	
	
	</tr>
	<%} %>
	
	</tbody>
	
	</table>

</body>
</html>