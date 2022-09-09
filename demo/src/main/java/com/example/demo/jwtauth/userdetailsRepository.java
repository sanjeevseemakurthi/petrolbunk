package com.example.demo.jwtauth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface userdetailsRepository extends JpaRepository<userdata,String>{
	<List>userdata findByUsername(String s);
	@Query("SELECT username FROM userdata")
	List<String> findallusernames();
}
