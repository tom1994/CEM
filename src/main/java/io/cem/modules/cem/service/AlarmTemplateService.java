package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AlarmTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public interface AlarmTemplateService {
	
	AlarmTemplateEntity queryObject(Integer id);
	
	List<AlarmTemplateEntity> queryList(Map<String, Object> map);

	List<AlarmTemplateEntity> queryatList(Integer id);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AlarmTemplateEntity alarmTemplate);
	
	void update(AlarmTemplateEntity alarmTemplate);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
