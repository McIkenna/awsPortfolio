package com.ikenna.portfolios.services;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ikenna.portfolios.common.CommonUtils;
import com.ikenna.portfolios.exceptions.WorkException;
import com.ikenna.portfolios.infos.Work;
import com.ikenna.portfolios.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkService implements WorkRepository {

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;

    @Override
    public Work findByWorkId(String workId) {
        try{
            return mapper.load(Work.class, workId);
        }catch(Exception e){
            throw new WorkException("The work with ID :  '" + workId + "' does not exist");
        }
    }

    @Override
    public Work save(MultipartFile multipartFile, Work work) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Work + "/" + fileName;

            work.setWorkImage(fileUrl);
            mapper.save(work);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Work, fileName , file));
        }
        catch(Exception e){
            throw new WorkException("This work '" + work.getWorkId() + "' already exist");
        }

        return work;
    }

    @Override
    public String deleteWork(String workId) {
        try{
            Work work = mapper.load(Work.class, workId);
            mapper.delete(work);
            String fileName = work.getWorkImage().substring(work.getWorkImage().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Work + "/", fileName));
            return "Work is deleted !!";
        }catch (Exception e){
            throw new WorkException("This Work Cannot be deleted");
        }
    }

    @Override
    public String updateWork(MultipartFile multipartFile, Work work) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Work + "/" + fileName;

            work.setWorkImage(fileUrl);
            mapper.save(work, buildExpression(work));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Work, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new WorkException("The Work'" + work.getCompanyName() + "' already exist");
        }
    }

    @Override
    public Iterable<Work> findAll() {
        return mapper.scan(Work.class, new DynamoDBScanExpression());
    }


    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private DynamoDBSaveExpression buildExpression(Work work){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("workId", new ExpectedAttributeValue((new AttributeValue().withS(work.getWorkId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
