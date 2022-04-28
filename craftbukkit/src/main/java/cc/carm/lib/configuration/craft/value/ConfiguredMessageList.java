package cc.carm.lib.configuration.craft.value;

import cc.carm.lib.configuration.common.value.ConfigMessageList;
import cc.carm.lib.configuration.core.source.ConfigCommentInfo;
import cc.carm.lib.configuration.core.source.ConfigurationProvider;
import cc.carm.lib.configuration.craft.CraftConfigValue;
import cc.carm.lib.configuration.craft.builder.message.CraftMessageListBuilder;
import cc.carm.lib.configuration.craft.data.MessageText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ConfiguredMessageList<M> extends ConfigMessageList<M, MessageText, CommandSender> {

    @NotNull
    public static <M> CraftMessageListBuilder<M> create(@NotNull BiFunction<@Nullable CommandSender, @NotNull String, @Nullable M> messageParser) {
        return CraftConfigValue.builder().createMessage().asList(messageParser);
    }

    public static CraftMessageListBuilder<String> asStrings() {
        return CraftConfigValue.builder().createMessage().asStringList();
    }

    public static ConfiguredMessageList<String> ofStrings(@NotNull String... defaultMessages) {
        return asStrings().defaults(defaultMessages).build();
    }

    public ConfiguredMessageList(@Nullable ConfigurationProvider<?> provider,
                                 @Nullable String sectionPath, @Nullable ConfigCommentInfo comments,
                                 @NotNull List<MessageText> messages, @NotNull String[] params,
                                 @NotNull BiFunction<@Nullable CommandSender, @NotNull String, @Nullable M> messageParser,
                                 @NotNull BiConsumer<@NotNull CommandSender, @NotNull List<M>> sendFunction) {
        super(provider, sectionPath, comments, MessageText.class, messages, params, messageParser, sendFunction, MessageText::of);
    }

    public void broadcast(@NotNull Map<String, Object> placeholders) {
        Bukkit.getOnlinePlayers().forEach(pl -> send(pl, placeholders));
        send(Bukkit.getConsoleSender(), placeholders);
    }

}
