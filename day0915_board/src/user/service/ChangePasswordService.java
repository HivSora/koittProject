package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import common.exception.InvalidPasswordException;
import common.exception.UserNotFoundException;
import jdbc.connector.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

public class ChangePasswordService {
	private static ChangePasswordService instanse = new ChangePasswordService();
	private ChangePasswordService() {}
	public static ChangePasswordService getInstanse() {return instanse;}
	
	//비밀번호를 변경하기 위한 비즈니스 로직을 수행
	//로그인아이디, 현 비번, 새 비번을 인자로 받고
	//그것을 통해서 비번이 제대로 되어있는지, 현재 있는 사용자인지 확인
	//로직 수행
	
	public void  changePassword(String loginId, String oldPwd, String newPwd) {
		UserDao userDao = UserDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			//user 객체를 받아오고
			//user 객체와 입력받은 비밀번호를 비교
			//정상이면 update, 아니면 예외를 날리고 롤백
			try {
				conn.setAutoCommit(false);
				//user객체를 받아옴
				User user = userDao.selectByLoginId(conn, loginId);
				
				//user가 없다면
				if(user==null) {
					throw new UserNotFoundException("존재하지않는 유저");
				}
				//비밀번호를 잘못 입력했다면
				if(!user.matchPassword(oldPwd)) {
					throw new InvalidPasswordException("일치하지않는 비밀번호");
				}
				
				
				//정상적으로 비번이 입력되었다면!
				//업데이트에 보낼 객체의 비번을 바꿔 입력
				user.setPassword(newPwd);
				userDao.update(conn, user);
				conn.commit();
				
			}catch(SQLException e) {
				conn.rollback();
				throw new RuntimeException(e);
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
