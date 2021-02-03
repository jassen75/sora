package cc.js.sora.fight.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.fight.HeroEquip;

public interface HeroEquipRepository extends JpaRepository<HeroEquip, Long> {
	
    @Query("from HeroEquip where hero.id=?1")
    public HeroEquip findHeroEquip(long heroId);

}
