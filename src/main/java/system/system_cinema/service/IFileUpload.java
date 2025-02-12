package system.system_cinema.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileUpload {
    List<String> uploadFile(MultipartFile multipartFile) throws IOException;
    String upDateFile(MultipartFile multipartFile, String id) throws IOException;
}
