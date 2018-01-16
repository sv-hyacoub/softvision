package com.example.api.service;

import com.example.api.dao.InfoDAO;
import com.example.api.mappers.InfoMapperImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class JokeServiceTest {

    @Mock
    private InfoDAO infoDAO;
    private ApiService apiService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        apiService = new ApiServiceImpl(infoDAO, new InfoMapperImpl());
    }

    @Test
    public void getJoke() {
    }
}