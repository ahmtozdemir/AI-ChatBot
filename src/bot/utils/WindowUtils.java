package bot.utils;

import javafx.scene.control.TextInputDialog;

public class WindowUtils {

    public static String buildTextDialog(String title, String header, String defaultInputText) {
        TextInputDialog dialog = new TextInputDialog(defaultInputText);
        dialog.setTitle(title);
        dialog.setGraphic(null);
        dialog.setHeaderText(header);
        dialog.showAndWait();
        return dialog.getResult();
    }
}
