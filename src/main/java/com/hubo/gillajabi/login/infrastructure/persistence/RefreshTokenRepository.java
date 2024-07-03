package com.hubo.gillajabi.login.infrastructure.persistence;


import com.hubo.gillajabi.login.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
