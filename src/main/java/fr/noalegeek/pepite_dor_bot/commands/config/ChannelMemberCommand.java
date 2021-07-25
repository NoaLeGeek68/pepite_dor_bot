package fr.noalegeek.pepite_dor_bot.commands.config;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.noalegeek.pepite_dor_bot.Main;
import fr.noalegeek.pepite_dor_bot.commands.CommandCategories;
import fr.noalegeek.pepite_dor_bot.utils.helpers.MessageHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;

import java.util.Locale;

public class ChannelMemberCommand extends Command {

    public ChannelMemberCommand() {
        this.name = "channelmember";
        this.cooldown = 5;
        this.help = "Défini le salon où les annonces des personnes qui rejoignent ou qui quittent le serveur avec son identifiant.";
        this.example = "join 657966618353074206";
        this.aliases = new String[]{"channelm","cmember","cm"};
        this.arguments = "<join|remove> <identifiant du salon>";
        this.category = CommandCategories.CONFIG.category;
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getAuthor().isBot()) return;
        String[] args = event.getArgs().split("\\s+");
        if(args.length != 2){
            event.replyError(MessageHelper.syntaxError(event.getAuthor(),this)+
                    "Les arguments disponibles sont **join** et **remove**.\n" +
                    "L'argument **join** définira le salon où les annonces de bienvenues apparaîtront.\n" +
                    "L'argument **remove** définira le salon où les annonces de départs apparaîtront.");
            return;
        }
        GuildChannel channelMember = event.getGuild().getGuildChannelById(args[1]);
        if(channelMember == null){
            event.replyError("Ce salon n'existe pas.");
            return;
        }
        switch (args[0].toLowerCase(Locale.ROOT)){
            case "join":
                if(event.getGuild().getGuildChannelById(Main.getServerConfig().channelMemberJoin.get(event.getGuild().getId())).equals(channelMember)){
                    event.replyError(MessageHelper.formattedMention(event.getAuthor()) + "Le salon que vous voulez changer pour les messages de bienvenue est le même que celui configuré actuellement.");
                    return;
                }
                Main.getServerConfig().channelMemberJoin.put(event.getGuild().getId(),channelMember.getId());
                break;
            case "remove":
                if(event.getGuild().getGuildChannelById(Main.getServerConfig().channelMemberRemove.get(event.getGuild().getId())).equals(channelMember)){
                    event.replyError(MessageHelper.formattedMention(event.getAuthor()) + "Le salon que vous voulez changer pour les messages de départs est le même que celui configuré actuellement.");
                    return;
                }
                Main.getServerConfig().channelMemberRemove.put(event.getGuild().getId(),channelMember.getId());
                break;
            default:
                event.replyError(MessageHelper.syntaxError(event.getAuthor(),this)+"" +
                        "Les arguments disponibles sont **join** et **remove**.\n" +
                        "L'argument **join** définira le salon où les messages de bienvenues apparaîtront.\n" +
                        "L'argument **remove** définira le salon où les messages de départs apparaîtront.");
        }
        event.replySuccess("Le salon " + channelMember.getAsMention() + " a bien été défini.");
    }
}
