package com.example.demo.controller;

import com.example.demo.Requests.Datepayload;
import com.example.demo.Response.readingsresponse;
import com.example.demo.entity.account;
import com.example.demo.entity.calibration;
import com.example.demo.entity.pumps;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.entity.readings;
import com.example.demo.utils.nametype;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
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
        List<pumps> pumps  = PumpsRepository.findAllByCid(userdata.getCid());
        List <readings> datereadings = readingsRepository.findAllByDateAndCid(payload.getDate(),userdata.getCid());
        JSONObject result = new JSONObject();
        if (datereadings.size() != 0 || (payload.getDate().compareTo(LocalDate.now().minusDays(1)) != 0)) {
            result.put("alreadysaved",true);
            List<readings> existdata = readingsRepository.findAllByDateAndCid(payload.getDate(),userdata.getCid());
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
        List<pumps> pumpsdata  = PumpsRepository.findAllByCid(userdata.getCid());
        System.out.println(LocalDate.now());
        List <readings> datereadings = readingsRepository.findAllByDateAndCid(LocalDate.now().minusDays(1),userdata.getCid());
        if (datereadings.size() == 0) {
            for (pumps eachpump: pumpsdata) {
                for (readings eachreading : data) {
                    if (eachpump.getId() == eachreading.getPumpid()) {
                        eachreading.setCid(userdata.getCid());
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
        List <readings> datereadings = readingsRepository.findAllByDateAndCid(LocalDate.now(),userdata.getCid());
        JSONObject result = new JSONObject();
        if (datereadings.size() == 0) {
            result.put("Allowemployee",true);
        } else  {
            result.put("Allowemployee",false);
        }
        return result.toString();
    }
    @GetMapping("getpumpsforedit")
    public String getpumpsforedit(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        List<nametype> test = new ArrayList<nametype>();
        for ( String s: new String[] {"number","tank","price","latestclosedreading"}) {
            test.add(new nametype(s,"Number"));
        }
        test.add(new nametype("product","Text"));
        result.put("columNames",new String[]{"number","tank","product","price"});
        result.put("editable", test);
        result.put("data",PumpsRepository.findAllByCid(userdata.getCid()));
        return result.toString();
    }
    @PostMapping("pumpeditandsave")
    public  String pumpeditandsave(@RequestHeader(value = "Authorization") String authorization,@RequestBody  pumps needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        needsavedata.setCid(userdata.getCid());
        PumpsRepository.save(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
    @PostMapping("deletepump")
    public  String deletepump(@RequestHeader(value = "Authorization") String authorization,@RequestBody  pumps needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        PumpsRepository.delete(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
}
