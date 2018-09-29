package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//로그인여부확인 체크 필터
public class LoginCheckFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//세션을 받아서 세션이 살아있는지 확인을 한다.
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("authUser")==null) {
			HttpServletResponse response = (HttpServletResponse)resp;
			//세션이 없으면 로그인 페이지로 이동			
			response.sendRedirect(request.getContextPath()+"/login");
		}else {
			//세션이 있으면???
			//요청한 기능이 있는곳으로 보내버림		
			chain.doFilter(req, resp);
		}
		

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {


	}

}
