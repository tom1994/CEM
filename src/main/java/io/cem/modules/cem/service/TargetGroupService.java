package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetGroupEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TargetGroupService {
	
	TargetGroupEntity queryObject(Integer id);

	/**
	 * 列表
	 * @param map
	 * @return List<TargetGroupEntity>
	 */
	List<TargetGroupEntity> queryList(Map<String, Object> map);

	/**
	 * 目标地址组名称
	 * @param map
	 * @return List<TargetGroupEntity>
	 */
	List<TargetGroupEntity> queryByTgNameList(Map<String, Object> map);

	/**
	 * 根据ID查询目标地址组列表
	 * @param id
	 * @return List<TargetGroupEntity>
	 */
	List<TargetGroupEntity> queryTGList(Integer id);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param targetGroup
	 */
	void save(TargetGroupEntity targetGroup);

	/**
	 * 更新
	 * @param targetGroup
	 */
	void update(TargetGroupEntity targetGroup);

	/**
	 * 名称是否相同
	 * @param tgName
	 * @return int
	 */
	int queryExist(String tgName);

	/**
	 * 名称是否相同
	 * @param tgName
	 * @param id
	 * @return int
	 */
	int queryUpdate(String tgName,int id);

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
