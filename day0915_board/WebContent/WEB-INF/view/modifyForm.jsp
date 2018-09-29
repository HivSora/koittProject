<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정</title>
</head>
<body>
<form action="modify" method="post">
	<input type="hidden" name="no" value="${modReq.articleId }">
	<p>
		제목 : <input type="text" name="title" value="${modReq.title }">
		<c:if test="${errors.title }">제목을 입력하세요</c:if>
	</p>
	<p>
		내용 : <br>
		<textarea rows="5" cols="30" name="content">${modReq.content }</textarea>
	</p>
	<input type="submit" value="수정"> 
</form>
</body>
</html>