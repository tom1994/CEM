package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:51
 */
public interface RecordHourWebVideoDao extends BaseDao<RecordHourWebVideoEntity> {
    List<RecordHourWebVideoEntity> queryVideoList(Map<String, Object> map);
}
