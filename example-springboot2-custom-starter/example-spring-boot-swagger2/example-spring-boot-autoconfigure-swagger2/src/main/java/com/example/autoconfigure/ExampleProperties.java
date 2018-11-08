package com.example.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = ExampleProperties.EXAMPLE_PREFIX)
public class ExampleProperties {
	public static final String EXAMPLE_PREFIX = "example.info.app";

	private String name;
	private String active;
	private String basePackage;

	private String title;
	private String description;
	private String apiVersion;
	private String termsServiceUrl;

	private String license;
	private String licenseUrl;

	private Contact contact;

	@Data
	public static class Contact {
		private String name;
		private String url;
		private String email;
	}
}
