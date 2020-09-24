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

public class Connectible implements XMLSerializable {
    private ConnectibleType type;
    private String name;
    private int id;
    private Double volume;
    private Double length;
    private Double startRadius;
    private Double endRadius;

    public Connectible(ConnectibleType type, String name, int id, Double volume, Double length, Double startRadius,
            Double endRadius) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.volume = volume;
        this.length = length;
        this.startRadius = startRadius;
        this.endRadius = endRadius;
    }

    public static Connectible fromJson(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        String typeName = object.getString("type");
        ConnectibleType type = ConnectibleType.fromString(typeName);
        String name = object.getString("name");
        Integer id = object.getInt("id");
        Double volume = object.isNull("volume") ? null : object.getJsonNumber("volume").doubleValue();
        Double length = object.isNull("length") ? null : object.getJsonNumber("length").doubleValue();
        Double startRadius = object.isNull("startRadius") ? null : object.getJsonNumber("startRadius").doubleValue();
        Double endRadius = object.isNull("endRadius") ? null : object.getJsonNumber("endRadius").doubleValue();

        return new Connectible(type, name, id, volume, length, startRadius, endRadius);
    }

    public JsonObjectBuilder toJson() {
        JsonObjectBuilder connectible = Json.createObjectBuilder();
        connectible.add("type", type.getTypeName());
        connectible.add("name", name);
        connectible.add("id", id);

        if (volume != null) {
            connectible.add("volume", volume);
        }

        if (length != null) {
            connectible.add("length", length);
        }

        if (startRadius != null) {
            connectible.add("startRadius", startRadius);
        }

        if (endRadius != null) {
            connectible.add("endRadius", endRadius);
        }

        return connectible;
    }

    public Node toXml(Document doc) {
        Element connectible = doc.createElement(type.getTypeName());
        connectible.setAttribute("name", name);
        connectible.setAttribute("id", Integer.toString(id));

        if (volume != null) {
            connectible.setAttribute("volume", volume.toString());
        }

        if (length != null) {
            connectible.setAttribute("length", length.toString());
        }

        if (startRadius != null) {
            connectible.setAttribute("startRadius", startRadius.toString());
        }

        if (endRadius != null) {
            connectible.setAttribute("endRadius", endRadius.toString());
        }

        return connectible;
    }

    @Override
    public XMLSerializable addElement(String qName, Attributes attrs) throws SAXException {
        throw new SAXException("A connectible element cannot have a child.");
    }

    @Override
    public String getTagName(String tag) {
        return type.getTypeName();
    }
}
