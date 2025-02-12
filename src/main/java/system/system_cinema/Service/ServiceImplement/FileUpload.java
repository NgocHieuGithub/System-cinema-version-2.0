package system.system_cinema.Service.ServiceImplement;

import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.Service.IFileUpload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUpload implements IFileUpload {

    Cloudinary cloudinary;

    @Override
    public List<String> uploadFile(MultipartFile multipartFile) throws IOException {
        Map<?,?> uploadResult = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of(
                                "public_id", UUID.randomUUID().toString(),
                                "folder", "System_Cinema"));
        List<String> list = new ArrayList<>();
        list.add(uploadResult.get("url").toString());
        list.add(uploadResult.get("public_id").toString());
        return list;
    }

    @Override
    public String upDateFile(MultipartFile multipartFile, String public_id) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of( "public_id", public_id,
                                "invalidate", true))
                .get("url")
                .toString();
    }
}
