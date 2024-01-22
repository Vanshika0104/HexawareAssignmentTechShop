package exception;

public class UnavailableProductException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public UnavailableProductException(String message) {
        super(message);
    } 
 
}
