package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Miao
 */
public interface ProbeService {
	
	ProbeEntity queryObject(Integer id);
	
	List<ProbeEntity> queryList(Map<String, Object> map);

	List<ProbeEntity> queryProbeList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(ProbeEntity probe);
	
	void update(ProbeEntity probe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}