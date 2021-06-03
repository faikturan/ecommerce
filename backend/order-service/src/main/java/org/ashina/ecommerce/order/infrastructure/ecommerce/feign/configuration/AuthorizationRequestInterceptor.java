package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.ashina.ecommerce.order.infrastructure.security.SecurityContextHelper;
import org.springframework.http.HttpHeaders;

public class AuthorizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String accessToken = "Bearer " + SecurityContextHelper.getCurrentAccessToken();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, accessToken);
    }
}
