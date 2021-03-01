package cc.js.sora.fight.db;

import org.hibernate.type.ListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.fight.EquipType;
import cc.js.sora.fight.Hero;

public interface EquipTypeRepository extends JpaRepository<EquipType, Long>{

}
