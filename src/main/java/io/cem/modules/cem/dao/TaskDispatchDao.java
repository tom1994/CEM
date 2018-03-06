package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/***
 */
public interface TaskDispatchDao extends BaseDao<TaskDispatchEntity> {
    List<TaskDispatchEntity> queryDispatchList(Map<String, Object> map);

    int queryDispatchTotal(Integer id);

    int queryTestStatus(Integer id);

    void saveAll(List<TaskDispatchEntity> taskDispatchEntityList);

    void cancelTask(Integer id);

    String[] queryTargetBatch(String[] targetIdList);

    List<TaskDispatchEntity> taskQueryDispatchList(Map<String, Object> map);

    int taskQueryDispatchTotal(Map<String, Object> map);

    int saveAndReturn(TaskDispatchEntity taskDispatchEntity);
}
