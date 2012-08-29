// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.Affectation;

privileged aspect Affectation_Roo_Json {
    
    public String Affectation.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Affectation Affectation.fromJsonToAffectation(String json) {
        return new JSONDeserializer<Affectation>().use(null, Affectation.class).deserialize(json);
    }
    
    public static String Affectation.toJsonArray(Collection<Affectation> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Affectation> Affectation.fromJsonArrayToAffectations(String json) {
        return new JSONDeserializer<List<Affectation>>().use(null, ArrayList.class).use("values", Affectation.class).deserialize(json);
    }
    
}
