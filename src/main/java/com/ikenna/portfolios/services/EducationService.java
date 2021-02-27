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
import com.ikenna.portfolios.exceptions.EducationException;
import com.ikenna.portfolios.exceptions.InfoException;
import com.ikenna.portfolios.infos.Education;
import com.ikenna.portfolios.repository.EducationRepository;
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
public class EducationService implements EducationRepository {


    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;



    @Override
    public Education findByEduId(String eduId) {
        try{
            return mapper.load(Education.class, eduId);
        }catch(Exception e){
            throw new EducationException("Schoolname with '" + eduId + "' Cannot be found");
        }
    }

    @Override
    public Education save(MultipartFile multipartFile, Education education) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Education + "/" + fileName;

            education.setEducationImage(fileUrl);
            mapper.save(education);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Education, fileName , file));
        }
        catch(Exception e){
            throw new InfoException("This user '" + education.getSchoolName()  + "' already exist");
        }

        return education;
    }

    @Override
    public String deleteEdu(String eduId) {
        try{
            Education education = mapper.load(Education.class, eduId);
            mapper.delete(education);
            String fileName = education.getEducationImage().substring(education.getEducationImage().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Education + "/", fileName));
            return "Education deleted !!";
        }catch (Exception e){
            throw new EducationException("This Education Record Cannot be deleted");
        }
    }

    @Override
    public String updateEdu(MultipartFile multipartFile, Education education) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Education + "/" + fileName;

            education.setEducationImage(fileUrl);
            mapper.save(education, buildExpression(education));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Education, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new EducationException("This user '" + education.getSchoolName() + "' already exist");
        }
    }

    @Override
    public Iterable<Education> findAll() {
        return mapper.scan(Education.class, new DynamoDBScanExpression());
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

    private DynamoDBSaveExpression buildExpression(Education education){
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("eduId", new ExpectedAttributeValue((new AttributeValue().withS(education.getEduId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
