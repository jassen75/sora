package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {
	
    @Query("from Season where status=2 order by number desc")
	public Season getRunningSeason();
    
    @Query("from Season where number=?1")
	public Season getSeason(int number);
    
    @Query("from Season where status=3 order by number asc")
    public List<Season> getCompleteSeasons();
}
