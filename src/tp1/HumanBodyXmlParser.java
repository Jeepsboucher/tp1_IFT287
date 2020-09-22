package tp1;

import java.lang.System;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class HumanBodyXmlParser extends DefaultHandler {
    public HumanBody HumanBody;

    private Integer systemCount = 0;
    private Integer flowCount = 0;
    private Integer connectionCount = 0;

    public HumanBody GetHumanBody() {
        return HumanBody;
    }

    public void startDocument() throws SAXException {
		System.out.println("D�but du document");
	}

	/**
	 * Cette m�thode est appel�e par le parser � la fin du document.
	 */
	public void endDocument() throws SAXException {
		System.out.println("Fin du document");
	}

	public void startElement(String namespaceURI, String lName, String qName,
			Attributes attrs) throws SAXException {
        switch (qName) {
            case "MainBody":
                HumanBody = new HumanBody(attrs.getValue("bodyName"),attrs.getValue("bodyID"));
                break;
            case "Systems":
                break;
            case "System":
                HumanBody.Systems.add(
                    new tp1.System(attrs.getValue("name"),attrs.getValue("id"),attrs.getValue("type"))
                );
                break;
            case "Flow":
                HumanBody.Systems.get(systemCount).Flows.add(
                    new Flow(attrs.getValue("name"),attrs.getValue("id"))
                );
                break;
            case "Connectible":
                break;
            case "Connections":
                break;
            case "Connection":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connections.add(
                    new Connection(attrs.getValue("id"))
                );
                break;
            case "to":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connections.get(connectionCount).toList.add(
                        Integer.parseInt(attrs.getValue("id"))
                    );
                break;
            case "Organ":
                HumanBody.Organs.add(
                    new Organ(attrs.getValue("name"),attrs.getValue("id"),attrs.getValue("systemID"))
                );
                break;
            default:
                addingConnectible(qName, attrs);
                break;
        }
    }
    
    private void addingConnectible(String qName, Attributes attrs) {
        ConnectibleType type = ConnectibleType.valueOf(qName);
        String name = attrs.getValue("name");
        String id = attrs.getValue("id");
        Double volume = attrs.getValue("volume") == null ? null : Double.parseDouble(attrs.getValue("volume"));
        Double length = attrs.getValue("length") == null ? null : Double.parseDouble(attrs.getValue("length"));
        Double startRadius = attrs.getValue("startRadius") == null ? null : Double.parseDouble(attrs.getValue("startRadius"));
        Double endRadius = attrs.getValue("endRadius") == null ? null : Double.parseDouble(attrs.getValue("endRadius"));
        HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
            new Connectible(type, name, id, volume, length, startRadius, endRadius)
        );
    }

	public void endElement(String namespaceURI, String lName, String qName)
			throws SAXException {
        switch (qName) {
            case "Connection":
                connectionCount++;
                break;
            case "Flow":
                flowCount++;
                connectionCount=0;
                break;
            case "System":
                systemCount++;
                flowCount=0;
                connectionCount=0;
                break;
            default:
                break;
        }
	}


	public void characters(char buf[], int offset, int len) throws SAXException {
		System.out.print(new String(buf, offset, len));
	}


	public void warning(SAXParseException e) throws SAXException {
		System.out.println("Warning : " + e);
	}


	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error : " + e);
    }
}
