package bot.commands;

import bot.utils.WebUtils;

//google komutu geçerli format :google aranacak_kelime
public class GoogleCommand extends Command {

    private String searchFor;

    public GoogleCommand() {
    }

    @Override
    public String getTrigger() {
        return "google";
    }

    //validate
    @Override
    public boolean isValid(String toValidate) {
        if (toValidate.startsWith(getTrigger() + " ")) {
            toValidate = toValidate.substring(getTrigger().length()).trim();
            searchFor = WebUtils.adjustSearchText(toValidate);
            return true;
        }
        return false;
    }

    //google da ara
    @Override
    public void execute() {
        WebUtils.openURL("https://www.google.com/webhp#q=" + searchFor);
    }
}
