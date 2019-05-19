package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PublicHoliday implements Serializable{

	private static final long serialVersionUID = -8607640984681418685L;

	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String name;
	
	public PublicHoliday() {
		super();
	}
	
	//For Testing
	public PublicHoliday(Date date, String name) {
		super();
		this.date = date;
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
