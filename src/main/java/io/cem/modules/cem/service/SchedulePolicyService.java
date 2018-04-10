package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.SchedulePolicyEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface SchedulePolicyService {
	
	SchedulePolicyEntity queryObject(Integer id);
	
	List<SchedulePolicyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SchedulePolicyEntity schedulePolicy);

	int queryExist(String spName);
	
	void update(SchedulePolicyEntity schedulePolicy);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
