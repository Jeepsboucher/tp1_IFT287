package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class HumanBody implements XMLSerializable {
    private String bodyName;
    private int bodyId;

    private List<System> systems;
    private List<Organ> organs;

    public HumanBody(String bodyName, int bodyId) {
        this.bodyName = bodyName;
        this.bodyId = bodyId;
    }

    public static HumanBody fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        HumanBody humanBody = new HumanBody(object.getString("bodyName"), object.getInt("bodyID"));

        humanBody.systems = new ArrayList<>();
        JsonArray systems = (JsonArray) object.get("Systems");
        for (JsonValue val : systems) {
            humanBody.systems.add(System.fromJson(val));
        }

        humanBody.organs = new ArrayList<>();
        JsonArray organs = (JsonArray) object.get("Organs");
        for (JsonValue val : organs) {
            humanBody.organs.add(Organ.fromJson(val));
        }

        return humanBody;
    }

    public JsonObject toJson() {
        JsonObjectBuilder humanBodyJson = Json.createObjectBuilder();
        humanBodyJson.add("bodyName", bodyName);
        humanBodyJson.add("bodyID", bodyId);

        JsonArrayBuilder jsonSystems = Json.createArrayBuilder();
        for (System system : systems) {
            jsonSystems.add(system.toJson());
        }
        humanBodyJson.add("Systems", jsonSystems);

        JsonArrayBuilder jsonOrgans = Json.createArrayBuilder();
        for (Organ organ : organs) {
            jsonOrgans.add(organ.toJson());
        }
        humanBodyJson.add("Organs", jsonOrgans);

        return humanBodyJson.build();
    }

    public Document toXml(Document doc) {
        Element root = doc.createElement("MainBody");
        root.setAttribute("bodyID", Integer.toString(bodyId));
        root.setAttribute("bodyName", bodyName);

        Element systemsXElement = doc.createElement("Systems");
        for (System system : systems) {
            systemsXElement.appendChild(system.toXml(doc));
        }
        root.appendChild(systemsXElement);

        Element organsXElement = doc.createElement("Organs");
        for (Organ organ : organs) {
            organsXElement.appendChild(organ.toXml(doc));
        }
        root.appendChild(organsXElement);

        doc.appendChild(root);

        return doc;
    }

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        XMLSerializable child = null;
        String name;
        int id;

        switch (qName) {
            case "Systems":
                if (systems != null) {
                    throw new SAXException("MainBody can only contain one Systems child.");
                }

                systems = new ArrayList<>();
                break;
            case "Organs":
                if (organs != null) {
                    throw new SAXException("MainBody can only contain one Organs child.");
                }

                organs = new ArrayList<>();
                break;
            case "System":
                if (systems == null) {
                    throw new SAXException("System should be child of a Systems element.");
                }

                if (attrs == null || attrs.getLength() != 3 || attrs.getValue("name") == null || attrs.getValue("id") == null
                    || attrs.getValue("type") == null) {
                    throw new SAXException("System must have only a name, an id and type.");
                }
                
                name = attrs.getValue("name");
                id = Integer.parseInt(attrs.getValue("id"));
                int type = Integer.parseInt(attrs.getValue("type"));

                System system = new System(name, id, type);
                systems.add(system);
                child = system;
                break;
            case "Organ":
                if (this.organs == null) {
                    throw new SAXException("Organs should be child of a Organs element.");
                }

                if (attrs == null || attrs.getLength() != 3 || attrs.getValue("name") == null || attrs.getValue("id") == null
                    || attrs.getValue("systemID") == null) {
                    throw new SAXException("Organ must have only a name, an id and a systemID");        
                }

                name = attrs.getValue("name");
                id = Integer.parseInt(attrs.getValue("id"));
                int systemID = Integer.parseInt(attrs.getValue("systemID"));

                Organ organ = new Organ(name, id, systemID);
                organs.add(organ);
                child = organ;
                break;
            default:
                throw new SAXException("Unknow tag : " + qName);
        }

        return child;
    }

    @Override
    public String getTagName(String tag) {
        return "MainBody";
    }
}
