package com.hubo.gillajabi.point.application.presenation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.point.domain.service.UserPointService;
import com.hubo.gillajabi.point.fixture.UserPointFixture;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointDocumentRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@ActiveProfiles("test")
class UserPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPointFixture userPointFixture;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void cleanupAfterTest() {
        mongoTemplate.getCollectionNames().forEach(collectionName -> {
            mongoTemplate.dropCollection(collectionName);
        });

        RedisServerCommands serverCommands = Objects.requireNonNull(redisTemplate.getConnectionFactory())
                .getConnection()
                .serverCommands();
        serverCommands.flushAll();
    }

    @DisplayName("사용자 포인트 저장이 성공한다")
    @Test
    void 사용자_포인트_저장_성공() throws Exception {
        // given
        String requestJson = """
                {
                  "content": "여기는 좋은 장소입니다.",
                  "latitude": 37.5665,
                  "longitude": 126.9780,
                  "imageUrls": [
                    "https://example.com/image1.jpg"
                  ]
                }
                """.replace("\n", "").replace("\r", "");
        ImageUploadUrl imageUploadUrl = userPointFixture.createImageUploadUrl("https://example.com/image1.jpg");

        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        userPointFixture.startTrack(memberAuthentication.getMember(), userPointFixture.createAndLoadCourse(), imageUploadUrl);

        // when
        mockMvc.perform(
                        post("/api/user-point")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());

        // then
        List<UserPointDocument> userPointDocuments = userPointFixture.getRepository(UserPointDocumentRepository.class).findAll();
        assertThat(userPointDocuments).hasSize(1);
        assertThat(userPointDocuments.get(0).getContent()).isEqualTo("여기는 좋은 장소입니다.");
    }

    @DisplayName("사용자 포인트의 이미지가 없을경우")
    @Test
    void 사용자의_포인트_저장_요청에서_이미지가_없을경우_() throws Exception {
        // given
        String requestJson = """
                {
                  "content": "여기는 좋은 장소입니다.",
                  "latitude": 37.5665,
                  "longitude": 126.9780,
                  "imageUrls": [
                    "https://example.com/image1.jpg"
                  ]
                }
                """.replace("\n", "").replace("\r", "");
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);

        // when
        String result = mockMvc.perform(
                        post("/api/user-point")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        assertThat(rootNode.get("message").asText()).isEqualTo("유효하지 않은 이미지입니다.");
    }

    @DisplayName("트래킹 중이 아닌경우 실패")
    @Test
    void 사용자가_트래킹_중이_아닌_경우() throws Exception {
        // given
        String requestJson = """
                {
                  "content": "여기는 좋은 장소입니다.",
                  "latitude": 37.5665,
                  "longitude": 126.9780,
                  "imageUrls": [
                    "https://example.com/image1.jpg"
                  ]
                }
                """.replace("\n", "").replace("\r", "");
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        ImageUploadUrl imageUploadUrl = userPointFixture.createImageUploadUrl("https://example.com/image1.jpg");

        // when
        String result = mockMvc.perform(
                        post("/api/user-point")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        assertThat(rootNode.get("message").asText()).isEqualTo("현재 트래킹중이 아닙니다.");
    }

    @DisplayName("사용자 포인트 삭제가 성공한다")
    @Test
    void 사용자_포인트_삭제_성공() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        UserPointDocument userPointDocument = userPointFixture.createAndLoadUserPointDocument(memberAuthentication.getMember());

        // when
        mockMvc.perform(
                        delete("/api/user-point/{id}", userPointDocument.getUserPointId())
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        // then
        List<UserPointDocument> userPointDocuments = userPointFixture.getRepository(UserPointDocumentRepository.class).findAll();
        assertThat(userPointDocuments).isEmpty();
    }

    @DisplayName("사용자 포인트 삭제 시 해당 사용자의 포인트가 없을 경우 실패한다")
    @Test
    void 사용자_포인트_삭제시_해당_사용자의_포인트가_없을_경우_실패한다() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);

        // when
        String result = mockMvc.perform(
                        delete("/api/user-point/{id}", 1L)
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        assertThat(rootNode.get("message").asText()).isEqualTo("포인트를 찾을 수 없습니다.");
    }

    @DisplayName("사용자 포인트 삭제시 해당 사용자의 포인트가 아닐 경우 실패한다")
    @Test
    void 사용자_포인트_삭제시_해당_사용자의_포인트가_아닐_경우_실패() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        UserPointDocument userPointDocument = userPointFixture.createAndLoadUserPointDocument(memberAuthentication.getMember());
        MemberAuthentication otherMemberAuthentication = userPointFixture.createAndLoadMember("otherUser", RoleStatus.USER);
        TokenResponse otherTokenResponse = userPointFixture.createAndLoadTokenResponse(otherMemberAuthentication);

        // when
        String result = mockMvc.perform(
                        delete("/api/user-point/{id}", userPointDocument.getUserPointId())
                                .header("Authorization", "Bearer " + otherTokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        assertThat(rootNode.get("message").asText()).isEqualTo("포인트 소유자가 아닙니다.");
    }
}

