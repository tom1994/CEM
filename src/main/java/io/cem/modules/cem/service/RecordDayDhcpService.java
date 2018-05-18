package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayDhcpEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDayDhcpService {
	
	RecordDayDhcpEntity queryObject(Integer id);
	
	List<RecordDayDhcpEntity> queryList(Map<String, Object> map);

	/**
	 * 定时任务
	 * @param map
	 * @return List<RecordDayDhcpEntity>
	 */
	List<RecordDayDhcpEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayDhcpEntity recordDayDhcp);
	
	void update(RecordDayDhcpEntity recordDayDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
