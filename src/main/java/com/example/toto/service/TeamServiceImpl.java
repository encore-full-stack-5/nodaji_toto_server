package com.example.toto.service;

import com.example.toto.domain.entity.Team;
import com.example.toto.domain.repository.TeamRepository;
import com.example.toto.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;

    @Override
    public Team findById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("TEAM"));
    }
}
