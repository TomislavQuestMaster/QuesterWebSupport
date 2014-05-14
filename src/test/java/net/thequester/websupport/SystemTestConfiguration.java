package net.thequester.websupport;

import net.thequester.websupport.spring.DatabaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author tdubravcevic
 */
@Configuration
@Import(DatabaseConfig.class)
@PropertySource("classpath:test/systemTest.properties")
public class SystemTestConfiguration {
}

