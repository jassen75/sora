package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
    @Query("from Role where season=?1")
    public List<Role> findRoleBySeason(int season);

}
