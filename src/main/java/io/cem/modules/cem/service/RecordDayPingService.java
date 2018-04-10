package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordDayPingEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordDayPingService {

	RecordDayPingEntity queryObject(Integer id);

	List<RecordDayPingEntity> queryList(Map<String, Object> map);

	List<RecordDayPingEntity> queryDay(Map<String,Object> map);

	int queryTotal(Map<String, Object> map);

	void save(RecordDayPingEntity recordDayPing);

	void update(RecordDayPingEntity recordDayPing);

	void delete(Integer id);

	void deleteBatch(Integer[] ids);
}
