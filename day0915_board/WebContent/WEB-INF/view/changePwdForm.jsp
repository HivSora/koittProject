<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 변경</title>
</head>
<body>
<form action="changePwd" method="post">
	<p>
		<input type="password" name="oldPwd" placeholder="현재 비밀번호">
		<br><c:if test="${errors.oldPwd }">현재 비밀번호를 입력해 주십시오.</c:if>
		<br><c:if test="${errors.wrongOldPwd }">잘못된 비밀번호 입니다.</c:if>
	</p>
	<p>
		<input type="password" name="newPwd"  placeholder="새 비밀번호">
		<br><c:if test="${errors.samePwd }">현재 비밀번호와 새 비밀번호가 일치합니다</c:if>
		<br><c:if test="${errors.newPwd }">새 비밀번호를 입력해 주십시오.</c:if>
	</p>
	<input type="submit" value="비밀번호 변경">
</form>
<a href="index.jsp">[메인으로]</a>
</body>
</html>