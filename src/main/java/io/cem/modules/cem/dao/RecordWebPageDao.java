package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.*;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebPageDao extends BaseDao<RecordWebPageEntity> {
    List<RecordWebPageEntity> queryWebPageTest(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryWebPage(Map<String, Object> map);
    List<RecordDayWebPageEntity> queryDay(Map<String, Object> map);
    List<RecordWebPageEntity> queryWebPageList(Map<String, Object> map);
    List<RecordFailEntity> queryWebPageFail(Map<String, Object> map);
}
