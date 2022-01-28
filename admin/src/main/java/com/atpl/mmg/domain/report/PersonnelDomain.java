package com.atpl.mmg.domain.report;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class PersonnelDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8611507266927265938L;

	private BigInteger id;
	private String personnelSource;
	private String source;
	private BigInteger personnelYear;
	private BigInteger personnelAmount;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getPersonnelSource() {
		return personnelSource;
	}

	public void setPersonnelSource(String personnelSource) {
		this.personnelSource = personnelSource;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigInteger getPersonnelYear() {
		return personnelYear;
	}

	public void setPersonnelYear(BigInteger personnelYear) {
		this.personnelYear = personnelYear;
	}

	public BigInteger getPersonnelAmount() {
		return personnelAmount;
	}

	public void setPersonnelAmount(BigInteger personnelAmount) {
		this.personnelAmount = personnelAmount;
	}

}
