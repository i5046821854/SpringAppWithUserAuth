package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LoginCheckInterceptor;
import hello.login.web.interceptor.LoginInterceptor;
import lombok.extern.java.Log;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration   //필터도 애노테이션으로 자동 컴포넌트 스캔이 되지만  order를 지정해줄 수 없으므로 configuration
public class WebConfig implements WebMvcConfigurer {

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new LogFilter());
        filterBean.setOrder(1);   //필터의 순서 (낮은 순서대로 먼저 실행됨)
        filterBean.addUrlPatterns("/*"); //모든 url에 적용

        return filterBean;
    }

    //@Bean
    public FilterRegistrationBean logFilterV2(){
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new LoginCheckFilter());
        filterBean.setOrder(2);   //필터의 순서 (낮은 순서대로 먼저 실행됨)
        filterBean.addUrlPatterns("/*"); //나중에 화이트 리스트에서 걸러짐

        return filterBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).order(1)
                .addPathPatterns("/**").excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor()).order(2)  //여러개의 인터셉터를 하나의 메소드에서 정의
               .addPathPatterns("/**").excludePathPatterns("/", "/css/**", "/*.ico", "/error", "/members/add", "/login", "/logout");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
