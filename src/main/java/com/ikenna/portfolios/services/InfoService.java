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
import com.ikenna.portfolios.exceptions.InfoException;
import com.ikenna.portfolios.infos.Info;
import com.ikenna.portfolios.repository.InfoRepository;
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
public class InfoService implements InfoRepository {


    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;


    public Info findByInfoId(String infoId) {
        try{
            return mapper.load(Info.class, infoId);
        }catch(Exception e){
            throw new InfoException("The user with Id '" + infoId + "' does not exist");
        }
    }

    @Override
    public Info save(MultipartFile multipartFile, Info info) {
        try{
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Info + "/" + fileName;

           info.setPersonalImage(fileUrl);
            mapper.save(info);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Info, fileName , file));

        }catch (Exception e){
            throw new InfoException("The user with phone number '" + info.getPhone() + "' already exist");
        }
        return info;
    }

    @Override
    public String deleteInfo(String infoId) {
        try{
            Info info = mapper.load(Info.class, infoId);
            mapper.delete(info);
            String fileName = info.getPersonalImage().substring(info.getPersonalImage().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Info + "/", fileName));
            return "Info deleted !!";
        }catch (Exception e){
            throw new InfoException("This Vehicle Cannot be deleted");
        }
    }

    @Override
    public String updateInfo(MultipartFile multipartFile, Info info) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Info + "/" + fileName;

            info.setPersonalImage(fileUrl);
            mapper.save(info, buildExpression(info));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Info, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new InfoException("This user '" + info.getFirstName() + " " + info.getLastName() + "' already exist");
        }
    }

    @Override
    public Iterable<Info> findAll() {
        return mapper.scan(Info.class, new DynamoDBScanExpression());
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

    private DynamoDBSaveExpression buildExpression(Info info){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("infoId", new ExpectedAttributeValue((new AttributeValue().withS(info.getInfoId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
