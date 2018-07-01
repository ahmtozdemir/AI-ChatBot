package bot.commands;

public abstract class Command {

    Command() {
    }

    public abstract String getTrigger();

    public abstract boolean isValid(String toValidate);

    public abstract void execute();

}
