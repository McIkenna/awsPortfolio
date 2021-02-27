package com.ikenna.portfolios.infos;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SkillInfo")
public class Skill {
    @DynamoDBHashKey(attributeName = "skillId")
    @DynamoDBAutoGeneratedKey
    private String skillId;
    @DynamoDBAttribute
    private String skillName;
    @DynamoDBAttribute
    private String subName;
    @DynamoDBAttribute
    private String proficiency;
    @DynamoDBAttribute
    private String skillImageUrl;
    @DynamoDBAttribute
    private int rating;

}
