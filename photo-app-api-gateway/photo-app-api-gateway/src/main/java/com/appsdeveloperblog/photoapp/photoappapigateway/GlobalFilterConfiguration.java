package com.appsdeveloperblog.photoapp.photoappapigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilterConfiguration {

    private Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Bean
    public GlobalFilter secondPrefilter() {
        return (exchange, chain) -> {
            logger.info("Second Pre-filter was executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Second Post-filter was executed...");
            }));
        };
    }

    @Bean
    public GlobalFilter thirdPrefilter() {
        return (exchange, chain) -> {
            logger.info("Third Pre-filter was executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Third Post-filter was executed...");
            }));
        };
    }

    @Bean
    public GlobalFilter fourthPrefilter() {
        return (exchange, chain) -> {
            logger.info("Fourth Pre-filter was executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Fourth Post-filter was executed...");
            }));
        };
    }

}
