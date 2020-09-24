package tp1;

import org.xml.sax.Attributes;

public interface XMLSerializable {
    XMLSerializable addElement(String qName, Attributes attrs);
    String getTagName(String tag);
}
