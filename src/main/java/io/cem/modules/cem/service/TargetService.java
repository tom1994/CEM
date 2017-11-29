package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public interface TargetService {
	
	TargetEntity queryObject(Integer id);
	
	List<TargetEntity> queryList(Map<String, Object> map);

	List<TargetEntity> queryTargetList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TargetEntity target);
	
	void update(TargetEntity target);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
