<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card shadow-lg">
                    <div class="card-header text-center">
                        <h3>Connexion</h3>
                    </div>
                    <div class="card-body">
                        <form action="connection" method="post">
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Mot de passe</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <p>Pas encore de compte ? <a href="register.jsp">Inscrivez-vous</a></p>
                    </div>
                </div>
                <%-- Affichage des messages d'erreur --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger mt-3"><%= request.getAttribute("errorMessage") %></div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
