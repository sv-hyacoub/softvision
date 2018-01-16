package com.example.api.service;

import com.example.api.dto.InfoDTO;
import java.util.List;

public interface ApiService {
    List<InfoDTO> getAllInfo();

    InfoDTO getInfoByIp(String ip, Long time);

    InfoDTO createInfo(InfoDTO infoDTO);
}