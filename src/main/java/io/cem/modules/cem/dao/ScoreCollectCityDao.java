package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectCityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreCollectCityDao {
    List<ScoreCollectCityEntity> queryList(Map<String, Object> map);
    void save(ScoreCollectCityEntity scc);
    void deleteBatch(int[] ids);
    void delAll();
}
