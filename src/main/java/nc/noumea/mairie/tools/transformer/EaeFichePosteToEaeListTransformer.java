package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.eae.EaeFichePoste;
import flexjson.BasicType;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;
import flexjson.transformer.Inline;

public class EaeFichePosteToEaeListTransformer extends AbstractTransformer
		implements Inline {

	@Override
	public Boolean isInline() {
		return Boolean.TRUE;
	}

	@Override
	public void transform(Object object) {

		TypeContext typeContext = getContext().peekTypeContext();
	    
	    boolean isObjectNotInline = false;
	    
	    if (typeContext == null || typeContext.getBasicType() != BasicType.OBJECT) {
	        typeContext = getContext().writeOpenObject();
	        isObjectNotInline = true;
	    }
	    
	    EaeFichePoste fdp = (EaeFichePoste) object;
	    
	    if (!typeContext.isFirst()) 
	    	getContext().writeComma();
	    
	    typeContext.setFirst(false);

	    getContext().writeComma();
	    getContext().writeName("service");
	    getContext().transform(fdp.getCodeService());
	    
	    getContext().writeComma();
	    getContext().writeName("agentShd");
	    getContext().transform(fdp.getAgentShd());
	    
	    if (isObjectNotInline) {
            getContext().writeCloseObject();
        }
	}

}
