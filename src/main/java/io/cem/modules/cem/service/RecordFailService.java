package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.RecordFailEntity;

import java.util.List;
import java.util.Map;

public interface RecordFailService {
    RecordFailEntity queryObject(Integer id);

    List<RecordFailEntity> queryList(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryPingFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryTracertFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> querySlaFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryDhcpFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryDnsFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryPppoeFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryRadiusFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryWebPageFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryWebDownloadFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryFtpFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryVideoFail(Map<String, Object> map);

    /**
     * 定时任务
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryGameFail(Map<String, Object> map);

    /**
     * 失败和总数
     * @param map
     * @return  List<RecordFailEntity>
     */
    List<RecordFailEntity> queryFail1(Map<String, Object> map);

    List<RecordFailEntity> queryFail2(Map<String, Object> map);
    List<RecordFailEntity> queryFail3(Map<String, Object> map);
    List<RecordFailEntity> queryFail4(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(RecordFailEntity city);

    void update(RecordFailEntity city);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
