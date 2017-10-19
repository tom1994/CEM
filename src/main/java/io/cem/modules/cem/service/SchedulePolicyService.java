package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.SchedulePolicyEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface SchedulePolicyService {
	
	SchedulePolicyEntity queryObject(Integer id);
	
	List<SchedulePolicyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	void add(SchedulePolicyEntity schedulePolicy);
	
	void save(SchedulePolicyEntity schedulePolicy);
	
	void update(SchedulePolicyEntity schedulePolicy);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
