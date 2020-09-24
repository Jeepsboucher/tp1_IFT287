package tp1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface XMLSerializable {
    XMLSerializable addElement(String qName, Attributes attrs) throws SAXException;
    String getTagName(String tag);
}
