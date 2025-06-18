package dd.projects.ddshop.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatbotRequestDTO {

    private String model;
    private String input;
}
