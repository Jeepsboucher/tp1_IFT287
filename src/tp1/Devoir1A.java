// Travail fait par :
//   Jean-Philippe Boucher - 19125046
//   NomEquipier2 - Matricule

package tp1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.System;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * Fichier de base pour le Devoir1A du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 6 août 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet de convertir un fichier XML en son équivalent en JSON.
 *
 * Paramètres du programme
 * 0- Nom du fichier XML
 * 1- Nom du fichier JSON
 * 
 * </pre>
 */
public class Devoir1A {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        System.out.println("Args:" + args.length);
        if (args.length < 2)
        {
            System.out.println("Usage: java tp1.Devoir1A <fichierXML> <fichierJSON>");
            return;
        }
        
        String nomFichierXML = args[0];
        String nomFichierJSON = args[1];
        
        System.out.println("Debut de la conversion du fichier " + nomFichierXML + " vers le fichier " + nomFichierJSON);

        // Votre code de conversion devrait aller ici

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser parser = factory.newSAXParser();
        HumanBodyXmlParser handler = new HumanBodyXmlParser();
        parser.parse(new File(nomFichierXML), handler);

        JsonObject body = handler.GetHumanBody().createJsonObject();

        StringWriter stWriter = new StringWriter();
        Map<String, Object> config = new HashMap<String, Object>(1);
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory f = Json.createWriterFactory(config);
        JsonWriter jsonWriter = f.createWriter(stWriter);
        jsonWriter.writeObject(body);
        jsonWriter.close();

        String json = stWriter.toString();
        FileWriter writer = new FileWriter(nomFichierJSON);
        writer.write(json);
        writer.close();
        System.out.println("Conversion terminee.");
    }

}
