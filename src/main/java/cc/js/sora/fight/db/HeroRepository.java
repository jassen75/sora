package cc.js.sora.fight.db;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.js.sora.fight.Hero;

public interface HeroRepository  extends JpaRepository<Hero, Long>{

}
