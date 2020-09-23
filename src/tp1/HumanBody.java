package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.*;

public class HumanBody {
    private String bodyName;
    private String bodyId;

    private List<System> systems;
    private List<Organ> organs;

    private HumanBody(String bodyName, String bodyId) {
        this.bodyName = bodyName;
        this.bodyId = bodyId;

        systems = new ArrayList<>();
        organs = new ArrayList<>();
    }

    public static HumanBody fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        HumanBody humanBody = new HumanBody(
            object.getString("bodyName"),
            object.getString("bodyID")
        );

        JsonArray systems = (JsonArray) object.get("Systems");
        for (JsonValue val : systems) {
            humanBody.systems.add(System.fromJson(val));
        }
        
        JsonArray organs = (JsonArray) object.get("Organs");
        for (JsonValue val : organs) {
            humanBody.organs.add(Organ.fromJson(val));
        }
        
        return humanBody;
    }

    public JsonObject toJson() {
        JsonObjectBuilder humanBodyJson = Json.createObjectBuilder();
        humanBodyJson.add("bodyName", bodyName);
        humanBodyJson.add("bodyID", bodyId);

        JsonArrayBuilder jsonSystems = Json.createArrayBuilder();
        for (System system : systems) {
            jsonSystems.add(system.toJson());
        }
        humanBodyJson.add("Systems", jsonSystems);

        JsonArrayBuilder jsonOrgans = Json.createArrayBuilder();
        for (Organ organ : organs) {
            jsonOrgans.add(organ.toJson());
        }
        humanBodyJson.add("Organs", jsonOrgans);

        return humanBodyJson.build();
    }

    public Document toXml(Document doc) {
        Element root = doc.createElement("MainBody");
        root.setAttribute("bodyID", bodyId);
        root.setAttribute("bodyName", bodyName);

        Element systems = doc.createElement("Systems");
        for (System system : systems) {
            systems.appendChild(system.toXml(doc));
        }
        root.appendChild(systems);

        Element organs = doc.createElement("Organs");
        for (Organ organ : organs) {
            organs.appendChild(organ.toXml(doc));
        }
        root.appendChild(organs);

        doc.appendChild(root);

        return doc;
    }
}
