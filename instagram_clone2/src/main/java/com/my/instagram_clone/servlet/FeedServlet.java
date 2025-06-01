package com.my.instagram_clone.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.instagram_clone.dao.CommentDAO;
import com.my.instagram_clone.dao.FollowDAO;
import com.my.instagram_clone.dao.LikeDAO;
import com.my.instagram_clone.dao.PhotoDAO;
import com.my.instagram_clone.model.Comment;
import com.my.instagram_clone.model.Photo;
import com.my.instagram_clone.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/feed")
public class FeedServlet extends HttpServlet {

    private PhotoDAO photoDAO;
    private CommentDAO commentDAO;
    private FollowDAO followDAO;
    private LikeDAO likeDAO;

    @Override
    public void init() {
        photoDAO = new PhotoDAO();
        commentDAO = new CommentDAO();
        followDAO = new FollowDAO();
        likeDAO = new LikeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loggedInUser = (User) session.getAttribute("user");
     
        request.setAttribute("loggedInUser", loggedInUser);

        List<Photo> photos = photoDAO.getAllPhotos();
        request.setAttribute("photos", photos);

        Map<Integer, List<Comment>> commentsMap = new HashMap<>();
        Map<Integer, Integer> likesCountMap = new HashMap<>();
        Map<Integer, Boolean> likedByUserMap = new HashMap<>();
        Map<Integer, Boolean> followingMap = new HashMap<>();

        for (Photo photo : photos) {
            int photoId = photo.getId();
            int ownerId = photo.getUserId();
            
           

            commentsMap.put(photoId, commentDAO.getCommentsByPhotoId(photoId));
            likesCountMap.put(photoId, likeDAO.getLikesCount(photoId));
            likedByUserMap.put(photoId, likeDAO.isLiked(loggedInUser.getId(), photoId));

            // Check follow status only if not the same user
            if (loggedInUser.getId() != ownerId) {
                followingMap.put(photoId, followDAO.isFollowing(loggedInUser.getId(), ownerId));
            } else {
                followingMap.put(photoId, null); // or false, up to how you display
            }
        }

        request.setAttribute("commentsMap", commentsMap);
        request.setAttribute("likesCountMap", likesCountMap);
        request.setAttribute("likedByUserMap", likedByUserMap);
        request.setAttribute("followingMap", followingMap);

        request.getRequestDispatcher("feed.jsp").forward(request, response);
    }
}
