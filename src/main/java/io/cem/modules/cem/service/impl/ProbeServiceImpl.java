package io.cem.modules.cem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.ProbeDao;
import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;


@Service("probeService")
public class ProbeServiceImpl implements ProbeService {
    @Autowired
    private ProbeDao probeDao;

    @Override
    public ProbeEntity queryObject(Integer id) {
        return probeDao.queryObject(id);
    }

    @Override
    public List<ProbeEntity> queryList(Map<String, Object> map) {
        return probeDao.queryList(map);
    }

    @Override
    public List<ProbeEntity> queryProbeList(Map<String, Object> map) {
        List<ProbeEntity> probeEntityList = probeDao.queryProbeList(map);
        return probeDao.queryProbeList(map);
    }

    @Override
    public List<ProbeEntity> queryExitList(Map<String, Object> map) {

        return probeDao.queryExitList(map);
    }

    @Override
    public int queryExist(String probeName) {
        return probeDao.queryExist(probeName);
    }

    @Override
    public int queryExist(String probeName, int probeId) {
        return probeDao.queryExist(probeName, probeId);
    }

    @Override
    public List<ProbeEntity> queryPortList(Integer id) {
        return probeDao.queryPortList(id);
    }


    @Override
    public List<ProbeEntity> queryOnlineList(Integer id) {

        return probeDao.queryOnlineList(id);
    }

    @Override
    public List<ProbeEntity> queryCenterList(Integer id) {

        return probeDao.queryCenterList(id);
    }

    @Override
    public List<ProbeEntity> queryProbeByLayer(Integer id) {
        List<ProbeEntity> probeList = new ArrayList<>();
        if (probeDao.queryProbeByLayer(id) != null) {
            ProbeEntity probeLayer = probeDao.queryProbeByLayer(id);
            while (probeLayer.getUpstream() != null && probeLayer.getUpstream() != 0) {
                probeList.add(probeLayer);
                probeLayer = probeDao.queryProbeByLayer(probeLayer.getUpstream());
            }
            probeList.add(probeLayer);
        } else {
            probeList.add(probeDao.queryObject(id));
        }
        return probeList;
    }

    @Override
    public void updateUpstream(Integer id) {
        probeDao.updateUpstream(id);
    }

    @Override
    public List<ProbeEntity> queryProbeByCity(Integer id) {
        return probeDao.queryProbeByCity(id);
    }

    @Override
    public List<ProbeEntity> queryProbe(Integer id) {
        return probeDao.queryProbe(id);
    }

    @Override
    public List<ProbeEntity> queryProbeListByGroup(Integer id) {
        return probeDao.queryProbeListByGroup(id);
    }

    @Override
    public ProbeEntity queryDetail(Integer id) {
        return probeDao.queryDetail(id);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return probeDao.queryTotal(map);
    }

    @Override
    public void save(ProbeEntity probe) {
        probeDao.save(probe);
    }

    @Override
    public void update(ProbeEntity probe) {
        probeDao.update(probe);
    }

    @Override
    public void delete(Integer id) {
        probeDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        probeDao.deleteBatch(ids);
    }

}
