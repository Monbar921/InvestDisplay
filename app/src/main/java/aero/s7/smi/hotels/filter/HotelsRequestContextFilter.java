package aero.s7.smi.hotels.filter;

import aero.s7.smi.hotels.common.model.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HotelsRequestContextFilter extends OncePerRequestFilter {
    private final RequestContext requestContext;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain
    ) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(HttpHeaders.FROM))
                .ifPresent(requestContext::setUid);
        filterChain.doFilter(request, response);
    }
}
