package io.cem.modules.cem.service.impl;

import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordDhcpDao;
import io.cem.modules.cem.dao.RecordHourDhcpDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service("recordHourDhcpService")
public class RecordHourDhcpServiceImpl implements RecordHourDhcpService {
	@Autowired
	private RecordHourDhcpDao recordHourDhcpDao;
	@Autowired
	private RecordDhcpDao recordDhcpDao;
	
	@Override
	public RecordHourDhcpEntity queryObject(Integer id){
		return recordHourDhcpDao.queryObject(id);
	}

	@Override
	public List<RecordHourDhcpEntity> queryDhcp(Map<String, Object> map){
		return recordDhcpDao.queryDhcp(map);
	}

	@Override
	public List<RecordHourDhcpEntity> queryList(Map<String, Object> map){
		return recordHourDhcpDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDhcpList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourDhcpDao.queryDhcpList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourDhcpDao.queryTargetHourList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<>
				( recordHourDhcpDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<>
				( recordHourDhcpDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourDhcpEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<>(recordHourDhcpDao.queryDayList(map));
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourDhcpDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.save(recordHourDhcp);
	}
	
	@Override
	public void update(RecordHourDhcpEntity recordHourDhcp){
		recordHourDhcpDao.update(recordHourDhcp);
	}
	
	@Override
	public void delete(Integer id){
		recordHourDhcpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourDhcpDao.deleteBatch(ids);
	}
	
	@Override
	public List<ScoreEntity> connectionDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryDayList(map);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryDayList(map);
		while (true) {
			if (pingList_future.isDone() && tracertList_future.isDone()) {
				List<RecordHourPingEntity> pingList = pingList_future.get();
				List<RecordHourTracertEntity> tracertList = tracertList_future.get();
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList,map);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList,map);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList,map);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList,map);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList,map);
				scoreList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);

		return scoreList;
	}

	@Override
	public List<ScoreEntity> connectionHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
			map3.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));


		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
		Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
		Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
		Future<List<RecordHourPingEntity>> pingList_future2 = recordHourPingService.queryPingList(map3);
		Future<List<RecordHourTracertEntity>> tracertList_future2 = recordHourTracertService.queryTracertList(map3);
		while (true) {
			if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()&&pingList_future2.isDone() && tracertList_future2.isDone()) {
				List<RecordHourPingEntity> pingList = pingList_future.get();
				pingList.addAll(pingList_future1.get());
				pingList.addAll(pingList_future2.get());
				List<RecordHourTracertEntity> tracertList = tracertList_future.get();
				tracertList.addAll(tracertList_future1.get());
				tracertList.addAll(tracertList_future2.get());
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList,map);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList,map);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList,map);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList,map);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList,map);
				scoreList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
	    return scoreList;
	}

	@Override
	public List<ScoreEntity> connectionDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		RecordHourTracertService recordHourTracertService= (RecordHourTracertService) SpringContextUtils.getBean("recordHourTracertService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		//组装2个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));


		Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map1);
		Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map1);
		Future<List<RecordHourPingEntity>> pingList_future1 = recordHourPingService.queryPingList(map2);
		Future<List<RecordHourTracertEntity>> tracertList_future1 = recordHourTracertService.queryTracertList(map2);
		while (true) {
			if (pingList_future.isDone() && tracertList_future.isDone()&&pingList_future1.isDone() && tracertList_future1.isDone()) {
				List<RecordHourPingEntity> pingList = pingList_future.get();
				pingList.addAll(pingList_future1.get());
				List<RecordHourTracertEntity> tracertList = tracertList_future.get();
				tracertList.addAll(tracertList_future1.get());
				List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList,map);
				List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList,map);
				List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList,map);
				List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList,map);
				List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList,map);
				scoreList = recordHourPingService.calculateDate1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> qualityDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.queryDayList(map);
		Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDayList(map);
		Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDayList(map);
		Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryDayList(map);
		Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryDayList(map);
		while (true) {
			if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()) {
				List<RecordHourSlaEntity> slaList = slaList_future.get();
				List<RecordHourDnsEntity> dnsList = dnsList_future.get();
				List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
				List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
				List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList,map);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList,map);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList,map);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList,map);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList,map);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList,map);
				scoreList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> qualityHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");

		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
			map3.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
		Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
		Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
		Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
		Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
		Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
		Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
		Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
		Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
		Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
		Future<List<RecordHourSlaEntity>> slaList_future2 = recordHourSlaService.querySlaList(map3);
		Future<List<RecordHourDnsEntity>> dnsList_future2= recordHourDnsService.queryDnsList(map3);
		Future<List<RecordHourDhcpEntity>> dhcpList_future2 = recordHourDhcpService.queryDhcpList(map3);
		Future<List<RecordHourPppoeEntity>> pppoeList_future2 = recordHourPppoeService.queryPppoeList(map3);
		Future<List<RecordHourRadiusEntity>> radiusList_future2 = recordHourRadiusService.queryRadiusList(map3);
		while (true) {
			if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
					slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()&&
					slaList_future2.isDone() && dnsList_future2.isDone() && dhcpList_future2.isDone() && dnsList_future2.isDone() && pppoeList_future2.isDone() && radiusList_future2.isDone()) {
				List<RecordHourSlaEntity> slaList = slaList_future.get();
				slaList.addAll(slaList_future1.get());
				slaList.addAll(slaList_future2.get());
				List<RecordHourDnsEntity> dnsList = dnsList_future.get();
				dnsList.addAll(dnsList_future1.get());
				dnsList.addAll(dnsList_future2.get());
				List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
				dhcpList.addAll(dhcpList_future1.get());
				dhcpList.addAll(dhcpList_future2.get());
				List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
				pppoeList.addAll(pppoeList_future1.get());
				pppoeList.addAll(pppoeList_future2.get());
				List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
				radiusList.addAll(radiusList_future1.get());
				radiusList.addAll(radiusList_future2.get());
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList,map);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList,map);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList,map);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList,map);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList,map);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList,map);
				scoreList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> qualityDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourSlaService recordHourSlaService= (RecordHourSlaService) SpringContextUtils.getBean("recordHourSlaService");
		RecordHourDnsService recordHourDnsService= (RecordHourDnsService) SpringContextUtils.getBean("recordHourDnsService");
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourPppoeService recordHourPppoeService= (RecordHourPppoeService) SpringContextUtils.getBean("recordHourPppoeService");
		RecordHourRadiusService recordHourRadiusService= (RecordHourRadiusService) SpringContextUtils.getBean("recordHourRadiusService");
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");

		//组装3个map对于数据进行筛选
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));


		Future<List<RecordHourSlaEntity>> slaList_future = recordHourSlaService.querySlaList(map1);
		Future<List<RecordHourDnsEntity>> dnsList_future = recordHourDnsService.queryDnsList(map1);
		Future<List<RecordHourDhcpEntity>> dhcpList_future = recordHourDhcpService.queryDhcpList(map1);
		Future<List<RecordHourPppoeEntity>> pppoeList_future = recordHourPppoeService.queryPppoeList(map1);
		Future<List<RecordHourRadiusEntity>> radiusList_future = recordHourRadiusService.queryRadiusList(map1);
		Future<List<RecordHourSlaEntity>> slaList_future1 = recordHourSlaService.querySlaList(map2);
		Future<List<RecordHourDnsEntity>> dnsList_future1 = recordHourDnsService.queryDnsList(map2);
		Future<List<RecordHourDhcpEntity>> dhcpList_future1 = recordHourDhcpService.queryDhcpList(map2);
		Future<List<RecordHourPppoeEntity>> pppoeList_future1 = recordHourPppoeService.queryPppoeList(map2);
		Future<List<RecordHourRadiusEntity>> radiusList_future1 = recordHourRadiusService.queryRadiusList(map2);
		while (true) {
			if (slaList_future.isDone() && dnsList_future.isDone() && dhcpList_future.isDone() && dnsList_future.isDone() && pppoeList_future.isDone() && radiusList_future.isDone()&&
					slaList_future1.isDone() && dnsList_future1.isDone() && dhcpList_future1.isDone() && dnsList_future1.isDone() && pppoeList_future1.isDone() && radiusList_future1.isDone()) {
				List<RecordHourSlaEntity> slaList = slaList_future.get();
				slaList.addAll(slaList_future1.get());
				List<RecordHourDnsEntity> dnsList = dnsList_future.get();
				dnsList.addAll(dnsList_future1.get());
				List<RecordHourDhcpEntity> dhcpList = dhcpList_future.get();
				dhcpList.addAll(dhcpList_future1.get());
				List<RecordHourPppoeEntity> pppoeList = pppoeList_future.get();
				pppoeList.addAll(pppoeList_future1.get());
				List<RecordHourRadiusEntity> radiusList = radiusList_future.get();
				radiusList.addAll(radiusList_future1.get());
				List<ScoreEntity> slaTcp = recordHourSlaService.calculateSlaTcp(slaList,map);
				List<ScoreEntity> slaUdp = recordHourSlaService.calculateSlaUdp(slaList,map);
				List<ScoreEntity> dns = recordHourSlaService.calculateDns(dnsList,map);
				List<ScoreEntity> dhcp = recordHourSlaService.calculateDhcp(dhcpList,map);
				List<ScoreEntity> pppoe = recordHourSlaService.calculatePppoe(pppoeList,map);
				List<ScoreEntity> radius = recordHourSlaService.calculateRadius(radiusList,map);
				scoreList = recordHourSlaService.calculateDate2(slaTcp, slaUdp, dns, dhcp, pppoe, radius);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> pageDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		//网页浏览类业务
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryDayList(map);
		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				scoreList = recordHourWebPageService.calculateService3(webPageList,map);
				scoreList = recordHourWebPageService.calculateDate3(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> pageHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebPageService recordHourWebPageService= (RecordHourWebPageService) SpringContextUtils.getBean("recordHourWebPageService");
		RecordHourPingService recordHourPingService =(RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		if (map.get("terminalTime") != null && map.get("startTime") != null) {
			if (map.get("terminalTime").toString().equals("00:00:00")) {
				map.put("ava_terminal", recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));
			}
			if (map.get("startTime").toString().equals("23:00:00")) {
				map.put("ava_start", recordHourPingService.queryAfterDay(map.get("ava_terminal").toString()));
			}
		}
		Future<List<RecordHourWebPageEntity>> webPageList_future = recordHourWebPageService.queryWebList(map);
		while (true) {
			if (webPageList_future.isDone()) {
				List<RecordHourWebPageEntity> webPageList = webPageList_future.get();
				scoreList = recordHourWebPageService.calculateService3(webPageList,map);
				scoreList = recordHourWebPageService.calculateDate3(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> downloadDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryDayList(map);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryDayList(map);
		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList,map);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList,map);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList,map);
				scoreList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> downloadHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");


		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
			map3.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
			map3.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
			map3.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
			map3.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));

		map3.put("ava_start",recordHourPingService.queryAfterDay(map.get("ava_start").toString()));
		map3.put("ava_terminal",recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));

		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
		Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future2 = recordHourWebDownloadService.queryWebDownloadList(map3);
		Future<List<RecordHourFtpEntity>> ftpList_future2 = recordHourFtpService.queryFtpList(map3);
		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()&&webDownloadList_future2.isDone() && ftpList_future2.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				webDownloadList.addAll(webDownloadList_future1.get());
				webDownloadList.addAll(webDownloadList_future2.get());
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				ftpList.addAll(ftpList_future1.get());
				ftpList.addAll(ftpList_future2.get());
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList,map);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList,map);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList,map);
				scoreList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> downloadDayHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebDownloadService recordHourWebDownloadService= (RecordHourWebDownloadService) SpringContextUtils.getBean("recordHourWebDownloadService");
		RecordHourFtpService recordHourFtpService= (RecordHourFtpService) SpringContextUtils.getBean("recordHourFtpService");
		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");


		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		if(map.get("city_id")!=null){
			map1.put("city_id",map.get("city_id"));
			map2.put("city_id",map.get("city_id"));
		}
		if(map.get("county_id")!=null){
			map1.put("county_id",map.get("county_id"));
			map2.put("county_id",map.get("county_id"));
		}
		if(map.get("target_id")!=null){
			map1.put("target_id",map.get("target_id"));
			map2.put("target_id",map.get("target_id"));
		}
		if(map.get("probe_id")!=null){
			map1.put("probe_id",map.get("probe_id"));
			map2.put("probe_id",map.get("probe_id"));
		}
		map1.put("ava_start",map.get("ava_start"));
		map1.put("ava_terminal",map.get("ava_start"));
		map1.put("startTime",map.get("starTime"));
		map1.put("terminalTime","23:00:00");
		map2.put("ava_start",map.get("ava_terminal"));
		map2.put("ava_terminal",map.get("ava_terminal"));
		map2.put("startTime","00:00:00");
		map2.put("terminalTime",map.get("terminalTime"));



		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future = recordHourWebDownloadService.queryWebDownloadList(map1);
		Future<List<RecordHourFtpEntity>> ftpList_future = recordHourFtpService.queryFtpList(map1);
		Future<List<RecordHourWebDownloadEntity>> webDownloadList_future1 = recordHourWebDownloadService.queryWebDownloadList(map2);
		Future<List<RecordHourFtpEntity>> ftpList_future1 = recordHourFtpService.queryFtpList(map2);

		while (true) {
			if (webDownloadList_future.isDone() && ftpList_future.isDone()&&webDownloadList_future1.isDone() && ftpList_future1.isDone()) {
				List<RecordHourWebDownloadEntity> webDownloadList = webDownloadList_future.get();
				webDownloadList.addAll(webDownloadList_future1.get());
				List<RecordHourFtpEntity> ftpList = ftpList_future.get();
				ftpList.addAll(ftpList_future1.get());
				List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webDownloadList,map);
				List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftpList,map);
				List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftpList,map);
				scoreList = recordHourWebDownloadService.calculateDate4(webDownload, ftpDownload, ftpUpload);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> videoDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryDayList(map);
		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				scoreList = recordHourWebVideoService.calculateService5(videoList,map);
				scoreList = recordHourWebVideoService.calculateDate5(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> videoHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourWebVideoService recordHourWebVideoService= (RecordHourWebVideoService) SpringContextUtils.getBean("recordHourWebVideoService");
		RecordHourPingService recordHourPingService =(RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		if (map.get("terminalTime") != null && map.get("startTime") != null) {
			if (map.get("terminalTime").toString().equals("00:00:00")) {
				map.put("ava_terminal", recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));
			}
			if (map.get("startTime").toString().equals("23:00:00")) {
				map.put("ava_start", recordHourPingService.queryAfterDay(map.get("ava_terminal").toString()));
			}
		}
		Future<List<RecordHourWebVideoEntity>> videoList_future = recordHourWebVideoService.queryVideoList(map);
		while (true) {
			if (videoList_future.isDone()) {
				List<RecordHourWebVideoEntity> videoList = videoList_future.get();
				scoreList = recordHourWebVideoService.calculateService5(videoList,map);
				scoreList = recordHourWebVideoService.calculateDate5(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	
	}

	@Override
	public List<ScoreEntity> gameDayChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryDayList(map);
		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				scoreList = recordHourGameService.calculateService6(gameList,map);
				scoreList = recordHourGameService.calculateDate6(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

	@Override
	public List<ScoreEntity> gameHourChart(Map<String,Object> map) throws ExecutionException, InterruptedException{
		List<ScoreEntity> scoreList;
		RecordHourDhcpService recordHourDhcpService= (RecordHourDhcpService) SpringContextUtils.getBean("recordHourDhcpService");
		RecordHourGameService recordHourGameService= (RecordHourGameService) SpringContextUtils.getBean("recordHourGameService");
		RecordHourPingService recordHourPingService =(RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
		if (map.get("terminalTime") != null && map.get("startTime") != null) {
			if (map.get("terminalTime").toString().equals("00:00:00")) {
				map.put("ava_terminal", recordHourPingService.queryBeforeDay(map.get("ava_terminal").toString()));
			}
			if (map.get("startTime").toString().equals("23:00:00")) {
				map.put("ava_start", recordHourPingService.queryAfterDay(map.get("ava_terminal").toString()));
			}
		}
		Future<List<RecordHourGameEntity>> gameList_future = recordHourGameService.queryGameList(map);
		while (true) {
			if (gameList_future.isDone()) {
				List<RecordHourGameEntity> gameList = gameList_future.get();
				scoreList = recordHourGameService.calculateService6(gameList,map);
				scoreList = recordHourGameService.calculateDate6(scoreList);
				break;
			}
			Thread.sleep(1000);
		}
		//scoreList=recordHourDhcpService.combination(map,scoreList);
		return scoreList;
	}

//	@Override
//	public List<ScoreEntity> combination(Map<String,Object> map,List<ScoreEntity> scoreList){
//		List<ScoreEntity> list=new ArrayList<>();
//		RecordHourPingService recordHourPingService= (RecordHourPingService) SpringContextUtils.getBean("recordHourPingService");
//		if (map.get("city_Id") == null && map.get("county_id") == null && map.get("probe_id") == null) {
//			list = recordHourPingService.dateChart1(scoreList);
//		} else if (map.get("county_id") == null && map.get("probe_id") == null) {
//			list = recordHourPingService.cityChart1(scoreList);
//		} else if ((map.get("probe_id") == null)||(map.get("city_id") == null&&map.get("probe_id") == null)) {
//			list = recordHourPingService.probeChart1(scoreList);
//		} else {
//			list = scoreList;
//		}
//		return list;
//	}


	
}
