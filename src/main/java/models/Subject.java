package models;

import java.sql.Date;

public class Subject{
	private int id;
	private String title;
	private String content;
	private Date date;
	private Category category;
		
	public Subject(int idSubject, String title, String content, Date date, Category category) {			
		this.id = idSubject;
		this.title = title;
		this.content = content;
		this.date = date;
		this.category = category;
	}
	
}
