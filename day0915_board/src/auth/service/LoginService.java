package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connector.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

//사용자로 부터 입력받은 데이터를 통해

public class LoginService {
	private static LoginService instance = new LoginService();
	private LoginService() {}
	public static LoginService getInstance() {return instance;}
	
	//성공시 
	public AuthUser  login(String loginId, String password) throws SQLException {
		UserDao userDao = UserDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			User user = userDao.selectByLoginId(conn, loginId);
			
			//사용자가 없다면 (아이디가 디비에 없을시
			if(user==null) {
				throw new LoginFailException("없는사용자");
			}
			//비밀번호랑 아이디가 맞는지
			if(!user.matchPassword(password)) {
				throw new LoginFailException("일치하지않는 비밀번호");
			}
			
			//인증이 완료되었으니 auth객체를 반환
			return new AuthUser(user.getLoginId(), user.getName(),user.getUserId());
		}
		//아이디를 통해 사용자 객체를 select해서 가져옴
		//객체가 있으면 비밀번호를 비교
		//객체가 없음 없는 사용자라고 예외를 날림
	}
	
}
