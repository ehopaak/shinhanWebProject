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
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  
<meta charset="UTF-8">
<script>

$(function(){
	$("#btn1").click(function() {
		$("tr:nth-child(2n)").css("background-color","green");
    });
	$("#btn2").click(function() {
		$("tr > td:nth-child(2):contains(S)").css("background-color","green");
    });
	$("#btn3").click(function() {
		
	var arr =	$("tr > td:nth-child(5)").css("background-color","lightblue");
    });
	
});

#container > table > tbody > tr:nth-child(1) > td:nth-child(5)

</script>
<style>

	#container { 
        width:1200px;  
        padding:15px;  
        border:1px dotted gray;  
      }
	@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
	h1{
		font-family: 'Jeju Gothic', sans-serif;
		border: 2px solid black; 
  		padding: 10px; 
  		margin: 10px; 
	}
	.align-left {text-align:left;}
	th{
	
		font-family: 'Jeju Gothic', sans-serif;
	}
	.align- {text-align:left;}
	tr:hover{
		background-color: pink;
	}
	.align-center {text-align:center;}
	div{
      	background-color: lightgreen;
      }
</style>

<title>Insert title here</title>
</head>
<body>
	<div id="container">    
	<h1>직원목록</h1>
	<button 
	onclick="lacation.href='emp_insert.html'"
	type="button" class="btn btn-success">직원등록</button>
	<a type="button" class="btn btn-success" href="e"></a>
	<hr>
	<button id="btn1">짝수row선택</button>
	<button id="btn2">이름 S로 시작하는 직원</button>
	<button id="btn3">급여가 10000 이상인 직원</button>
	
	
	
	
	<table class="table table-hover">
	<thead>
	<tr class= "align-center" style="background-color:powderblue;">
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
	<tr class="align-center">
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
</div>
</body>
</html>