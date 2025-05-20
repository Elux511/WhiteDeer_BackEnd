package com.WhiteDeer.service;

import com.WhiteDeer.dto.GroupDetailDTO;
import com.WhiteDeer.dto.GroupInfoDTO;
import com.WhiteDeer.dao.GroupInfo;
import java.util.List;


public interface GroupInfoService {
    List<GroupInfoDTO> getJoinedGroups(Long userId);


    List<GroupInfoDTO> getManagedGroups(Long userId);

    List<GroupInfoDTO> searchGroups(String id, String name, String begin, String end);

    List<Long> getManagedGroupIds(Long userId);

    int joinGroup(Long userId, Long groupId);

    Boolean createGroup(String groupName, Long maxMember, String introduction, Long creatorId);

    Boolean deleteGroup( Long userId,Long groupId);

    GroupDetailDTO getGroupDetails(Long groupId);

    Boolean quitGroup(Long userId, Long groupId);

    GroupInfo getGroupInfo(Long groupId);


    String deleteTaskById(long groupId, long taskId);

    void finishTaskById(long groupId, long taskId);
}
