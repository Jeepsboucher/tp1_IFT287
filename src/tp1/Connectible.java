package tp1;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Connectible {
    private ConnectibleType type;
    private String name;
    private String id;
    private Double volume;
    private Double length;
    private Double startRadius;
    private Double endRadius;
    
    public Connectible(ConnectibleType type, String name, String id, Double volume, Double length, Double startRadius,
                        Double endRadius) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.volume = volume;
        this.length = length;
        this.startRadius = startRadius;
        this.endRadius = endRadius;
    }

    public JsonObjectBuilder createJsonObject() {
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

    public Node createXmlObject(Document doc) {
        Element connectible = doc.createElement(type.getTypeName());
        connectible.setAttribute("name", name);
        connectible.setAttribute("id", id);

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
}
