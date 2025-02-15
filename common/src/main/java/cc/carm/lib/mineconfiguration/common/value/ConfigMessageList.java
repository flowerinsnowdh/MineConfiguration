package cc.carm.lib.mineconfiguration.common.value;

import cc.carm.lib.configuration.core.function.ConfigDataFunction;
import cc.carm.lib.configuration.core.source.ConfigurationProvider;
import cc.carm.lib.configuration.core.value.type.ConfiguredList;
import cc.carm.lib.mineconfiguration.common.data.AbstractText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ConfigMessageList<M, T extends AbstractText<R>, R>
        extends ConfiguredList<T> implements BaseMessage<R, List<M>> {

    protected final @NotNull String[] params;
    protected final @NotNull BiFunction<@Nullable R, @NotNull String, @Nullable M> messageParser;
    protected final @NotNull BiConsumer<@NotNull R, @NotNull List<M>> sendFunction;

    protected final @NotNull Function<String, T> textBuilder;

    public ConfigMessageList(@Nullable ConfigurationProvider<?> provider, @Nullable String sectionPath,
                             @Nullable List<String> headerComments, @Nullable String inlineComments,
                             @NotNull Class<T> textClazz, @NotNull List<T> messages, @NotNull String[] params,
                             @NotNull BiFunction<@Nullable R, @NotNull String, @Nullable M> messageParser,
                             @NotNull BiConsumer<@NotNull R, @NotNull List<M>> sendFunction,
                             @NotNull Function<String, @NotNull T> textBuilder) {
        super(
                provider, sectionPath, headerComments, inlineComments, textClazz, messages,
                ConfigDataFunction.castToString().andThen(textBuilder::apply), AbstractText::getMessage
        );
        this.params = params;
        this.messageParser = messageParser;
        this.sendFunction = sendFunction;
        this.textBuilder = textBuilder;
    }

    @Override
    public String[] getParams() {
        return params;
    }

    @Override
    public void apply(@NotNull R receiver, @NotNull List<M> message) {
        sendFunction.accept(receiver, message);
    }

    /**
     * 为某位接收者解析消息
     *
     * @param receiver     消息的接收者
     * @param placeholders 消息中的变量与对应参数
     */
    @Override
    public @Nullable List<M> parse(@Nullable R receiver, @NotNull Map<String, Object> placeholders) {
        List<T> list = get();
        if (list.isEmpty()) return null;

        List<String> messages = list.stream().map(T::getMessage).collect(Collectors.toList());
        if (String.join("", messages).isEmpty()) return null;

        return list.stream().map(value -> value.parse(this.messageParser, receiver, placeholders))
                .collect(Collectors.toList());
    }

    public void setMessages(@NotNull String... values) {
        setMessages(values.length == 0 ? null : Arrays.asList(values));
    }

    public void setMessages(@Nullable List<String> values) {
        if (values == null || values.isEmpty()) {
            set(null);
        } else {
            set(buildText(values));
        }
    }

    protected List<T> buildText(List<String> values) {
        return values.stream().map(textBuilder).collect(Collectors.toList());
    }

}
