package com.example.toto;

import com.example.toto.domain.entity.Team;

public class TestTeamInit {
    public final Team teamA;
    public final Team teamB;
    public final Team teamC;
    public final Team teamD;

    public TestTeamInit() {
        teamA = new Team(1L, "teamA", null, null);
        teamB = new Team(2L, "teamB", null, null);
        teamC = new Team(3L, "teamC", null, null);
        teamD = new Team(4L, "teamD", null, null);
    }
}
