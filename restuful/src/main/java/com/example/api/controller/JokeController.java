package com.example.api.controller;

import com.example.api.dto.BackendDTO;
import com.example.api.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@ConfigurationProperties(prefix = "backend")
public class JokeController {

    private JokeService jokeService;
    private RestTemplate template;

    private String saying;
    private String backendServiceHost;
    private int backendServicePort;

    @Autowired
    public JokeController(JokeService jokeService, RestTemplate restTemplate) {
        this.jokeService = jokeService;
        this.template = restTemplate;
    }

    @RequestMapping(value = {"/", "", "/joke"})
    public String showJoke(Model model) {
        String backendServiceUrl = String.format("http://%s:%d/api/backend?greeting={greeting}", backendServiceHost, backendServicePort);
        System.out.println("Sending to: " + backendServiceUrl);
        BackendDTO response = template.getForObject(backendServiceUrl, BackendDTO.class, saying);
        model.addAttribute("joke", jokeService.getJoke());
        model.addAttribute("status", response.getGreeting() + " at host: " + response.getIp() + " timestamp: " + response.getTime());
        return "chucknorris";
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getBackendServiceHost() {
        return backendServiceHost;
    }

    public void setBackendServiceHost(String backendServiceHost) {
        this.backendServiceHost = backendServiceHost;
    }

    public int getBackendServicePort() {
        return backendServicePort;
    }

    public void setBackendServicePort(int backendServicePort) {
        this.backendServicePort = backendServicePort;
    }
}
