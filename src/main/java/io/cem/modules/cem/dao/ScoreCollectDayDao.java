package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectDayEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreCollectDayDao {
    List<ScoreCollectDayEntity> queryList(Map<String, Object> map);
    void save(ScoreCollectDayEntity sde);
    void deleteBatch(int[] ids);
}
