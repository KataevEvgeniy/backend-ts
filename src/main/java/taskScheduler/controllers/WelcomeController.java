package taskScheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String toWelcomePage() {
		return "welcome";
	}
	
	@GetMapping("/main")
	public String toMainPage() {
		return "main";
	}
}
