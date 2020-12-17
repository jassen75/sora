package cc.js.sora.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "season")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Season {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name="number")
	int number;
	
	// -1 unknown 0 planning 1 running 2 complete
	@Column(name="status")
	@Enumerated(EnumType.ORDINAL)  
	SeasonStatus status;
	
	@Column(name="match_type")
	String matchType;
	
	@Column(name="start_time")
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern="yyyy-MM-dd")
	Date matchTime;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "season")
	//@JoinTable(name="role",joinColumns={@JoinColumn(name="season_id")})
	List<Role> roles = new ArrayList();
	
	
	@JsonProperty("players")
	public List<Player> getPlayers() {
		List result = new ArrayList();
		roles.forEach(role->{result.add(role.getPlayer());});
		return result;
	}

	public void setPlayers(List<Player> players) {
		players.forEach(player->{
			Role role = new Role();
			role.setSeason(this);
			role.setPlayer(player);
			role.setName(Role.ROLE_PLAYER);
			roles.add(role);
		});
	}
}
