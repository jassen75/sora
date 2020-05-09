package cc.js.sora.match.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Player;

public interface PlayerRepository  extends JpaRepository<Player, Long>{
	
	
    @Query("from Player where name=?1 and server=?2")
    public Player findPlayer(String name, String server);

}
