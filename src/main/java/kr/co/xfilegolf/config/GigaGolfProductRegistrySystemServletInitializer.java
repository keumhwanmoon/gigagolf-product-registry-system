package kr.co.xfilegolf.config;

import kr.co.xfilegolf.GigagolfProductRegistrySystemApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author jason, Moon
 * @since 2016-10-11
 */
@Configuration
public class GigaGolfProductRegistrySystemServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GigagolfProductRegistrySystemApplication.class);
    }
}
