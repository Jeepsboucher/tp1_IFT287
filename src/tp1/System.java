package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class System implements XMLSerializable {
    private String name;
    private String id;
    private String type;

    private List<Flow> flows;

    private System(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;

        flows = new ArrayList<>();
    }

    public static System fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        System system = new System(
            object.getString("name"),
            object.getString("id"),
            object.getString("type")
        );

        JsonArray flows = (JsonArray) object.get("Flows");
        for (JsonValue val : flows) {
            system.flows.add(Flow.fromJson(val));
        }

        return system;
    }

    public JsonObjectBuilder toJson() {
        JsonObjectBuilder systemJson = Json.createObjectBuilder();
        systemJson.add("name", name);
        systemJson.add("id", id);
        systemJson.add("type", type);
        JsonArrayBuilder jsonFlows = Json.createArrayBuilder();
        for (Flow flow : flows) {
            jsonFlows.add(flow.toJson());
        }
        systemJson.add("Flows", jsonFlows);
        return systemJson;
    }

	public Node toXml(Document doc) {
		Element system = doc.createElement("System");
        system.setAttribute("name", name);
        system.setAttribute("id", id);
        system.setAttribute("type", type);

        for (Flow flow : flows) {
            system.appendChild(flow.toXml(doc));
        }

        return system;
	}

}
