package cc.js.sora.fight.db;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.js.sora.fight.Soldier;

public interface SoldierRepository  extends JpaRepository<Soldier, Long>{

}
