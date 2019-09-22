package game_2048;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class DrawUtils {

    private DrawUtils() {
    }

    public static int getMenssageWidth(String message, Font font, Graphics2D g) {
        /*it will get the message width in therms of pixel so we can center de text*/
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(message, g);
        return (int) bounds.getWidth();
    }

    public static int getMessageHeight(String message, Font font, Graphics2D g) {
        /*this can be trick because the height of a text like 28 size, is going to be like 32 of height */
        g.setFont(font);
        if (message.length() == 0) {
            return 0;
        }
        TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
        return (int) tl.getBounds().getHeight();
    }
}
