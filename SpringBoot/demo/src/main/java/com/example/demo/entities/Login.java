package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 int loginid;
	 
	 @Column
	 String userid,password,answer;
	 
	 boolean status;
	
	 @ManyToOne
	 @JoinColumn(name="roleid")
	 Role roleid;
	 @Column
	 int questionid;

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Login(String userid, String password, String answer, boolean status, Role roleid, int questionid) {
		super();
		this.userid = userid;
		this.password = password;
		this.answer = answer;
		this.status = status;
		this.roleid = roleid;
		this.questionid = questionid;
	}

	public int getLoginid() {
		return loginid;
	}

	public void setLoginid(int loginid) {
		this.loginid = loginid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Role getRoleid() {
		return roleid;
	}

	public void setRoleid(Role roleid) {
		this.roleid = roleid;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

}
