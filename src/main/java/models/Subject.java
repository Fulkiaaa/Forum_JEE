package models;

import java.util.Date;

public class Subject {
    private int id;
    private String title;
    private String content;
    private int idCategorie;
    private int idUser;
    private Date creationDate;

    // Constructeur par défaut
    public Subject() {
    }

    // Constructeur avec paramètres
    public Subject(int id, String title, String content, int idCategorie, int idUser, Date creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.idCategorie = idCategorie;
        this.idUser = idUser;
        this.creationDate = creationDate;
    }

    // Getters et Setters
    public int getId() {
        
    	return id;
    }

    public void setId(int id) {
        
    	this.id = id;
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

    public int getIdCategorie() {
        
    	return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        
    	this.idCategorie = idCategorie;
    }

    public int getIdUser() {
        
    	return idUser;
    }

    public void setIdUser(int idUser) {
        
    	this.idUser = idUser;
    }

    public Date getCreationDate() {
        
    	return creationDate;
    }

    public void setCreationDate(Date creationDate) {
    	
        this.creationDate = creationDate;
    }

    // Méthode toString pour afficher les informations du sujet
    @Override
    public String toString() {
        return "Sujet{" +
                "id=" + id +
                ", titre='" + title + '\'' +
                ", contenu='" + content + '\'' +
                ", idCategorie=" + idCategorie +
                ", idUtilisateur=" + idUser +
                ", dateCreation=" + creationDate +
                '}';
    }
}
