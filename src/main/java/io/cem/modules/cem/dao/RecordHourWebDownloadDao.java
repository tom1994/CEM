package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:52
 */
public interface RecordHourWebDownloadDao extends BaseDao<RecordHourWebDownloadEntity> {
    List<RecordHourWebDownloadEntity> queryWebDownloadList(Map<String, Object> map);
    List<RecordHourWebDownloadEntity> queryDayList(Map<String, Object> map);
}
