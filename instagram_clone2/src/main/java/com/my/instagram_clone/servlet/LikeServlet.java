package com.my.instagram_clone.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.my.instagram_clone.dao.LikeDAO;
import com.my.instagram_clone.model.User;

import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    private LikeDAO likeDAO;

    @Override
    public void init() {
        likeDAO = new LikeDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        int photoId = Integer.parseInt(request.getParameter("photoId"));
        String action = request.getParameter("action");

        if ("like".equalsIgnoreCase(action)) {
            likeDAO.likePhoto(userId, photoId);
        } else if ("unlike".equalsIgnoreCase(action)) {
            likeDAO.unlikePhoto(userId, photoId);
        }

        response.sendRedirect("feed");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("feed");
    }
}
