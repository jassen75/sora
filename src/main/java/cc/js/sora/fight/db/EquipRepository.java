package cc.js.sora.fight.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.fight.Equip;

public interface EquipRepository extends JpaRepository<Equip, Long> {
	
	@Query("from Equip r where r.equipType.id=?1")
	List<Equip> findByType(long equipType);

}
