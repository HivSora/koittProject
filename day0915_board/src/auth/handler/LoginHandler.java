package auth.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.AuthUser;
import auth.service.LoginFailException;
import auth.service.LoginService;
import common.handler.CommandHandler;

public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	//get과 post를 나눔
	//get =  로그인 화면요청
	//post = 로그인 요청
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req,resp);
		}else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req,resp);
		}else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		//서비스 객체 받기
		LoginService loginService = LoginService.getInstance();
		//request에서 파라미터를 받음.
		String loginId = ((req.getParameter("loginId")==null)? null: req.getParameter("loginId").trim());
		String password = ((req.getParameter("password")==null)? null: req.getParameter("password").trim());
		
		Map<String, Boolean> errors = new HashMap<String,Boolean>();
		req.setAttribute("errors", errors);
		//빈값 확인
		if(loginId == null || loginId.isEmpty()) {
			errors.put("loginId", true);
		}
		if(password==null || password.isEmpty()) {
			errors.put("password", true);
		}
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			//서비스를 통해서 로그인을 실행 및 검출
			//문제가 발생시 ex 발생 바로 catch로 들어감
			AuthUser authUser = loginService.login(loginId, password);
			//성공시 세션에 로그인한 사용자의 정보를 담음
			req.getSession().setAttribute("authUser", authUser);
			//첫 화면으로 돌려줌
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			System.out.println(e.getMessage());
			errors.put("Id_Or_Pwd_NotMatch", true);
			return FORM_VIEW;
		}
		
		//비어있지않다면 서비스를 통해서 인증을 하고
		//성공하면 auth객체를 만들어 세션에 넣고
		//실패한다면 에러에 실패정보를 넣고 로그인 화면으로 다시 보냄
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {
		return FORM_VIEW;
	}
	
	

}
