// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.Spadmn;

privileged aspect Spadmn_Roo_Json {
    
    public String Spadmn.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public String Spadmn.toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }
    
    public static Spadmn Spadmn.fromJsonToSpadmn(String json) {
        return new JSONDeserializer<Spadmn>().use(null, Spadmn.class).deserialize(json);
    }
    
    public static String Spadmn.toJsonArray(Collection<Spadmn> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static String Spadmn.toJsonArray(Collection<Spadmn> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<Spadmn> Spadmn.fromJsonArrayToSpadmns(String json) {
        return new JSONDeserializer<List<Spadmn>>().use(null, ArrayList.class).use("values", Spadmn.class).deserialize(json);
    }
    
}
