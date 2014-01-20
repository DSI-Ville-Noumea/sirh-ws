// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.AgentRecherche;

privileged aspect AgentRecherche_Roo_Json {
    
    public String AgentRecherche.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String AgentRecherche.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static AgentRecherche AgentRecherche.fromJsonToAgentRecherche(String json) {
        return new JSONDeserializer<AgentRecherche>()
        .use(null, AgentRecherche.class).deserialize(json);
    }
    
    public static String AgentRecherche.toJsonArray(Collection<AgentRecherche> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String AgentRecherche.toJsonArray(Collection<AgentRecherche> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<AgentRecherche> AgentRecherche.fromJsonArrayToAgentRecherches(String json) {
        return new JSONDeserializer<List<AgentRecherche>>()
        .use("values", AgentRecherche.class).deserialize(json);
    }
    
}
