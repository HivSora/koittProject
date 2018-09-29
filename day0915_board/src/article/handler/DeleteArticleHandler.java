package article.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.ReadArticleService;
import auth.service.AuthUser;
import common.exception.ArticleNotFoundExcption;
import common.exception.PremissionDeniedException;
import common.handler.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
private static final String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, resp);
		}else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req,resp);
		}else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;	
		}
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
		String noStr = req.getParameter("no");
		int no = Integer.parseInt(noStr);
		
		DeleteRequest delReq =
				new DeleteRequest(authUser.getUserId(), no);
		req.setAttribute("delReq", delReq);
		
		//modifyRequest에 수정 내용들을 파라미터에서 받아서 담고
		//에러를 체크하고
		//서비스를 통해서 수정을 실행후
		//수정 성공화면으로 전환
		try {
			DeleteArticleService deleteArticleService = DeleteArticleService.getInstance();
			deleteArticleService.delete(delReq);
			return "/WEB-INF/view/deleteSuccess.jsp";
		}catch(ArticleNotFoundExcption e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}catch(PremissionDeniedException e) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
	
	private boolean canDelete(AuthUser authUser, ArticleData articleData) {
		int writeId = articleData.getArticle().getWriter().getWriterId();
		return authUser.getUserId() ==  writeId;
	}
	

	private String processForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReadArticleService readArticleService = ReadArticleService.getInstance();
		
		try {
			//게시글의 아이디를 받아서 
			String noStr = req.getParameter("no");
			int no = Integer.parseInt(noStr);
			
			//게시글을 읽어오는 서비스를 이용하여 데이터를 받아온다.
			ArticleData articleData = readArticleService.getArticle(no, false);
			
			//사용자가 수정을 할수 있는 권한이 있는지 확인
			AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
			
			if(!canDelete(authUser, articleData)) {
				//없으면 에러를 response에 담아서 보냄 SC_FORBIDDEN
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			//있으면 modifyRequest에 담아서 수정화면을 보여줌
			DeleteRequest delRequest = 
					new DeleteRequest(authUser.getUserId(), no);
			req.setAttribute("delReq", delRequest);
			
			return FORM_VIEW;
			
		}catch(ArticleNotFoundExcption e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
