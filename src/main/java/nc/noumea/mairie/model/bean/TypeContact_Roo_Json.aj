// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.TypeContact;

privileged aspect TypeContact_Roo_Json {
    
    public String TypeContact.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static TypeContact TypeContact.fromJsonToTypeContact(String json) {
        return new JSONDeserializer<TypeContact>().use(null, TypeContact.class).deserialize(json);
    }
    
    public static String TypeContact.toJsonArray(Collection<TypeContact> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<TypeContact> TypeContact.fromJsonArrayToTypeContacts(String json) {
        return new JSONDeserializer<List<TypeContact>>().use(null, ArrayList.class).use("values", TypeContact.class).deserialize(json);
    }
    
}
