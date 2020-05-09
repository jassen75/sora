package cc.js.sora.match.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.match.db.PlayerRepository;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;

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
