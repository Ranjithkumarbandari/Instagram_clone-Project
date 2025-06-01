<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Photo</title>
</head>
<body>
<h2>Upload a New Photo</h2>

<p>
    Logged in as: ${sessionScope.user.username} | <a href="logout">Logout</a>
</p>

<form action="uploadPhoto" method="post" enctype="multipart/form-data">
    <label for="photo">Select photo:</label><br/>
    <input type="file" name="photo" id="photo" accept="image/*" required/><br/><br/>
    
    <label for="caption">Caption (optional):</label><br/>
    <textarea name="caption" id="caption" rows="3" cols="40"></textarea><br/><br/>
    
    <input type="submit" value="Upload"/>
</form>

<p><a href="feed">Back to Feed</a></p>
</body>
</html> 