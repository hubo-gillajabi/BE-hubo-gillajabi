package com.hubo.gillajabi.crawl.infrastructure.persistence;

import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpxInfoRepository extends JpaRepository<GpxInfo, Long>{

}
