<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 작성</title>
</head>
<body>
<form action="write" method="post">
<p>
	<input type="text" name="title" value="${param.title }" placeholder="타이틀">
	<c:if test="${errors.title }">타이틀을 입력해주십시오</c:if>
</p>
<p>
	<textarea rows="5" cols="30" name="content" placeholder="내용">${param.content }</textarea>
</p>
<input type="submit" value="작성">
</form>
<a href="../index.jsp">[메인으로]</a>
</body>
</html>