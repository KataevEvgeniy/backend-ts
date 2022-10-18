package taskScheduler;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.postgresql.util.PSQLException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskScheduler.DAOLayer.MainDAO;
import taskScheduler.controllers.WelcomeController;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	private String username;
	@Id
	private String email;
	
	public UserTask createTask(String name,String description) {
		Random rnd = new Random();
		
		UserTask userTask = new UserTask(MainDAO.num++,name,description,this.email);
		
		
		return userTask;
	}
	
}
