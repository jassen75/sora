package cc.js.sora.match;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "video")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name="video_type")
	@Enumerated(EnumType.ORDINAL)  
	VideoType type;
	
	@Column(name="info")
	String info;

}
