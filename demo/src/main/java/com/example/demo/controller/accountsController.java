package com.example.demo.controller;

import com.example.demo.Requests.Datepayload;
import com.example.demo.Requests.idrequest;
import com.example.demo.Requests.savePerticulars;
import com.example.demo.entity.account;
import com.example.demo.entity.perticulars;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class accountsController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;

    @Autowired
    public com.example.demo.Repository.accountsRepository accountsRepository;
    @Autowired
    public com.example.demo.Repository.pericularsRepository pericularsRepository;

    @PostMapping("getaccounts")
    public String getaccounts(@RequestHeader(value = "Authorization") String authorization, @RequestBody Datepayload data) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        List<account> Accounts = accountsRepository.findAll();
        List<perticulars> allperticulars = pericularsRepository.findByDate(data.getDate());
        List<account> cash_id = accountsRepository.findByName("cash");
        List<perticulars> cash_date = pericularsRepository.findByAccountidAndDate(cash_id.get(0).getId(), data.getDate().minusDays(1));
        int index = 0;
        int forindex = -1;
        for (perticulars eachdata : allperticulars) {
            if (eachdata.getAccountid() == cash_id.get(0).getId()) {
                forindex = index;
            }
            index++;
        }
        if (forindex != -1) {
            allperticulars.remove(allperticulars.get(forindex));
        }
        JSONObject result = new JSONObject();
        if (cash_date.size() != 0) {
            result.put("cash", cash_date.get(0).getJama());
        } else {
            result.put("cash", 0);
        }
        result.put("data", Accounts);
        result.put("perticulars", allperticulars);
        return result.toString();
    }

    @PostMapping("saveperticulars")
    public String saveperticulars(@RequestHeader(value = "Authorization") String authorization, @RequestBody savePerticulars dateanddata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        long cash = 0;
        List<perticulars> data = dateanddata.getData();
        LocalDate fordate = dateanddata.getDate();
        perticulars cash_perticular = new perticulars();
        perticulars onlycashonday = deleteallperticulars(fordate);
        for (perticulars eachdata : data) {
            Optional<account> eachaccount = accountsRepository.findById(eachdata.getAccountid());
            if (!eachaccount.get().getName().equals("cash")) {
                eachaccount.get().setBalance(eachaccount.get().getBalance() + eachdata.getJama() - eachdata.getKarchu());
                eachdata.setBalance(eachaccount.get().getBalance());
                accountsRepository.save(eachaccount.get());
                pericularsRepository.save(eachdata);
                cash = cash - eachdata.getKarchu() + eachdata.getJama();
            } else {
                cash_perticular = eachdata;
            }
        }
        // for cash start
        cash_perticular.setJama(cash);
        cash_perticular.setKarchu(0l);
        Optional<account> eachaccount = accountsRepository.findById(cash_perticular.getAccountid());
        account account_cash = eachaccount.get();
        account_cash.setBalance(account_cash.getBalance() + cash_perticular.getJama());
        cash_perticular.setBalance(account_cash.getBalance());
        accountsRepository.save(account_cash);
        pericularsRepository.save(cash_perticular);
        // end for cash
        // change cash for next days
        if (onlycashonday != null) {
            long diffrence = cash_perticular.getJama() - onlycashonday.getJama();
            List<perticulars> futuredatescash = pericularsRepository.findByDateGreaterThanAndAccountid(fordate, cash_perticular.getAccountid());
            for (perticulars eachdata : futuredatescash) {
                eachdata.setJama(eachdata.getJama() + diffrence);
                pericularsRepository.save(eachdata);
            }
        }
        //end for nextdays
        JSONObject result = new JSONObject();
        result.put("data", data);
        return result.toString();
    }

    // method is used to delete all the data in a day
    public perticulars deleteallperticulars(LocalDate date) {
        List<account> cash_id = accountsRepository.findByName("cash");
        List<perticulars> cash_date = pericularsRepository.findByAccountidAndDate(cash_id.get(0).getId(), date);
        if (cash_date.size() != 0) {
            List<perticulars> dayperticulars = pericularsRepository.findByDate(date);
            for (perticulars eachdata : dayperticulars) {
                Optional<account> eachaccount = accountsRepository.findById(eachdata.getAccountid());
                eachaccount.get().setBalance(eachaccount.get().getBalance() + eachdata.getJama() - eachdata.getKarchu());
                eachdata.setBalance(eachaccount.get().getBalance());
                accountsRepository.save(eachaccount.get());
                pericularsRepository.delete(eachdata);
            }
            return cash_date.get(0);
        }
        return null;
    }

    @GetMapping("getaccountsonly")
    public String getaccountsonly(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        List<account> Accounts = accountsRepository.findAll();
        JSONObject result = new JSONObject();
        result.put("data", Accounts);
        return result.toString();
    }
    @PostMapping("getpericulardetails")
    public String getpericulardetails(@RequestHeader(value = "Authorization") String authorization, @RequestBody idrequest idclass) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("data", pericularsRepository.findByAccountid(idclass.getId()));
        return result.toString();
    }
}