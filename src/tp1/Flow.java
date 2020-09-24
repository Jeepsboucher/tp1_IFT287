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

public class Flow implements XMLSerializable {
    private String name;
    private int id;

    private List<Connectible> connectibles;
    private List<Connection> connections;

    public Flow(String name, int id) {
        this.name = name;
        this.id = id;

        connectibles = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public static Flow fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Flow flow = new Flow(object.getString("name"), object.getInt("id"));

        JsonArray connectiblesJson = (JsonArray) object.get("Connectibles");
        for (JsonValue val : connectiblesJson) {
            Connectible connectible = Connectible.fromJson(val);
            if (connectible != null)
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
        system.setAttribute("id", Integer.toString(id));

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

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        XMLSerializable child = null;
        int id;

        switch (qName) {
            case "Connectible":
                if (connectibles != null) {
                    throw new SAXException("Flow can only contain one Connectible child.");
                }

                connectibles = new ArrayList<>();
                break;
            case "Connections":
                if (connections != null) {
                    throw new SAXException("Flow can only contain one Connections child.");
                }

                connections = new ArrayList<>();
                break;
            case "Connection":
                if (connections == null) {
                    throw new SAXException("Flow should be child of a Connection element.");
                }

                if (attrs == null || attrs.getLength() != 1 || attrs.getValue("id") == null) {
                    throw new SAXException("Connection must have only an id.");
                }

                id = Integer.parseInt(attrs.getValue("id"));

                Connection connection = new Connection(id);
                connections.add(connection);

                child = connection;
                break;
            default:
                ConnectibleType type = ConnectibleType.fromString(qName);
                if (type == null) {
                    throw new SAXException("Unknow tag : " + qName);
                }
                if (connectibles == null) {
                    throw new SAXException("A " + qName + " must appear in a connectibles list (Connectible).");
                }

                if (attrs == null || attrs.getLength() < 2 || attrs.getLength() > 6 || attrs.getValue("name") == null
                        || attrs.getValue("id") == null) {
                    throw new SAXException("Every connectible must have at least a name and an id.");
                }

                String name = attrs.getValue("name");
                id = Integer.parseInt(attrs.getValue("id"));

                Double startRadius = null;
                String startRadiusStr = attrs.getValue("startRadius");
                if (startRadiusStr != null) {
                    startRadius = Double.parseDouble(startRadiusStr);
                }

                Double endRadius = null;
                String endRadiusStr = attrs.getValue("endRadius");
                if (endRadiusStr != null) {
                    endRadius = Double.parseDouble(endRadiusStr);
                }

                Double length = null;
                String lengthStr = attrs.getValue("length");
                if (lengthStr != null) {
                    length = Double.parseDouble(lengthStr);
                }

                Double volume = null;
                String volumeStr = attrs.getValue("volume");
                if (volumeStr != null) {
                    volume = Double.parseDouble(volumeStr);
                }

                Connectible connectible = new Connectible(type, name, id, volume, length, startRadius, endRadius);
                connectibles.add(connectible);
                break;
        }
        return child;
    }

    @Override
    public String getTagName(String tag) {
        return "Flow";
    }
}