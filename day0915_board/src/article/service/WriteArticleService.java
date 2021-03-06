package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.connector.ConnectionProvider;

public class WriteArticleService {
	private static WriteArticleService instance = new WriteArticleService();
	private WriteArticleService() {}
	public static WriteArticleService getInstance () {return instance;}
	
	public int write(WriteRequest wr) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();
		//게시글의 테이블이 두개가 있고, 두개의 articleId는 같아야 무결성을 해치지않는다
		//따라서 두개의 articleId는 동기화되어야하고
		//article이 삽입 됐을 때 articleId가 생성되기 때문에
		//삽입후 select를 하여 articleId를 받아온다
		//그리고 그 articleId를 content(내용)삽입시 사용하여 articleId가 같게 처리한다.
		
		try(Connection conn = ConnectionProvider.getConnection()){
			try {
			//article -> articleId를 받을수 있음. article_content  articleId 같아야함
			//article이 성공하고 article_content실패면 안되니 롤백해줘야함
			conn.setAutoCommit(false);
			
			Article article = new Article(wr.getWriter(), wr.getTitle());
			Article savedArticle = articleDao.insert(conn, article);
			if(savedArticle==null) {
				throw new RuntimeException("게시글 삽입 실패");
			}
			ArticleContent content = new ArticleContent(savedArticle.getArticleId(), wr.getContent());
			ArticleContent savedcontent = articleContentDao.insert(conn, content);
			if(savedcontent==null) {
				throw new RuntimeException("content 삽입 실패");
			}
			conn.commit();
			return savedArticle.getArticleId();
			}catch(SQLException e) {
				conn.rollback();
				throw new RuntimeException();
			}catch (RuntimeException e) {
				conn.rollback();
				throw e;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
