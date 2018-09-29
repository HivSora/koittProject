package user.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.AuthUser;
import common.exception.InvalidPasswordException;
import common.exception.UserNotFoundException;
import common.handler.CommandHandler;
import user.service.ChangePasswordService;

public class ChangePasswordHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/changePwdForm.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// 입력받은 데이타(비밀번호)가 비었는지  무결성 체크후
	//비번 화면으로 돌려보내거나 성공화면으로 보냄
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req,resp);
		}else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req,resp);
		}else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//form_view로 부터 입력받은 비밀번호의 무결성 검증을 해야하고
		//세션에 있는 auth객체로부터 로그인ID정보를 받아오고 
		//서비스를 이용해서 비번 변경 수행
		AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
		
		Map<String, Boolean> errors = new HashMap<String,Boolean>();
		req.setAttribute("errors", errors);
		String oldPwd = req.getParameter("oldPwd");
		String newPwd = req.getParameter("newPwd");
		
		if(oldPwd == null || oldPwd.isEmpty()) {
			errors.put("oldPwd", true);
		}
		if(newPwd==null || newPwd.isEmpty()) {
			errors.put("newPwd",true);
		}
		if(newPwd.equals(oldPwd)) {
			errors.put("samePwd", true);
		}
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			ChangePasswordService changePasswordService = ChangePasswordService.getInstanse();
			changePasswordService.changePassword(authUser.getLoignId(), oldPwd, newPwd);
			return "/WEB-INF/view/changePwdSuccess.jsp";
		}catch(InvalidPasswordException e) {
			errors.put("wrongOldPwd", true);
			return FORM_VIEW;
		}catch(UserNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {
		return FORM_VIEW;
	}
	
	

}
