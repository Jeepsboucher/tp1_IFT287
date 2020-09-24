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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Connection implements XMLSerializable {
    private int id;

    private List<Integer> toList;

    public Connection(int id) {
        this.id = id;
        toList = new ArrayList<>();
    }

    public static Connection fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Connection connection = new Connection(object.getInt("id"));

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
        connection.setAttribute("id", Integer.toString(id));

        for (Integer to : toList) {
            Element t = doc.createElement("to");
            t.setAttribute("id", to.toString());
            connection.appendChild(t);
        }

        return connection;
    }

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        XMLSerializable child = null;

        switch (qName) {
            case "to":
                if (attrs == null || attrs.getLength() != 1 || attrs.getValue("id") == null) {
                    throw new SAXException("Every \"to\" tag must have an id.");
                }

                int id = Integer.parseInt(attrs.getValue("id"));
                toList.add(id);
                break;
        }

        return child;
    }

    @Override
    public String getTagName(String tag) {
        return "Connection";
    }
}
