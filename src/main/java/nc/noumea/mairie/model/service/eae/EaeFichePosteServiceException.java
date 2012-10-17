package nc.noumea.mairie.model.service.eae;

public class EaeFichePosteServiceException extends Exception  {

	private static final long serialVersionUID = 1L;

	public EaeFichePosteServiceException() {
		super();
	}
	
	public EaeFichePosteServiceException(String message) {
		super(message);
	}
	
	public EaeFichePosteServiceException(String message, Exception innerException) {
		super(message, innerException);
	}
}
