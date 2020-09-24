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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class System implements XMLSerializable {
    private String name;
    private int id;
    private int type;

    private List<Flow> flows;

    public System(String name, int id, int type) {
        this.name = name;
        this.id = id;
        this.type = type;

        flows = new ArrayList<>();
    }

    public static System fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        System system = new System(object.getString("name"), object.getInt("id"), object.getInt("type"));

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
        system.setAttribute("id", Integer.toString(id));
        system.setAttribute("type", Integer.toString(type));

        if (flows != null) {
            for (Flow flow : flows) {
                system.appendChild(flow.toXml(doc));
            }
        }

        return system;
    }

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        XMLSerializable child = null;
        switch (qName) {
            case "Flow":
                if (attrs == null || attrs.getLength() != 2 || attrs.getValue("id") == null
                        || attrs.getValue("name") == null) {
                    throw new SAXException("Flow must have only an id and a name.");
                }

                int id = Integer.parseInt(attrs.getValue("id"));
                String name = attrs.getValue("name");

                Flow flow = new Flow(name, id);
                flows.add(flow);
                child = flow;
                break;
            default:
                throw new SAXException("Unknown tag : " + qName);
        }
        return child;
    }

    @Override
    public String getTagName(String tag) {
        return "System";
    }

}
