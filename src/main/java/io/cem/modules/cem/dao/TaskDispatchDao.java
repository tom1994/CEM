package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TaskDispatchEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
public interface TaskDispatchDao extends BaseDao<TaskDispatchEntity> {
    List<TaskDispatchEntity> queryDispatchList(Integer id);
    int queryDispatchTotal(Integer id);
}
