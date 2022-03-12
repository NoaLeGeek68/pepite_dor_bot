package fr.noalegeek.simplebot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.noalegeek.simplebot.enums.CommandCategories;
import fr.noalegeek.simplebot.utils.MessageHelper;
import net.dv8tion.jda.api.Permission;

public class CreateChannelCommand extends Command {

    public CreateChannelCommand() {
        this.name = "createchannel";
        this.cooldown = 30;
        this.arguments = "arguments.createChannel";
        this.help = "help.createChannel";
        this.example = "text général 846048803554852905";
        this.category = CommandCategories.STAFF.category;
        this.guildOnly = true;
        this.aliases = new String[]{"cc", "createc", "cchannel"};
        this.userPermissions = new Permission[]{Permission.MANAGE_CHANNEL};
        this.botPermissions = new Permission[]{Permission.MANAGE_CHANNEL};
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+");
        if(args.length != 2 && args.length != 3){
            MessageHelper.syntaxError(event, this, MessageHelper.translateMessage(event, "syntax.createChannel"));
            return;
        }
        if (args[1].toCharArray().length > 100) {
            event.reply(MessageHelper.formattedMention(event.getAuthor()) + MessageHelper.translateMessage(event, "error.createChannel.tooManyCharacters"));
            return;
        }
        switch (args[0]) {
            case "text" -> event.getGuild().createTextChannel(args[1], event.getGuild().getCategoryById(args[2])).queue();
            case "voice" -> event.getGuild().createVoiceChannel(args[1], event.getGuild().getCategoryById(args[2])).queue();
            default -> MessageHelper.syntaxError(event, this, MessageHelper.translateMessage(event, "syntax.createChannel"));
        }
    }
}