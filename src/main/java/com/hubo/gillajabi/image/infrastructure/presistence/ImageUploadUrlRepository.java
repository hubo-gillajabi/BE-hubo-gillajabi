package com.hubo.gillajabi.image.infrastructure.presistence;

import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import org.springframework.data.repository.CrudRepository;



public interface ImageUploadUrlRepository extends CrudRepository<ImageUploadUrl, String> {
}
