package models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Message {
    private int id;
    private String contenu;
    private Date date;
    private Subject sujet;
    private User user;

    public Message(int id, String contenu, Date date, Subject sujet, User user) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.sujet = sujet;
        this.user = user;
    }

    public int getId() { return id; }
    
    public String getContenu() { return contenu; }
    
    public Date getDate() { return date; }
    
    public Subject getSujet() { return sujet; }
    
    public User getUser() { return user; }

    public String getDateFormated() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(date);
    }
}
