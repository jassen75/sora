package cc.js.sora.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.Player;
import cc.js.sora.Record;
import cc.js.sora.db.PlayerRepository;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Player> list() {
		List<Player> currentRecord = playerRepository.findAll();
		return currentRecord;
	}
	
}
