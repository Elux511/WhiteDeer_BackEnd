package com.WhiteDeer.service;

import com.WhiteDeer.dto.GroupInfoDTO;
import com.WhiteDeer.dto.TeamDetailDTO;

import java.util.List;


public interface GroupInfoService {
    List<GroupInfoDTO> getJoinedGroups(Long userId);

    List<GroupInfoDTO> getManagedGroups(Long userId);

    List<GroupInfoDTO> searchGroups(String id, String name, String begin, String end);

    Boolean joinTeam(Long userId, Long teamId);

    Boolean createTeam(String groupname, Long maxmember, String introduction, Long creatorId);

    Boolean deleteGroup( Long userId,Long groupId);

    TeamDetailDTO getTeamDetails(Long teamId);

    Boolean quitTeam(Long userId, Long teamId);
}
