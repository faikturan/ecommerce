package org.ashina.ecommerce.gateway.server.route;

import org.ashina.ecommerce.gateway.server.filter.AuthorizationHeaderFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, AuthorizationHeaderFilter authorizationHeaderFilter) {

        return builder
                .routes()
                .route(r -> r
                        .path("/catalog-service/**")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("'/catalog-service' + '/(?<remaining>.*)'", "'/$\\{remaining}'")
                                .filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://CATALOG-SERVICE")
                        .id("catalog-service")
                )
                .build();
    }

}
