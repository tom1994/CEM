package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.AlarmTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface AlarmTemplateService {
	/**
	 * 根据id获取信息
	 * @param id
	 * @return AlarmTemplateEntity
	 */
	AlarmTemplateEntity queryObject(Integer id);

	/**
	 * 获取告警模版列表
	 * @param map
	 * @return List<AlarmTemplateEntity>
	 */
	List<AlarmTemplateEntity> queryList(Map<String, Object> map);

	/**
	 * 获取信息
	 * @param id
	 * @return List<AlarmTemplateEntity>
	 */
	List<AlarmTemplateEntity> queryatList(Integer id);

	/**
	 * 根据servicetype的id来列出符合条件的告警模板
	 * @param id
	 * @return List<AlarmTemplateEntity>
	 */
	List<AlarmTemplateEntity> queryAtByService(Integer id);

	/**
	 * 获取总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param alarmTemplate
	 */
	void save(AlarmTemplateEntity alarmTemplate);

	/**
	 * 更新
	 * @param alarmTemplate
	 */
	void update(AlarmTemplateEntity alarmTemplate);

	/**
	 * 模版名称的数目
	 * @param atName
	 * @return int
	 */
	int queryExist(String atName);

	/**
	 * 更新时获取模版名称的数目
	 * @param atName
	 * @param id
	 * @return int
	 */
	int queryUpdate(String atName,int id);

	/**
	 * 删除
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(Integer[] ids);
}
