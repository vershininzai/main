package ru.mku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.validation.Validator;
import ru.mku.properties.MkuProperties;
import ru.mku.properties.MkuPropertiesValidator;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class MkuApplication extends ResourceServerConfigurerAdapter implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MkuApplication.class);

    private final MkuProperties properties;

    public MkuApplication(MkuProperties properties) {
        this.properties = properties;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new MkuPropertiesValidator();
    }

    @Bean
    public static ShaPasswordEncoder getShaPasswordEncoder() {
        return new ShaPasswordEncoder(512);
    }


    @Override
    public void run(String... args) {
        printProps();
    }


    private void printProps() {
        System.out.println("=========================================");
        System.out.println("test host: " + this.properties.getTestHost());
        System.out.println("=========================================");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(MkuApplication.class).run(args);
    }
}
