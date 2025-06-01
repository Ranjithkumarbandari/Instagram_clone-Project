<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.my.instagram_clone.model.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>Instagram Clone - Feed</title>
    <style>
        .photo-container { border: 1px solid #ddd; padding: 10px; margin-bottom: 20px; width: 600px; }
        .photo-header { display: flex; justify-content: space-between; align-items: center; }
        .username { font-weight: bold; }
        .follow-btn { padding: 5px 10px; background-color: #007bff; color: white; border: none; cursor: pointer; }
        .unfollow-btn { background-color: #dc3545; }
        .photo-img { max-width: 100%; margin-top: 10px; }
        .caption { margin-top: 10px; }
        .likes { margin-top: 5px; font-weight: bold; }
        .comments { margin-top: 10px; }
        .comment { margin-bottom: 5px; }
        .like-btn { cursor: pointer; color: #007bff; background: none; border: none; }
        form { display: inline; }
    </style>
</head>
<body>

<h2>Welcome, ${loggedInUser.fullName} (@${loggedInUser.username})</h2>

<c:forEach var="photo" items="${photos}">
    <div class="photo-container">
        <div class="photo-header">
            <div class="username">
                <c:out value="${photo.username}" />
            </div>

            <c:choose>
                <c:when test="${loggedInUser.id != photo.userId}">
                    <c:choose>
                        <c:when test="${followingMap[photo.id] == true}">
                            <form action="follow" method="post">
                                <input type="hidden" name="action" value="unfollow" />
                                <input type="hidden" name="followeeId" value="${photo.userId}" />
                                <button type="submit" class="follow-btn unfollow-btn">Unfollow</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="follow" method="post">
                                <input type="hidden" name="action" value="follow" />
                                <input type="hidden" name="followeeId" value="${photo.userId}" />
                                <button type="submit" class="follow-btn">Follow</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <span>It's you</span>
                </c:otherwise>
            </c:choose>
        </div>

        <img src="${photo.imageUrl}" alt="Photo" class="photo-img" />

        <div class="caption">${photo.caption}</div>

        <div class="likes">
            Likes: ${likesCountMap[photo.id]}
            <form action="like" method="post">
                <input type="hidden" name="photoId" value="${photo.id}" />
                <c:choose>
                    <c:when test="${likedByUserMap[photo.id]}">
                        <input type="hidden" name="action" value="unlike" />
                        <button type="submit" class="like-btn">Unlike</button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="like" />
                        <button type="submit" class="like-btn">Like</button>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>

        <div class="comments">
            <strong>Comments:</strong>
            <c:forEach var="comment" items="${commentsMap[photo.id]}">
                <div class="comment">
                    <strong>${comment.username}:</strong> ${comment.commentText}
                </div>
            </c:forEach>

            <form action="addcomment" method="post">
                <input type="hidden" name="photoId" value="${photo.id}" />
                <input type="text" name="commentText" placeholder="Add a comment..." required />
                <button type="submit">Post</button>
            </form>
        </div>
    </div>
</c:forEach>

</body>
</html>
