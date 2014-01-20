// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.pk;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.pk.NiveauEtudeFPPK;

privileged aspect NiveauEtudeFPPK_Roo_Json {
    
    public String NiveauEtudeFPPK.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String NiveauEtudeFPPK.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static NiveauEtudeFPPK NiveauEtudeFPPK.fromJsonToNiveauEtudeFPPK(String json) {
        return new JSONDeserializer<NiveauEtudeFPPK>()
        .use(null, NiveauEtudeFPPK.class).deserialize(json);
    }
    
    public static String NiveauEtudeFPPK.toJsonArray(Collection<NiveauEtudeFPPK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String NiveauEtudeFPPK.toJsonArray(Collection<NiveauEtudeFPPK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<NiveauEtudeFPPK> NiveauEtudeFPPK.fromJsonArrayToNiveauEtudeFPPKs(String json) {
        return new JSONDeserializer<List<NiveauEtudeFPPK>>()
        .use("values", NiveauEtudeFPPK.class).deserialize(json);
    }
    
}
