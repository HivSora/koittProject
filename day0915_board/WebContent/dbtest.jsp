<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.connector.ConnectionProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	try(Connection conn =  ConnectionProvider.getConnection()){
		out.print("성공");
	}catch(SQLException e){
		out.print("실패! "+e.getMessage());
	}
%>
</body>
</html>