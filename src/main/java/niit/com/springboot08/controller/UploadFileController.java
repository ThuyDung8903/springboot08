package niit.com.springboot08.controller;

import niit.com.springboot08.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

@Controller("uploadFileController")
public class UploadFileController {
    @Value("${UPLOAD_DIR}")
    public String UPLOAD_DIR;

    @GetMapping("/upload-file")
    public String uploadFile(Model model) {
        //Show all images
        ArrayList<Image> images = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/springboot08", "root", "");
            String sql = "SELECT * FROM images";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Image image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setName(resultSet.getString("name"));
                image.setPath(resultSet.getString("path"));
                images.add(image);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("images", images);
        return "upload-file";
    }

    @PostMapping("/upload-file-handle")
    public String uploadFileHandle(@RequestParam("image") MultipartFile file) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/springboot08", "root", "");
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            String newDIR = month + "_" + year;
            File folder = new File(UPLOAD_DIR + newDIR);
            if (!folder.exists() || folder.isFile()) {
                folder.mkdir();
            }
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_DIR + newDIR + "\\" + fileName);
            String sql = "INSERT INTO images (name, path) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, file.getOriginalFilename());
            preparedStatement.setString(2, newDIR + "/" + fileName);
            preparedStatement.executeUpdate();
            fileOutputStream.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:upload-file";
    }

    @GetMapping("/serve-file")
    public ResponseEntity<byte[]> serveFile(@RequestParam("file-name") String filename) {
        try {
            FileInputStream fileInputStream = new FileInputStream(UPLOAD_DIR + filename);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
