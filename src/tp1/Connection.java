package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Connection {
    public String id;

    public List<Integer> toList;

    public Connection(String id) {
        this.id = id;
        toList = new ArrayList<>();
    }

    public JsonObjectBuilder createJsonObject() {
        JsonObjectBuilder connectionJson = Json.createObjectBuilder();
        connectionJson.add("id", id);

        JsonArrayBuilder jsonTo = Json.createArrayBuilder();
        for (Integer to : toList) {
            jsonTo.add(to);
        }
        connectionJson.add("to", jsonTo);
        return connectionJson;
    }

	public static int createToFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        return object.getInt("id");
	}

	public Node createXmlObject(Document doc) {
		Element connection = doc.createElement("Connection");
        connection.setAttribute("id", id);

        for (Integer to : toList) {
            Element t = doc.createElement("to");
            t.setAttribute("id", to.toString());
            connection.appendChild(t);
        }

        return connection;
	}
}
