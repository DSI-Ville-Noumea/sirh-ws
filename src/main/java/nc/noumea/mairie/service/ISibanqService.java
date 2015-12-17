package nc.noumea.mairie.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.web.dto.BanqueGuichetDto;

public interface ISibanqService {

	public Sibanq getBanque(Integer codeBanque);

	public List<BanqueGuichetDto> getListBanqueAvecGuichet();
}
