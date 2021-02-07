package cc.js.sora.fight.db;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.js.sora.fight.Action;

public interface ActionRepository  extends JpaRepository<Action, Long> {

}
