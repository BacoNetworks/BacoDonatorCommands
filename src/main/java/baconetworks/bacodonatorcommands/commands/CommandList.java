package baconetworks.bacodonatorcommands.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandList {
    public static CommandSpec ChatColor = CommandSpec.builder().description(Text.of("Set your permanent chat color")).arguments(GenericArguments.optional(GenericArguments.string(Text.of("color")))).permission("donatorcommands.command.chatcolorcommand").executor(new ChatColorCommand()).build();
    public static CommandSpec Prefix = CommandSpec.builder().description(Text.of("Set your prefix")).arguments(GenericArguments.optional(GenericArguments.string(Text.of("prefix")))).permission("donatorcommands.command.prefixcommand").executor(new PrefixCommand()).build();

}
