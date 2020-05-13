package cc.js.sora.match;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "player1")
	Player player1;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "player2")
	Player player2;

	@Column(name = "score1")
	int score1;

	@Column(name = "score2")
	int score2;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "season_id")
	@JsonIgnore
	Season season;

	@Column(name = "record_group")
	int group;

	@Column(name = "stage")
	int stage;

	@Column(name = "map")
	int map;

	@Column(name = "match_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	Date matchTime;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "video_id")
	VideoInfo video;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(id).append(" ").append(player1).append(" vs ").append(player2).append(score1)
				.append(":").append(score2);
		return sb.toString();
	}
}
