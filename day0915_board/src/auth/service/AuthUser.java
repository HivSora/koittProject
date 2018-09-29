package auth.service;

//로그인 인증에 사용할 객체
//로그인상태 확인용 객체
public class AuthUser {
	private int userId;
	private String LoignId;
	private String name;
	
	public AuthUser(String loignId, String name, int userId) {
		this.userId = userId;
		this.LoignId = loignId;
		this.name = name;
	}
	
	public String getLoignId() {
		return LoignId;
	}

	public String getName() {
		return name;
	}

	public void setLoignId(String loignId) {
		LoignId = loignId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
}
