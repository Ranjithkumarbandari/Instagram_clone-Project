<%@ page import="java.util.List" %>
<%@ page import="com.my.instagram_clone.dao.CommentDAO" %>
<%@ page import="com.my.instagram_clone.model.Comment" %>

<%
    int photoId = 0;
    try {
        photoId = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e) {
        out.println("<p>Invalid photo ID.</p>");
        return;
    }

    CommentDAO commentDAO = new CommentDAO();
    List<Comment> comments = commentDAO.getCommentsByPhotoId(photoId);
%>

<html>
<head>
    <title>Photo Details</title>
</head>
<body>

<h2>Photo Details - Photo ID: <%= photoId %></h2>

<% if(request.getAttribute("error") != null) { %>
    <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<!-- Display comments -->
<h3>Comments</h3>
<div>
    <% if (comments.isEmpty()) { %>
        <p>No comments yet. Be the first to comment!</p>
    <% } else {
        for (Comment comment : comments) { %>
            <div>
                <strong><%= comment.getUsername() %></strong>:
                <%= comment.getCommentText() %>
                <em>(<%= comment.getCreatedAt() %>)</em>
            </div>
    <%  } } %>
</div>

<!-- Add new comment form -->
<h3>Add a Comment</h3>
<form action="<%= request.getContextPath() %>/addcomment" method="post">
    <input type="hidden" name="photoId" value="<%= photoId %>" />
    <textarea name="commentText" placeholder="Write your comment here..." required></textarea><br/>
    <button type="submit">Post Comment</button>
</form>

</body>
</html>