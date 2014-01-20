// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.pk;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.pk.CadreEmploiFPPK;

privileged aspect CadreEmploiFPPK_Roo_Json {
    
    public String CadreEmploiFPPK.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String CadreEmploiFPPK.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static CadreEmploiFPPK CadreEmploiFPPK.fromJsonToCadreEmploiFPPK(String json) {
        return new JSONDeserializer<CadreEmploiFPPK>()
        .use(null, CadreEmploiFPPK.class).deserialize(json);
    }
    
    public static String CadreEmploiFPPK.toJsonArray(Collection<CadreEmploiFPPK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String CadreEmploiFPPK.toJsonArray(Collection<CadreEmploiFPPK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<CadreEmploiFPPK> CadreEmploiFPPK.fromJsonArrayToCadreEmploiFPPKs(String json) {
        return new JSONDeserializer<List<CadreEmploiFPPK>>()
        .use("values", CadreEmploiFPPK.class).deserialize(json);
    }
    
}
