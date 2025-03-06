<%@ page import="models.User, models.Role" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Subject, models.Category, models.User, models.Role" %>

<% User currentUser = (User) session.getAttribute("user");
boolean isAdmin = false;
if(currentUser != null){
	if(currentUser.getRole().getName().equals("administrateur")){
		isAdmin = true;
	}
}

//get les categories
ArrayList<Category> categories = new ArrayList<Category>();
categories = (ArrayList<Category>) request.getAttribute("categories");
%>

<header class="bg-dark shadow-sm py-4">
    <div class="container d-flex align-items-center justify-content-between">
        <!-- Logo a gauche -->
        <a href="/Forum_JEE/home.jsp">
            <img src="assets/images/La_Manu_logo_blanc_slogan.png" alt="Logo" style="max-height: 70px;">
        </a>
        <!-- Liens de navigation -->
        <nav class="nav">
        <%
		if (categories != null) {
			if(categories.size() == 0){
				if(isAdmin == true){%>
            		<a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#createCategoryModal">Ajouter une catégorie</a>
            <%  } 
			}else{	
				for (int i = 0; i < categories.size() && i < 3; i++) { 
				%>					
		        	<a class="nav-link text-white fw-bold mx-3" href="category?id=<%= categories.get(i).getId() %>"><%= categories.get(i).getName() %></a>
		    <%  }
				if(categories.size() > 3){
					%>
			    	<div class="nav-item dropdown">
				    	<a class="nav-link dropdown-toggle text-white fw-bold mx-3" href="javascript:void(0)" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		                    More Sports
		                </a>
		                <ul class="dropdown-menu">
					    <%
					    for (int i = 3; i < categories.size(); i++) { %>
					        <li><a class="dropdown-item" href="category?id=<%= categories.get(i).getId() %>"><%= categories.get(i).getName() %></a></li>
					    <% } %>
					    
					    <%
	                    if(isAdmin == true){%>
	                    	<li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#createCategoryModal">Ajouter une catégorie</a></li> 
	                    <% } %>   
	                    
					    </ul>
			    	</div>		    		    
				<% }	
			}
		}
		%>		
        </nav>
        <!-- Bouton Inscription -->
        <a href="register.jsp" class="btn btn-primary fw-bold">Inscription / Connexion</a>
    </div>
    
    <!-- Modal -->
	<div class="modal fade" id="createCategoryModal" tabindex="-1" aria-labelledby="createCategoryModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title text-center w-100" id="createCategoryModalLabel">Créer une nouvelle catégorie</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
	            </div>
	            <div class="modal-body">
	                <% if (isAdmin) { %>
	                    <!-- Formulaire d'ajout de sujet -->
	                    <form action="header" method="POST">

	                        <div class="mb-3">
	                            <label for="title" class="form-label">Titre de la catégorie</label>
	                            <input type="text" class="form-control" id="title" name="title" required>
	                        </div>
	                        <button type="submit" class="btn btn-primary">Créer</button>
	                    </form>
	                <% } else { %>
	                    <!-- Message d'erreur si l'utilisateur n'est pas connecté -->
	                    <div class="alert alert-warning" role="alert">
	                        Vous devez être connecté en tant qu'administrateur pour créer une catégorie. <a href="connection.jsp" class="alert-link">Connectez-vous ici.</a>
	                    </div>
	                <% } %>
	            </div>
	        </div>
	    </div>
	</div>			
</header>