package com.my.instagram_clone.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.my.instagram_clone.dao.PhotoDAO;
import com.my.instagram_clone.dao.PhotoTagDAO;
import com.my.instagram_clone.dao.TagDAO;
import com.my.instagram_clone.model.User;
import com.my.instagram_clone.model.Tag;

@WebServlet("/uploadPhoto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
                 maxFileSize = 1024 * 1024 * 10,       // 10MB
                 maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class UploadPhotoServlet extends HttpServlet {

    private PhotoDAO photoDAO;
    private TagDAO tagDAO;

    @Override
    public void init() {
        photoDAO = new PhotoDAO();
        tagDAO = new TagDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect GET requests to upload page or feed page
        response.sendRedirect("upload.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loggedInUser = (User) session.getAttribute("user");

        Part filePart = request.getPart("photo");
        String caption = request.getParameter("caption");

        // Validate file
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";

        // Create uploads directory if it doesn't exist
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        // Create unique file name to avoid collisions
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadDir + File.separator + uniqueFileName;

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "File upload failed.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        // Save photo info in DB - image_url is relative path: "uploads/uniqueFileName"
        String imageUrl = "uploads/" + uniqueFileName;

     // inside your doPost after saving photo

        int photoId = photoDAO.savePhoto(loggedInUser.getId(), imageUrl, caption);

        if (photoId > 0) {
            List<String> tags = extractTags(caption);
            PhotoTagDAO photoTagDAO = new PhotoTagDAO();

            for (String tagName : tags) {
                Tag tag = tagDAO.insertTagIfNotExists(tagName);
                if (tag != null && tag.getId() > 0) {
                    photoTagDAO.insertPhotoTag(photoId, tag.getId());
                }
            }
            response.sendRedirect("feed");
        } else {
            request.setAttribute("error", "Photo upload failed in DB.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
        }
    }

    // Extract hashtags (#tag) from caption text
    private List<String> extractTags(String caption) {
        List<String> tags = new ArrayList<>();
        if (caption != null && !caption.isEmpty()) {
            Pattern pattern = Pattern.compile("#(\\w+)");
            Matcher matcher = pattern.matcher(caption);
            while (matcher.find()) {
                tags.add(matcher.group(1).toLowerCase());
            }
        }
        return tags;
    }
}
