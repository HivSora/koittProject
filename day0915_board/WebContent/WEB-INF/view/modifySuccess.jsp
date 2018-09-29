<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정</title>
</head>
<body>
게시글을 수정했습니다.
<br>
${ctxPath = pageContext.request.contextPath;"" }
<a href="${ctxPath }/article/list">[게시글 목록]</a>
<a href="${ctxPath }/article/read?no=${modReq.articleId}">[게시글 내용]</a>
<a href="../index.jsp">[메인으로]</a>
</body>
</html>