package com.hubo.gillajabi.track.domain.service;

import com.hubo.gillajabi.track.infrastructure.persistence.PhotoPointRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PhotoPointServiceTest {

    @Mock
    private PhotoPointRepository photoPointRepository;

    @InjectMocks
    private PhotoPointService photoPointService;

    public static FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    @Nested
    @DisplayName("포토 포인트 업로드 테스트")
    class PhotoPointUploadTest {

        @Test
        @DisplayName("포토 포인트 업로드 성공 테스트")
        void 포토_포인트_업로드_성공() {
        }

        @Test
        @DisplayName("캐싱된 포토가 없을경우 에러")
        void 캐싱된_포토가_없을_경우_실패(){

        }

        @Test
        @DisplayName("트래킹 중인 코스가 아닐경우 에러")
        void 트래킹_중인_코스가_아닐경우_실패(){

        }

    }

    @Nested
    @DisplayName("포토 포인트 삭제 테스트")
    class PhotoPointDeleteTest{

        @Test
        @DisplayName("포토 포인트 삭제 성공 테스트")
        void 포토_포인트_삭제_성공(){

        }

        @Test
        @DisplayName("포토 포인트 삭제 실패 테스트")
        void 포토_포인트가_없을_경우(){

        }

        @Test
        @DisplayName("트래킹 중인 코스가 아닐경우 에러")
        void 포토_포인트가_트래킹_중인_코스가_아닐경우(){

        }
    }

}
