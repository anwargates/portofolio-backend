package com.api.portofolio.repositories;

import com.api.portofolio.models.entities.PortoUser;
import com.api.portofolio.models.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<Skills, Long> {

}
