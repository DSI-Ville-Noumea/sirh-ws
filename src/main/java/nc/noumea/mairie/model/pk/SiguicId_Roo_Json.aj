// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.pk;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.pk.SiguicId;

privileged aspect SiguicId_Roo_Json {
    
    public String SiguicId.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static SiguicId SiguicId.fromJsonToSiguicId(String json) {
        return new JSONDeserializer<SiguicId>().use(null, SiguicId.class).deserialize(json);
    }
    
    public static String SiguicId.toJsonArray(Collection<SiguicId> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<SiguicId> SiguicId.fromJsonArrayToSiguicIds(String json) {
        return new JSONDeserializer<List<SiguicId>>().use(null, ArrayList.class).use("values", SiguicId.class).deserialize(json);
    }
    
}
