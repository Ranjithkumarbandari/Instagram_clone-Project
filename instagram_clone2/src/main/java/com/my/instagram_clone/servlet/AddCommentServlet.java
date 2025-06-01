package com.my.instagram_clone.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.my.instagram_clone.dao.CommentDAO;
import com.my.instagram_clone.model.User;

@WebServlet("/addcomment")
public class AddCommentServlet extends HttpServlet {
    private CommentDAO commentDAO;

    @Override
    public void init() {
        commentDAO = new CommentDAO();
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
        String commentText = request.getParameter("commentText");

        commentDAO.addComment(userId, photoId, commentText);
        response.sendRedirect("feed");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect("feed");
    }
}
