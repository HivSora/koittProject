package common.handler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
