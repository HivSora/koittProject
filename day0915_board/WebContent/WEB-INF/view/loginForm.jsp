<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>
<form action="login" method="post">
<c:if test="${errors.Id_Or_Pwd_NotMatch }">아이디나 패스워드가 일치하지않습니다.</c:if>
	<p>
		<input type="text" name="loginId" value="${param.loginId }" placeholder="아이디" >
		<br><c:if test="${errors.loginId }">아이디를 입력하세요</c:if>
	</p>
	<p>
		<input type="password" name="password" value="${param.password }" placeholder="비밀번호" >
		<br><c:if test="${errors.password }">비밀번호를 입력하세요</c:if>
	</p>
	<input  type="submit" value="로그인">
</form>
<a href="index.jsp">[메인으로]</a>
</body>
</html>