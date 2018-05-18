package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AlarmRecordEntity;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface AlarmRecordService {
	/**
	 * 根据id筛选某条信息
	 * @param id
	 * @return AlarmRecordEntity
	 */
	AlarmRecordEntity queryObject(Integer id);

	/**
	 * 获取告警信息列表
	 * @param map
	 * @return List<AlarmRecordEntity>
	 */
	List<AlarmRecordEntity> queryList(Map<String, Object> map);

	/**
	 * 获取告警信息列表（有筛选信息）
	 * @param map
	 * @return List<AlarmRecordEntity>
	 */
	List<AlarmRecordEntity> queryAlarmRecordList(Map<String, Object> map);

	/**
	 *获取告警信息列表总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param alarmRecord
	 */
	void save(AlarmRecordEntity alarmRecord);

	/**
	 * 更新
	 * @param alarmRecord
	 */
	void update(AlarmRecordEntity alarmRecord);

	/**
	 * 更改告警确认状态
	 * @param id
	 */
	void operate(Integer id);

	/**
	 *删除
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(Integer[] ids);
}
