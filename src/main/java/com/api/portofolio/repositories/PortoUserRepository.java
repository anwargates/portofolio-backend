package com.api.portofolio.repositories;

import com.api.portofolio.models.entities.PortoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface PortoUserRepository extends JpaRepository<PortoUser, Long> {

    Optional<PortoUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
