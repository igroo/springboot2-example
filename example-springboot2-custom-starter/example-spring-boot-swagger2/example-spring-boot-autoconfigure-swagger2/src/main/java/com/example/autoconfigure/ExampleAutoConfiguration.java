package com.example.autoconfigure;

import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnClass({ Docket.class })
@EnableConfigurationProperties(ExampleProperties.class)
@Import(BeanValidatorPluginsConfiguration.class)
@EnableSwagger2
@AutoConfigureAfter
@Slf4j
public class ExampleAutoConfiguration {
	@Autowired
    private ExampleProperties exampleProperties;

	@Bean
    @ConfigurationProperties(prefix = ExampleProperties.EXAMPLE_PREFIX)
    public Properties exampleProperties() {
        return new Properties();
    }

	@Bean
	public Docket apiDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
//             				.apis(RequestHandlerSelectors.any())
							.apis(RequestHandlerSelectors.basePackage(exampleProperties.getBasePackage())).paths(PathSelectors.any())
//             				.paths(PathSelectors.ant("/v2/**"))
							.build().apiInfo(getApiInfo());

		 log.debug("Enable active {} swagger2", exampleProperties.getActive());

		 return docket;
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(exampleProperties.getTitle() + " / " + exampleProperties.getActive(), // TITLE
							exampleProperties.getDescription(), 	// DESCRIPTION
							exampleProperties.getApiVersion(), 		// VERSION
							exampleProperties.getTermsServiceUrl(), // TERMS OF SERVICE URL
							new Contact(exampleProperties.getContact().getName(), exampleProperties.getContact().getUrl(), exampleProperties.getContact().getEmail()), 		// "NAME","URL","EMAIL"
							exampleProperties.getLicense(), 		// LICENSE
							exampleProperties.getLicenseUrl(), 		// LICENSE URL
							new ArrayList<VendorExtension>());
	}
}
