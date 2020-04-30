package cc.js.sora;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	Player player1;
	
	@OneToOne(fetch = FetchType.LAZY)
	Player player2;
	
	@Column(name="score1")
	int score1;

	@Column(name="score2")
	int score2;
	
	@Column(name="season")
	int seanson;
	
	@Column(name="match_time")
    @Temporal(TemporalType.TIMESTAMP)
	Date matchTime;
	
	
}
