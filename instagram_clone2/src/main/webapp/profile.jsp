<%
    int loggedInUserId = (int) session.getAttribute("userId");
    int profileUserId = Integer.parseInt(request.getParameter("userId"));

    if (loggedInUserId != profileUserId) {
        com.my.instagram_clone.dao.FollowDAO dao = new com.my.instagram_clone.dao.FollowDAO();
        boolean isFollowing = false;
        try {
            isFollowing = dao.isFollowing(loggedInUserId, profileUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
%>
<form action="follow" method="post">
    <input type="hidden" name="followeeId" value="<%= profileUserId %>">
    <% if (isFollowing) { %>
        <button type="submit" name="action" value="unfollow">Unfollow</button>
    <% } else { %>
        <button type="submit" name="action" value="follow">Follow</button>
    <% } %>
</form>
<%
    }
%>
