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
                        attrs.getValue("id")
                    );
                break;
            case "Organ":
                HumanBody.Organs.add(
                    new Organ(attrs.getValue("name"),attrs.getValue("id"),attrs.getValue("systemID"))
                );
                break;
            default:
                AddingConnectible(qName, attrs);
                break;
        }
    }
    
    private void AddingConnectible(String qName, Attributes attrs) {
        Double length = attrs.getValue("length") == null ? null : Double.parseDouble(attrs.getValue("length"));
        Double endRadius = attrs.getValue("endRadius") == null ? 0 : Double.parseDouble(attrs.getValue("endRadius"));
        switch (qName) {
            case "Atrium":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Atrium(attrs.getValue("name"), 
                        attrs.getValue("id"), 
                        Double.parseDouble(attrs.getValue("volume")))
                );
                break;
            case "Ventricle":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Ventricle(attrs.getValue("name"), 
                        attrs.getValue("id"), 
                        Double.parseDouble(attrs.getValue("volume")))
                );
            break;
            case "Artery":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Artery(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("startRadius")),
                        endRadius,
                        length)
                );
                break;
            case "Vein":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Vein(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("startRadius")),
                        endRadius,
                        length)
                );
                break;
            case "Capillaries":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Capillaries(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
            break;
            case "Nose":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Nose(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
                break;
            case "AirConnectible":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new AirConnectible(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("startRadius")),
                        endRadius,
                        length)
                );
                break;
            case "Alveoli":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Alveoli(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")))
                );
                break;
            case "DigestiveTract":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new DigestiveTract(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
            break;
            case "StomachTract":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new StomachTract(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
                break;
            case "DuodenumTract":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new DuodenumTract(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
                break;
            case "RectumTract":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new RectumTract(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
            break;
            case "BiDuct":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new BiDuct(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
                break;
            case "Duct":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new Duct(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
                break;
            case "DuctOverflowableJunction":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new DuctOverflowableJunction(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
            break;
            case "DeversingDuct":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new DeversingDuct(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
                break;
            case "InnerGallbladder":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new InnerGallbladder(attrs.getValue("name"),
                        attrs.getValue("id"))
                );
                break;
            case "SalivaryDuct":
                HumanBody.Systems.get(systemCount).Flows.get(flowCount).Connectibles.add(
                    new SalivaryDuct(attrs.getValue("name"),
                        attrs.getValue("id"),
                        Double.parseDouble(attrs.getValue("volume")),
                        length)
                );
                break; 
            default:
                break;
        }
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
