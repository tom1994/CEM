package io.cem.modules.job.dao;

import io.cem.modules.sys.dao.BaseDao;
import io.cem.modules.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
