package servlets;

import models.Role;
import models.User;

import utils.DBConnection;
import utils.PasswordUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@SuppressWarnings("serial")
public class DeconnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getSession().removeAttribute("user");
    	response.sendRedirect("/Forum_JEE/");
    }
}