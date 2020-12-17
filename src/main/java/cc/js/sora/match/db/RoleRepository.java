package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
    @Query("from Role r where r.season.matchType=?1 and r.season.number=?2 and r.name=?3")
    public List<Role> findRoleBySeason(String matchType, int season, String roleName);
    
    @Query("from Role r where r.season.matchType=?1 and r.season.number=?2 and r.player.name=?3 and r.player.server=?4")
    public Role findRole(String matchType, int season, String player, String server);

}
