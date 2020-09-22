package tp1;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Organ {
    public String Name;
    public String Id;
    public String SystemId;

    public Organ(String name, String id, String systemId) {
        Name = name;
        Id = id;
        SystemId = systemId;
    }

    public JsonObjectBuilder CreateJsonObject() {
        JsonObjectBuilder systemJson = Json.createObjectBuilder();
        systemJson.add("name", Name);
        systemJson.add("id", Id);
        systemJson.add("systemID", SystemId);
        return systemJson;
    }

	public Node CreateXmlObject(Document doc) {
		Element system = doc.createElement("Organ");
        system.setAttribute("name", Name);
        system.setAttribute("id", Id);
        system.setAttribute("systemID", SystemId);

        return system;
	}
}
