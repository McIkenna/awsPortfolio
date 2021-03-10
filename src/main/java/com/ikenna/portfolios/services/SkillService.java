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
import com.ikenna.portfolios.exceptions.SkillException;
import com.ikenna.portfolios.infos.Skill;
import com.ikenna.portfolios.repository.SkillsRepository;
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
public class SkillService implements SkillsRepository {
    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;

    @Override
    public Skill findBySkillId(String skillId) {
        try{
            return mapper.load(Skill.class, skillId);
        }catch(Exception e){
            throw new SkillException("The user with phone number '" + skillId + "' does not exist");
        }
    }

    @Override
    public Skill save(MultipartFile multipartFile, Skill skill) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Skill + "/" + fileName;

            skill.setSkillImageUrl(fileUrl);
            mapper.save(skill);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Skill, fileName , file));
        }
        catch(Exception e){
            throw new SkillException("This user '" + skill.getSkillName() + "' already exist");
        }
        return skill;
    }

    @Override
    public String deleteSkill(String skillId) {
        try{
            Skill skill = mapper.load(Skill.class, skillId);
            mapper.delete(skill);
            String fileName = skill.getSkillImageUrl().substring(skill.getSkillImageUrl().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Skill + "/", fileName));
            return "Skill is deleted !!";
        }catch (Exception e){
            throw new SkillException("This Skill Cannot be deleted");
        }
    }

    @Override
    public String updateSkill(MultipartFile multipartFile, Skill skill) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Project + "/" + fileName;

            skill.setSkillName(fileUrl);
            mapper.save(skill, buildExpression(skill));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Project, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new SkillException("The Skill'" + skill.getSkillName() + "' already exist");
        }
    }

    @Override
    public Iterable<Skill> findAll() {
        return mapper.scan(Skill.class, new DynamoDBScanExpression());
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

    private DynamoDBSaveExpression buildExpression(Skill skill){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("skillId", new ExpectedAttributeValue((new AttributeValue().withS(skill.getSkillId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
