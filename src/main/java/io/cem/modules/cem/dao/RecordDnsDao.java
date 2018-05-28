package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayDnsEntity;
import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.cem.entity.RecordHourDnsEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDnsDao extends BaseDao<RecordDnsEntity> {
    List<RecordDnsEntity> queryDnsTest(Map<String, Object> map);

    List<RecordHourDnsEntity> queryDns(Map<String, Object> map);

    List<RecordDayDnsEntity> queryDay(Map<String, Object> map);

    List<RecordDnsEntity> queryDnsList(Map<String, Object> map);

    List<RecordFailEntity> queryDnsFail(Map<String, Object> map);
}
