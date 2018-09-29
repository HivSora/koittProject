package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import common.exception.ArticleNotFoundExcption;
import common.exception.PremissionDeniedException;
import jdbc.connector.ConnectionProvider;

public class DeleteArticleService {
	private static DeleteArticleService instance = new DeleteArticleService();
	private DeleteArticleService() {}
	public static DeleteArticleService getInstance() {return instance;}
	
	
	public void delete(DeleteRequest mr) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			try {
				conn.setAutoCommit(false);
				Article article = articleDao.selectById(conn, mr.getArticleId());
				//게시글 존재 확인
				if(article == null) {
					throw new ArticleNotFoundExcption("존재하지않는 게시글");
				}
				//사용자 권한 확인
				if(article.getWriter().getWriterId() != mr.getUserId()) {
					throw new PremissionDeniedException("사용자 권한 없음");
				}
				
				articleDao.delete(conn, mr.getArticleId());
				articleContentDao.delete(conn, mr.getArticleId());
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
