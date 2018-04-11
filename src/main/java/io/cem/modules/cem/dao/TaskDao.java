package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TaskEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface TaskDao extends BaseDao<TaskEntity> {
    List<TaskEntity> queryTaskList(Map<String,Object> map);
    int queryExist(String taskName);
    List<TaskEntity> infoByService(Integer servicetype);
}
