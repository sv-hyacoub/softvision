package com.example.api.mappers;

import com.example.api.dto.InfoDTO;
import com.example.api.domain.Info;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// mapper class
@Component
@NoArgsConstructor
public class InfoMapperImpl implements InfoMapper {

    @Override
    public InfoDTO InfoToInfoDTO(Info info) {
        if (info == null) {
            return null;
        }
        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setIp(info.getIp());
        infoDTO.setTime(info.getTime());
        return infoDTO;
    }

    @Override
    public Info InfoDtoToInfo(InfoDTO infoDTO) {
        if (infoDTO == null) {
            return null;
        }
        Info info = new Info();
        info.setIp(infoDTO.getIp());
        info.setTime(infoDTO.getTime());
        return info;
    }
}
