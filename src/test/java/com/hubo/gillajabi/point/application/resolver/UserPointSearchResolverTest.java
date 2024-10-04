package com.hubo.gillajabi.point.application.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.course.domain.entity.CourseBookMark;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseBookMarkRepository;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.login.application.annotation.MemberOnly;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.point.domain.entity.UserPoint;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.point.domain.service.UserPointSearchService;
import com.hubo.gillajabi.point.fixture.UserPointFixture;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointDocumentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hubo.gillajabi.search.infrastructure.config.ElasticsearchConfig;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BeanArbitraryIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assume;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import co.elastic.clients.json.ObjectDeserializer;

import java.util.Objects;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@ActiveProfiles("test")
class UserPointSearchResolverTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPointSearchResolver userPointSearchResolver;

    @Autowired
    private UserPointSearchService userPointSearchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserPointFixture userPointFixture;

    @Autowired
    protected ApplicationContext context;

    @BeforeEach
    void setUp() {
        mongoTemplate.indexOps(UserPointDocument.class).ensureIndex(
                new GeospatialIndex("location")
                        .typed(GeoSpatialIndexType.GEO_2DSPHERE)
                        .named("user_points_location_2dsphere")
        );
    }

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

    @DisplayName("북마크를 해제한 포인트 조회가 성공한다")
    @Test
    void 북마크_없는_유저_포인트_조회_200() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        UserPointDocument userPointDocument = userPointFixture.createUserPointDocument(memberAuthentication.getMember());

        String requestJson = """
                {
                  "query": "query {
                    getUsedPointPreviews(bookmarked: false, cursor: null, limit: 10) {
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """.replace("\n", "").replace("\r", "");

        // when
        String result = mockMvc
                .perform(
                        post("/graphql")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);


        // then
        JsonNode rootNode = objectMapper.readTree(result);
        String responseId = rootNode.path("data")
                .path("getUsedPointPreviews")
                .path("userPointPreviews")
                .get(0)
                .path("id")
                .asText();

        assertThat(responseId).isEqualTo(userPointDocument.getId());
    }

    @DisplayName("로그인 하지 않은 유저가 조회시 실패한다")
    @Test
    void 로그인_하지_않은_유저가_조회시_실패_400() throws Exception {
        // given
        String requestJson = """
                {
                  "query": "query {
                    getUsedPointPreviews(bookmarked: false, cursor: null, limit: 10) {
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """.replace("\n", "").replace("\r", "");

        // when
        String result = mockMvc
                .perform(
                        post("/graphql")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        JsonNode errorsNode = rootNode.path("errors");

        assertThat(errorsNode.isArray()).isTrue();
        assertThat(errorsNode.size()).isEqualTo(1);
        assertThat(errorsNode.get(0).path("message").asText()).isEqualTo("접근이 거부되었습니다.");
    }

    @DisplayName("북마크로 조회시 해당 북마크 코스의 데이터가 없는 경우")
    @Test
    void 북마크로_조회시_해당_북마크_코스의_데이터가_없는_경우() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        UserPointDocument userPointDocument = userPointFixture.createUserPointDocument(memberAuthentication.getMember());
        userPointFixture.createCourseBookMark(memberAuthentication.getMember(), userPointFixture.createAndLoadCourse());


        String requestJson = """
                {
                  "query": "query {
                    getUsedPointPreviews(bookmarked: true, cursor: null, limit: 10) {
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """.replace("\n", "").replace("\r", "");

        // when
        String result = mockMvc
                .perform(
                        post("/graphql")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        JsonNode userPointPreviews = rootNode.path("data")
                .path("getUsedPointPreviews")
                .path("userPointPreviews");

        assertThat(userPointPreviews.size()).isEqualTo(0);
    }

    @DisplayName("북마크로 조회시 해당 북마크 코스의 데이터가 있는 경우")
    @Test
    void 북마크로_조회시_해당_북마크_코스의_포인트가_있는_경우() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        Course course = userPointFixture.createAndLoadCourse();
        UserPointDocument userPointDocument = userPointFixture.createUserPointDocument(memberAuthentication.getMember(), course);
        userPointFixture.createCourseBookMark(memberAuthentication.getMember(), course);

        String requestJson = """
                {
                  "query": "query {
                    getUsedPointPreviews(bookmarked: true, cursor: null, limit: 1) {
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """.replace("\n", "").replace("\r", "");

        // when
        String result = mockMvc
                .perform(
                        post("/graphql")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        Long userPointId = rootNode.path("data")
                .path("getUsedPointPreviews")
                .path("userPointPreviews")
                .get(0)
                .path("userPointId")
                .asLong();

        assertThat(userPointId).isEqualTo(userPointDocument.getUserPointId());
    }

    @DisplayName("기준점으로 조회시 근처에 해당 코스의 유저 포인트의 데이터가 있는경우")
    @Test
    void 기준점_중심으로_조회시_해당_코스의_유저_포인트가_있는경우() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        Course course = userPointFixture.createAndLoadCourse();
        UserPointDocument userPointDocument = userPointFixture.createUserPointDocument(memberAuthentication.getMember(), course);
        userPointFixture.createCourseBookMark(memberAuthentication.getMember(), course);

        // when
        String requestJson = String.format("""
                {
                  "query": "query {
                    getUsedPointPreviewsByCourse(courseId: %d, latitude: 37.123456, longitude: 127.123456, radius: 100.00){
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """, course.getId()).replace("\n", "").replace("\r", "");


        String result = mockMvc
                .perform(
                        post("/graphql")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        Long userPointId = rootNode.path("data")
                .path("getUsedPointPreviewsByCourse")
                .path("userPointPreviews")
                .get(0)
                .path("userPointId")
                .asLong();

        assertThat(userPointId).isEqualTo(userPointDocument.getUserPointId());
    }

    @DisplayName("기준점을 중심으로 조회시 해당 코스 포인트는 있지만 근처에 유저 포인트의 데이터가 없는경우")
    @Test
    void 기준점_중심으로_조회시_해당_코스의_포인트가_존재하지만_근처에_없는_경우() throws Exception {
        // given
        MemberAuthentication memberAuthentication = userPointFixture.createAndLoadMember("testUser", RoleStatus.USER);
        TokenResponse tokenResponse = userPointFixture.createAndLoadTokenResponse(memberAuthentication);
        Course course = userPointFixture.createAndLoadCourse();
        UserPointDocument userPointDocument = userPointFixture.createUserPointDocument(memberAuthentication.getMember(), course);
        userPointFixture.createCourseBookMark(memberAuthentication.getMember(), course);

        // when
        String requestJson = String.format("""
                {
                  "query": "query {
                    getUsedPointPreviewsByCourse(courseId: %d, latitude: 11.00, longitude: 11.00, radius: 1.00){
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """, course.getId()).replace("\n", "").replace("\r", "");


        String result = mockMvc
                .perform(
                        post("/graphql")
                                .header("Authorization", "Bearer " + tokenResponse.accessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        JsonNode userPointPreviews =
                rootNode.path("data")
                        .path("getUsedPointPreviewsByCourse")
                        .path("userPointPreviews");

        assertThat(userPointPreviews.size()).isEqualTo(0);
    }

    @DisplayName("기준점_중심으로_조회시_비회원인_경우_실패")
    @Test
    void 기준점_중심으로_조회시_비회원인_경우() throws Exception {
        // when
        String requestJson = String.format("""
                {
                  "query": "query {
                    getUsedPointPreviewsByCourse(courseId: 1, latitude: 11.00, longitude: 11.00, radius: 1.00){
                      userPointPreviews {
                        id
                        content
                        userPointId
                        course {
                          id
                          name
                        }
                        longitude
                        latitude
                        imageUrl
                        member {
                          id
                          nickname
                          profile
                        }
                      }
                      pageInfo {
                        nextCursor
                        hasNextPage
                      }
                    }
                  }"
                }
                """.replace("\n", "").replace("\r", ""));


        String result = mockMvc
                .perform(
                        post("/graphql")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        JsonNode rootNode = objectMapper.readTree(result);
        JsonNode errorsNode = rootNode.path("errors");

        assertThat(errorsNode.isArray()).isTrue();
        assertThat(errorsNode.size()).isEqualTo(1);
        assertThat(errorsNode.get(0).path("message").asText()).isEqualTo("접근이 거부되었습니다.");
    }


}