package com.example.api.dao;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.api.domain.Info;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class InfoDAOImpl implements InfoDAO {

    private String tableName;
    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;

    public InfoDAOImpl() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJJ4BQSRUCAUU5OUA", "FeOQbDkPVphKT3KoFnDIfyCxid/b39sSOEofpsOV");
        this.client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:9999", "eu-central-1"))
                .build();
        this.tableName = "Statistics";
        this.dynamoDB = new DynamoDB(client);
    }

    // Works !!!
    @Override
    public List<Info> findAll() {
        List<Info> infos = new ArrayList<>();
        ScanResult result = null;
        do {
            ScanRequest req = new ScanRequest();
            req.setTableName(tableName);
            if (result != null) {
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }
            result = client.scan(req);
            List<Map<String, AttributeValue>> rows = result.getItems();
            for (Map<String, AttributeValue> map : rows) {
                Info info = new Info();
                try {
                    AttributeValue value = map.get("ip");
                    String ip = value.getS();
                    System.out.println("IP: " + ip);
                    AttributeValue value1 = map.get("time");
                    Long time = Long.valueOf(value1.getN());
                    System.out.println("Timestamp: " + time);
                    info.setIp(ip);
                    info.setTime(time);
                } catch (NumberFormatException e) {
                    System.out.println("Thrown the " + e.getMessage());
                }
                infos.add(info);
            }
        } while (result.getLastEvaluatedKey() != null);
        return infos;
    }

    @Override
    public Info find(String hashKeyName, String hashKeyValue, String rangeKeyName, Long rangeKeyValue) {
        Info info = new Info();
        HashMap<String, AttributeValue> mapGet = new HashMap<>();
        mapGet.put(hashKeyName, new AttributeValue(hashKeyValue));
        mapGet.put(rangeKeyName, new AttributeValue().withN(String.valueOf(rangeKeyValue)));
        GetItemRequest request = new GetItemRequest()
                .withKey(mapGet)
                .withTableName(tableName);
        try {
            Map<String, AttributeValue> map = client.getItem(request).getItem();
            if (map != null) {
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    if (key.equals(hashKeyName)) {
                        System.out.println(map.get(key).getS());
                        info.setIp(map.get(key).getS());
                    } else if (key.equals(rangeKeyName)) {
                        System.out.println(map.get(key).getN());
                        info.setTime(Long.valueOf(map.get(key).getN()));
                    }
                }
            } else {
                System.out.format("No item found with the key %s!\n", rangeKeyName);
            }
            return info;
        } catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // putting an item
    @Override
    public Info create(Info info) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("ip", new AttributeValue().withS(info.getIp()));
        map.put("time", new AttributeValue().withN(String.valueOf(info.getTime())));

        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(tableName)
                .withItem(map);
        PutItemResult putItemResult = client.putItem(putItemRequest);
        System.out.println(putItemResult.toString());
        if (putItemResult.getConsumedCapacity() != null) {
            return info;
        }
        return null;
    }
}

