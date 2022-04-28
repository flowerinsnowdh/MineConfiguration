package cc.carm.lib.configuration.craft.value;

import cc.carm.lib.configuration.common.value.ConfigMessage;
import cc.carm.lib.configuration.core.source.ConfigCommentInfo;
import cc.carm.lib.configuration.core.source.ConfigurationProvider;
import cc.carm.lib.configuration.craft.CraftConfigValue;
import cc.carm.lib.configuration.craft.builder.message.CraftMessageValueBuilder;
import cc.carm.lib.configuration.craft.data.MessageText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ConfiguredMessage<M> extends ConfigMessage<M, MessageText, CommandSender> {

    @NotNull
    public static <M> CraftMessageValueBuilder<@Nullable M> create(@NotNull BiFunction<@Nullable CommandSender, @NotNull String, @Nullable M> messageParser) {
        return CraftConfigValue.builder().createMessage().asValue(messageParser);
    }

    public static CraftMessageValueBuilder<String> asString() {
        return CraftConfigValue.builder().createMessage().asStringValue();
    }

    public static ConfiguredMessage<String> ofString() {
        return asString().build();
    }

    public static ConfiguredMessage<String> ofString(@NotNull String defaultMessage) {
        return asString().defaults(defaultMessage).build();
    }

    public ConfiguredMessage(@Nullable ConfigurationProvider<?> provider,
                             @Nullable String sectionPath, @Nullable ConfigCommentInfo comments,
                             @NotNull MessageText defaultMessage, @NotNull String[] params,
                             @NotNull BiFunction<@Nullable CommandSender, @NotNull String, @Nullable M> messageParser,
                             @NotNull BiConsumer<@NotNull CommandSender, @NotNull M> sendFunction) {
        super(provider, sectionPath, comments, MessageText.class, defaultMessage, params, messageParser, sendFunction, MessageText::of);
    }


    public void broadcast(@NotNull Map<String, Object> placeholders) {
        Bukkit.getOnlinePlayers().forEach(pl -> send(pl, placeholders));
        send(Bukkit.getConsoleSender(), placeholders);
    }


}
