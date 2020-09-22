package tp1;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class System {
    public String Name;
    public String Id;
    public String Type;

    public ArrayList<Flow> Flows;

    public System(String name, String id, String type) {
        Name = name;
        Id = id;
        Type = type;

        Flows = new ArrayList<Flow>();
    }

    public JsonObjectBuilder CreateJsonObject() {
        JsonObjectBuilder systemJson = Json.createObjectBuilder();
        systemJson.add("name", Name);
        systemJson.add("id", Id);
        systemJson.add("type", Type);
        JsonArrayBuilder jsonFlows = Json.createArrayBuilder();
        for (Flow flow : Flows) {
            jsonFlows.add(flow.CreateJsonObject());
        }
        systemJson.add("Flows", jsonFlows);
        return systemJson;
    }

	public static Flow CreateFlowFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Flow flow = new Flow(
            object.getString("name"),
            object.getString("id"));

        JsonArray connectibles = (JsonArray) object.get("Connectibles");
        for (JsonValue val : connectibles) {
            Connectible connectible = Flow.CreateConnectibleFromJsonObject(val);
            if(connectible != null)
                flow.Connectibles.add(Flow.CreateConnectibleFromJsonObject(val));
        }

        JsonArray connections = (JsonArray) object.get("Connections");
        for (JsonValue val : connections)
            flow.Connections.add(Flow.CreateConnectionFromJsonObject(val));

        return flow;
	}

	public Node CreateXmlObject(Document doc) {
		Element system = doc.createElement("System");
        system.setAttribute("name", Name);
        system.setAttribute("id", Id);
        system.setAttribute("type", Type);

        for (Flow flow : Flows) {
            system.appendChild(flow.CreateXmlObject(doc));
        }

        return system;
	}

}
