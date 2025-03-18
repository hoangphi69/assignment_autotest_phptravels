package utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {
  private static final String BASE_PATH = "src/test/java/";

  public static JsonNode getTestData(String fileName, String key) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      File file = new File(BASE_PATH + fileName);
      JsonNode data = mapper.readTree(file);
      return data.get(key);
    } catch (IOException e) {
      System.err.println("Lỗi đọc file: " + e.getMessage());
      return null;
    }
  }
}