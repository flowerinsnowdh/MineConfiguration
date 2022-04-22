package cc.carm.lib.configuration;

import cc.carm.lib.configuration.bungee.BungeeConfigProvider;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.JsonConfiguration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MineConfiguration {

    protected static BungeeConfigProvider create(File file, String source, ConfigurationProvider loader) {
        BungeeConfigProvider provider = new BungeeConfigProvider(file, loader);
        try {
            provider.initializeFile(source);
            provider.initializeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provider;
    }

    public static BungeeConfigProvider from(File file, String source) {
        return fromYAML(file, source);
    }

    public static BungeeConfigProvider from(File file) {
        return from(file, file.getName());
    }

    public static BungeeConfigProvider from(String fileName) {
        return from(fileName, fileName);
    }

    public static BungeeConfigProvider from(String fileName, String source) {
        return from(new File(fileName), source);
    }

    public static BungeeConfigProvider fromYAML(File file, String source) {
        return create(file, source, ConfigurationProvider.getProvider(YamlConfiguration.class));
    }

    public static BungeeConfigProvider fromYAML(String fileName, String source) {
        return fromYAML(new File(fileName), source);
    }

    public static BungeeConfigProvider fromYAML(File file) {
        return fromYAML(file, file.getName());
    }

    public static BungeeConfigProvider fromYAML(String fileName) {
        return fromYAML(fileName, fileName);
    }


    public static BungeeConfigProvider fromJSON(File file, String source) {
        return create(file, source, ConfigurationProvider.getProvider(JsonConfiguration.class));
    }

    public static BungeeConfigProvider fromJSON(String fileName, String source) {
        return fromJSON(new File(fileName), source);
    }

    public static BungeeConfigProvider fromJSON(File file) {
        return fromJSON(file, file.getName());
    }

    public static BungeeConfigProvider fromJSON(String fileName) {
        return fromJSON(fileName, fileName);
    }


}
