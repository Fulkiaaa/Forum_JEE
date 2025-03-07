package models;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Role role;
    
    // Constructeurs
    public User() {}

    public User(int id, String username, String email, String password) {    	
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = new Role(2, "member"); //le role est un membre simple par défault
    }
    
    public User(int id, String username, String email, String password, Role role) {    	
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public int getId() {     	
    	return id; 
    }
    
    public void setId(int id) {     	
    	this.id = id; 
    }

    public String getUsername() {     	
    	return username; 	
    }
    
    public void setUsername(String username) {     	
    	this.username = username; 
    }

    public String getEmail() {     	
    	return email; 
    }
    
    public void setEmail(String email) {     	
    	this.email = email; 
    }

    public String getPassword() {     	
    	return password; 
    }
    
    public void setPassword(String password) {     	
    	this.password = password; 
    }
    
    public Role getRole() {
    	return this.role;
    }
    
    public void setRole(Role role) {
    	this.role = role;
    }
}