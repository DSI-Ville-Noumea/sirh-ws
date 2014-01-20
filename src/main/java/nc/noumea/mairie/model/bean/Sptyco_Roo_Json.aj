// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.Sptyco;

privileged aspect Sptyco_Roo_Json {
    
    public String Sptyco.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String Sptyco.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static Sptyco Sptyco.fromJsonToSptyco(String json) {
        return new JSONDeserializer<Sptyco>()
        .use(null, Sptyco.class).deserialize(json);
    }
    
    public static String Sptyco.toJsonArray(Collection<Sptyco> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String Sptyco.toJsonArray(Collection<Sptyco> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<Sptyco> Sptyco.fromJsonArrayToSptycoes(String json) {
        return new JSONDeserializer<List<Sptyco>>()
        .use("values", Sptyco.class).deserialize(json);
    }
    
}
