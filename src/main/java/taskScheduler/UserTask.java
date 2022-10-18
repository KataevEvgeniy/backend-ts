package taskScheduler;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_tasks")
public class UserTask {
	@Id
	@Column(name="task_id")
	private long taskId;
	
	private String name;
	private String description;
	private String email;
	
	
	
	
}
