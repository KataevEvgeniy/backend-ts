package taskScheduler.controllers;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taskScheduler.User;
import taskScheduler.UserTask;
import taskScheduler.DAOLayer.MainDAO;


@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins="http://localhost:8080")
public class WelcomeController {
	
	int num = 0;
	
	@GetMapping(path="/welcome",params="recent")
	public String toWelcomePage() {
		//System.out.println(MyDAO.index());
		num += 1;
		System.out.println("--------------" + num);
		return "{\"userId\": " + num + ",\"id\": 1}";
	}
	
	@PostMapping("/getData")
	public String get() {
		
		return null;
	}
	
	@GetMapping("/main")
	public String toMainPage() {
//		User user = new User("zeka","ggg52");
//		MainDAO.create(user);
//		
		User user = (User)MainDAO.read(User.class, "ggg5");
		System.out.println(user.getEmail()+" "+user.getUsername());
		
		//user.setUsername("nike");
		//MainDAO.update(user);
		
		user = (User)MainDAO.read(User.class, "ggg5");
		System.out.println(user.getEmail()+" "+user.getUsername());
		
		UserTask task = user.createTask("name", "description");
//		for (int i = 0; i < 1000000;i++) {
//			task = user.createTask("name", "description");
//			MainDAO.create(task);
//		}
		
		task = (UserTask)MainDAO.read(task.getClass(), 1);
		System.out.println("=========>" + MainDAO.getMax(UserTask.class,"taskId"));
		//System.out.println(MainDAO.readAll(User.class).toString());
		for (Object obj : MainDAO.readAll(User.class)) {
			User user1 = (User)obj;
			System.out.println(user.getUsername().trim() +" "+ user.getEmail().trim());
		}
		
		return "main";
	}
}
