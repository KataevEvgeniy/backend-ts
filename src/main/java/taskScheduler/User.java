package taskScheduler;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

import org.postgresql.util.PSQLException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskScheduler.DAOLayer.MainDAO;
import taskScheduler.controllers.WelcomeController;

@JsonAutoDetect
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	private String username;
	@Id
	private String email;
	private String password;
	
	public UserTask createTask(String name,String description) {
		Random rnd = new Random();
		UserTask userTask = new UserTask(MainDAO.num++,name,description,this.email);
		return userTask;
	}
	
	public void encryptPassword() {
		try { 
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] digest = messageDigest.digest(this.password.getBytes("UTF-8"));
			this.password = DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return this.username + "||" + this.email + "||" + this.password;
	}
	
	public boolean equals(User user) {
		if (user.getEmail().equals(this.email) && user.getPassword().equals(this.password)) {
			return true;
		}
		return false;
	}
	
}
