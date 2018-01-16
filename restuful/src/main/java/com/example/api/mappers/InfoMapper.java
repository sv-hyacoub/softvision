package com.example.api.mappers;

import com.example.api.domain.Info;
import com.example.api.dto.InfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface InfoMapper {
    InfoDTO InfoToInfoDTO(Info info);
    Info InfoDtoToInfo(InfoDTO infoDTO);
}
