package com.example.demo.controller;
import com.example.demo.Requests.Datepayload;
import com.example.demo.entity.engineoils;
import com.example.demo.entity.engineoilsstock;
import com.example.demo.entity.pumps;
import com.example.demo.entity.readings;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        List<engineoilsstock> stockoils  = engineoilstocksRepository.findAll();
        List <engineoils> dateoils = engineoilRepository.findAllByDate(payload.getDate());
        JSONObject result = new JSONObject();
        if (dateoils.size() != 0 || (payload.getDate().compareTo(LocalDate.now()) != 0)) {
            result.put("alreadysaved",true);
            result.put("engineoils",engineoilRepository.findAllByDate(payload.getDate()));
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
        List<engineoilsstock> enginoilstock  = engineoilstocksRepository.findAll();
        System.out.println(LocalDate.now());
        List <engineoils> dateengineoils = engineoilRepository.findAllByDate(LocalDate.now());
        if (dateengineoils.size() == 0) {
            for (engineoilsstock eachengineoilsstock: enginoilstock) {
                for (engineoils eachoil : data) {
                    System.out.println(eachoil.toString());
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
}
