package cc.js.sora.db;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.js.sora.Player;

public interface PlayerRepository  extends JpaRepository<Player, Long>{

}
