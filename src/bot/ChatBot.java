package bot;

import bot.commands.*;
import bot.utils.FileUtils;
import bot.utils.WindowUtils;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

// GUI
 
class ChatBot {

    private String userName;
    private TextArea chat;
    private TextField inputField;
    private ImageView face;
    private PauseTransition pause;
    private final String botName = "Mars";
    private static final long WRITE_TIME = 500;
    private static final long THINKING_TIME = 1700;
    private static final long FACE_TIME = 3000;
    
    //komutlar
    private final Command[] cmds = {new GoogleCommand()}; 
    private Learner learner;

    ChatBot(TextArea chat, TextField inputField, Button send, ImageView face) throws InterruptedException {
        this.chat = chat;
        this.inputField = inputField;
        this.face = face;

        // learner ve vocabulary.txt i baþlat
        FileUtils.createFiles();
        learner = new Learner(new File("vocabulary.txt"));

        // inputfield ý oku
        send.setOnAction(e -> {
            String in = readInput();
            try {
                if (in.length() > 0) {
                    chat.appendText(userName + ": " + in + "\n");
                    handleInput(in);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            inputField.clear();
        });

        //mesaj göndermek için enter 
        inputField.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                send.fire();
            }
        });

        chat.appendText(botName + ": Ýsminiz nedir? :)\n");
        userName = WindowUtils.buildTextDialog("Ýsminiz nedir?", "Lütfen isminizi giriniz :", "Ýsim");

        //greet user:
        chat.appendText(botName + ": Ýsminiz " + userName + "? Þimdi not alýyorum...\n");
        pause = new PauseTransition(Duration.millis(THINKING_TIME));
        pause.setOnFinished(e -> answer(Phrases.GREETINGS));
        pause.play();
    }

    // textfield ý oku
    private String readInput() {
        return inputField.getText();
    }

    private void handleInput(String input) throws InterruptedException {

        pause.setOnFinished(null); //resetle

        //komut olup olmadýðýný kontrol et
        for (Command cmd : cmds) {
            if (cmd.isValid(input)) {
                cmd.execute();
                return;
            }
        }

        //cevaplar ve öðren komutu:/öðren
        if (input.equals("/öðren")) {
            learn();
            return;
        } else if ((matches(input, Phrases.HOW_ARE_YOU))) {
            answer(Phrases.ANSWERS_TO_HOW_ARE_YOU);
        } else if (matches(input, Phrases.WHAT_ARE_YOU_DOING)) {
            answer(Phrases.ANSWERS_TO_WHAT_ARE_YOU_DOING);
        } else if (matches(input, Phrases.EXIT)) {
            answer(Phrases.GOODBYES);
            pause.setDuration(Duration.millis(THINKING_TIME));
            Platform.runLater(() -> showImage("/res/sad.gif"));
            pause.setOnFinished(e -> System.exit(0));
        } else if (matches(input, Phrases.WHAT_TIME_IS_IT)) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            botPrintAfterSleep(WRITE_TIME, "Saat " + df.format(new Date()));
        } else if (matches(input, Phrases.THANKS)) {
            answer(Phrases.NO_PROBLEM);
        } else if (input.equals("neden")) {
            botPrintAfterSleep(WRITE_TIME, "neden neden?");
        } else if (input.equals("neden neden neden")) {
            botPrintAfterSleep(WRITE_TIME, "neden neden neden neden?");
        } else if (matches(input, Phrases.LOL)) {
            answer(Phrases.LAUGHING);
            pause.setDuration(Duration.millis(FACE_TIME));
            Platform.runLater(() -> showImage("/res/laughing.gif"));
            pause.setOnFinished(e -> showImage("/res/smile.gif"));
        } else if (matches(input, Phrases.TELL_ME_A_JOKE)) {
            answer(Phrases.JOKES);
            pause.setDuration(Duration.millis(FACE_TIME));
            Platform.runLater(() -> showImage("/res/joking.gif"));
            pause.setOnFinished(e -> showImage("/res/smile.gif"));
        } else if (matches(input, Phrases.HOW_OLD_ARE_YOU)) {
            answer(Phrases.BOTS_AGE);
            LocalDate birthday = LocalDate.of(2017, 05, 01); 
            LocalDate now = LocalDate.now();
            Period p = Period.between(birthday, now); //doðum gününü hesapla
            botPrintAfterSleep(WRITE_TIME, "  Bu yüzden ben " + p.getYears() + " yýl " + p.getMonths() + " ay ve " + p.getDays() + " gün yaþýndayým.");
        } else if (matches(input, Phrases.HELLO)) {
            answer(Phrases.GREETINGS);
        } else if (matches(input, Phrases.CONFIRMATIONS)) {
            answer(Phrases.ANSWERS_TO_CONFIRMATIONS);
        } else if (matches(input, Phrases.NEED_OF_WISDOM)) {
            answer(Phrases.WISE_WORDS);
            pause.setDuration(Duration.millis(FACE_TIME));
            Platform.runLater(() -> showImage("/res/wise.gif"));
            pause.setOnFinished(e -> showImage("/res/smile.gif"));
        } else if (Phrases.CUSTOM_VOCABULARY.contains(input)) {
            int index = Phrases.CUSTOM_VOCABULARY.indexOf(input);
            botPrintAfterSleep(WRITE_TIME, Phrases.CUSTOM_RESPONSES.get(index));
        } else {
            pause.setDuration(Duration.millis(THINKING_TIME));
            Platform.runLater(() -> showImage("/res/confused.gif"));
            pause.setOnFinished(e -> {
                answer(Phrases.DIDNT_UNDERSTAND);
                showImage("/res/smile.gif");
            });
        }
        pause.play();
    }

    //gif deðiþtir
    private void showImage(String res) {
        face.setImage(new Image(getClass().getResource(res).toString(), face.getFitWidth(), face.getFitHeight(), false, false));
    }

    //vocabulary de olup olmadýðýný kontrol et
    private boolean matches(String string, ArrayList<String> strings) {
        for (String s : strings) {
            if (string.contains(s)) {
                return true;
            }
        }
        return false;
    }

    //random cevap
    private void answer(ArrayList<String> list) {
        Collections.shuffle(list);
        String answer = list.get(0);
        if (answer.contains("%user%")) {
            answer = answer.replace("%user%", userName);
        }
        botPrintAfterSleep(WRITE_TIME, answer);
    }

    private void botPrintAfterSleep(long sleep, String msg) {
        PauseTransition pause = new PauseTransition(Duration.millis(sleep));
        pause.setOnFinished(e -> chat.appendText(botName + ": " + msg + "\n"));
        pause.play();
    }

    private void learn() {
        //cümleyi al
        String vocabulary = WindowUtils.buildTextDialog("Hangi cümleyi öðretmek istiyorsunuz " + botName + "?", "Lütfen cümleyi giriniz:", "Kelimeler");

        //cevabý al
        String response = WindowUtils.buildTextDialog("Cevap " + botName + " ne olmalý?", "Lütfen cümleyi giriniz:", "Cevap");

        learner.learnSentence(vocabulary, response);
    }
}
