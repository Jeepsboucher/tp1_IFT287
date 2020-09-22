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

public class Flow {
    public String Name;
    public String Id;

    public List<Connectible> Connectibles;
    public List<Connection> Connections;

    public Flow(String name, String id) {
        Name = name;
        Id = id;

        Connectibles = new ArrayList<>();
        Connections = new ArrayList<>();
    }

    public JsonObjectBuilder createJsonObject() {
        JsonObjectBuilder flowJson = Json.createObjectBuilder();
        flowJson.add("name", Name);
        flowJson.add("id", Id);

        JsonArrayBuilder jsonConnectibles = Json.createArrayBuilder();
        for (Connectible connectible : Connectibles) {
            jsonConnectibles.add(connectible.createJsonObject());
        }
        flowJson.add("Connectibles", jsonConnectibles);

        JsonArrayBuilder jsonConnections = Json.createArrayBuilder();
        for (Connection connection : Connections) {
            jsonConnections.add(connection.createJsonObject());
        }
        flowJson.add("Connections", jsonConnections);
        return flowJson;
    }

	public static Connectible createConnectibleFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        ConnectibleType type = ConnectibleType.valueOf(object.getString("type"));
        String name = object.getString("name");
        String id = object.getString("id");
        Double volume = object.getJsonNumber("volume") == null ? null : object.getJsonNumber("volume").doubleValue();
        Double length = object.getJsonNumber("length") == null ? null : object.getJsonNumber("length").doubleValue();
        Double startRadius = object.getJsonNumber("startRadius") == null ? null : object.getJsonNumber("startRadius").doubleValue();
        Double endRadius = object.getJsonNumber("endRadius") == null ? null : object.getJsonNumber("endRadius").doubleValue();
        return new Connectible(type, name, id, volume, length, startRadius, endRadius);
	}

	public static Connection createConnectionFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Connection connection = new Connection(
            object.getString("id"));

        JsonArray to = (JsonArray) object.get("to");
        for (JsonValue val : to)
            connection.toList.add(Connection.createToFromJsonObject(val));

        return connection;
	}

	public Node createXmlObject(Document doc) {
		Element system = doc.createElement("Flow");
        system.setAttribute("name", Name);
        system.setAttribute("id", Id);

        Element connectible = doc.createElement("Connectible");
        for (Connectible c : Connectibles) {
            connectible.appendChild(c.createXmlObject(doc));
        }
        system.appendChild(connectible);

        Element connections = doc.createElement("Connections");
        for (Connection c : Connections) {
            connections.appendChild(c.createXmlObject(doc));
        }
        system.appendChild(connections);

        return system;
	}
}