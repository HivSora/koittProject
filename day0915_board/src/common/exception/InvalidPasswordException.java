package common.exception;

//현제 비밀번호를 잘못 입력했을시 발생시킬 예외
public class InvalidPasswordException extends RuntimeException {

	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
