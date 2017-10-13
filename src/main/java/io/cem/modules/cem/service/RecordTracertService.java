package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordTracertEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
public interface RecordTracertService {
	
	RecordTracertEntity queryObject(Integer id);
	
	List<RecordTracertEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordTracertEntity recordTracert);
	
	void update(RecordTracertEntity recordTracert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
