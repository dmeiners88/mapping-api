package de.dmeiners.mapping.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClasspathScriptNameResolver implements ScriptNameResolver {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathScriptNameResolver.class);

    private final String prefix;
    private final String tenantKeyInScriptContext;

    /**
     * Constructs an instance with default values:
     * <ul>
     * <li>prefix: <code>/jexl</code></li>
     * <li>tenant key in context: <code>tenantId</code></li>
     * </ul>
     * Let's assume the script context map contains a mapping <code>tenantId -> 1234</code>.
     * This instance will thus search for a given script name of <code>myScript.jexl</code> in the following paths, in order:
     * <ol>
     * <li><code>/jexl/1234/myScript.jexl</code></li>
     * <li><code>/jexl/myScript.jexl</code></li>
     * </ol>
     */
    public ClasspathScriptNameResolver() {
        this("/jexl", "tenantId");
    }

    /**
     * Constructs an instance with the given values for the classpath prefix and tenant key in context.
     * <p>
     * Let's assume a classpath prefix of <code>/scripts</code> was given and the script context map contains a
     * mapping <code>tenantId -> 1234</code>.
     * This instance will thus search for a given script name of <code>myScript.jexl</code> in the following paths, in order:
     * <ol>
     * <li><code>/scripts/1234/myScript.jexl</code></li>
     * <li><code>/scripts/myScript.jexl</code></li>
     * </ol>
     */
    public ClasspathScriptNameResolver(String prefix, String tenantKeyInScriptContext) {
        this.prefix = prefix;
        this.tenantKeyInScriptContext = tenantKeyInScriptContext;

        logger.debug("Initialized with prefix '{}' and tenant key in script context '{}'.", this.prefix, this.tenantKeyInScriptContext);
    }

    /**
     * @param scriptName The script name, the suffix <code>.jexl</code> is optional
     * @param context    The script context
     */
    @Override
    public String resolve(String scriptName, Map<String, Object> context) {

        URL resource = getResource(ensureSuffix(scriptName), context);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8))) {

            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {

            throw new ScriptNameResolutionException(
                String.format("Error while reading script '%s' from classpath.", resource.getPath()), e);
        }
    }

    private URL getResource(String scriptName, Map<String, Object> context) {

        List<String> searchPath = Stream.of(withTenantPrefix(scriptName, context), withoutTenantPrefix(scriptName))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        logger.debug("Trying to resolve the following script names, in order: {}.", searchPath);

        return searchPath.stream()
            .map(ClasspathScriptNameResolver.class::getResource)
            .filter(Objects::nonNull)
            .peek(resource -> logger.debug("Successfully resolved '{}'.", resource.getPath()))
            .findFirst()
            .orElseThrow(() -> new ScriptNameResolutionException(
                String.format("Could not find any of the following classpath resources: %s.", searchPath)));
    }

    private String ensureSuffix(String scriptName) {

        return scriptName.endsWith(".jexl") ? scriptName : scriptName + ".jexl";
    }

    private String withoutTenantPrefix(String scriptName) {

        return this.prefix + "/" + scriptName;
    }

    private String withTenantPrefix(String scriptName, Map<String, Object> context) {

        if (context.containsKey(this.tenantKeyInScriptContext)) {
            return this.prefix + "/" + context.get(this.tenantKeyInScriptContext) + "/" + scriptName;
        }

        return null;
    }
}
