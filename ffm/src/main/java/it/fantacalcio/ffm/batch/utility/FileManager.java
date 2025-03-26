package it.fantacalcio.ffm.batch.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class FileManager {
    public String copyToInDirectory(MultipartFile file, String directory) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        Path filePath = Paths.get(directory, fileName);

        if (Files.exists(filePath)) {
            throw new FileAlreadyExistsException("File " + fileName + " already exists");
        }

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    public void deleteFile(String filePath) {
        if(filePath != null){
            try {
                Path path = Paths.get(filePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("File deleted: " + filePath);
                } else {
                    System.out.println("File not found: " + filePath);
                }
            } catch (Exception e) {
                System.out.println("Exception deleteFile: " + e);
            }
        }
    }
}
