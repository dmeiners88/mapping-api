package de.dmeiners.mapping.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Script {

    /**
     * Executes this script against a given target object and context.
     *
     * @throws ExecutionException  if an error occurs during script execution
     * @throws ResultTypeException if the script returns another type than the target object has
     */
    <T> T execute(T target, Map<String, Object> context);

    /**
     * @throws ExecutionException  if an error occurs during script execution
     * @throws ResultTypeException if the script returns another type than the target object has
     */
    <T> List<T> execute(Collection<T> targets, Map<String, Object> context);
}
