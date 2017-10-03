package xpparty.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import xpparty.XPParty;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by Frani on 29/09/2017.
 */
public final class ConfigManager {

    private File configDir;
    private XPParty instance;

    public ConfigManager(XPParty instance, File configDir) {
        this.configDir = configDir;
        this.instance = instance;
        if (!this.configDir.exists()) {
            try {
                this.configDir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ConfigCategory loadConfig() {
        try {
            System.out.println("Config dir: " + instance.configDir);
            File file = new File(instance.configDir, "XPParty.conf");
            if (!file.exists()) {
                file.createNewFile();
            }
            ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(file).build();
            CommentedConfigurationNode config = loader.load(ConfigurationOptions.defaults().setObjectMapperFactory(instance.factory).setShouldCopyDefaults(true));
            ConfigCategory root = config.getValue(TypeToken.of(ConfigCategory.class), new ConfigCategory());
            loader.save(config);
            return root;
        } catch (Exception e) {
            XPParty.instance.logger.warn("Could not load config: ");
            e.printStackTrace();
            return null;
        }
    }

}
