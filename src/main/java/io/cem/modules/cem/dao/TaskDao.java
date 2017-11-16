package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TaskEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
public interface TaskDao extends BaseDao<TaskEntity> {
    List<TaskEntity> queryTaskList(Map<String,Object> map);
}
