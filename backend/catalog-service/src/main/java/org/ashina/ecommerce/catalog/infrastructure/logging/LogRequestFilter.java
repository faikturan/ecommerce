package org.ashina.ecommerce.catalog.infrastructure.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LogRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // We CANNOT simply read the request body here
        // Because the InputStream would be consumed and cannot be read again by the actual processing/server
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestURL = getRequestURL(request);
        String requestMethod = getRequestMethod(request);
        Map<String, String> requestHeaders = getRequestHeaders(request);
        // We can only log the request body AFTER the request has been made and ContentCachingRequestWrapper did its work
        String requestBody = getRequestBody(requestWrapper);

        log.debug("{} {}\nHeaders: {}\nBody: {}", requestMethod, requestURL, requestHeaders, requestBody);

        // Client would not receive response unless call this method
        responseWrapper.copyBodyToResponse();
    }

    private String getRequestURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestURL.append("?").append(queryString);
        }
        return requestURL.toString();
    }

    private String getRequestMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headersByName = request.getHeaders(headerName);
            while (headersByName.hasMoreElements()) {
                String headerValue = headersByName.nextElement();
                headerMap.put(headerName, headerValue);
            }
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] content = requestWrapper.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }
        return new String(content, 0, Math.min(content.length, 10000));
    }
}