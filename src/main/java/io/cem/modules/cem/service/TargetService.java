package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface TargetService {
	
	TargetEntity queryObject(Integer id);

	/**
	 * 根据业务类型筛选
	 * @param serviceId
	 * @return List<TargetEntity>
	 */
	List<TargetEntity> infoBatch(Integer serviceId);
	
	List<TargetEntity> queryList(Map<String, Object> map);

	/**
	 * 列表
	 * @param map
	 * @return List<TargetEntity>
	 */
	List<TargetEntity> queryTgByTList(Map<String, Object> map);

	/**
	 * 根据业务类型查询
	 * @param spId
	 * @return List<TargetEntity>
	 */
	List<TargetEntity> queryTargetList(Integer spId);

	/**
	 * 根据探针组查询
	 * @param id
	 * @return
	 */
	List<TargetEntity> queryTargetListByGroup(Integer id);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 名称是否相同
	 * @param targetName
	 * @return int
	 */
	int queryExist(String targetName);

	/**
	 * 更新时名称是否相同
	 * @param targetName
	 * @param id
	 * @return
	 */
	int queryUpdate(String targetName,int id);

	/**
	 * 保存
	 * @param target
	 */
	void save(TargetEntity target);

	/**
	 * 更新
	 * @param target
	 */
	void update(TargetEntity target);

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

	/**
	 * 查询目标地址名称
	 * @param ids
	 * @return
	 */
	List<TargetEntity> queryTargetNames(int[] ids);
}
