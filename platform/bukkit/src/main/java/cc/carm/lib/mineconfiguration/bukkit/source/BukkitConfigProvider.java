package cc.carm.lib.mineconfiguration.bukkit.source;

import cc.carm.lib.configuration.core.ConfigInitializer;
import cc.carm.lib.configuration.core.source.ConfigurationComments;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class BukkitConfigProvider extends CraftConfigProvider {

    protected static final char SEPARATOR = '.';

    protected @NotNull BukkitYAMLComments comments = new BukkitYAMLComments();

    public BukkitConfigProvider(@NotNull File file) {
        super(file);
    }

    public void initializeConfig() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.initializer = new ConfigInitializer<>(this);
    }

    @Override
    public @NotNull CraftSectionWrapper getConfiguration() {
        return CraftSectionWrapper.of(this.configuration);
    }

    @Override
    public void save() throws Exception {
        configuration.save(getFile());

        StringWriter writer = new StringWriter();
        this.comments.writeComments(configuration, new BufferedWriter(writer));
        String value = writer.toString(); // config contents

        Path toUpdatePath = getFile().toPath();
        if (!value.equals(new String(Files.readAllBytes(toUpdatePath), StandardCharsets.UTF_8))) {
            Files.write(toUpdatePath, value.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public @Nullable ConfigurationComments getComments() {
        return this.comments;
    }

}
