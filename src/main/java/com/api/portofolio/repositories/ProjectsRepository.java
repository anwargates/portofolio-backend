package com.api.portofolio.repositories;

import com.api.portofolio.models.entities.Experience;
import com.api.portofolio.models.entities.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}
