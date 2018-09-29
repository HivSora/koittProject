package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.model.User;
import user.service.JoinService;

//데이터베이스의 user 테이블에 쿼리를 실행할 클래스
public class UserDao {
	private static UserDao instance = new UserDao();
	private UserDao() {}
	public static UserDao getInstance() {return instance;}
	
		//회원 가입시 필요한 쿼리 메소드를 작성.
		//insert (등록용) , select (중복 체크용)
	
	//로그인 아이디가 중복인가를 확연하려면 당연히 loginId 값이 전달되어야한다.
	
	public User selectByLoginId(Connection conn, String loginId) throws SQLException {
		String sql = "SELECT * FROM koitt.user where loginId=? ";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, loginId);
			User user = null;
			//결과값을 받아와야한다. ResultSet에 받는다.
			//이 역시 close를 해줘야한자.
			try(ResultSet rs = pst.executeQuery()){
				if(rs.next()) {
					user = new User(rs.getInt("userId"),
							rs.getString("loginId"),
							rs.getString("name"),
							rs.getString("password"),
							rs.getTimestamp("regdate").toLocalDateTime());
				}else {
					return null;
				}
			}
			return user;
		}
	
	}
	
	//안에 내용물이 있는 user객체를 받아서 데이타 베이스에 user를 삽입
	
	public void insert(Connection conn, User user) throws SQLException {
		String sql = "insert into Koitt.user(loginId,name,password) values(?,?,?)";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, user.getLoginId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getPassword());
			pst.executeUpdate();
		}
	}
	
	//사용자 정보 수정
	
	public void update(Connection conn,User user) throws SQLException {
		String sql = "update user set name=?, password=? where userId=?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, user.getName());
			pst.setString(2, user.getPassword());
			pst.setInt(3, user.getUserId());
			pst.executeUpdate();
			
		}
		
	}

}
