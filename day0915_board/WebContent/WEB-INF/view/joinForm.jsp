<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
<form action="join" method="post">
	<p>
		<input type="text" name="loginId" value="${param.loginId }" placeholder="아이디">
		<br><c:if test="${errors.loginId }">아이디를 입력하세요.</c:if>
		<br><c:if test="${errors.duplicateId }">이미 사용중인 아이디입니다.</c:if>
	</p>
	<p>
		<input type="text" name="name" value="${param.name }" placeholder ="이름">
		<br><c:if test="${errors.name }">이름을 입력하세요.</c:if>
	</p>
	<p>
		<input type="password" name="password" value="${param.password }" placeholder ="비밀번호">
		<br><c:if test="${errors.password }">비밀번호를 입력하세요.</c:if>
	</p>
	<p>
		<input type="password" name="confirmPassword" value="${param.confirmPassword }" placeholder ="비밀번호 확인">
		<br><c:if test="${errors.confirmPassword}">비밀번호 확인을 입력하세요.</c:if>
		<br><c:if test="${errors.notMatch }">비밀번호가 일치하지않습니다.</c:if>
	</p>
	<input type="submit" value="가입">
</form>
<a href="index.jsp">[메인으로]</a>
</body>
</html>