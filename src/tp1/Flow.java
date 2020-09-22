package tp1;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Flow {
    public String Name;
    public String Id;

    public ArrayList<Connectible> Connectibles;
    public ArrayList<Connection> Connections;

    public Flow(String name, String id) {
        Name = name;
        Id = id;

        Connectibles = new ArrayList<Connectible>();
        Connections = new ArrayList<Connection>();
    }

    public JsonObjectBuilder CreateJsonObject() {
        JsonObjectBuilder flowJson = Json.createObjectBuilder();
        flowJson.add("name", Name);
        flowJson.add("id", Id);

        JsonArrayBuilder jsonConnectibles = Json.createArrayBuilder();
        for (Connectible connectible : Connectibles) {
            jsonConnectibles.add(connectible.createJsonObject());
        }
        flowJson.add("Connectibles", jsonConnectibles);

        JsonArrayBuilder jsonConnections = Json.createArrayBuilder();
        for (Connection connection : Connections) {
            jsonConnections.add(connection.createJsonObject());
        }
        flowJson.add("Connections", jsonConnections);
        return flowJson;
    }

	public static Connectible CreateConnectibleFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        String type = object.getString("type");
        Double length = object.getJsonNumber("length") == null ? null : object.getJsonNumber("length").doubleValue();
        Double volume = object.getJsonNumber("volume") == null ? null : object.getJsonNumber("volume").doubleValue();
        Double startRadius = object.getJsonNumber("startRadius") == null ? null : object.getJsonNumber("startRadius").doubleValue();
        Double endRadius = object.getJsonNumber("endRadius") == null ? null : object.getJsonNumber("endRadius").doubleValue();
        return new Connectible()
        Connectible toReturn = null;
        switch (type) {
            case "Atrium":
                toReturn = new Atrium(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue());
                break;
            case "Ventricle":
                toReturn = new Ventricle(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue());
                break;
            case "Artery":
                toReturn = new Artery(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("startRadius").doubleValue(),
                    endRadius,
                    length);
                break;
            case "Vein":
                toReturn = new Vein(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("startRadius").doubleValue(),
                    endRadius,
                    length);
                break;
            case "Capillaries":
                toReturn = new Capillaries(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break;
            case "Nose":
                toReturn = new Nose(object.getString("name"), 
                    object.getString("id"));
                break;
            case "AirConnectible":
                toReturn = new AirConnectible(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("startRadius").doubleValue(),
                    endRadius,
                    length);
                break;
            case "Alveoli":
                toReturn = new Alveoli(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue());
                break;
            case "DigestiveTract":
                toReturn = new DigestiveTract(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break;
            case "StomachTract":
                toReturn = new StomachTract(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break;
            case "DuodenumTract":
                toReturn = new DuodenumTract(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break;
            case "RectumTract":
                toReturn = new RectumTract(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break;
            case "BiDuct":
                toReturn = new BiDuct(object.getString("name"), 
                object.getString("id"));
                break;
            case "Duct":
                toReturn = new Duct(object.getString("name"), 
                object.getString("id"));
                break;
            case "DuctOverflowableJunction":
                toReturn = new DuctOverflowableJunction(object.getString("name"), 
                    object.getString("id"));
            break;
            case "DeversingDuct":
                toReturn = new DeversingDuct(object.getString("name"), 
                    object.getString("id"));
                break;
            case "InnerGallbladder":
                toReturn = new InnerGallbladder(object.getString("name"), 
                    object.getString("id"));
                break;
            case "SalivaryDuct":
                toReturn = new SalivaryDuct(object.getString("name"), 
                    object.getString("id"), 
                    object.getJsonNumber("volume").doubleValue(),
                    length);
                break; 
        }
        return toReturn;
	}

	public static Connection CreateConnectionFromJsonObject(JsonValue tree) {
        JsonObject object = (JsonObject) tree;
        Connection connection = new Connection(
            object.getString("id"));

        JsonArray to = (JsonArray) object.get("to");
        for (JsonValue val : to)
            connection.toList.add(Connection.createToFromJsonObject(val));

        return connection;
	}

	public Node CreateXmlObject(Document doc) {
		Element system = doc.createElement("Flow");
        system.setAttribute("name", Name);
        system.setAttribute("id", Id);

        Element connectible = doc.createElement("Connectible");
        for (Connectible c : Connectibles) {
            connectible.appendChild(c.createXmlObject(doc));
        }
        system.appendChild(connectible);

        Element connections = doc.createElement("Connections");
        for (Connection c : Connections) {
            connections.appendChild(c.CreateXmlObject(doc));
        }
        system.appendChild(connections);

        return system;
	}
}