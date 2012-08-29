// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.pk;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.noumea.mairie.model.pk.ActiviteFPPK;

privileged aspect ActiviteFPPK_Roo_Json {
    
    public String ActiviteFPPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ActiviteFPPK ActiviteFPPK.fromJsonToActiviteFPPK(String json) {
        return new JSONDeserializer<ActiviteFPPK>().use(null, ActiviteFPPK.class).deserialize(json);
    }
    
    public static String ActiviteFPPK.toJsonArray(Collection<ActiviteFPPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ActiviteFPPK> ActiviteFPPK.fromJsonArrayToActiviteFPPKs(String json) {
        return new JSONDeserializer<List<ActiviteFPPK>>().use(null, ArrayList.class).use("values", ActiviteFPPK.class).deserialize(json);
    }
    
}
