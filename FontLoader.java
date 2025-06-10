import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;


/*
*   Do I know how this works? No
*   Will I know how this works? Someday
*   Am I complaining? Not in the slightest
*/

public class FontLoader {
    public static Font loadJeopardyFont(float size) {
        try {
            InputStream fontStream = FontLoader.class.getResourceAsStream("/Fonts/jeopardy.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found in resources");
            }

            Font jeopardyFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            return jeopardyFont.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Error: Font not found, using default.");
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

}
