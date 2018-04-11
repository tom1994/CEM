package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayDnsEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordDayDnsService {
	
	RecordDayDnsEntity queryObject(Integer id);
	
	List<RecordDayDnsEntity> queryList(Map<String, Object> map);

	List<RecordDayDnsEntity> queryDay(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDayDnsEntity recordDayDns);
	
	void update(RecordDayDnsEntity recordDayDns);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
