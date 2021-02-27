package com.ikenna.portfolios.memorydb;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ikenna.portfolios.common.CommonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }
    private AmazonDynamoDB amazonDynamoDBConfig(){

        AWSCredentials dynamoCredentials =  new BasicAWSCredentials(CommonUtils.ACCESS_KEY,CommonUtils.SECRET_KEY);
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(CommonUtils.SERVICE_ENDPOINT, CommonUtils.REGION))
                .withCredentials(new AWSStaticCredentialsProvider(dynamoCredentials))
                .build();
    }
}
