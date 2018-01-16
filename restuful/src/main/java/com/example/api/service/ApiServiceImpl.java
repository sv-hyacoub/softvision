package com.example.api.service;

import com.example.api.dao.InfoDAO;
import com.example.api.domain.Info;
import com.example.api.dto.InfoDTO;
import com.example.api.mappers.InfoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService {

    private final InfoDAO infoDAO;
    private final InfoMapper infoMapper;

    public ApiServiceImpl(InfoDAO infoDAO, InfoMapper infoMapper) {
        this.infoDAO = infoDAO;
        this.infoMapper = infoMapper;
    }

    @Override
    public List<InfoDTO> getAllInfo() {
        return infoDAO.findAll()
                .stream()
                .map(infoMapper::InfoToInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InfoDTO getInfoByIp(String ip, Long time) {
        return infoMapper.InfoToInfoDTO(infoDAO.find("ip", ip, "time", time));
    }

    @Override
    public InfoDTO createInfo(InfoDTO infoDTO) {
        return saveAndReturnDTO(infoMapper.InfoDtoToInfo(infoDTO));
    }

    private InfoDTO saveAndReturnDTO(Info info) {
        Info savedInfo = infoDAO.create(info);
        return infoMapper.InfoToInfoDTO(savedInfo);
    }
}
