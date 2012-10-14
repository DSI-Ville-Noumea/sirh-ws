// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean.eae;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.eae.EaeFichePoste;

privileged aspect EaeFichePoste_Roo_Json {
    
    public String EaeFichePoste.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static EaeFichePoste EaeFichePoste.fromJsonToEaeFichePoste(String json) {
        return new JSONDeserializer<EaeFichePoste>().use(null, EaeFichePoste.class).deserialize(json);
    }
    
    public static String EaeFichePoste.toJsonArray(Collection<EaeFichePoste> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<EaeFichePoste> EaeFichePoste.fromJsonArrayToEaeFichePostes(String json) {
        return new JSONDeserializer<List<EaeFichePoste>>().use(null, ArrayList.class).use("values", EaeFichePoste.class).deserialize(json);
    }
    
}
