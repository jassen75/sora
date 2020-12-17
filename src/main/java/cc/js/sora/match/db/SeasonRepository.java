package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {
	
    @Query("from Season where status=2 and matchType=?1 order by number desc")
	public Season getRunningSeason(String matchType);
    
    @Query("from Season where matchType=?1 and number=?2")
	public Season getSeason(String matchType, int number);
    
    @Query("from Season where status=3 and matchType=?1 order by number asc")
    public List<Season> getCompleteSeasons(String matchType);
    
    @Query("from Season where matchType=?1 order by number asc")
    public List<Season> findSeasons(String matchType);
}
