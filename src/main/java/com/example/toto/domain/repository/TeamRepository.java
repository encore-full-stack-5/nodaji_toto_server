package com.example.toto.domain.repository;

import com.example.toto.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository
        extends JpaRepository<Team, Long> {
}
