package user.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exception.DuplicateException;
import common.handler.CommandHandler;
import user.service.JoinRequset;
import user.service.JoinService;

//사용자의 요청을 받아서 폼 화면을 보여줄지, 데이터로 회원가입 할지 구분하여 처리
public class JoinHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	//사용자는 url board/join ->
	//form에서 전송할 action역시 board/join
	//GET 방식으로 요청이 오면 폼을 보여주는 뷰로 리턴을 하고
	//POST 방식으로 요청이 오면 회원가입을 처리하고 결과를 보여주는 뷰로 리턴
	
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
	//사용자로부터 회원가입 데이터를 입력받아
	//submit 버튼을 클릭해서 데이터가 넘어왔을때 실행되는 메소드
	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) {
		//파라미터를 통해서 입력받은 데이터를 joinRequest객체에 담음 
		JoinRequset joinRequset = new JoinRequset();
		joinRequset.setLoginId(req.getParameter("loginId"));
		joinRequset.setName(req.getParameter("name"));
		joinRequset.setPassword(req.getParameter("password"));
		joinRequset.setConfirmPassword(req.getParameter("confirmPassword"));
		
		//joinRequest를 통해 입력받은 데이터가 제대로 입력되어있는지,
		//잘못된 정보는 errors라는 맵에 넣어놓기 위해 errors라는 맵 생성
		Map<String, Boolean> errors = new HashMap<String,Boolean>();
		//errors는 view에 표출해주기 위해 request 속성값으로 넣어줌
		req.setAttribute("errors", errors);
		
		//무결성 체크
		joinRequset.validate(errors);
		//validate메소드가 지나오면, errors 맵에는 잘못된 데이터필드명(키값)과 함께 true라는 value값이
		//추가되어 있다.
		
		//잘못들어왔다면 다시 폼화면으로 반환
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		//잘 들어오면 joinService를 통해 회원가입 로직 실행
		JoinService joinService = JoinService.getInstance();
		try {
			//join인 로직은 아이디가 중복일 때 예외를 여기로 던짐
			joinService.join(joinRequset);
			//성공화면 반환
			return "/WEB-INF/view/joinSuccess.jsp";
			
		}catch(DuplicateException e) {
			//아이디가 중복일 때 service에서 발생시킨 예외를 받아서 처리
			errors.put("duplicateId", true);
			return FORM_VIEW;
		}
		
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {
		return FORM_VIEW;
	}

	
	
}
