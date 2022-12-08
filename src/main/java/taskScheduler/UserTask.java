package taskScheduler;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskScheduler.DAOLayer.MainDAO;

@Data
@NoArgsConstructor

@Entity
@Table(name="user_tasks")
public class UserTask {
	@Id
	private long id = MainDAO.getMax(UserTask.class, "id") + 1;;
	
	private String title;
	private String definition;
	@Column(name="user_email")
	private String email;
	
	@Column(name="start_time")
	private String startTime;
	@Column(name="end_time")
	private String endTime;
	
	public UserTask(String title,String definition, String email, String startTime, String endTime) {
		
		this.title = title;
		this.definition = definition;
		this.email = email;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
	
}
