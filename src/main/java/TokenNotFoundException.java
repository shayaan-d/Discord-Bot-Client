public class TokenNotFoundException extends RuntimeException {
	public TokenNotFoundException(String message) {
		super("Token Not Found, or Token Is Invalid." + message);
	}
	public TokenNotFoundException(Throwable e) {
		super("Token Not Found, or Token Is Invalid.", e);
	}
}
