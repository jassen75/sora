package cc.js.sora.space.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
    @RequestMapping(value= {"/match"})
	public String index() {
		return "forward:/match/match.html";
	}

    
    @RequestMapping(value= {"/index", "/space", "/"})
	public String space() {
		return "forward:/space/index.html";
	}
    
    @RequestMapping(value= {"/lyb"})
	public String lyb() {
		return "forward:/match/kkb.html";
	}
    
    @RequestMapping(value= {"/fight"})
	public String fight() {
		return "forward:/fight/index.html";
	}
}
