package com.ssafy.trip.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

	private final ChatClient simpleChatClient;
	
    public String ask(String userInput) { 
   	 var responseSpec = simpleChatClient.prompt().system(spec -> spec.param("language", "korean")
   			 .param("character", "chill")).user(userInput).call();
   	 return responseSpec.content();
//   	 return responseSpec.chatResponse();
    } 
}