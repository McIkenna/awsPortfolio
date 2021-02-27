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
import com.ikenna.portfolios.infos.Doc;
import com.ikenna.portfolios.repository.DocRepository;
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
public class DocStorageService implements DocRepository {

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;


    @Override
    public Doc findByDocId(String docId) {
        try{
            return mapper.load(Doc.class, docId);
        }catch(Exception e){
            throw new WorkException("The Document with ID: '" + docId + "' does not exist");
        }
    }

    @Override
    public Doc save(MultipartFile multipartFile, Doc doc) {
        try{
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Doc + "/" + fileName;

            doc.setUrlDownload(fileUrl);
            mapper.save(doc);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Doc, fileName , file));

        }catch (Exception e){
            throw new WorkException("The Document '" + doc.getDocName() + "' already exist");
        }
        return doc;
    }

    @Override
    public String deleteDoc(String docId) {
        try{
            Doc doc = mapper.load(Doc.class, docId);
            mapper.delete(doc);
            String fileName = doc.getUrlDownload().substring(doc.getUrlDownload().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_Doc + "/", fileName));
            return "Document deleted !!";
        }catch (Exception e){
            throw new WorkException("This Vehicle Cannot be deleted");
        }
    }

    @Override
    public String updateDoc(MultipartFile multipartFile, Doc doc) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_Doc + "/" + fileName;


            doc.setUrlDownload(fileUrl);
            mapper.save(doc, buildExpression(doc));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_Doc, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new WorkException("This Document '" + doc.getDocName() + "' already exist");
        }
    }

    @Override
    public Iterable<Doc> findAll() {
        return mapper.scan(Doc.class, new DynamoDBScanExpression());
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

    private DynamoDBSaveExpression buildExpression(Doc doc){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("docId", new ExpectedAttributeValue((new AttributeValue().withS(doc.getDocId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
