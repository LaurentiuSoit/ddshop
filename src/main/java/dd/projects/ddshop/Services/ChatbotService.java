package dd.projects.ddshop.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dd.projects.ddshop.DTOs.ChatbotRequestDTO;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Repositories.ProductDao;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChatbotService {

    @Value("${openai.responses.uri}")
    private String openAiURI;

    @Value("${openai.api_key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDao productDao;

    private final RestClient restClient;

    public ChatbotService(ProductDao productDao) {
        this.restClient = RestClient.create();
        this.productDao = productDao;
    }

    public ResponseEntity<String> retrieveChatbotAnswer(String input) {
        try {
            if (!StringUtils.isBlank(input)) {
                List<Product> products = productDao.findAll();
                String productJson = objectMapper.writeValueAsString(products);
                String formattedInput =
                    "You are a chatbot helping users browse an e-commerce store focused on medieval/military equipment, " +
                    "but you can also help them with real historical knowledge if asked. Google if needed. " +
                    "Don't write overly long answers, they should fit in a chat window. " +
                    "If the question isn't related to the website, the chatbot or the theme of medieval/military equipment," +
                    "answer with \"I am not able to answer that. \" " +
                    "Be entirely and completely truthful." +
                    "Here is the user's question : " +
                    "\"" +
                    input +
                    "\"." +
                    "Here is the product list. Use it only if needed : " +
                    productJson;

                String responseJson = restClient
                    .post()
                    .uri(openAiURI)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .body(new ChatbotRequestDTO(model, formattedInput))
                    .retrieve()
                    .body(String.class);

                JsonNode root = objectMapper.readTree(responseJson);
                String text = root
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();
                if (!StringUtils.isBlank(text)) {
                    return new ResponseEntity<>(text, HttpStatus.OK);
                }
                return new ResponseEntity<>("Response invalid.", HttpStatus.BAD_GATEWAY);
            }
            return new ResponseEntity<>("Bad request.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
