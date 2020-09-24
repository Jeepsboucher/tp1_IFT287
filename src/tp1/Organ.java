package tp1;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Organ implements XMLSerializable {
    private String name;
    private int id;
    private int systemId;

    public Organ(String name, int id, int systemId) {
        this.name = name;
        this.id = id;
        this.systemId = systemId;
    }

    public static Organ fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        return new Organ(object.getString("name"), object.getInt("id"), object.getInt("systemID"));
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
        system.setAttribute("id", Integer.toString(id));
        system.setAttribute("systemID", Integer.toString(systemId));

        return system;
    }

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        throw new SAXException("An organ element cannot have a child.");
    }

    @Override
    public String getTagName(String tag) {
        return "Organ";
    }
}
