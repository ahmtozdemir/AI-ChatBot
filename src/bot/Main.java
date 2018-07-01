package bot;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private TextArea chat;
    private TextField inputField;
    private Button send;
    private ImageView face;
    private static final int WIDTH = 16 * 30; 
    private static final int IMG_HEIGHT = 16 * 30;

    public static void main(String[] args) {
        launch();
    }

    //GUI
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChatBot");
        primaryStage.setResizable(false);
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #ffffff");
        primaryStage.setScene(new Scene(root, WIDTH, 740));

        initComponents(root);

        primaryStage.show();
        try {
            new ChatBot(chat, inputField, send, face);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents(VBox root) {
        // gifler
        face = new ImageView(new Image("res/smile.gif", WIDTH, IMG_HEIGHT, false, false));
        face.setFitWidth(WIDTH);
        face.setFitHeight(IMG_HEIGHT);
        root.getChildren().add(face);

        //chat
        chat = new TextArea();
        chat.setEditable(false);
        chat.setFocusTraversable(false);
        chat.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))));
        chat.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");
        root.getChildren().add(chat);

        inputField = new TextField();
        root.getChildren().add(inputField);

        root.setAlignment(Pos.CENTER);
        send = new Button("SEND");
        send.setPrefSize(WIDTH, 30);
        root.getChildren().add(send);
    }
}
