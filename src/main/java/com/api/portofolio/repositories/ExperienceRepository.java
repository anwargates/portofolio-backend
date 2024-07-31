package com.api.portofolio.repositories;

import com.api.portofolio.models.entities.Experience;
import com.api.portofolio.models.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

}
