package com.mycompany.cevent.conf;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mycompany.cevent.repository.MessageRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = MessageRepository.class)
public class DatabaseConfiguration {

	private final Logger log = LoggerFactory
			.getLogger(DatabaseConfiguration.class);

	@Inject
	private Environment env;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		for (String profile : env.getActiveProfiles()) {
			if (profile.equals(Constants.SPRING_PROFILE_OPENSHIFT)) {
				String openshiftMongoDbHost = System
						.getenv("OPENSHIFT_MONGODB_DB_HOST");
				int openshiftMongoDbPort = Integer.parseInt(System
						.getenv("OPENSHIFT_MONGODB_DB_PORT"));
				String username = System
						.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
				String password = System
						.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
				MongoClient mongoClient = new MongoClient(openshiftMongoDbHost,
						openshiftMongoDbPort);
				UserCredentials userCredentials = new UserCredentials(username,
						password);
				String databaseName = System.getenv("OPENSHIFT_APP_NAME");
				MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(
						mongoClient, databaseName, userCredentials);
				MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
				return mongoTemplate;
			}
		}
		MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongoClient,
				"test");
		MongoTemplate mongoTemplate = new MongoTemplate(factory);
		return mongoTemplate;
	}
}
