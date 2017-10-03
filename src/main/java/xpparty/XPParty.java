package xpparty;

import com.google.inject.Inject;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.AttackEntityEvent;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import xpparty.commands.PartyCommand;
import xpparty.config.ConfigCategory;
import xpparty.config.ConfigManager;

import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "xpparty",
        name = "XPParty",
        version = "1.0",
        description = "Simple party plugin with XP distribution",
        authors = {
                "Eufranio"
        }
)
public class XPParty {

    @Inject
    public Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    public File configDir;

    @Inject
    public GuiceObjectMapperFactory factory;

    public static XPParty instance;
    public static ConfigCategory CONFIG;
    private ConfigManager manager;
    public static PartyManager pManager;
    public PluginEvents eventListeners;

    @Listener
    public void onInit(GameInitializationEvent event) {
        eventListeners = new PluginEvents();
    }

    @Listener
    public void onServerStart(GamePostInitializationEvent event) {
        instance = this;
        manager = new ConfigManager(instance, configDir);
        CONFIG = manager.loadConfig();
        this.logger.info("XPParty is starting!");
        this.pManager = new PartyManager();
        PartyCommand commands = new PartyCommand();
        commands.register(instance);
        Sponge.getEventManager().registerListeners(this, eventListeners);
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        CONFIG = null;
        CONFIG = manager.loadConfig();
    }

}
