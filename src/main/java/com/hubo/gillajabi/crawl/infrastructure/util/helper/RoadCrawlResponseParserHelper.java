package com.hubo.gillajabi.crawl.infrastructure.util.helper;

public class RoadCrawlResponseParserHelper {


    /**
     * 두루누비 cityParser ("경남 김해시")에서 "김해시"를 추출
     *
     * @param sigun 두루누비에서 받은 문자열 ("경남 김해시")
     * @return "경남 김해시"에서 추출한 "김해시"
     * @throws IllegalArgumentException 잘못된 형식일 경우 던지는 예외
     */
    public static String parseDuruResponseByCity(String sigun) {

        String[] parts = sigun.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 포맷 형식 :  " + sigun);
        }

        return parts[1];
    }

    /**
     * 두루누비 cityParser ("경남 김해시")에서 "경남"을 추출
     *
     * @param sigun 두루누비에서 받은 문자열 ("경남 김해시")
     * @return "경남 김해시"에서 추출한 "경남"
     * @throws IllegalArgumentException 잘못된 형식일 경우 던지는 예외
     */
    public static String parseDuruResponseByProvince(String sigun) {
        String[] parts = sigun.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 포맷 형식 :  " + sigun);
        }

        return parts[0];
    }

}
