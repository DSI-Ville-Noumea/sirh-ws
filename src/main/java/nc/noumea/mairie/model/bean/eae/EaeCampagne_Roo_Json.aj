// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean.eae;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.eae.EaeCampagne;

privileged aspect EaeCampagne_Roo_Json {
    
    public String EaeCampagne.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public String EaeCampagne.toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }
    
    public static EaeCampagne EaeCampagne.fromJsonToEaeCampagne(String json) {
        return new JSONDeserializer<EaeCampagne>().use(null, EaeCampagne.class).deserialize(json);
    }
    
    public static String EaeCampagne.toJsonArray(Collection<EaeCampagne> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static String EaeCampagne.toJsonArray(Collection<EaeCampagne> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<EaeCampagne> EaeCampagne.fromJsonArrayToEaeCampagnes(String json) {
        return new JSONDeserializer<List<EaeCampagne>>().use(null, ArrayList.class).use("values", EaeCampagne.class).deserialize(json);
    }
    
}
