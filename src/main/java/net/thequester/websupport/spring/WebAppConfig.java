package net.thequester.websupport.spring;

import net.thequester.websupport.database.repositories.QuestProvider;
import net.thequester.websupport.database.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@ComponentScan("net.thequester.websupport")
@EnableWebMvc
@PropertySource("classpath:application.properties")
@Import({DatabaseConfig.class, SecurityConfiguration.class})
public class WebAppConfig {

	@Autowired
	private QuestRepository repository;

	@Bean
	public QuestProvider questProvider(){
		return new QuestProvider(repository);
	}

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/app/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(100000);
		return resolver;
	}

}
