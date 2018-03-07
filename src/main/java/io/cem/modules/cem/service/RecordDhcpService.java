package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDhcpEntity;
import io.cem.modules.cem.entity.RecordHourDhcpEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDhcpService {
	
	RecordDhcpEntity queryObject(Integer id);
	
	List<RecordDhcpEntity> queryList(Map<String, Object> map);

	List<RecordDhcpEntity> queryDhcpTest(Map<String, Object> map);

	List<RecordDhcpEntity> queryDhcpList(Map<String, Object> map);

	List<RecordHourDhcpEntity> queryIntervalList(Map<String, Object> map);

	int queryIntervalTotal(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RecordDhcpEntity recordDhcp);
	
	void update(RecordDhcpEntity recordDhcp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
