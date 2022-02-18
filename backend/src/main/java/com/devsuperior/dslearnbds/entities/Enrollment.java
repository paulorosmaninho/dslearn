package com.devsuperior.dslearnbds.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EmbeddedId;

import com.devsuperior.dslearnbds.entities.pk.EnrollmentPK;

public class Enrollment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private EnrollmentPK id = new EnrollmentPK();
	private Instant enrollMoment;
	private Instant refundMoment;
	private boolean avaliable;
	private boolean onlyUpdate;
	
	

}
