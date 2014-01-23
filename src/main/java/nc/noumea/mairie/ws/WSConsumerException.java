package nc.noumea.mairie.ws;

public class WSConsumerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WSConsumerException() {
		super();
	}

	public WSConsumerException(String message) {
		super(message);
	}

	public WSConsumerException(String message, Exception innerException) {
		super(message, innerException);
	}
}
