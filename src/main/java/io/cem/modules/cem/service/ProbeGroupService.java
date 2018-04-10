package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeGroupEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface ProbeGroupService {
	
	ProbeGroupEntity queryObject(Integer id);
	
	List<ProbeGroupEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProbeGroupEntity probeGroup);

	int queryExist(String pgName);
	
	void update(ProbeGroupEntity probeGroup);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
