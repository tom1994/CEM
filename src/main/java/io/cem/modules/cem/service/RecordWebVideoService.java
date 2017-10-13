package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordWebVideoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
public interface RecordWebVideoService {
	
	RecordWebVideoEntity queryObject(Integer id);
	
	List<RecordWebVideoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordWebVideoEntity recordWebVideo);
	
	void update(RecordWebVideoEntity recordWebVideo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
