package undercast.client.internetTools;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * @author molenzwiebel
 * ServerStatusHTMLParser,
 * a class which will try parse the given html to get the current map, name, players and next map.
 * Returns: A String[][] with as first value the server (0 = alpha, 11 = nostalgia)
 * and as second value the thing you want.
 * 0 = Name, 1 = Players, 2 = Now playing map, 3 = Next map
 * So, data[0][2] will give the now playing map on Alpha and
 *     data[11][1] will give the players on Nostalgia
 */
public class ServerStatusHTMLParser {
    // Function to remove the last character is it is a space
    public static String stripLastSpace(String str) {
        
        if (str.length() > 0 && str.charAt(str.length()-1) == ' ') {
            str = str.substring(0, str.length()-1);
            return str;
        }
        else {
            return str;
        }
    }

    public static String[][] parse(String string) throws Exception {
        // Remove all text between "<div class='span4'>" and "<div class='span8'>" (staff online data, not needed)
        String realSource = string.replace(string.substring(string.indexOf("<div class='span4'>"), string.indexOf("<div class='span8'>")), "");

        // Create 2 readers
        Reader HTMLReader = new StringReader(realSource);
        Reader HTMLReader2 = new StringReader(realSource);
        // Create 2 parsers
        ParserDelegator pd = new ParserDelegator();
        ParserDelegator pd2 = new ParserDelegator();
        // Create our own parse handlers
        Parser p = new Parser();
        NextParser p2 = new NextParser();
        // Parse
        pd.parse(HTMLReader, p, false);
        pd2.parse(HTMLReader2, p2, false);
        // Make up return values
        // Add the next map to the other data as we use two parsers
        int i=0;
        for (String s : p2.mapData) {
            p.mapData[i][3] = s;
            i++;
        }
        return p.mapData;
    }
}
class Parser extends HTMLEditorKit.ParserCallback {
    // Currently in a h4 tag?
    private boolean inTD = false;
    // The number of attributes already gotten (such as name, players, map)
    private int count = 0;
    // # of map currently parsing
    private int mapCount = -1;
    // Data
    public String[][] mapData = new String[30][4];

    // Function called when a tag (<tagName>) is opened
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        // If it is a tag we want, make sure to have a look at it
        if(t.equals(HTML.Tag.H4)) {
            inTD = true;
            count = 1;
            mapCount++;
        }
    }

    public void handleEndTag(HTML.Tag t, int pos) {
        // Close the tag (</tagName>)
        if(t.equals(HTML.Tag.H4)) {
            inTD = false;
            count = 0;
        }
    }

    public void handleText(char[] data, int pos) {
        // Handle the text in between tags (<tag>TEXT</tag>)
        if(inTD)
        {
            // Write the data
            mapData[mapCount][count-1] = ServerStatusHTMLParser.stripLastSpace(new String(data).replace("Now: ", ""));
            count++;
        }
    }
}
class NextParser extends HTMLEditorKit.ParserCallback {
    // Currently in a a tag?
    private boolean inTD = false;
    // # of map currently parsing
    private int mapCount = 0;
    // Data
    public String[] mapData = new String[30];

    // Function called when a tag (<tagName>) is opened
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        // If it is a tag we want, make sure to have a look at it
        if(t.equals(HTML.Tag.A)) {
            inTD = true;
        }
    }

    public void handleEndTag(HTML.Tag t, int pos) {
        // Close the tag (</tagName>)
        if(t.equals(HTML.Tag.A)) {
            inTD = false;
        }
    }

    public void handleText(char[] data, int pos) {
        // Handle the text in between tags (<tag>TEXT</tag>)
        if(inTD) {
            // Make sure we only parse "Next: Map"
            if (!new String(data).contains("Next:")) {
                inTD = false;
            } else {
                // Write data
                mapData[mapCount] = new String(data).replace("Next: ", "");
                mapCount++;
            }
        }
    }
}
