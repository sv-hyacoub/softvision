package com.example.api.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
@Data
@DynamoDBTable(tableName = "Statistics")
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    @DynamoDBHashKey(attributeName = "ip")
    private String ip;
    @DynamoDBRangeKey(attributeName = "time")
    private Long time;


}
