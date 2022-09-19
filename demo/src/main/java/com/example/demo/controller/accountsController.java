package com.example.demo.controller;

import com.example.demo.Requests.Datepayload;
import com.example.demo.Requests.idrequest;
import com.example.demo.Requests.savePerticulars;
import com.example.demo.entity.account;
import com.example.demo.entity.calibration;
import com.example.demo.entity.perticulars;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.utils.nametype;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
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
        List<account> Accounts = accountsRepository.findByCid(userdata.getCid());
        List<perticulars> allperticulars = pericularsRepository.findByDateAndCid(data.getDate(),userdata.getCid());
        List<account> cash_id = accountsRepository.findByNameAndCid("cash",userdata.getCid());
        List<perticulars> cash_date = pericularsRepository.findByCidAndAccountidAndDate(userdata.getCid(),cash_id.get(0).getId(), data.getDate().minusDays(1));
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
            result.put("cash", cash_date.get(0).getBalance());
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
        double cash = 0;
        List<perticulars> data = dateanddata.getData();
        LocalDate fordate = dateanddata.getDate();
        perticulars cash_perticular = new perticulars();
        perticulars onlycashonday = deleteallperticulars(fordate,userdata);
        for (perticulars eachdata : data) {
            Optional<account> eachaccount = accountsRepository.findByIdAndCid(eachdata.getAccountid(),userdata.getCid());
            if (!eachaccount.get().getName().equals("cash")) {
                eachaccount.get().setBalance(eachaccount.get().getBalance() - eachdata.getJama() + eachdata.getKarchu());
                eachdata.setBalance(eachaccount.get().getBalance());
                eachaccount.get().setCid(userdata.getCid());
                eachdata.setCid(userdata.getCid());
                accountsRepository.save(eachaccount.get());
                pericularsRepository.save(eachdata);
                cash = cash - eachdata.getKarchu() + eachdata.getJama();
            } else {
                cash_perticular = eachdata;
            }
        }
        // for cash start
        List<perticulars> previousdaycash = pericularsRepository.findByCidAndAccountidAndDate(userdata.getCid(),cash_perticular.getAccountid(),fordate.minusDays(1));
        cash_perticular.setJama(cash );
        cash_perticular.setKarchu(0d);
        Optional<account> eachaccount = accountsRepository.findByIdAndCid(cash_perticular.getAccountid(), userdata.getCid());
        account account_cash = eachaccount.get();
        account_cash.setBalance(previousdaycash.get(0).getBalance() + cash);
        cash_perticular.setBalance(previousdaycash.get(0).getBalance() + cash);
        account_cash.setCid(userdata.getCid());
        cash_perticular.setCid(userdata.getCid());
        accountsRepository.save(account_cash);
        pericularsRepository.save(cash_perticular);
        // end for cash
        // change cash for next days
        if (onlycashonday != null) {
            double diffrence = cash_perticular.getBalance() - onlycashonday.getBalance();
            List<perticulars> futuredatescash = pericularsRepository.findAllByCidAndDateGreaterThanAndAccountid(userdata.getCid(),fordate, cash_perticular.getAccountid());
            for (perticulars eachdata : futuredatescash) {
                eachdata.setBalance(eachdata.getBalance() + diffrence);
                eachdata.setCid(userdata.getCid());
                pericularsRepository.save(eachdata);
            }
        }
        //end for nextdays
        JSONObject result = new JSONObject();
        result.put("data", data);
        return result.toString();
    }

    // method is used to delete all the data in a day
    public perticulars deleteallperticulars(LocalDate date,userdata userdata) {
        List<account> cash_id = accountsRepository.findByNameAndCid("cash",userdata.getCid());
        List<perticulars> cash_date = pericularsRepository.findAllByAccountidAndDateAndCid(cash_id.get(0).getId(), date, userdata.getCid());
        if (cash_date.size() != 0) {
            List<perticulars> dayperticulars = pericularsRepository.findByDateAndCid(date, userdata.getCid());
            for (perticulars eachdata : dayperticulars) {
                Optional<account> eachaccount = accountsRepository.findByIdAndCid(eachdata.getAccountid(), userdata.getCid());
                eachaccount.get().setBalance(eachaccount.get().getBalance() + eachdata.getJama() - eachdata.getKarchu());
                eachdata.setBalance(eachaccount.get().getBalance());
                eachaccount.get().setCid(userdata.getCid());
                eachdata.setCid(userdata.getCid());
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
        List<account> Accounts = accountsRepository.findAllByCid(userdata.getCid());
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
        Optional<account> eachaccount = accountsRepository.findByIdAndCid(idclass.getId(), userdata.getCid());
        account test = eachaccount.get();
        result.put("accinfo",test.getName());
        result.put("balance",test.getBalance());
        return result.toString();
    }
    @GetMapping("getaccountsforedit")
    public String getaccountsforedit(@RequestHeader(value = "Authorization") String authorization) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        result.put("columNames",new String[]{"name","phno","balance"});
        List<nametype> test = new ArrayList<nametype>();
        test.add(new nametype("name","Text"));
        test.add(new nametype("phno","Text"));

        if(userdata.getRole().equals("admin")) {
            result.put("data",accountsRepository.findAllByCid(userdata.getCid()));
            test.add(new nametype("onlyadmin","Boolean"));
        } else {
            result.put("data",accountsRepository.findAllByCidAndOnlyadmin(userdata.getCid(),false));
        }
        result.put("editable", test);
        return result.toString();
    }
    @PostMapping("accountseditandsave")
    public  String accountseditandsave(@RequestHeader(value = "Authorization") String authorization, @RequestBody account needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        needsavedata.setCid(userdata.getCid());
        accountsRepository.save(needsavedata);
        JSONObject result = new JSONObject();
        result.put("Status","sucess");
        return result.toString();
    }
    @PostMapping("deleteaccount")
    public  String deleteaccount(@RequestHeader(value = "Authorization") String authorization, @RequestBody account needsavedata) {
        String Token = authorization.replace("Bearer ", "");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject result = new JSONObject();
        if (needsavedata.getBalance() == 0 ) {
            accountsRepository.delete(needsavedata);
            result.put("Status","Sucess");
        } else  {
            result.put("Status","Failed");
            result.put("errmessage","Balance should be 0");
        }
        return result.toString();
    }

}