package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
	
    @Query("from Record where season=?1 order by matchTime asc")
    public List<Record> findRecordBySeason(int season);  

}
