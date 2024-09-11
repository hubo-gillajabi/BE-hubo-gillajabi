package com.hubo.gillajabi.review.domain.entity;

import com.hubo.gillajabi.city.application.dto.response.CityPreviewDTO;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.course.application.dto.response.CoursePreview;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.global.dto.ImageDTO;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.review.infrastructure.dto.response.MemberPreview;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Setting(settingPath = "elasticsearch-settings.json")
@Document(indexName = "posts")
public class PostSearchDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "my_nori_analyzer")
    private String title;

    @Field(type = FieldType.Keyword, index = false)
    private List<ImageDocument> images;

    @Field(type = FieldType.Long, index = false)
    private Long step;

    @Field(type = FieldType.Double, index = false)
    private BigDecimal distance;

    @Field(type = FieldType.Long, index = false)
    private Long calorie;

    @Field(type = FieldType.Nested)
    private MemberDocument member;

    @Field(type = FieldType.Integer, index = false)
    private Integer courseRating;

    @Field(type = FieldType.Date, index = false)
    private LocalDate createdTime;

    @Field(type = FieldType.Nested)
    private CourseDocument course;

    @Field(type = FieldType.Nested)
    private CityDocument city;


    public static PostSearchDocument fromPost(Post post) {
        PostSearchDocument postSearchDocument = new PostSearchDocument();
        postSearchDocument.setId(post.getId());
        postSearchDocument.setTitle(post.getTitle());
        postSearchDocument.setImages(post.getTrackRecord().getPhotoPoints().stream()
                .map(ImageDocument::fromPhotoPoint).collect(Collectors.toList()));
        postSearchDocument.setStep(post.getTrackRecord().getStep());
        postSearchDocument.setDistance(post.getTrackRecord().getDistance());
        postSearchDocument.setCalorie(post.getTrackRecord().getCalorie());
        postSearchDocument.setMember(MemberDocument.fromMember(post.getMember()));
        postSearchDocument.setCourseRating(post.getCourseRating());
        postSearchDocument.setCreatedTime(post.getCreatedTime().toLocalDate());
        postSearchDocument.setCourse(CourseDocument.fromCourse(post.getTrackRecord().getCourse()));
        postSearchDocument.setCity(CityDocument.fromCity(post.getTrackRecord().getCourse().getCity()));
        return postSearchDocument;
    }


    public List<ImageDTO> getImageDTOs() {
        return this.images.stream()
                .map(image -> new ImageDTO(image.getImageUrl(), image.getLatitude(), image.getLongitude()))
                .collect(Collectors.toList());
    }

    public CoursePreview getCoursePreview() {
        System.out.println("this.course.getId() = " + this.course.getId());
        return new CoursePreview(Long.parseLong(this.course.getId()), this.course.getName());
    }

    public CityPreviewDTO getCityPreview() {
        return new CityPreviewDTO(Long.parseLong(this.city.getId()), this.city.getName(), Province.fromValue(this.city.getProvince()));
    }

    public MemberPreview getMemberPreview() {
        return new MemberPreview(this.member.getNickName(), this.member.getProfileImageUrl());
    }
}

@Getter
@Setter
class CourseDocument {
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text, analyzer = "my_nori_analyzer")
    private String name;

    public static CourseDocument fromCourse(Course course) {
        CourseDocument courseDocument = new CourseDocument();
        courseDocument.setId(String.valueOf(course.getId()));
        courseDocument.setName(course.getOriginName());
        return courseDocument;
    }
}

@Getter
@Setter
class CityDocument {
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text, analyzer = "my_nori_analyzer")
    private String name;

    @Field(type = FieldType.Keyword, index = false)
    private String province;

    public static CityDocument fromCity(City city) {
        CityDocument cityDocument = new CityDocument();
        cityDocument.setId(String.valueOf(city.getId()));
        cityDocument.setName(city.getName());
        cityDocument.setProvince(city.getProvince().getValue());
        return cityDocument;
    }
}

@Getter
@Setter
class MemberDocument {
    @Field(type = FieldType.Text, analyzer = "my_nori_analyzer")
    private String nickName;

    @Field(type = FieldType.Keyword)
    private String profileImageUrl;

    public static MemberDocument fromMember(Member member) {
        MemberDocument memberDocument = new MemberDocument();
        memberDocument.setNickName(member.getNickName());
        memberDocument.setProfileImageUrl(member.getProfileImageUrl());
        return memberDocument;
    }
}

@Getter
@Setter
class ImageDocument {

    @Field(type = FieldType.Keyword, index = false)
    private String id;

    @Field(type = FieldType.Keyword, index = false)
    private BigDecimal latitude;

    @Field(type = FieldType.Keyword, index = false)
    private BigDecimal longitude;

    @Field(type = FieldType.Keyword, index = false)
    private String imageUrl;

    public static ImageDocument fromPhotoPoint(PhotoPoint photoPoint) {
        ImageDocument imageDocument = new ImageDocument();
        imageDocument.setId(String.valueOf(photoPoint.getId()));
        imageDocument.setLatitude(photoPoint.getLatitude());
        imageDocument.setLongitude(photoPoint.getLongitude());
        imageDocument.setImageUrl(photoPoint.getPhotoUrl());
        return imageDocument;
    }
}

