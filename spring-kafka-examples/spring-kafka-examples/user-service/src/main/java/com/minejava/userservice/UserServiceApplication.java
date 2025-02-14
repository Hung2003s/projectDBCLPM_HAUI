package com.minejava.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.index.ReactiveIndexOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

import com.minejava.userservice.model.User;

@ComponentScan("com.minejava")
@SpringBootApplication
public class UserServiceApplication {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceApplication.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(UserServiceApplication.class, args);

		String mongoDbHost = ctx.getEnvironment().getProperty("spring.data.mongodb.host");
		String mongoDbPort = ctx.getEnvironment().getProperty("spring.data.mongodb.port");
		String mongoDbUrl = ctx.getEnvironment().getProperty("spring.data.mongodb.url");

		LOG.info("Connected to MongoDB: " + mongoDbHost + ":" + mongoDbPort);
		LOG.info("connected to uri" + mongoDbUrl);
    }

    @Autowired
	ReactiveMongoOperations mongoOperations;

	@EventListener(ContextRefreshedEvent.class)
	public void initIndicesAfterStartup() {
		MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext =
				mongoOperations.getConverter().getMappingContext();
		IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);

		ReactiveIndexOperations indexOps = mongoOperations.indexOps(User.class);
		resolver.resolveIndexFor(User.class)
				.forEach(e -> indexOps.ensureIndex(e).block());
	}
}
