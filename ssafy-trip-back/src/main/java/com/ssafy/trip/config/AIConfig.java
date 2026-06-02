package com.ssafy.trip.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import redis.clients.jedis.JedisPooled;

@Configuration
public class AIConfig {

	String systemPrompt = """
            너는 이제부터 입력된 정보에 따른 최적의 관광지를 추천해주는 가이드야.
            다음 입력 정보에 따라 현재 여행지 근처 추천 장소를 다음 포맷에 맞게 보여줘.
            입력은 {{현재 여행지, 관광지 종류, 예상 비용, 추천 장소 개수}}로 주어질 거야. 다음 포맷에 맞게 출력해줘.
            1. 추천 관광지 이름
            2. 주소
            3. 예상 소모 비용
            4. 한줄 설명
        """;

    @Bean
    ChatClient simpleChatClient(ChatClient.Builder builder) {
        // TODO: 02. ChatClient 타입의 빈을 생성해보자.
         return builder.defaultSystem(systemPrompt)
        		 .defaultAdvisors(new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE-1)).build();
         
        //END
    }

}
