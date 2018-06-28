package de.dmeiners.mapping.api;

import java.util.Collections;
import java.util.Map;

public abstract class BaseScript implements Script {

    @Override
    public <T> T execute(T target, Map<String, Object> context) {

        return this.execute(Collections.singletonList(target), context).get(0);
    }
}
