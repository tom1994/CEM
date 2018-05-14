package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ScoreCollectAllEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface ScoreCollectAllDao {
    List<ScoreCollectAllEntity> queryList(Map<String,Object> p);
    void save(ScoreCollectAllEntity s);
    void del(Map<String,Object> p);
    void delAll();
    //下面方法废弃
    List<ScoreCollectAllEntity> queryListForServiceType(Map<String,Object> p);
    List<ScoreCollectAllEntity> queryListForTarget(Map<String,Object> p);
    List<ScoreCollectAllEntity> queryListForArea(Map<String,Object> p);






}
