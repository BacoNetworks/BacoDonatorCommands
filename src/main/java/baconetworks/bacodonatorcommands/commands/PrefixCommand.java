package baconetworks.bacodonatorcommands.commands;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class PrefixCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof ConsoleSource) {
            Text notplayer = Text.builder("You need to be a player to run this command").color(TextColors.DARK_RED).build();
            src.sendMessage(notplayer);
            return CommandResult.success();
        }

        Player player = (Player) src;

        Optional<String> Argument = args.getOne(Text.of("prefix"));
        if (Argument.isPresent()) {
            String ArgumentString = Argument.get();

            String[] ForbiddenRanks = {"contributor", "mod", "moderator", "dev", "developer", "op", "admin", "senior", "senioradmin", "owner", "staff"};
            String[] ForbiddenColors = {"&k", "&l", "&m", "&n", "&o", "&r", "[", "]", "{", "}" };

            String result = ArgumentString;

            if(ArgumentString.length() < 2){
                Text badlenght = Text.builder("One length prefixes are not allowed! ").color(TextColors.DARK_RED).build();
                src.sendMessage(badlenght);
                return CommandResult.success();
            }

            while (result.contains("&")){
                int replacePartIndex = result.indexOf('&');
                result = result.substring(0, replacePartIndex) + result.substring(replacePartIndex+2);
            }

            for (String forbiddenRank : ForbiddenRanks) {
                if (StringUtils.containsIgnoreCase(forbiddenRank, result)) {
                    Text badprefix = Text.builder("Impersonating staff or a contributor is a bannable offence!").color(TextColors.DARK_RED).build();
                    src.sendMessage(badprefix);
                    return CommandResult.success();
                }
            }
            for (String forbiddenColor : ForbiddenColors) {
                if (StringUtils.containsIgnoreCase(forbiddenColor, ArgumentString)) {
                    Text badcolor = Text.builder(" You are not allowed to use these color codes! Or the characters [ and ] or { and }").color(TextColors.DARK_RED).build();
                    src.sendMessage(badcolor);
                    return CommandResult.success();
                }
            }

            ArgumentString = "&0[&f"+ ArgumentString + "&0]";

            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta clear prefixes");
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta addprefix 30 " + ArgumentString);

            Text setprefix = Text.builder("Your prefix has been successfully set!").color(TextColors.GREEN).build();
            src.sendMessage(setprefix);

            return CommandResult.success();
        }else {
            Text commandusage = Text.builder("Command usage: /prefix <prefix>").color(TextColors.DARK_RED).build();
            src.sendMessage(commandusage);
        }
        return CommandResult.success();
    }
}
