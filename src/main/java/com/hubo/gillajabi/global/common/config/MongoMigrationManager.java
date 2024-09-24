package com.hubo.gillajabi.global.common.config;

import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.PlaceDocument;
import com.opencsv.exceptions.CsvException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;


@Component
@Slf4j
public class MongoMigrationManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void migrateData() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:db/mongo_migration/*.csv");

        for (Resource resource : resources) {
            List<PlaceDocument> placeDocuments = readPlacesFromCsv(resource);
            for (PlaceDocument placeDocument : placeDocuments) {
                // 중복 체크 로직 추가
                boolean exists = mongoTemplate.exists(
                        Query.query(
                                Criteria.where("type").is(placeDocument.getType())
                                        .and("courseId").is(placeDocument.getCourseId())
                        ),
                        PlaceDocument.class
                );

                if (!exists) {
                    mongoTemplate.save(placeDocument);
                }
            }
            System.out.println("데이터 마이그레이션 완료: " + resource.getFilename());
        }
    }

    private List<PlaceDocument> readPlacesFromCsv(Resource resource) throws IOException {
        List<PlaceDocument> placeDocuments = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // 첫줄 표 제목 제거
            for (String[] row : rows) {
                PlaceDocument placeDocument = new PlaceDocument();
                placeDocument.setLocation(new GeoJsonPoint(
                        Double.parseDouble(row[0]),
                        Double.parseDouble(row[1])
                ));
                placeDocument.setName(row[2]);
                placeDocument.setType(PlaceType.fromKoreanName(row[3]));
                placeDocument.setDescription(row[4]);
                placeDocument.setImageUrl(row[5]);
                placeDocument.setMapUrl(row[6]);
                placeDocument.setCourseId(row[7].isEmpty() ? null : Integer.parseInt(row[7]));
                placeDocuments.add(placeDocument);
            }
        } catch (CsvException e) {
            throw new IOException("csv파싱 에러 ", e);
        }
        return placeDocuments;
    }
}