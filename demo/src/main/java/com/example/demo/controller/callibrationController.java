package com.example.demo.controller;


import com.example.demo.Requests.Datepayload;
import com.example.demo.Requests.dateRange;
import com.example.demo.entity.account;
import com.example.demo.entity.calibration;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.utils.nametype;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        result.put("data",calibrationRepository.findAllByCid(userdata.getCid()));
    return  result.toString();
    }
    @GetMapping("getcallibdetails")
    public String getcallibdetails(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("columNames",new String[]{"date","nextdate","discription"});
        List<nametype> test = new ArrayList<nametype>();
        test.add(new nametype("date","Date"));
        test.add(new nametype("nextdate","Date"));
        test.add(new nametype("discription","Text"));
        result.put("editable", test);
        result.put("data",calibrationRepository.findAllByCid(userdata.getCid()));
        return result.toString();
    }
    @PostMapping("postcallibdetails")
    public String getpostcallibrations(@RequestHeader(value = "Authorization") String authorization, @RequestBody dateRange payload) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("data",calibrationRepository.findByCidAndDateGreaterThanEqualAndNextdateLessThanEqual(
                userdata.getCid(),payload.getInitialdate(),payload.getFinaldate()));
        return result.toString();
    }
    @PostMapping("calibratioeditandsave")
    public  String calibratioeditandsave(@RequestHeader(value = "Authorization") String authorization,@RequestBody calibration needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        needsavedata.setCid(userdata.getCid());
        calibrationRepository.save(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
    @PostMapping("deletecalibration")
    public  String deletecalibration(@RequestHeader(value = "Authorization") String authorization,@RequestBody calibration needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        calibrationRepository.delete(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
}