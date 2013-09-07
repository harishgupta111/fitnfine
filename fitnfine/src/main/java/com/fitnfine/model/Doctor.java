package com.fitnfine.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FF_Doctor")
@Cache(region = "entity.FF_Doctor", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class Doctor extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8201484496778503079L;
	
	@Id
	@Column(name = "doctorID", insertable = false, updatable = false)
	private String doctorID;
	
	@Column(name = "doctorName")
	private String doctorName;
	
	@Column(name = "doctorAddress")
	private String doctorAddress;
	
	@Column(name = "doctorSpecialization")
	private String doctorSpecialization;
	
	public String getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorAddress() {
		return doctorAddress;
	}

	public void setDoctorAddress(String doctorAddress) {
		this.doctorAddress = doctorAddress;
	}

	public String getDoctorSpecialization() {
		return doctorSpecialization;
	}

	public void setDoctorSpecialization(String doctorSpecialization) {
		this.doctorSpecialization = doctorSpecialization;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((doctorAddress == null) ? 0 : doctorAddress.hashCode());
		result = prime * result
				+ ((doctorID == null) ? 0 : doctorID.hashCode());
		result = prime * result
				+ ((doctorName == null) ? 0 : doctorName.hashCode());
		result = prime
				* result
				+ ((doctorSpecialization == null) ? 0 : doctorSpecialization
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		if (doctorAddress == null) {
			if (other.doctorAddress != null)
				return false;
		} else if (!doctorAddress.equals(other.doctorAddress))
			return false;
		if (doctorID == null) {
			if (other.doctorID != null)
				return false;
		} else if (!doctorID.equals(other.doctorID))
			return false;
		if (doctorName == null) {
			if (other.doctorName != null)
				return false;
		} else if (!doctorName.equals(other.doctorName))
			return false;
		if (doctorSpecialization == null) {
			if (other.doctorSpecialization != null)
				return false;
		} else if (!doctorSpecialization.equals(other.doctorSpecialization))
			return false;
		return true;
	}
	
	
	
	
	
	
	

}
