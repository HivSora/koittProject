package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import common.exception.ArticleNotFoundExcption;
import common.exception.PremissionDeniedException;
import jdbc.connector.ConnectionProvider;

public class ModifyArticleService {
	private static ModifyArticleService instance = new ModifyArticleService();
	private ModifyArticleService() {}
	public static ModifyArticleService getInstance() {return instance;}
	
	//게시글이 있는지 확인,
	//사용자 권한이 있는지 확인,
	//articleDao, articleContentDao를 이용해서 게시글 수정 메소드 실행
	public void modify(ModifyRequest mr) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			try {
				conn.setAutoCommit(false);
				Article article = articleDao.selectById(conn, mr.getArticleId());
				if(article == null) {
					throw new ArticleNotFoundExcption("존재하지않는 게시글");
				}
				if(article.getWriter().getWriterId() != mr.getUserId()) {
					throw new PremissionDeniedException("사용자 권한 없음");
				}
				articleDao.update(conn, mr.getArticleId(), mr.getTitle());
				articleContentDao.update(conn, mr.getArticleId(), mr.getContent());
				conn.commit();
			}catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException();
			}catch (PremissionDeniedException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
				throw new RuntimeException();
		}
	}
}
