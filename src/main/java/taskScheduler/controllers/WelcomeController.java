package taskScheduler.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import antlr.collections.List;
import taskScheduler.User;
import taskScheduler.UserTask;
import taskScheduler.DAOLayer.MainDAO;
import taskScheduler.tokens.AuthToken;
import taskScheduler.tokens.AutoUpdatingKey;
import taskScheduler.tokens.EncryptedAuthToken;



@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins="http://localhost:8080/")
public class WelcomeController {
	
	@PostMapping( path="/register", consumes ={"application/json"})
	public ResponseEntity<String> register(@RequestBody String userRegisterData) {
		HttpHeaders headers = new HttpHeaders();
		User user = null;
		try {
			ObjectMapper mapper = new ObjectMapper(); //Deserialization request JSON
			user = mapper.readValue((new StringReader(userRegisterData)), User.class);
		} catch (IOException e) {e.printStackTrace();}
		
		if(user == null) return new ResponseEntity<String>("User may not have been initialized", HttpStatus.BAD_REQUEST);
		
		user.encryptPassword();
		
		try {
			MainDAO.create(user);
		} catch (SQLDataException e) {
			return new ResponseEntity<String>("User already registered", headers, HttpStatus.BAD_REQUEST);
		}
		
		AuthToken token = new AuthToken(user);
		EncryptedAuthToken encryptedToken = token.encrypt();
		
	    headers.add("Authorization", encryptedToken.toString());
	    headers.add("Access-Control-Expose-Headers", "Authorization");
		return new ResponseEntity<String>("Login is accept", headers, HttpStatus.CREATED);
	}
	
	@PostMapping(path="/login", consumes ={"application/json"})
	public ResponseEntity<String> login(@RequestBody String userLoginData){
		HttpHeaders headers = new HttpHeaders();
		User loginingUser = null;
		
		try {
			ObjectMapper mapper = new ObjectMapper(); //Deserialization requested JSON
			loginingUser = mapper.readValue((new StringReader(userLoginData)), User.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(loginingUser == null) return new ResponseEntity<String>("User may not have been initialized", HttpStatus.BAD_REQUEST);
		loginingUser.encryptPassword();
		
		User verifyUser = (User)MainDAO.read(User.class, loginingUser.getEmail());
		if(!loginingUser.equals(verifyUser)) {
			return new ResponseEntity<String>("Login failed", headers, HttpStatus.BAD_REQUEST);
		}
		
		AuthToken token = new AuthToken(loginingUser);
		EncryptedAuthToken encryptedToken = token.encrypt();
		
	    headers.add("Authorization", encryptedToken.getEncryptedStringToken());
	    headers.add("Access-Control-Expose-Headers", "Authorization");
		return new ResponseEntity<String>("Login is accept", headers, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path="/checkToken")
	public ResponseEntity<String> checkToken(@RequestHeader(name = "Authorization") String token) {
		var encryptedToken = new EncryptedAuthToken(token);
		try {
			if(encryptedToken.isTrue()) {
				return new ResponseEntity<String>("Token is true", HttpStatus.ACCEPTED);
			}
		} catch (BadPaddingException e) {
			return new ResponseEntity<String>("Token is expired", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Token is false", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path="/saveTask", consumes ={"application/json"})
	public ResponseEntity<String> saveTask(@RequestBody String taskData,@RequestHeader(name = "Authorization") String token) {
		UserTask task = new UserTask();
		String userEmail = "";
		
		try { 
			AuthToken decryptedToken = (new EncryptedAuthToken(token)).decrypt();
			userEmail = decryptedToken.getUserEmail();
		} catch (BadPaddingException e) {
			return new ResponseEntity<String>("Token is expired", HttpStatus.BAD_REQUEST);
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper(); //Deserialization requested JSON
			task = mapper.readValue((new StringReader(taskData)), UserTask.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		task.setEmail(userEmail);
		try {
			MainDAO.create(task);
		} catch (SQLDataException e) {
			return new ResponseEntity<String>("Task didn't created", HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("task-id", Long.toString(task.getId()));

	    headers.add("Access-Control-Expose-Headers", "task-id");
		return new ResponseEntity<String>("Task created", headers, HttpStatus.CREATED);
	}
	
	
	@GetMapping(path="/getAllTasks")
	public ResponseEntity<String> getAllTasks(@RequestHeader(name = "Authorization") String token) {
		var encryptedToken = new EncryptedAuthToken(token);
		String email;
		String JSONlist = "";
		
		try {
			if(!encryptedToken.isTrue()) {
				return new ResponseEntity<String>("Token is false", HttpStatus.BAD_REQUEST);
			}
			email = encryptedToken.decrypt().getUserEmail();
		} catch (BadPaddingException e) {
			return new ResponseEntity<String>("Token is expired", HttpStatus.BAD_REQUEST);
		}
		
		@SuppressWarnings (value="unchecked")
		ArrayList<UserTask> list = (ArrayList<UserTask>) MainDAO.readAll(UserTask.class,email);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JSONlist = mapper.writeValueAsString(list);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(JSONlist,HttpStatus.ACCEPTED);
	}
}
