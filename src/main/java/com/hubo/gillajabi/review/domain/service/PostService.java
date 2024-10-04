package com.hubo.gillajabi.review.domain.service;

import com.hubo.gillajabi.course.domain.entity.CourseTag;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.Tag;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.TagRepository;
import com.hubo.gillajabi.global.common.dto.PageInfo;
import com.hubo.gillajabi.image.domain.entity.ImageGpsInfo;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageGpsInfoRepository;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.review.application.dto.request.PostWriteRequest;
import com.hubo.gillajabi.review.application.dto.response.PostPreviewResponse;
import com.hubo.gillajabi.review.application.dto.response.PostResponse;
import com.hubo.gillajabi.review.domain.entity.Post;
import com.hubo.gillajabi.review.domain.entity.PostSearchDocument;
import com.hubo.gillajabi.review.domain.entity.PostTag;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseBookMarkRepository;
import com.hubo.gillajabi.review.infrastructure.dto.response.PostPreview;
import com.hubo.gillajabi.review.infrastructure.exception.PostException;
import com.hubo.gillajabi.review.infrastructure.exception.PostExceptionCode;
import com.hubo.gillajabi.review.infrastructure.persistence.PostRepository;
import com.hubo.gillajabi.review.infrastructure.persistence.PostSearchRepository;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final TrackRecordRepository trackRecordRepository;

    private final ImageGpsInfoRepository imageGpsInfoRepository;

    private final CourseRepository courseRepository;

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    private final CourseBookMarkRepository courseBookMarkRepository;

    private final PostSearchRepository postSearchRepository;

    private final EntityManager entityManager;

    @Transactional
    public void writePost(final String userName, final PostWriteRequest request) {
        Member member = memberRepository.getEntityByUserName(userName);
        boolean existsById = postRepository.existsByTrackRecordId(request.trackRecordId());

        if (existsById) {
            throw new PostException(PostExceptionCode.ALREADY_POST);
        }

        TrackRecord trackRecord = trackRecordRepository.getEntityById(request.trackRecordId());
        trackRecord.checkTrackOwner(member);

        Course course = getCourse(trackRecord);

        Set<PhotoPoint> photoPoints = createPhotoPoints(request.getImageUrls(), trackRecord);
        trackRecord.addPhotoPoints(photoPoints);

        List<PostTag> courseTags = createCourseTags(request.tags(), course);

        // TODO : 매일 아침 태그 상위 5개 지정 배치 잡 코스에 등록
        Post post = createPost(request, trackRecord, courseTags);
        entityManager.persist(post);

        PostSearchDocument postSearchDocument = PostSearchDocument.fromPost(post);
        postSearchRepository.save(postSearchDocument);

        postRepository.save(post);
    }

    private Course getCourse(TrackRecord trackRecord) {
        return Optional.ofNullable(trackRecord.getCourse())
                .orElseThrow(() -> new EntityNotFoundException("코스가 없습니다. 코스를 먼저 만들어주세요."));
    }

    private Set<PhotoPoint> createPhotoPoints(List<String> imageUrls, TrackRecord trackRecord) {
        Set<PhotoPoint> photoPoints = new HashSet<>();
        for (String imageUrl : imageUrls) {
            imageGpsInfoRepository.findById(ImageGpsInfo.getGpsInfoKey(imageUrl))
                    .ifPresent(imageGpsInfo ->
                            photoPoints.add(PhotoPoint.createByImageGpsInfo(trackRecord, imageGpsInfo))
                    );
        }
        return photoPoints;
    }

    private List<PostTag> createCourseTags(final List<String> tagNames, final Course course) {
        final List<PostTag> courseTags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = Tag.createTag(tagName);
                        return tagRepository.save(newTag);
                    });

            final PostTag postTag = PostTag.createPostTag(tag);
            courseTags.add(postTag);

            final CourseTag courseTag = CourseTag.createCourseTag(course, tag);
            course.addCourseTag(courseTag);
            courseRepository.save(course);
        }
        return courseTags;
    }

    private Post createPost(final PostWriteRequest request, final TrackRecord trackRecord, final List<PostTag> courseTags) {
        final Post post = Post.fromPostWriteRequest(request, trackRecord);
        post.addCourseTags(courseTags);
        post.addCourseRatting(request.star());
        return post;
    }

    @Transactional
    public void deletePost(final String userName, final Long postId) {
        Member member = memberRepository.getEntityByUserName(userName);

        Post post = postRepository.getPostEntityById(postId);
        post.checkPostOwner(member);

        post.changeStatusToDeleted();
        postSearchRepository.deleteById(String.valueOf(postId));

        postRepository.save(post);
    }

    public PostPreviewResponse getPostPreviews(final String keyword, final String username, final Boolean bookmarked, final Long page, final Long size) {
        final Member member = memberRepository.getEntityByUserName(username);
        final Pageable pageable = PageRequest.of(page.intValue(), size.intValue());

        Page<PostSearchDocument> searchResults;
        List<Long> bookmarkedCourseIds = courseBookMarkRepository.findCourseIdsByMember(member);

        if (Boolean.TRUE.equals(bookmarked)) {
            List<String> bookmarkedCourseIdStrings = bookmarkedCourseIds.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            searchResults = searchBookmarkedPosts(keyword, bookmarkedCourseIdStrings, pageable);
        } else {
           searchResults = searchAllPosts(keyword, pageable);
        }

        List<PostPreview> postPreviews = searchResults.getContent().stream()
                .map(doc -> PostPreview.fromPostSearchDocument(doc, bookmarkedCourseIds))
                .collect(Collectors.toList());
        return new PostPreviewResponse(postPreviews, PageInfo.from(searchResults));
    }


    private Page<PostSearchDocument> searchBookmarkedPosts(String keyword, List<String> bookmarkedCourseIds, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return postSearchRepository.searchPostsWithBookmarkFilter(keyword, bookmarkedCourseIds, pageable);
        } else {
            return postSearchRepository.getBookmarkedPosts(bookmarkedCourseIds, pageable);
        }
    }

    private Page<PostSearchDocument> searchAllPosts(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return postSearchRepository.searchPosts(keyword, pageable);
        } else {
            return postSearchRepository.getAllPosts(pageable);
        }
    }

    public PostResponse getPost(String username, Long postId) {
        Member member = memberRepository.getEntityByUserName(username);
        Post post = postRepository.getPostEntityById(postId);

        boolean bookmarked = false;
        if(member != null){
            bookmarked = courseBookMarkRepository.existsByMemberAndCourse(member, post.getCourse());
        }

        return PostResponse.createByEntity(post, bookmarked);
    }
}
