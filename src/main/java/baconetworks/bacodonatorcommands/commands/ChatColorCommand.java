package baconetworks.bacodonatorcommands.commands;

import baconetworks.bacodonatorcommands.BacoDonatorCommands;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatColorCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof ConsoleSource) {
            throw new CommandException(Text.of(TextColors.RED, "You need to be a player to run this command"));
        }

        Player player = (Player) src;

        String[] PossibleColors = {"black", "darkgray", "gray", "darkblue", "blue", "darkaqua", "aqua", "darkgreen", "green", "darkred", "red", "purple", "magenta", "gold", "yellow", "white"};
        String[] PossibleColorsWords = {"Black", "Dark Gray", "Gray", "Dark Blue", "Blue", "Dark Aqua", "Aqua", "Dark Green", "Green", "Dark Red", "Red", "Purple", "Magenta", "Gold", "Yellow", "White"};
        TextColor[] PossibleTextColors = {TextColors.BLACK, TextColors.DARK_GRAY, TextColors.GRAY, TextColors.DARK_BLUE, TextColors.BLUE, TextColors.DARK_AQUA, TextColors.AQUA, TextColors.DARK_GREEN,
                TextColors.GREEN, TextColors.DARK_RED, TextColors.RED, TextColors.DARK_PURPLE, TextColors.LIGHT_PURPLE, TextColors.GOLD, TextColors.YELLOW, TextColors.WHITE};

        Optional<String> Argument = args.getOne(Text.of("color"));
        if (Argument.isPresent()) {
            String ArgumentString = Argument.get();
            String[] PossibleColorsCode = {"0", "8", "7", "1", "9", "3", "b", "2", "a", "4", "c", "5", "d", "6", "e", "f"};
            for (int i = 0; i < PossibleColors.length; i++) {
                if (PossibleColors[i].equalsIgnoreCase(ArgumentString)) {
                    String s = "chatcolour " + PossibleColorsCode[i];
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta unset chatcolour");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + player.getUniqueId() + " meta set " + s);
                    return CommandResult.success();
                }
            }
        } else {
            //Some definitions
            final BacoDonatorCommands pl = BacoDonatorCommands.instance;
            List<Text> texts = new ArrayList<>();
            //We defined the builder here
            for (int i = 0; i < PossibleColors.length; i++) {
                texts.add(Text.builder()
                        .append(Text.builder()
                                .color(PossibleTextColors[i])
                                .style(TextStyles.BOLD)
                                .append(Text.of(PossibleColorsWords[i] + " Color"))
                                .onClick(TextActions.runCommand("/chatcolor " + PossibleColors[i]))
                                .onHover(TextActions.showText(Text.of("Click here to make your chat color permanently " + PossibleColorsWords[i].toLowerCase() + "!")))
                                .build())
                        .build());
            }
            PaginationList paginationlist = pl.getPaginationService().builder().title(BacoDonatorCommands.ChatColorCommand.apply().build()).contents(texts).build();
            paginationlist.sendTo(player);
        }
        return CommandResult.success();
    }
}
