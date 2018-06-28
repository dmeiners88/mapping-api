package de.dmeiners.mapping.api.noop;

import de.dmeiners.mapping.api.BaseScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class NoopScript extends BaseScript {

    private static final Logger logger = LoggerFactory.getLogger(NoopScript.class);

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        logger.warn("Executing a no-operation script. Returning input objects unaltered.");
        return new ArrayList<>(targets);
    }
}
