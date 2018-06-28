package de.dmeiners.mapping.api;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class PostProcessorBuilder {

    private final PostProcessorProvider provider;
    private ScriptNameResolver scriptNameResolver = new ClasspathScriptNameResolver();
    private final Map<String, Object> extensions = new HashMap<>();

    PostProcessorBuilder(PostProcessorProvider provider) {
        this.provider = provider;
    }

    public PostProcessorBuilder scriptNameResolver(ScriptNameResolver scriptNameResolver) {

        if (nonNull(scriptNameResolver)) {
            this.scriptNameResolver = scriptNameResolver;
        }
        return this;
    }

    public PostProcessorBuilder extension(String name, Object delegate) {

        if (nonNull(name) && nonNull(delegate)) {
            this.extensions.put(name, delegate);
        }
        return this;
    }

    public PostProcessor build() {

        return provider.create(this.scriptNameResolver, this.extensions);
    }
}
