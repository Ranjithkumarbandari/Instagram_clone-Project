package com.my.instagram_clone.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.instagram_clone.dao.UserDAO;
import com.my.instagram_clone.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form values
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String bio = request.getParameter("bio");

        try {
            // Check if username already exists
            if (userDAO.isUsernameTaken(username)) {
                request.setAttribute("error", "Username is already taken.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Create new user object
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setBio(bio);

            // Register user
            boolean registered = userDAO.register(user);
            if (registered) {
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("error", "Registration failed. Try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error occurred.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
