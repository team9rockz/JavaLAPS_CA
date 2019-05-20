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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicHoliday other = (PublicHoliday) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
	
}
