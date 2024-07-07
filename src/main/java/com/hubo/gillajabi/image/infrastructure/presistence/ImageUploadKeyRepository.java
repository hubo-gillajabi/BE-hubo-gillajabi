package com.hubo.gillajabi.image.infrastructure.presistence;

import com.hubo.gillajabi.image.domain.entity.ImageUploadKey;
import org.springframework.data.repository.CrudRepository;

public interface ImageUploadKeyRepository extends CrudRepository<ImageUploadKey, String>{
}
