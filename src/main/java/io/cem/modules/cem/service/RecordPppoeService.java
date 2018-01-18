package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordPppoeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public interface RecordPppoeService {
	
	RecordPppoeEntity queryObject(Integer id);
	
	List<RecordPppoeEntity> queryList(Map<String, Object> map);

	List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordPppoeEntity recordPppoe);
	
	void update(RecordPppoeEntity recordPppoe);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
