package com.example.demo.controller;
import com.example.demo.Requests.Datepayload;
import com.example.demo.entity.*;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.utils.nametype;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class engineoilsController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;
    @Autowired
    public com.example.demo.Repository.engineoilRepository engineoilRepository;
    @Autowired
    public com.example.demo.Repository.engineoilstocksRepository engineoilstocksRepository;


    @PostMapping("getengineoils")
    public String getengineoils(@RequestHeader(value = "Authorization") String authorization, @RequestBody Datepayload payload) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        List<engineoilsstock> stockoils  = engineoilstocksRepository.findAllByCid(userdata.getCid());
        List <engineoils> dateoils = engineoilRepository.findAllByDateAndCid(payload.getDate(),userdata.getCid());
        JSONObject result = new JSONObject();
        if (dateoils.size() != 0 || (payload.getDate().compareTo(LocalDate.now()) != 0)) {
            result.put("alreadysaved",true);
            result.put("engineoils",engineoilRepository.findAllByDateAndCid(payload.getDate(),userdata.getCid()));
        }
        result.put("data",stockoils);
        return result.toString();
    }
    @PostMapping("saveengineoils")
    public String saveengineoils(@RequestHeader(value = "Authorization") String authorization, @RequestBody List<engineoils> data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        String message = "Data already exist";
        List<engineoilsstock> enginoilstock  = engineoilstocksRepository.findAllByCid(userdata.getCid());
        System.out.println(LocalDate.now());
        List <engineoils> dateengineoils = engineoilRepository.findAllByDateAndCid(LocalDate.now(),userdata.getCid());
        if (dateengineoils.size() == 0) {
            for (engineoilsstock eachengineoilsstock: enginoilstock) {
                for (engineoils eachoil : data) {
                    eachoil.setCid(userdata.getCid());
                    if (eachengineoilsstock.getId() == eachoil.getEid()) {
                        eachengineoilsstock.setQtyleft((eachengineoilsstock.getQtyleft() - eachoil.getSales() + eachoil.getPurchase()));
                    }
                }
            }
            engineoilstocksRepository.saveAll(enginoilstock);
            engineoilRepository.saveAll(data);
            message = "data saved sucessfully";
        }
        JSONObject result = new JSONObject();
        result.put("data",message);
        return result.toString();
    }
    @GetMapping("getenginoilforedit")
    public String getcallibdetails(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("columNames",new String[]{"name","qtyleft"});
        List<nametype> test = new ArrayList<nametype>();
        test.add(new nametype("name","Text"));
        result.put("editable", test);
        result.put("data",engineoilstocksRepository.findAllByCid(userdata.getCid()));
        return result.toString();
    }
    @PostMapping("engineoileditandsave")
    public  String engineoileditandsave(@RequestHeader(value = "Authorization") String authorization,@RequestBody engineoilsstock needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        needsavedata.setCid(userdata.getCid());
        engineoilstocksRepository.save(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
    @PostMapping("deleteengineoilstock")
    public  String deleteengineoilstock(@RequestHeader(value = "Authorization") String authorization,@RequestBody engineoilsstock needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        if (needsavedata.getQtyleft() == 0) {
            engineoilstocksRepository.delete(needsavedata);
            result.put("Status","sucess");
        } else {
            result.put("Status","Failed");
            result.put("errmessage","Balance should be 0");
        }
        return result.toString();
    }
}
