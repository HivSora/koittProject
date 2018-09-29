package auth.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Redirect;

import auth.service.LoginFailException;
import common.handler.CommandHandler;

public class LogoutHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//requset와 response를 줄 필요가 없으니 
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		//세션을 날리고 리다이랙트로 돌려줌
		resp.sendRedirect(req.getContextPath()+"/index.jsp");
		return null;
	
	}

	
	

}
