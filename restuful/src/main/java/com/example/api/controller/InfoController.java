package com.example.api.controller;

import com.example.api.dto.InfoDTO;
import com.example.api.service.ApiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(InfoController.BASE_URL)
public class InfoController {

    private ApiService apiService;
    static final String BASE_URL = "/display";

    @Autowired
    public InfoController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InfoDTO> display() {
        return apiService.getAllInfo();
    }

    @GetMapping({"/{ip}/{time}"})
    @ResponseStatus(HttpStatus.OK)
    public InfoDTO displayPath(@PathVariable String ip, @PathVariable Long time) {
        return apiService.getInfoByIp(ip, time);
    }

    @GetMapping(value = "/withParams")
    @ResponseStatus(HttpStatus.OK)
    public InfoDTO displayByIp(@RequestParam String ip, @RequestParam Long time) {
        return apiService.getInfoByIp(ip, time);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InfoDTO createNewInfo(@RequestBody InfoDTO infoDTO) {
        return apiService.createInfo(infoDTO);
    }
}
