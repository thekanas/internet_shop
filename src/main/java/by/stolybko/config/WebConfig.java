package by.stolybko.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(DatabaseConfig.class)
@EnableWebMvc
public class WebConfig  implements WebMvcConfigurer {

}
