package com.example.Sample.SpringTest;

import MyPackage.Request;
import MyPackage.Response;
import com.example.Sample.SpringTest.collection.Attribute_Template;
import com.example.Sample.SpringTest.collection.MDM_Expressions;
import com.example.Sample.SpringTest.collection.Template;
import com.example.Sample.SpringTest.grpc.EntryPoint;
import com.example.Sample.SpringTest.grpc.MyResponseObserver;
import com.example.Sample.SpringTest.repository.TemplateRepository;
import  org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class SpringTestApplication {

	public static void main(String[] args) {
		TemplateRepository templateRepository = new TemplateRepository() {
			@Override
			public Template findByTemplateName(String templateName) {
				return null;
			}

			@Override
			public <S extends Template> S insert(S entity) {
				return null;
			}

			@Override
			public <S extends Template> List<S> insert(Iterable<S> entities) {
				return List.of();
			}

			@Override
			public <S extends Template> List<S> findAll(Example<S> example) {
				return List.of();
			}

			@Override
			public <S extends Template> List<S> findAll(Example<S> example, Sort sort) {
				return List.of();
			}

			@Override
			public <S extends Template> List<S> saveAll(Iterable<S> entities) {
				return List.of();
			}

			@Override
			public List<Template> findAll() {
				return List.of();
			}

			@Override
			public List<Template> findAllById(Iterable<String> strings) {
				return List.of();
			}

			@Override
			public <S extends Template> S save(S entity) {
				return null;
			}

			@Override
			public Optional<Template> findById(String s) {
				return Optional.empty();
			}

			@Override
			public boolean existsById(String s) {
				return false;
			}

			@Override
			public long count() {
				return 0;
			}

			@Override
			public void deleteById(String s) {

			}

			@Override
			public void delete(Template entity) {

			}

			@Override
			public void deleteAllById(Iterable<? extends String> strings) {

			}

			@Override
			public void deleteAll(Iterable<? extends Template> entities) {

			}

			@Override
			public void deleteAll() {

			}

			@Override
			public List<Template> findAll(Sort sort) {
				return List.of();
			}

			@Override
			public Page<Template> findAll(Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Template> Optional<S> findOne(Example<S> example) {
				return Optional.empty();
			}

			@Override
			public <S extends Template> Page<S> findAll(Example<S> example, Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Template> long count(Example<S> example) {
				return 0;
			}

			@Override
			public <S extends Template> boolean exists(Example<S> example) {
				return false;
			}

			@Override
			public <S extends Template, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
				return null;
			}
		};
		SpringApplication.run(SpringTestApplication.class, args);
		// Create a few sample attributes
		Attribute_Template attribute1 = new Attribute_Template();
		attribute1.setAttribute_name("Attribute1");
		attribute1.setAttribute_type("String");

		Attribute_Template attribute2 = new Attribute_Template();
		attribute2.setAttribute_name("Attribute2");
		attribute2.setAttribute_type("Integer");

		// Create a list of attributes
		List<Attribute_Template> attributes = Arrays.asList(attribute1, attribute2);

		// Create a sample template
		Template template = new Template();
		template.setId(new BigInteger("1234567890"));
		template.setTemplate_name("SampleTemplate");
		template.setAttributes(attributes);
		templateRepository.save(template);

		Template fetchedTemplate = templateRepository.findByTemplateName("Test Template");
		System.out.println(fetchedTemplate);  // Should print the template with all details if saved

		//EntryPoint et = new EntryPoint();

//		Request request = Request.newBuilder()
//				.setRoute("m2IKIt4aqnXiL3drRABprliSIBEiqMM6O/GvO++a0Sg=")
//				.setJsondata("qduAdGNanN0oR0WoSXsRJYuAO0/Si/wM0ZTx54tinphrZDVeao/sopmrgg0TKr04XAW9Bb9lKsrL98/KvwuTEAGv/gNhUVw15arwhHSQg4WrUjUc5UrkqTvuAohqk6Il+mpJaM29tvyS1dVTFO4WVaFvJb6eKToRx7gOFzGpUyWkJo5Yms8s3W0E7jZu0E47HtCc2SDIDgLQ/QwkEKH6/iGe9u6J53uBFwXYWzB7NZMHhr0hqV5Y7XNTkPIpHTS1MIpkPyXCMQoKWxODii1BMuYquKeKYj/x4z60yRr46d0kt4W36BVD+cbE5Cgh+YNr0NwPuDJOAdj92B6cyL4GnlAqvDY5i/siDzOscz4nlzplDNyFLyD6F0qdT7XMn4AfLImgbM7LfuZxgkpP+LjSLSeKFfw35+a40CgISQGlOLs/PdUXJxLqwpUCBvOXRNI6+V9K6SdTAtUc+GG0rxyWrsEvqqwMhTElgRyhUd5AxpfW0/GBmu1E+L4rG8JTtzbq9tJYfAKWKxbPoj5M0MgzAOTMdVCjpfu4aYWgyOdErU/QXGK/guie7jU2DzcerOikpgPvfe1MTMmkVL2uXCTHXZN+IodKGibxz1d+kg2IsCNJ3WCADznQMylxHWLcqo+Nb0HWnKXFs+cQ9afV2CnXtIZ7pjhqCf7FZZ0RZV+zFnE4vr8be7iTpS2ulzveYTPqJtuk3jmLeL6xvhg0oCZ03Dlk43JoYq18Qyi4+vsZrNtrBbmC1Ir5784c09IJp8Iyuj4e1whfcx7e3u1Hq1+flgP7ADDBuKNVqNVVwHAW6eZPhmkXq+/udgcnPpIj5m840Y9lZ9ljQ1cVDy1z51M0YBKG3K2iXCE5QZMK1Sic5wqnYFNYbwPL1T80yCkonXdNVu0LysLyEhnupdx/kifB0aaxDosYkh/C9VP8dhSES4w=")
//				.build();
//
//		// Create a dummy StreamObserver for responses
//		MyResponseObserver responseObserver = new MyResponseObserver();
//
//		et.saveJson(request, responseObserver);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry corsRegistry){
				corsRegistry.addMapping("/**")
						.allowedMethods("*")						// allow all methods get, put, post
						.allowedOrigins("http://localhost:3000");
			}
		};
	}




}

