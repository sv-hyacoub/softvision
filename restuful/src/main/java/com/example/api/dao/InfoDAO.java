package com.example.api.dao;


import com.example.api.domain.Info;
import java.util.List;

public interface InfoDAO {
    List<Info> findAll();
    Info find(String ip, String hashKeyValue, String rangeKeyName, Long rangeKeyValue);
    Info create(Info info);
}
