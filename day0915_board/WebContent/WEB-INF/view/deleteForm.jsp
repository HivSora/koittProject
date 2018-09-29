<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 삭제</title>
</head>
<body>
<form action="delete" method="post">
	<input type="hidden" name="no" value="${delReq.articleId }">
	<p>
		번호 : '${delReq.articleId }'을 정말 삭제하시겠습니까?
	</p>
	<input type="submit" value="글 삭제"> 
	${ctxPath=pageContext.request.contextPath;'' }
	<a href="${ctxPath }/article/read?no=${delReq.articleId}">[취소]</a>
</form>
</body>
</html>