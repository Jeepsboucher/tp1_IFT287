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

public class Flow implements XMLSerializable {
    private String name;
    private String id;

    private List<Connectible> connectibles;
    private List<Connection> connections;

    private Flow(String name, String id) {
        this.name = name;
        this.id = id;

        connectibles = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public static Flow fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Flow flow = new Flow(
            object.getString("name"),
            object.getString("id")
        );

        JsonArray connectiblesJson = (JsonArray) object.get("Connectibles");
        for (JsonValue val : connectiblesJson) {
            Connectible connectible = Connectible.fromJson(val);
            if(connectible != null)
                flow.connectibles.add(Connectible.fromJson(val));
        }

        JsonArray connectionsJson = (JsonArray) object.get("Connections");
        for (JsonValue val : connectionsJson) {
            flow.connections.add(Connection.fromJson(val));
        }

        return flow;
	}

    public JsonObjectBuilder toJson() {
        JsonObjectBuilder flowJson = Json.createObjectBuilder();
        flowJson.add("name", name);
        flowJson.add("id", id);

        JsonArrayBuilder jsonConnectibles = Json.createArrayBuilder();
        for (Connectible connectible : connectibles) {
            jsonConnectibles.add(connectible.toJson());
        }
        flowJson.add("Connectibles", jsonConnectibles);

        JsonArrayBuilder jsonConnections = Json.createArrayBuilder();
        for (Connection connection : connections) {
            jsonConnections.add(connection.toJson());
        }
        flowJson.add("Connections", jsonConnections);

        return flowJson;
    }

	public Node toXml(Document doc) {
		Element system = doc.createElement("Flow");
        system.setAttribute("name", name);
        system.setAttribute("id", id);

        Element connectible = doc.createElement("Connectible");
        for (Connectible c : connectibles) {
            connectible.appendChild(c.toXml(doc));
        }
        system.appendChild(connectible);

        Element connectionsXElement = doc.createElement("Connections");
        for (Connection c : connections) {
            connectionsXElement.appendChild(c.toXml(doc));
        }
        system.appendChild(connectionsXElement);

        return system;
	}
}