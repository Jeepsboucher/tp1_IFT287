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
    public String BodyName;
    public String BodyId;

    public List<System> Systems;
    public List<Organ> Organs;

    public HumanBody(String bodyName, String bodyId) {
        BodyName = bodyName;
        BodyId = bodyId;

        Systems = new ArrayList<>();
        Organs = new ArrayList<>();
    }

    public JsonObject createJsonObject() {
        JsonObjectBuilder humanBodyJson = Json.createObjectBuilder();
        humanBodyJson.add("bodyName", BodyName);
        humanBodyJson.add("bodyID", BodyId);
        JsonArrayBuilder jsonSystems = Json.createArrayBuilder();
        for (System system : Systems) {
            jsonSystems.add(system.createJsonObject());
        }
        humanBodyJson.add("Systems", jsonSystems);

        JsonArrayBuilder jsonOrgans = Json.createArrayBuilder();
        for (Organ organ : Organs) {
            jsonOrgans.add(organ.createJsonObject());
        }
        humanBodyJson.add("Organs", jsonOrgans);

        return humanBodyJson.build();
    }

    public Document createDocument(Document doc) {
        Element root = doc.createElement("MainBody");
        root.setAttribute("bodyID", BodyId);
        root.setAttribute("bodyName", BodyName);

        Element systems = doc.createElement("Systems");
        for (System system : Systems) {
            systems.appendChild(system.createXmlObject(doc));
        }
        root.appendChild(systems);

        Element organs = doc.createElement("Organs");
        for (Organ organ : Organs) {
            organs.appendChild(organ.createXmlObject(doc));
        }
        root.appendChild(organs);

        doc.appendChild(root);

        return doc;
    }

    public static System createSystemFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        System system = new System(
            object.getString("name"),
            object.getString("id"),
            object.getString("type"));

        JsonArray flows = (JsonArray) object.get("Flows");
        for (JsonValue val : flows)
            system.Flows.add(System.createFlowFromJsonObject(val));

        return system;
    }

    public static Organ createOrganFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Organ organ = new Organ(
            object.getString("name"),
            object.getString("id"),
            object.getString("systemID"));

        return organ;
    }
}
