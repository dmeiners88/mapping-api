package de.dmeiners.mapping.api.noop;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.Script;
import de.dmeiners.mapping.api.ScriptNameResolver;

public class NoopPostProcessor extends BasePostProcessor {

    protected NoopPostProcessor(ScriptNameResolver scriptNameResolver) {
        super(scriptNameResolver);
    }

    @Override
    public Script compileInline(String scriptText) {
        return new NoopScript();
    }
}
