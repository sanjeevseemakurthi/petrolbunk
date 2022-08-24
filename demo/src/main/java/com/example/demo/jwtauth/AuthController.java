package com.example.demo.jwtauth;
import org.hibernate.boot.jaxb.Origin;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;


@RestController
public class AuthController {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public userdetailsRepository userdetailsRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	@PostMapping("/newuser")
	public String adduser(@RequestBody JwtRequest userdata1) {
		userdata userdata = new userdata();
		userdata.setPassword(passwordEncoder.encode(userdata1.getPassword()));
		userdata.setUsername(userdata1.getUsername());
		userdata.setRole(userdata1.getRole());
		userdetailsRepository.save(userdata);
		return "hi";
	}
	@PostMapping("/test")
	@ResponseBody
	public String test(@RequestBody String data) {
		 JSONObject demo = new JSONObject(data);
	     System.out.println(demo.get("password"));
	     demo.put("role", "user");
	     return demo.toString();
	}
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
	    try{
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        jwtRequest.getUsername(),jwtRequest.getPassword()
	                )
	        );
	    } catch (BadCredentialsException e) {
	        throw new Exception("Invalid Credentials", e);
	    }

	    final UserDetails userDetails
	            = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

	    final String token =
	            jwtUtility.generateToken(userDetails);
		userdata userdata = userdetailsRepository.findByUsername(userDetails.getUsername());
	    return new JwtResponse(token,userdata.getRole());
	}
	@GetMapping("/sessioncheck")
	public String sessioncheck(@RequestHeader(value = "Authorization") String authorization) {
		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		JSONObject demo = new JSONObject();
		demo.put("user",userdata.getUsername());
		demo.put("role", userdata.getRole());

		return demo.toString();
	}
}
