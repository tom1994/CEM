package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordHourSlaEntity;
import io.cem.modules.cem.entity.RecordSlaEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
public interface RecordSlaService {
	
	RecordSlaEntity queryObject(Integer id);
	
	List<RecordSlaEntity> queryList(Map<String, Object> map);

	List<RecordSlaEntity> querySlaTest(Map<String, Object> map);

	List<RecordSlaEntity> querySlaList(Map<String, Object> map);

	List<RecordHourSlaEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordSlaEntity recordSla);
	
	void update(RecordSlaEntity recordSla);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
