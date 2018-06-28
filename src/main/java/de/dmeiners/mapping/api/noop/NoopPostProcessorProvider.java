package de.dmeiners.mapping.api.noop;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorProvider;
import de.dmeiners.mapping.api.ScriptNameResolver;

public class NoopPostProcessorProvider implements PostProcessorProvider {

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver) {

        return new NoopPostProcessor(scriptNameResolver);
    }
}
