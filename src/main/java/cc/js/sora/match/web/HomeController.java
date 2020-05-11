package cc.js.sora.match.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
    @RequestMapping(value= {"/index", "/"})
	public String index() {
		return "forward:/match/index.html";
	}

    @RequestMapping(value= { "/admin"})
	public String admin() {
		return "forward:/match/admin.html";
	}
    
    @RequestMapping(value= { "/score"})
	public String score() {
		return "forward:/match/score.html";
	}
    
    @RequestMapping(value= { "/space"})
	public String space() {
		return "forward:/match/index.html";
	}
    
    @RequestMapping(value= { "/dashboard"})
	public String dashboard() {
		return "forward:/match/dashboard.html";
	}
}
