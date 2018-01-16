package com.example.api.mappers;

import com.example.api.dto.InfoDTO;
import com.example.api.domain.Info;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class InfoMapperTest {

    private InfoMapper infoMapper;

    private static final String ip = "192.168.1.15";
    private static final long time = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        infoMapper = new InfoMapperImpl();
    }

    @Test
    public void infoToInfoDTO() {
        //given
        Info info = new Info();
        info.setIp(ip);
        info.setTime(time);

        //when
        InfoDTO infoDTO = infoMapper.InfoToInfoDTO(info);

        //then
        assertEquals(ip, infoDTO.getIp());
        assertEquals(time, 1L);
    }
}