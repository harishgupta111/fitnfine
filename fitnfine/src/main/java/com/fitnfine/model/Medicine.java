package com.fitnfine.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FF_Medicine")
@Cache(region = "entity.FF_Medicine", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class Medicine extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6138446882466448046L;
	
	@Id
	@Column(name = "medicineID", insertable = false, updatable = false)
	private String medicineID;
	
	@Column(name = "medicineName")
	private String medicineName;
	
	@Column(name = "forAilment")
	private String forAilment;
	
	@Column(name = "medicineCost")
	private Double medicineCost;

	public String getMedicineID() {
		return medicineID;
	}

	public void setMedicineID(String medicineID) {
		this.medicineID = medicineID;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getForAilment() {
		return forAilment;
	}

	public void setForAilment(String forAilment) {
		this.forAilment = forAilment;
	}

	public Double getMedicineCost() {
		return medicineCost;
	}

	public void setMedicineCost(Double medicineCost) {
		this.medicineCost = medicineCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((forAilment == null) ? 0 : forAilment.hashCode());
		result = prime * result
				+ ((medicineCost == null) ? 0 : medicineCost.hashCode());
		result = prime * result
				+ ((medicineID == null) ? 0 : medicineID.hashCode());
		result = prime * result
				+ ((medicineName == null) ? 0 : medicineName.hashCode());
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
		Medicine other = (Medicine) obj;
		if (forAilment == null) {
			if (other.forAilment != null)
				return false;
		} else if (!forAilment.equals(other.forAilment))
			return false;
		if (medicineCost == null) {
			if (other.medicineCost != null)
				return false;
		} else if (!medicineCost.equals(other.medicineCost))
			return false;
		if (medicineID == null) {
			if (other.medicineID != null)
				return false;
		} else if (!medicineID.equals(other.medicineID))
			return false;
		if (medicineName == null) {
			if (other.medicineName != null)
				return false;
		} else if (!medicineName.equals(other.medicineName))
			return false;
		return true;
	}
	
}
