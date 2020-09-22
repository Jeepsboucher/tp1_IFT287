package tp1;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue;

public class HumanBodyJsonParser {

    private JsonStructure jsonStructure;

    private HumanBody humanBody;

    public HumanBodyJsonParser(JsonStructure jsonStructure) {
        this.jsonStructure = jsonStructure;
        humanBody = new HumanBody("", "");

    }

    public HumanBody ParseJson() {
        
        JsonObject object = (JsonObject) jsonStructure;
        humanBody = new HumanBody(
            object.getString("bodyName"),
            object.getString("bodyId"));

        JsonArray systems = (JsonArray) object.get("Systems");
        for (JsonValue val : systems)
            humanBody.Systems.add(HumanBody.CreateSystemFromJsonObject(val));
        
        JsonArray organs = (JsonArray) object.get("Organs");
        for (JsonValue val : organs)
            humanBody.Organs.add(HumanBody.CreateOrganFromJsonObject(val));
        
        return humanBody;
    }
}
