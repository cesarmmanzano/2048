package game_2048;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

//========================================================================//
public class MessageSize {

    private MessageSize() {
    }

    //A classe é usada para centralizar o texto quando necessário
    //=====================MESSAGE WIDTH======================================//
    //Largura da mensagem
    public static int getMessageWidth(String message, Font font, Graphics2D g) {
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(message, g);
        return (int) bounds.getWidth(); //Retorna largura 
    }

    //=======================MESSAGE HEIGHT===================================//
    //Altura da mensagem
    public static int getMessageHeight(String message, Font font, Graphics2D g) {
        g.setFont(font);
        if (message.length() == 0) {
            return 0;
        }
        TextLayout text = new TextLayout(message, font, g.getFontRenderContext());
        return (int) text.getBounds().getHeight(); //Retorna altura
    }

    //========================================================================//
}
