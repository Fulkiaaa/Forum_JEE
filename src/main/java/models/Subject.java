package models;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Subject{
	private int id;
	private String title;
	private String content;
	private Date date;
	private Category category;
	private User user;
		
	public Subject(int idSubject, String title, String content, Date date, Category category, User user) {			
		this.id = idSubject;
		this.title = title;
		this.content = content;
		this.date = date;
		this.category = category;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}
	
	public String getDateFormated() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    return sdf.format(this.date);
	}
	
}
