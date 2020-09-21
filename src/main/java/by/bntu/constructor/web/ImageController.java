package by.bntu.constructor.web;

import by.bntu.constructor.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService imageService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<String> getImages(@RequestParam(name = "size", defaultValue = "15", required = false) Integer size,
                                  @RequestParam(name = "next_cursor", required = false) String nextCursor) throws Exception {
        return imageService.listImages(size, nextCursor);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload")
    public String uploadImage(@RequestParam MultipartFile file) throws IOException {
        return imageService.uploadImage(file.getBytes());
    }


}