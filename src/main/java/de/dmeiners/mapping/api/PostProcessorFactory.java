package de.dmeiners.mapping.api;

import de.dmeiners.mapping.api.noop.NoopPostProcessorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class PostProcessorFactory {

    private static final Logger logger = LoggerFactory.getLogger(PostProcessorFactory.class);

    private static final ServiceLoader<PostProcessorProvider> loader = ServiceLoader.load(PostProcessorProvider.class);

    private PostProcessorFactory() {
    }

    public static PostProcessorBuilder builder() {

        Iterator<PostProcessorProvider> providers = loader.iterator();

        if (providers.hasNext()) {

            PostProcessorProvider provider = providers.next();

            return new PostProcessorBuilder(provider);
        }

        logger.warn("No post processor implementation found on classpath. Falling back to a no-operation implementation.");
        return new PostProcessorBuilder(new NoopPostProcessorProvider());
    }
}
