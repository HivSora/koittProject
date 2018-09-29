<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>
<body>
<table border="1">
	<tr>
		<td colspan="4"><a href="write">[게시글 쓰기]</a> </td>
	</tr>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
	</tr>
	<!-- 게시글이 없을 시 -->
		<c:if test="${!articlePage.hasArticles() }"><tr><td colspan="4">아직 게시글이 존재하지않습니다.</td></tr></c:if>
	<!-- 게시글이 있을 시 -->
	
		<c:forEach var="article"  items="${articlePage.artList }">
			<tr>
				<td>${article.articleId } </td>
				<td><a href="read?no=${article.articleId }&pageNo=${articlePage.currentPage}">${article.title }</a> </td>
				<td>${article.writer.name } </td>
				<td>${article.readCnt } </td>
			</tr>
		</c:forEach>
		
	<!-- 게시글이 있을 시 페이지 블럭 -->
	<c:if test="${articlePage.hasArticles() }">
		<tr>
			<td colspan="4">
				<c:if test="${articlePage.startPage > 5 }">
					<a href="list?pageNo=${articlePage.startPage - 5 }">[이전]</a>
				</c:if>
				<c:forEach var="pNo" begin="${articlePage.startPage }" end="${articlePage.endPage }">
					<a href="list?pageNo=${pNo }">[${pNo }]</a>
				</c:forEach>
				<c:if test="${articlePage.endPage < articlePage.totalPages }">
					<a href="list?pageNo=${articlePage.startPage + 5 }">[다음]</a>
				</c:if>
			</td>
		</tr>
	</c:if>	
</table>
<a href="../index.jsp">[메인으로]</a>
</body>
</html>