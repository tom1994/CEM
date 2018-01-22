package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.AlarmTemplateEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public interface AlarmTemplateDao extends BaseDao<AlarmTemplateEntity> {
	List<AlarmTemplateEntity> queryatList(Integer id);
	List<AlarmTemplateEntity> queryAtByService(Integer id);
}
