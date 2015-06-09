package nc.noumea.mairie.ws;


public interface ISirhAbsWSConsumer {

	boolean isUserApprobateur(Integer idAgent);

	boolean isUserOperateur(Integer idAgent);

	boolean isUserViseur(Integer idAgent);
}
