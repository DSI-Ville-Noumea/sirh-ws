package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.web.dto.NoeudDto;

public interface IADSWSConsumer {

	public NoeudDto getCurrentWholeTreeFromRoot();

	public NoeudDto getNoeudByIdService(Integer idService);

	public NoeudDto getDirectionPourEAE(Integer idServiceADS);

	public NoeudDto getSection(Integer idServiceADS);

	public NoeudDto getNoeudFromCodeServiceAS400(String servi);

	public NoeudDto getDirectionByIdService(Integer idServiceADS);

	public List<Integer> getListIdsServiceWithEnfantsOfService(Integer idService);

	public NoeudDto getParentOfNoeudByIdService(int idService);
}
