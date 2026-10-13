package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="securityquestions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int questionid;
	
	@Column
	String questiontext;

	public Question() {
		super();
	}

	public Question(int questionid, String questiontext) {
		super();
		this.questionid = questionid;
		this.questiontext = questiontext;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}
	
}
