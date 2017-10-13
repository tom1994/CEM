package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ProbeGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface ProbeGroupService {
	
	ProbeGroupEntity queryObject(Integer id);
	
	List<ProbeGroupEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProbeGroupEntity probeGroup);
	
	void update(ProbeGroupEntity probeGroup);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
