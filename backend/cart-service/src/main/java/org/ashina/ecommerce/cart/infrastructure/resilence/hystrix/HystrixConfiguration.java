package org.ashina.ecommerce.cart.infrastructure.resilence.hystrix;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

/**
 * Support for Hystrix Dashboard (not OpenFeign Hystrix)
 */
@Configuration
@EnableHystrix
@EnableHystrixDashboard
public class HystrixConfiguration {
}
