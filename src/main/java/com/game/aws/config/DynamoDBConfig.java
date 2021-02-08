package com.game.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.game.aws.repository.MineRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = MineRepository.class)
public class DynamoDBConfig {

	@Value("${amazon.aws.region}")
	private String region;

	@Value("${amazon.aws.service.endpoint}")
	private String endpoint;

	@Value("${amazon.aws.accesskey}")
	private String awsAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String awsSecretKey;


	@Bean
	public AmazonDynamoDB amazonDynamoDB(AWSCredentials awsCredentials) {
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint,region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
				.build();
		return amazonDynamoDB;
	}

	@Bean
	public AWSCredentials awsCredentials() {
		return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
	}

}
