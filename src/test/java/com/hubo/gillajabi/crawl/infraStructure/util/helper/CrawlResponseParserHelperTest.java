package com.hubo.gillajabi.crawl.infraStructure.util.helper;

import com.hubo.gillajabi.crawl.infrastructure.util.helper.CrawlResponseParserHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlResponseParserHelperTest {

    @Test
    @DisplayName("경남 김해시 -> 김해시로 파싱")
    public void 경남_김해시를_김해시로_파싱_성공() {
        // Given
        String input = "경남 김해시";
        String expected = "김해시";

        // When
        String result = CrawlResponseParserHelper.parseDuruResponseByCity(input);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("경남김해시 공백이 없을경우 -> IllegalArgumentException 에러 발생")
    public void 공백이_없는_지역명일때_파싱_실패() {
        // Given
        String input = "경남김해시"; // 공백이 없음

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CrawlResponseParserHelper.parseDuruResponseByCity(input);
        });

        // Then
        String expectedMessage = "잘못된 포맷 형식 :  " + input;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("경남 김해시 -> 경남으로 파싱")
    public void 경남_김해시_에서_김해시_파싱_성공() {
        // Given
        String input = "경남 김해시";
        String expected = "경남";

        // When
        String result = CrawlResponseParserHelper.parseDuruResponseByProvince(input);

        // Then
        assertEquals(expected, result);
    }
}
