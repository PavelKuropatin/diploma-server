package by.bntu.constructor.service.impl;

import by.bntu.constructor.service.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    @Override
    @SuppressWarnings("unchecked")
    public String uploadImage(byte[] imageBytes) throws IOException {
        Map<String, String> map = null;
        File file = new File("img");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(imageBytes);
        fileOutputStream.flush();
        fileOutputStream.close();
        map = cloudinary.uploader().upload(file, new HashMap());
        return map.get("url");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> listImages(int size, String nextCursor) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("resource_type", "image");
        map.put("max_results", size);
        if (StringUtils.isNotBlank(nextCursor)) {
            map.put("next_cursor", nextCursor);
        }
        ApiResponse response = cloudinary.api().resources(map);
        List<Map<String, Object>> images = (List<Map<String, Object>>) response.get("resources");
        return images.stream()
                .map(i -> i.get("url").toString())
                .collect(Collectors.toList());
    }
}
