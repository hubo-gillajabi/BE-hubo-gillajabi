package com.hubo.gillajabi.admin.infrastructure.presistence;

import com.hubo.gillajabi.admin.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String>{
}
