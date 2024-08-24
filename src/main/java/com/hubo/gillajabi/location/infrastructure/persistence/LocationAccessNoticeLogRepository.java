package com.hubo.gillajabi.location.infrastructure.persistence;

import com.hubo.gillajabi.location.domain.service.entity.LocationAccessNoticeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationAccessNoticeLogRepository extends JpaRepository<LocationAccessNoticeLog, Long> {
}
