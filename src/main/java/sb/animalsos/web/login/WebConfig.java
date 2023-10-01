package sb.animalsos.web.login;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sb.animalsos.web.filter.LogFilter;
import sb.animalsos.web.interceptor.LogInterceptor;
import sb.animalsos.web.interceptor.LoginCheckInterceptor;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogInterceptor())
                    .order(1)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/css/**", "/*.ico", "/error");
            registry.addInterceptor(new LoginCheckInterceptor())
                    .order(2)
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"
                    );
        }


  //  @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;


    }
}
