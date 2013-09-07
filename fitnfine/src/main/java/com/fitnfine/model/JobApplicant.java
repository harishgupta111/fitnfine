package com.fitnfine.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FF_JobApplicant")
@Cache(region = "entity.FF_JobApplicant", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class JobApplicant extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232300875222041545L;
	
	@Id
	@Column(name = "jobApplicantID", insertable = false, updatable = false)
	private String jobApplicantID;
	
	@Column(name = "applicantName")
	private String applicantName;
	
	@Column(name = "applicantAge")
    private Integer applicantAge;
	
	@Column(name = "experienceInYears")
    private Integer experienceInYears;

	@Column(name = "experienceComment")
	private String experienceComment;
	
	@Column(name = "contactAddress")
    private String contactAddress;
	
	@Column(name = "contactNumber")
    private String contactNumber;
	
	@Column(name = "otherComment")
    private String otherComment;
	
	@Lob
	@Column(name = "resumeFile")
	private byte[] resumeFile;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((applicantAge == null) ? 0 : applicantAge.hashCode());
		result = prime * result
				+ ((applicantName == null) ? 0 : applicantName.hashCode());
		result = prime * result
				+ ((contactAddress == null) ? 0 : contactAddress.hashCode());
		result = prime * result
				+ ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime
				* result
				+ ((experienceComment == null) ? 0 : experienceComment
						.hashCode());
		result = prime
				* result
				+ ((experienceInYears == null) ? 0 : experienceInYears
						.hashCode());
		result = prime * result
				+ ((jobApplicantID == null) ? 0 : jobApplicantID.hashCode());
		result = prime * result
				+ ((otherComment == null) ? 0 : otherComment.hashCode());
		result = prime * result
				+ ((resumeFile == null) ? 0 : resumeFile.hashCode());
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
		JobApplicant other = (JobApplicant) obj;
		if (applicantAge == null) {
			if (other.applicantAge != null)
				return false;
		} else if (!applicantAge.equals(other.applicantAge))
			return false;
		if (applicantName == null) {
			if (other.applicantName != null)
				return false;
		} else if (!applicantName.equals(other.applicantName))
			return false;
		if (contactAddress == null) {
			if (other.contactAddress != null)
				return false;
		} else if (!contactAddress.equals(other.contactAddress))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (experienceComment == null) {
			if (other.experienceComment != null)
				return false;
		} else if (!experienceComment.equals(other.experienceComment))
			return false;
		if (experienceInYears == null) {
			if (other.experienceInYears != null)
				return false;
		} else if (!experienceInYears.equals(other.experienceInYears))
			return false;
		if (jobApplicantID == null) {
			if (other.jobApplicantID != null)
				return false;
		} else if (!jobApplicantID.equals(other.jobApplicantID))
			return false;
		if (otherComment == null) {
			if (other.otherComment != null)
				return false;
		} else if (!otherComment.equals(other.otherComment))
			return false;
		if (resumeFile == null) {
			if (other.resumeFile != null)
				return false;
		} else if (!resumeFile.equals(other.resumeFile))
			return false;
		return true;
	}

	public String getJobApplicantID() {
		return jobApplicantID;
	}

	public void setJobApplicantID(String jobApplicantID) {
		this.jobApplicantID = jobApplicantID;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Integer getApplicantAge() {
		return applicantAge;
	}

	public void setApplicantAge(Integer applicantAge) {
		this.applicantAge = applicantAge;
	}

	public Integer getExperienceInYears() {
		return experienceInYears;
	}

	public void setExperienceInYears(Integer experienceInYears) {
		this.experienceInYears = experienceInYears;
	}

	public String getExperienceComment() {
		return experienceComment;
	}

	public void setExperienceComment(String experienceComment) {
		this.experienceComment = experienceComment;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getOtherComment() {
		return otherComment;
	}

	public void setOtherComment(String otherComment) {
		this.otherComment = otherComment;
	}

	public byte[] getResumeFile() {
		return resumeFile;
	}

	public void setResumeFile(byte[] bs) {
		this.resumeFile = bs;
	}
	
	

}
