package by.bntu.constructor.service;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    String uploadImage(byte[] imageBytes) throws IOException;

    List<String> listImages(int size, String nextCursor) throws Exception;
}
