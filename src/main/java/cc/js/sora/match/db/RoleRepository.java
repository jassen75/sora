package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
    @Query("from Role r left join fetch r.season s where s.number=?1 and r.name=?2")
    public List<Role> findRoleBySeason(int season, String roleName);
    
    @Query("from Role r left join fetch r.season s inner join r.player p where s.number=?1 and p.name=?2 and p.server=?3")
    public Role findRole(int season, String player, String server);

}
