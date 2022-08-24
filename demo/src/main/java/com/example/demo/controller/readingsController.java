package com.example.demo.controller;

import com.example.demo.Requests.Datepayload;
import com.example.demo.Response.readingsresponse;
import com.example.demo.entity.pumps;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.entity.readings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class readingsController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;

    @Autowired
    public com.example.demo.Repository.readingsRepository readingsRepository;

    @Autowired
    public com.example.demo.Repository.pumpsRepository PumpsRepository;

    @PostMapping("getpumps")
    public String getpumps(@RequestHeader(value = "Authorization") String authorization, @RequestBody Datepayload payload) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        List<pumps> pumps  = PumpsRepository.findAll();
        List <readings> datereadings = readingsRepository.findAllByDate(payload.getDate());
        JSONObject result = new JSONObject();
        if (datereadings.size() != 0 || (payload.getDate().compareTo(LocalDate.now()) != 0)) {
            result.put("alreadysaved",true);
            List<readings> existdata = readingsRepository.findAllByDate(payload.getDate());
            result.put("readings",existdata);
        }
        result.put("data",pumps);
        return result.toString();
    }
    @PostMapping("savereadings")
    public String savereadings(@RequestHeader(value = "Authorization") String authorization, @RequestBody  List<readings> data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        String message = "Data already exist";
        List<pumps> pumpsdata  = PumpsRepository.findAll();
        System.out.println(LocalDate.now());
        List <readings> datereadings = readingsRepository.findAllByDate(LocalDate.now());
        if (datereadings.size() == 0) {
            for (pumps eachpump: pumpsdata) {
                for (readings eachreading : data) {
                    if (eachpump.getId() == eachreading.getPumpid()) {
                        eachpump.setLatestclosedreading(eachreading.getClosing());
                        eachpump.setLatestopenreading(eachreading.getOpening());
                    }
                }
            }
            PumpsRepository.saveAll(pumpsdata);
            readingsRepository.saveAll(data);
            message = "data saved sucessfully";
        }
        JSONObject result = new JSONObject();
        result.put("data",message);
        return result.toString();
    }
    @GetMapping("checkreadings")
    public String checkreadings(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        List <readings> datereadings = readingsRepository.findAllByDate(LocalDate.now());
        JSONObject result = new JSONObject();
        if (datereadings.size() == 0) {
            result.put("Allowemployee",true);
        } else  {
            result.put("Allowemployee",false);
        }
        return result.toString();
    }
}
