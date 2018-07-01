package bot;

import bot.utils.FileUtils;
import java.io.File;

//yeni sorular ve cevaplarý öðren
class Learner {

    private File file;

    Learner(File vocFile) {
        this.file = vocFile;
        if (!FileUtils.read(file).equals("")) {
            loadSentences();
        }
    }
     
    void learnSentence(String vocabulary, String response) {
        FileUtils.write(vocabulary + "::" + response, file); //soru::cevap)
        loadSentences(); //kaydet
    }

    private void loadSentences() {
        String sentences = FileUtils.read(file);
        String[] couples = splitSentences(sentences);
        for (String couple : couples) {
            String[] vocabularyAndResponse = couple.split("::");
            addToVoc(vocabularyAndResponse);
        }
    }

    // her satýrý split et
    private String[] splitSentences(String sentences) {
        return sentences.split("\\n");
    }

    private void addToVoc(String[] vocabularyAndResponse) {
        Phrases.CUSTOM_VOCABULARY.add(vocabularyAndResponse[0]);
        Phrases.CUSTOM_RESPONSES.add(vocabularyAndResponse[1]);
    }
}
