package com.WhiteDeer.cache;

import com.WhiteDeer.dao.GroupInfo;
import java.util.List;
import java.util.Optional;



public interface GroupInfoCache {
    //读缓存
    Optional<GroupInfo> getGroupInfo(String groupId);

    void saveGroupInfo(GroupInfo groupInfo);


}
