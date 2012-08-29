// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.Silieu;

privileged aspect Silieu_Roo_Json {
    
    public String Silieu.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Silieu Silieu.fromJsonToSilieu(String json) {
        return new JSONDeserializer<Silieu>().use(null, Silieu.class).deserialize(json);
    }
    
    public static String Silieu.toJsonArray(Collection<Silieu> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Silieu> Silieu.fromJsonArrayToSilieus(String json) {
        return new JSONDeserializer<List<Silieu>>().use(null, ArrayList.class).use("values", Silieu.class).deserialize(json);
    }
    
}
