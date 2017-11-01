package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TaskProbeRelEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public interface TaskProbeRelDao extends BaseDao<TaskProbeRelEntity> {
    List<TaskProbeRelEntity> queryTaskList(Map<String,Object> map);
	
}
