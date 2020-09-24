// Travail fait par :
//   Jean-Philippe Boucher - 19 125 046
//   Charles Lachance - 17 093 137

package tp1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.System;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Fichier de base pour le Devoir1B du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 6 août 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet de convertir un fichier JSON en son équivalent en XML.
 *
 * Paramètres du programme
 * 0- Nom du fichier JSON
 * 1- Nom du fichier XML
 * 
 * </pre>
 */
public class Devoir1B {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java tp1.Devoir1B <fichierJSON> <fichierXML>");
            return;
        }

        String nomFichierJSON = args[0];
        String nomFichierXML = args[1];

        System.out.println("Debut de la conversion du fichier " + nomFichierJSON + " vers le fichier " + nomFichierXML);

        // Votre code de conversion devrait aller ici

        try {
            JsonReader reader = Json.createReader(new FileReader(nomFichierJSON));
            JsonStructure jsonStruct = reader.read();

            HumanBody humanBody = HumanBody.fromJson(jsonStruct);

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(true);
            Document document = f.newDocumentBuilder().newDocument();

            humanBody.toXml(document);

            FileOutputStream output = new FileOutputStream(nomFichierXML);
            PrintStream out = new PrintStream(output);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "HumanBody.dtd");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(out);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Conversion terminee.");

    }
}
