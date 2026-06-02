package com.ssafy.trip.restcontroller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.service.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController implements RestControllerHelper {
	private final AiService aiService;
	@PostMapping
    public ResponseEntity<?> askAi(@RequestBody String prompt) {
        try {
            String answer = aiService.ask(prompt);
            return ResponseEntity.ok().body(Map.of("response", answer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("AI 응답 실패");
        }
    }
//	@PostMapping
//	public ResponseEntity<?> simpleGeneration(@RequestBody Map<String, String> userInput) {
//        Object result = aiService.ask(userInput.get("message"));
//        return handleSuccess(Map.of("message", result));
//    }
}
