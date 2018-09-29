<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 게시판 만들기</title>
</head>
<body>
<c:if test="${! empty authUser.name }">
${authUser.name }님, 안녕하세요<br>
<a href="logout">[로그아웃하기]</a><br>
<a href="changePwd">[비밀번호 변경]</a><br>
<br>
<br>
<a href="article/list">[게시글 보기]</a>
<a href="article/write">[게시글 작성]</a><br>
</c:if>

<c:if test="${empty authUser.name }">
<a href="join">[회원가입]</a><br>
<a href="login">[로그인]</a><br>
</c:if>

</body>
</html>