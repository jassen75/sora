package cc.js.sora;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Record {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="player1")
	Player player1;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="player2")
	Player player2;
	
	@Column(name="score1")
	int score1;

	@Column(name="score2")
	int score2;
	
	@Column(name="season")
	int season;
	
	@Column(name="match_time")
    @Temporal(TemporalType.TIMESTAMP)
	Date matchTime;
	
	
}
