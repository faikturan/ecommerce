package org.ashina.ecommerce.order.infrastructure.resilience.hystrix;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@EnableHystrixDashboard
public class HystrixConfiguration {
}
