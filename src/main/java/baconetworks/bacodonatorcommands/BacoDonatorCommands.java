package baconetworks.bacodonatorcommands;

import baconetworks.bacodonatorcommands.commands.CommandList;
import com.google.inject.Inject;
import ninja.leaping.configurate.objectmapping.Setting;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

@Plugin(
        id = "bacodonatorcommands",
        name = "BacoDonatorCommands",
        description = "BacoDonator Commands",
        authors = {
                "kristi71111"
        }
)
public class BacoDonatorCommands {
    public static BacoDonatorCommands instance;

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    //Make commands
    private void makeCommands() {
        Sponge.getCommandManager().register(instance, CommandList.ChatColor, "chatcolor", "chatcolour");
        Sponge.getCommandManager().register(instance, CommandList.Prefix, "setprefix", "prefix");
    }


    //On Game Initialization
    @Listener
    public void onInit(GameInitializationEvent event) {
        instance = this;
    }

    //The text template builder
    @Setting("Chat Color Selector")
    public static TextTemplate ChatColorCommand = TextTemplate.of(TextColors.GREEN, TextStyles.BOLD, "Click to select");

    //Pagination service builder
    public PaginationService getPaginationService() {
        return game.getServiceManager().provide(PaginationService.class).get();
    }


    //On Game Stop
    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        logger.info("Stopped DonatorCommands successfully");
    }

    //On Game Start
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("I started just fine. Running on version 1.0.0 of DonatorCommands");
        makeCommands();
    }
}
