package nc.noumea.mairie.ws;

import nc.noumea.mairie.web.dto.NoeudDto;

public interface IADSWSConsumer {

	public NoeudDto getCurrentWholeTreeFromRoot();

	public NoeudDto getNoeudByIdService(Integer idService);

	public NoeudDto getDirectionPourEAE(Integer idServiceADS);

	public NoeudDto getSection(Integer idServiceADS);

	public NoeudDto getNoeudFromCodeServiceAS400(String servi);

	public NoeudDto getDirectionByIdService(Integer idServiceADS);
}
