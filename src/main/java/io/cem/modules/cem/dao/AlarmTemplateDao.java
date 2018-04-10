package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.AlarmTemplateEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 */
public interface AlarmTemplateDao extends BaseDao<AlarmTemplateEntity> {
	List<AlarmTemplateEntity> queryatList(Integer id);
	List<AlarmTemplateEntity> queryAtByService(Integer id);
	int queryExist(String atName);
}
