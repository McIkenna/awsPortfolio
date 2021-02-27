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
import com.ikenna.portfolios.exceptions.ProjectException;
import com.ikenna.portfolios.infos.Project;
import com.ikenna.portfolios.repository.ProjectRepository;
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
public class ProjectService implements ProjectRepository {

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;

    @Override
    public Project findByProjectId(String projectId) {
        try{
            return mapper.load(Project.class, projectId);
        }catch(Exception e){
            throw new ProjectException("The user with phone number '" + projectId + "' does not exist");
        }
    }

    @Override
    public Project save(MultipartFile multipartFile, Project project) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Project + "/" + fileName;

            project.setProjectImage(fileUrl);
            mapper.save(project);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Project, fileName , file));
        }
        catch(Exception e){
            throw new ProjectException("This user '" + project.getProjectTitle()  + "' already exist");
        }

        return project;
    }

    @Override
    public String deleteProject(String projectId) {
        try{
            Project project = mapper.load(Project.class, projectId);
            mapper.delete(project);
            String fileName = project.getProjectImage().substring(project.getProjectImage().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Project + "/", fileName));
            return "Project deleted !!";
        }catch (Exception e){
            throw new ProjectException("This Project Cannot be deleted");
        }
    }

    @Override
    public String updateProject(MultipartFile multipartFile, Project project) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Project + "/" + fileName;

            project.setProjectImage(fileUrl);
            mapper.save(project, buildExpression(project));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Project, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new ProjectException("The project'" + project.getProjectTitle() + "' already exist");
        }
    }

    @Override
    public Iterable<Project> findAll() {
        return mapper.scan(Project.class, new DynamoDBScanExpression());
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

    private DynamoDBSaveExpression buildExpression(Project project){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("projectId", new ExpectedAttributeValue((new AttributeValue().withS(project.getProjectId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
