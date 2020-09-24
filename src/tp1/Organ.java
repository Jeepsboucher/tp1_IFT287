package tp1;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Organ implements XMLSerializable {
    private String name;
    private String id;
    private String systemId;

    private Organ(String name, String id, String systemId) {
        this.name = name;
        this.id = id;
        this.systemId = systemId;
    }

    public static Organ fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        return new Organ(
            object.getString("name"),
            object.getString("id"),
            object.getString("systemID")
        );
    }

    public JsonObjectBuilder toJson() {
        JsonObjectBuilder systemJson = Json.createObjectBuilder();
        systemJson.add("name", name);
        systemJson.add("id", id);
        systemJson.add("systemID", systemId);
        return systemJson;
    }

	public Node toXml(Document doc) {
		Element system = doc.createElement("Organ");
        system.setAttribute("name", name);
        system.setAttribute("id", id);
        system.setAttribute("systemID", systemId);

        return system;
	}
}
