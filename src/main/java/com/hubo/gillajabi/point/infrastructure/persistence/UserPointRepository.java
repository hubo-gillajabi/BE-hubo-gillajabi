package com.hubo.gillajabi.point.infrastructure.persistence;

import com.hubo.gillajabi.point.domain.entity.UserPoint;
import com.hubo.gillajabi.point.infrastructure.exception.UserPointException;
import com.hubo.gillajabi.point.infrastructure.exception.UserPointExceptionCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

    default UserPoint getEntityById(Long id) {
        if(id != null) {
            return findById(id).orElseThrow(() -> new UserPointException(UserPointExceptionCode.NOT_FOUND_USER_POINT));
        }
        return null;
    }

}
