package common.exception;

//중복시 발생처리할 예정
public class DuplicateException extends RuntimeException {
	public DuplicateException(String message) {
		super(message);
	}
}
