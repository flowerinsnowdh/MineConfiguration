package cc.carm.lib.configuration.craft;

import cc.carm.lib.configuration.core.source.ConfigurationProvider;
import cc.carm.lib.configuration.core.value.impl.CachedConfigValue;
import cc.carm.lib.configuration.craft.builder.CraftConfigBuilder;
import cc.carm.lib.configuration.craft.source.CraftConfigProvider;
import cc.carm.lib.configuration.craft.source.CraftSectionWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CraftConfigValue<T> extends CachedConfigValue<T> {

    public static @NotNull CraftConfigBuilder builder() {
        return new CraftConfigBuilder();
    }

    public CraftConfigValue(@Nullable CraftConfigProvider provider, @Nullable String sectionPath,
                            @Nullable List<String> headerComments, @Nullable String inlineComments,
                            @Nullable T defaultValue) {
        super(provider, sectionPath, headerComments, inlineComments, defaultValue);
    }

    public CraftConfigProvider getBukkitProvider() {
        ConfigurationProvider<?> provider = getProvider();
        if (provider instanceof CraftConfigProvider) return (CraftConfigProvider) getProvider();
        else throw new IllegalStateException("Provider is not a SpigotConfigProvider");
    }

    public CraftSectionWrapper getBukkitConfig() {
        return getBukkitProvider().getConfiguration();
    }

}
