package io.thepro.dospaces.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class DoConfig {

	@Value("${do.space.key}")
	private String doSpaceKey;

	@Value("${do.space.secret}")
	private String doSpaceSecret;

	@Value("${do.space.endpoint}")
	private String doSpaceEndpoint;

	@Value("${do.space.region}")
	private String doSpaceRegion;

	@Bean
	public AmazonS3 getS3() {
		BasicAWSCredentials creds = new BasicAWSCredentials(doSpaceKey, doSpaceSecret);
		return AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(doSpaceEndpoint, doSpaceRegion))
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
	}

}
