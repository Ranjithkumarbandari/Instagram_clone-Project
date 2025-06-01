package com.my.instagram_clone.servlet;

import java.io.IOException;

import com.my.instagram_clone.dao.FollowDAO;
import com.my.instagram_clone.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/follow")
public class FollowServlet extends HttpServlet {
    private FollowDAO followDAO;

    @Override
    public void init() {
        followDAO = new FollowDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int followerId = loggedInUser.getId();

        String action = request.getParameter("action");
        int followeeId = Integer.parseInt(request.getParameter("followeeId"));

        if (followerId != followeeId) {
            if ("follow".equals(action)) {
                followDAO.follow(followerId, followeeId);
                System.out.println(followerId+" followed "+ followeeId);
            } else if ("unfollow".equals(action)) {
                followDAO.unfollow(followerId, followeeId);
                System.out.println(followerId+" Unfollowed "+ followeeId);
            }
        }

        response.sendRedirect("feed");
    }
}
