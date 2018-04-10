package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AlarmTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface AlarmTemplateService {
	
	AlarmTemplateEntity queryObject(Integer id);
	
	List<AlarmTemplateEntity> queryList(Map<String, Object> map);

	List<AlarmTemplateEntity> queryatList(Integer id);

	List<AlarmTemplateEntity> queryAtByService(Integer id);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AlarmTemplateEntity alarmTemplate);
	
	void update(AlarmTemplateEntity alarmTemplate);

	int queryExist(String atName);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
