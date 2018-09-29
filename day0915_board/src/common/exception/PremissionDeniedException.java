package common.exception;
//권한이 없을때 발생
public class PremissionDeniedException extends RuntimeException {

	public PremissionDeniedException(String message) {
		super(message);
	}
 
}
