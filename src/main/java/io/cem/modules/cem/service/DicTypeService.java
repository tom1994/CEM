package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.DicTypeEntity;

import java.util.List;
import java.util.Map;

/**
 */
public interface DicTypeService {

	/**
	 * 根据id获取某条信息
	 * @param id
	 * @return DicTypeEntity
	 */
	DicTypeEntity queryObject(Integer id);

	/**
	 * 获取列表
	 * @param map
	 * @return List<DicTypeEntity>
	 */
	List<DicTypeEntity> queryList(Map<String, Object> map);

	/**
	 * 总数
	 * @param map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存
	 * @param dicType
	 */
	void save(DicTypeEntity dicType);

	/**
	 * 更新
	 * @param dicType
	 */
	void update(DicTypeEntity dicType);

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
