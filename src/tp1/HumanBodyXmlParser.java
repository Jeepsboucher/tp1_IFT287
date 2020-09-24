package tp1;

import java.lang.System;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;
import java.util.stream.*;

public class HumanBodyXmlParser extends DefaultHandler {
    private Stack<XMLSerializable> elementStack;
    private HumanBody humanBody;

    public HumanBodyXmlParser() {
        elementStack = new Stack<>();
        humanBody = null;
    }

    public HumanBody getHumanBody() {
        return humanBody;
    }

    public void startDocument() throws SAXException {
        System.out.println("D�but du document");
        elementStack.clear();
    }

    /**
     * Cette m�thode est appel�e par le parser � la fin du document.
     */
    public void endDocument() throws SAXException {
        System.out.println("Fin du document");
    }

    public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("MainBody")) {
            if (!elementStack.empty()) {
                throw new SAXException("MainBody must be the first element of xml file.");
            }

            if (attrs == null || attrs.getLength() != 2) {
                throw new SAXException("MainBody must have only a name and an ID.");
            }

            String bodyName = attrs.getValue("bodyName");
            String bodyID = attrs.getValue("bodyID");

            HumanBody humanBody = new HumanBody(bodyName, bodyID);
            elementStack.push(humanBody);
            this.humanBody = humanBody;
        } else {
            if (elementStack.empty()) {
                throw new SAXException("MainBody must be the first element of xml file.");
            }

            XMLSerializable child = elementStack.peek().addElement(qName, attrs);
            if (child != null) {
                elementStack.push(child);
            }
        }
    }

    /*
     * private void addConnectible(String qName, Attributes attrs) { ConnectibleType
     * type = Stream.of(ConnectibleType.values()).filter(connectibleType ->
     * connectibleType.getTypeName().equals(qName)) .findFirst().get(); String name
     * = attrs.getValue("name"); String id = attrs.getValue("id"); Double volume =
     * attrs.getValue("volume") == null ? null :
     * Double.parseDouble(attrs.getValue("volume")); Double length =
     * attrs.getValue("length") == null ? null :
     * Double.parseDouble(attrs.getValue("length")); Double startRadius =
     * attrs.getValue("startRadius") == null ? null :
     * Double.parseDouble(attrs.getValue("startRadius")); Double endRadius =
     * attrs.getValue("endRadius") == null ? null :
     * Double.parseDouble(attrs.getValue("endRadius"));
     * humanBody.systems.get(systemCount).flows.get(flowCount).Connectibles.add( new
     * Connectible(type, name, id, volume, length, startRadius, endRadius) ); }
     */

    public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
        if (elementStack.empty()) {
            throw new SAXException("Opening and closing tags mismatch.");
        }

        if (qName.equals(elementStack.peek().getTagName(qName))) {
            elementStack.pop();
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
