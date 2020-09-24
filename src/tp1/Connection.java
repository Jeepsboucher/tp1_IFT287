package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonNumber;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Connection implements XMLSerializable {
    private String id;

    private List<Integer> toList;

    private Connection(String id) {
        this.id = id;
        toList = new ArrayList<>();
    }

	public static Connection fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Connection connection = new Connection(object.getString("id"));

        JsonArray toListJson = object.getJsonArray("to");
        for (JsonValue val : toListJson) {
            JsonNumber to = (JsonNumber) val;
            connection.toList.add(to.intValue());
        }

        return connection;
	}

    public JsonObjectBuilder toJson() {
        JsonObjectBuilder connectionJson = Json.createObjectBuilder();
        connectionJson.add("id", id);

        JsonArrayBuilder jsonTo = Json.createArrayBuilder();
        for (Integer to : toList) {
            jsonTo.add(to);
        }
        connectionJson.add("to", jsonTo);
        return connectionJson;
    }

	public Node toXml(Document doc) {
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
