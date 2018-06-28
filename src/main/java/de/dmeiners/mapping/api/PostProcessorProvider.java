package de.dmeiners.mapping.api;

import java.util.Map;

public interface PostProcessorProvider {

    PostProcessor create(ScriptNameResolver scriptNameResolver);

    default PostProcessor create(ScriptNameResolver scriptNameResolver, Map<String, Object> extensions) {
        return create(scriptNameResolver);
    }
}
