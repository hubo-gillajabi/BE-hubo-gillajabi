package com.hubo.gillajabi.global.common.config;

import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.Place;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;


@Component
public class MongoMigrationManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void migrateData() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:db/mongo_migration/*.csv");

        for (Resource resource : resources) {
            List<Place> places = readPlacesFromCsv(resource);
            for (Place place : places) {
                // 중복 체크 로직 추가
                boolean exists = mongoTemplate.exists(
                        Query.query(
                                Criteria.where("type").is(place.getType())
                                        .and("courseId").is(place.getCourseId())
                        ),
                        Place.class
                );

                if (!exists) {
                    mongoTemplate.save(place);
                }
            }
            System.out.println("mongodb data migration : " + resource.getFilename());
        }
    }

    private List<Place> readPlacesFromCsv(Resource resource) throws IOException {
        List<Place> places = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // 첫줄 표 제목 제거
            for (String[] row : rows) {
                Place place = new Place();
                place.setLocation(new double[]{
                        Double.parseDouble(row[0]), // longitude
                        Double.parseDouble(row[1])  // latitude
                });
                place.setName(row[2]);
                place.setType(PlaceType.fromKoreanName(row[3]));
                place.setDescription(row[4]);
                place.setImageUrl(row[5]);
                place.setMapUrl(row[6]);
                place.setCourseId(row[7].isEmpty() ? null : Integer.parseInt(row[7]));
                places.add(place);
            }
        } catch (CsvException e) {
            throw new IOException("csv파싱 에러 ", e);
        }
        return places;
    }
}