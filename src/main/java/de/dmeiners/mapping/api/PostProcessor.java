package de.dmeiners.mapping.api;

import java.util.Map;

public interface PostProcessor {

    /**
     * Resolves a given script name to a script text and compiles it.
     * The script name will be resolved with this instance's {@link ScriptNameResolver}.
     * Use {@link PostProcessorFactory#builder()} to specify a resolver at creation time.
     *
     * @throws ParseException if the script text can not be parsed
     */
    Script compile(String scriptName, Map<String, Object> context);

    /**
     * Compiles a given script text with this post processor instance.
     *
     * @throws ParseException if the script text can not be parsed
     */
    Script compileInline(String scriptText);
}
