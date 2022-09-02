package com.example.demo.controller;


import com.example.demo.Requests.Datepayload;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class callibrationController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;

    @Autowired
    public com.example.demo.Repository.calibrationRepository calibrationRepository;

    @GetMapping("getcallibration")
    public String getcallibration(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("data",calibrationRepository.findAll());
    return  result.toString();
    }
}