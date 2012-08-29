// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.bean.Budget;

privileged aspect Budget_Roo_Json {
    
    public String Budget.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Budget Budget.fromJsonToBudget(String json) {
        return new JSONDeserializer<Budget>().use(null, Budget.class).deserialize(json);
    }
    
    public static String Budget.toJsonArray(Collection<Budget> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Budget> Budget.fromJsonArrayToBudgets(String json) {
        return new JSONDeserializer<List<Budget>>().use(null, ArrayList.class).use("values", Budget.class).deserialize(json);
    }
    
}
