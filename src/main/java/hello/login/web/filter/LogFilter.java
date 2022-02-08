package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("do filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

        String s = UUID.randomUUID().toString();

        try{
            log.info("REQUEST {} {}", s, requestUri);
            chain.doFilter(request, response);   //다음 필터를 호출
        }catch(Exception e){
            throw e;

        }finally
        {
            log.info("RESPONSE {} {}", s, requestUri);
        }
    }

    @Override
    public void destroy() {
        log.info("destroyed");
    }
}
