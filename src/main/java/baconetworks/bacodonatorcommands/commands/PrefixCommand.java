package baconetworks.bacodonatorcommands.commands;

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
            throw new CommandException(Text.of(TextColors.RED, "You need to be a player to run this command"));
        }

        Player player = (Player) src;

        Optional<String> Argument = args.getOne(Text.of("prefix"));
        if (Argument.isPresent()) {
            String ArgumentString = Argument.get();

            String[] ForbiddenRanks = {"contributor", "mod", "moderator", "dev", "developer", "op", "admin", "senior", "senioradmin", "owner", "staff"};
            String[] ForbiddenColors = {"&k", "&l", "&m", "&n", "&o", "&r", "[", "]", "{", "}"};

            String result = ArgumentString;

            if (ArgumentString.length() < 2) {
                throw new CommandException(Text.of(TextColors.RED, "One length prefixes are not allowed!"));
            }
            if (ArgumentString.length() > 12) {
                throw new CommandException(Text.of(TextColors.RED, "You can't have super long prefixes."));
            }

            while (result.contains("&")) {
                int replacePartIndex = result.indexOf('&');
                result = result.substring(0, replacePartIndex) + result.substring(replacePartIndex + 2);
            }

            for (String forbiddenRank : ForbiddenRanks) {
                if (result.toLowerCase().contains(forbiddenRank)) {
                    throw new CommandException(Text.of(TextColors.RED, " This prefix is not usable since it contains characters of either a staff rank or the contributor rank!  " + "\n" + " Character used: " + forbiddenRank));
                }
            }
            for (String forbiddenColor : ForbiddenColors) {
                if (ArgumentString.toLowerCase().contains(forbiddenColor)) {
                    throw new CommandException(Text.of(TextColors.RED, " You are not allowed to use these color codes! Or the characters [ and ] or { and }." + "\n" + " Color code or character used: " + forbiddenColor));
                }
            }

            ArgumentString = "&0[&f"+ ArgumentString + "&0]";

            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta clear prefixes");
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta addprefix 30 " + ArgumentString);

            player.sendMessage(Text.of(TextColors.GREEN, "Your prefix has been successfully set!"));
            return CommandResult.success();
        }else {
            throw new CommandException(Text.of(TextColors.RED, "Command usage: /prefix <prefix>"));
        }
    }
}
