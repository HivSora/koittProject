package article.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import article.service.ArticleData;
import article.service.ReadArticleService;
import common.exception.ArticleContentNotFoundException;
import common.exception.ArticleNotFoundExcption;
import common.handler.CommandHandler;
import jdbc.connector.ConnectionProvider;

public class ReadArticleHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//사용자에게 어떤 요청을 받고
		//서비스를 이용해서 화면에 보여줄 데이터를 생성하고
		//화면으로 리턴
		try{ 
			ReadArticleService readArticleService = ReadArticleService.getInstance();
			int articleId =Integer.parseInt( req.getParameter("no"));
			ArticleData articledata =  readArticleService.getArticle(articleId, true);
			req.setAttribute("articleData", articledata);
			req.setAttribute("dataContent", articledata.getContent());
			return "/WEB-INF/view/readArticle.jsp";
		}catch(ArticleNotFoundExcption | ArticleContentNotFoundException e){
			//없는 데이터라면
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}	
	}
}
