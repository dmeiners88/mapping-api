package de.dmeiners.mapping.api;

import java.util.Collections;
import java.util.Map;

public abstract class BasePostProcessor implements PostProcessor {

    private final ScriptNameResolver scriptNameResolver;
    protected final Map<String, Object> extensions;

    protected BasePostProcessor(ScriptNameResolver scriptNameResolver, Map<String, Object> extensions) {
        this.scriptNameResolver = scriptNameResolver;
        this.extensions = extensions;
    }

    protected BasePostProcessor(ScriptNameResolver scriptNameResolver) {
        this(scriptNameResolver, Collections.emptyMap());
    }

    @Override
    public Script compile(String scriptName, Map<String, Object> context) {

        String scriptText;
        try {
            scriptText = this.scriptNameResolver.resolve(scriptName, context);
        } catch (ScriptNameResolutionException e) {
            throw new ParseException("Unable to parse because script name resolution failed.", e);
        }

        return this.compileInline(scriptText);
    }
}
