package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.cem.entity.RecordHourGameEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public interface RecordGameService {
	
	RecordGameEntity queryObject(Integer id);
	
	List<RecordGameEntity> queryList(Map<String, Object> map);

	List<RecordGameEntity> queryGameTest(Map<String, Object> map);

	List<RecordGameEntity> queryGameList(Map<String, Object> map);

	List<RecordHourGameEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordGameEntity recordGame);
	
	void update(RecordGameEntity recordGame);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
