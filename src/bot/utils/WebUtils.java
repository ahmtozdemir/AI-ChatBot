package bot.utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebUtils {
	
	//google search URL ayarlamasý
    public static String adjustSearchText(String s) {
        s = s.replace("%", "%25");
        s = s.replace(" ", "%20");
        s = s.replace("!", "%21");
        s = s.replace("\"", "%22");
        s = s.replace("#", "%23");
        s = s.replace("$", "%24");
        s = s.replace("&", "%26");
        s = s.replace("'", "%27");
        s = s.replace("(", "%28");
        s = s.replace(")", "%29");
        s = s.replace("*", "%2A");
        s = s.replace("+", "%2B");
        s = s.replace(",", "%2C");
        s = s.replace("-", "%2D");
        s = s.replace(".", "%2E");
        s = s.replace("/", "%2F");

        return s;
    }

    //varsayýlan browser da aç
    public static void openURL(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
