package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:23
 */
public interface RecordHourRadiusDao extends BaseDao<RecordHourRadiusEntity> {
    List<RecordHourRadiusEntity> queryRadiusList(Map<String, Object> map);
}
