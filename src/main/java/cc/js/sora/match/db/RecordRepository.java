package cc.js.sora.match.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.js.sora.match.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

	@Query("from Record r where r.season.matchType=?1 and r.season.number=?2 order by group asc")
	public List<Record> findRecordBySeason(String matchType, int season);

	@Query("from Record r where r.season.matchType=?1 and r.season.number=?2 and"+
	" ( (r.player1.name=?3  and r.player2.name=?4) or "+
			" (r.player1.name=?4  and r.player2.name=?3) )")
	public Record findRecord(String matchType, int season, String player1, String player2);

}
