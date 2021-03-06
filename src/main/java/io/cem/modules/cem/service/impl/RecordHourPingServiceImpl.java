package io.cem.modules.cem.service.impl;


import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordHourPingDao;
import io.cem.modules.cem.dao.RecordPingDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordFailService;
import io.cem.modules.cem.service.RecordHourPingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;



@Service("recordHourPingService")
public class RecordHourPingServiceImpl implements RecordHourPingService {
	@Autowired
	private RecordHourPingDao recordHourPingDao;
	@Autowired
	private RecordPingDao recordPingDao;
	
	@Override
	public RecordHourPingEntity queryObject(Integer id){
		return recordHourPingDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourPingEntity> queryList(Map<String, Object> map){
		return recordHourPingDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourPingEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourPingDao.queryExitList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourPingEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourPingDao.queryDayExitList(map));
	}

	@Override
	public List<RecordHourPingEntity> queryPing(Map<String, Object> map){
		return recordPingDao.queryPing(map);
	}

	@Override
	@Async
	public Future<List<RecordHourPingEntity>> queryPingList(Map<String, Object> map){ return  new AsyncResult<>
			(recordHourPingDao.queryPingList(map)); }


	@Override
	@Async
	public Future<List<RecordHourPingEntity>> queryTargetHourList(Map<String, Object> map){ return  new AsyncResult<>
			(recordHourPingDao.queryTargetHourList(map)); }

	@Override
	@Async
	public Future<List<RecordHourPingEntity>> queryDayList(Map<String, Object> map){ return new AsyncResult<> (recordHourPingDao.queryDayList(map)); }

	@Override
	public List<ScoreEntity> calculatePingIcmp(List<RecordHourPingEntity> pingList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		map.put("service_type",1);
		map.put("type",1);
		List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
		Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
		for(int i=0;i<recordFail.size();i++){
			failMap.put(recordFail.get(i),recordFail.get(i));
		}
		//double fail = (double)recordFail.getFail()/recordFail.getTotal();
		List<ScoreEntity> pingIcmp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for (int i = 0; i < pingList.size(); i++) {
				double score = 0;
				//Ping(ICMP)
				if ((pingList.get(i).getServiceType()) == 1) {
					try {
						//delay 100
						if ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI22"))) <= 0) {
							score+= 100 * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(PropertiesUtils.getValue("pingI22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI23"))) <= 0)) {
							score+= (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI23")))) * 20) / ((Double.parseDouble(pros.getValue("pingI22")) - (Double.parseDouble(pros.getValue("pingI23"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI24"))) <= 0)) {
							score+= (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI24")))) * 20) / ((Double.parseDouble(pros.getValue("pingI23")) - (Double.parseDouble(pros.getValue("pingI24"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI25"))) <= 0)) {
							score+= (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI25")))) * 20) / ((Double.parseDouble(pros.getValue("pingI24")) - (Double.parseDouble(pros.getValue("pingI25"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI26"))) <= 0)) {
							score+= (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI26")))) * 20) / ((Double.parseDouble(pros.getValue("pingI25")) - (Double.parseDouble(pros.getValue("pingI26"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI27"))) <= 0)) {
							score+= ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI27")))) * 20) / ((Double.parseDouble(pros.getValue("pingI26")) - (Double.parseDouble(pros.getValue("pingI27")))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 0
						else {
							score+= 0;
						}


						//delay_std 100
						if ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI32"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay_std 80-100
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI32"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI33"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI33")))) * 20) / ((Double.parseDouble(pros.getValue("pingI32")) - (Double.parseDouble(pros.getValue("pingI33"))))))) * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay_std 60-80
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI33"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI34"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI34")))) * 20) / ((Double.parseDouble(pros.getValue("pingI33")) - (Double.parseDouble(pros.getValue("pingI34"))))))) * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI34"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI35"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI35")))) * 20) / ((Double.parseDouble(pros.getValue("pingI34")) - (Double.parseDouble(pros.getValue("pingI35"))))))) * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI35"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI36"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI36")))) * 20) / ((Double.parseDouble(pros.getValue("pingI35")) - (Double.parseDouble(pros.getValue("pingI36"))))))) * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI36"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingI37"))) <= 0)) {
							score += ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI37")))) * 20) / ((Double.parseDouble(pros.getValue("pingI36")) - (Double.parseDouble(pros.getValue("pingI37")))))) * (Double.parseDouble(pros.getValue("pingI31")));
						}
						//delay 0
						else {
							score += 0;
						}

						//delay_var 100
						if ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI42"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 80-100
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI42"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI43"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI43")))) * 20) / ((Double.parseDouble(pros.getValue("pingI42")) - (Double.parseDouble(pros.getValue("pingI43"))))))) * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 60-80
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI43"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI44"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI44")))) * 20) / ((Double.parseDouble(pros.getValue("pingI43")) - (Double.parseDouble(pros.getValue("pingI44"))))))) * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 40-60
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI44"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI45"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI45")))) * 20) / ((Double.parseDouble(pros.getValue("pingI44")) - (Double.parseDouble(pros.getValue("pingI45"))))))) * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 20-40
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI45"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI46"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI46")))) * 20) / ((Double.parseDouble(pros.getValue("pingI45")) - (Double.parseDouble(pros.getValue("pingI46"))))))) * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 0-20
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI46"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingI47"))) <= 0)) {
							score += ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI47")))) * 20) / ((Double.parseDouble(pros.getValue("pingI46")) - (Double.parseDouble(pros.getValue("pingI47")))))) * (Double.parseDouble(pros.getValue("pingI41")));
						}
						//delay_var 0
						else {
							score += 0;
						}

						//jitter 100
						if ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI52"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 80-100
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI52"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI53"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingI53")))) * 20) / ((Double.parseDouble(pros.getValue("pingI52")) - (Double.parseDouble(pros.getValue("pingI53"))))))) * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 60-80
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI53"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI54"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingI54")))) * 20) / ((Double.parseDouble(pros.getValue("pingI53")) - (Double.parseDouble(pros.getValue("pingI54"))))))) * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 40-60
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI54"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI55"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingI55")))) * 20) / ((Double.parseDouble(pros.getValue("pingI54")) - (Double.parseDouble(pros.getValue("pingI55"))))))) * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 20-40
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI55"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI56"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingI56")))) * 20) / ((Double.parseDouble(pros.getValue("pingI55")) - (Double.parseDouble(pros.getValue("pingI56"))))))) * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 0-20
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI56"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingI57"))) <= 0)) {
							score += ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingI57")))) * 20) / ((Double.parseDouble(pros.getValue("pingI56")) - (Double.parseDouble(pros.getValue("pingI57")))))) * (Double.parseDouble(pros.getValue("pingI51")));
						}
						//jitter 0
						else {
							score += 0;
						}


						//jitter_std 100
						if ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI62"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 80-100
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI62"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI63"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI63")))) * 20) / ((Double.parseDouble(pros.getValue("pingI62")) - (Double.parseDouble(pros.getValue("pingI63"))))))) * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 60-80
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI63"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI64"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI64")))) * 20) / ((Double.parseDouble(pros.getValue("pingI63")) - (Double.parseDouble(pros.getValue("pingI64"))))))) * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 40-60
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI64"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI65"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI65")))) * 20) / ((Double.parseDouble(pros.getValue("pingI64")) - (Double.parseDouble(pros.getValue("pingI65"))))))) * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 20-40
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI65"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI66"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI66")))) * 20) / ((Double.parseDouble(pros.getValue("pingI65")) - (Double.parseDouble(pros.getValue("pingI66"))))))) * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 0-20
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI66"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingI67"))) <= 0)) {
							score += ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingI67")))) * 20) / ((Double.parseDouble(pros.getValue("pingI66")) - (Double.parseDouble(pros.getValue("pingI67")))))) * (Double.parseDouble(pros.getValue("pingI61")));
						}
						//jitter_std 0
						else {
							score += 0;
						}


						//jitter_var 100
						if ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 80-100
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI72"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI73"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI73")))) * 20) / ((Double.parseDouble(pros.getValue("pingI72")) - (Double.parseDouble(pros.getValue("pingI73"))))))) * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 60-80
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI73"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI74"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI74")))) * 20) / ((Double.parseDouble(pros.getValue("pingI73")) - (Double.parseDouble(pros.getValue("pingI74"))))))) * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 40-60
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI74"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI75"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI75")))) * 20) / ((Double.parseDouble(pros.getValue("pingI74")) - (Double.parseDouble(pros.getValue("pingI75"))))))) * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 20-40
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI75"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI76"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI76")))) * 20) / ((Double.parseDouble(pros.getValue("pingI75")) - (Double.parseDouble(pros.getValue("pingI76"))))))) * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 0-20
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI76"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingI77"))) <= 0)) {
							score += ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingI77")))) * 20) / ((Double.parseDouble(pros.getValue("pingI76")) - (Double.parseDouble(pros.getValue("pingI77")))))) * (Double.parseDouble(pros.getValue("pingI71")));
						}
						//jitter_var 0
						else {
							score += 0;
						}

						//loss 100
						Double lossRate = pingList.get(i).getLossRate()*100;
						if (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 80-100
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingI82"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI83"))) <= 0)) {
							score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingI83")))) * 20) / ((Double.parseDouble(pros.getValue("pingI82")) - (Double.parseDouble(pros.getValue("pingI83"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 60-80
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingI83"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI84"))) <= 0)) {
							score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingI84")))) * 20) / ((Double.parseDouble(pros.getValue("pingI83")) - (Double.parseDouble(pros.getValue("pingI84"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 40-60
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingI84"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI85"))) <= 0)) {
							score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingI85")))) * 20) / ((Double.parseDouble(pros.getValue("pingI84")) - (Double.parseDouble(pros.getValue("pingI85"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 20-40
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingI85"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI86"))) <= 0)) {
							score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingI86")))) * 20) / ((Double.parseDouble(pros.getValue("pingI85")) - (Double.parseDouble(pros.getValue("pingI86"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 0-20
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingI86"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingI87"))) <= 0)) {
							score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingI87")))) * 20) / ((Double.parseDouble(pros.getValue("pingI86")) - (Double.parseDouble(pros.getValue("pingI87")))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 0
						else {
							score += 0;
						}

						System.out.println("Ping Icmp:" + score);

						ScoreEntity icmpPing = new ScoreEntity();
						icmpPing.setId(pingList.get(i).getId());
						icmpPing.setCityName(pingList.get(i).getCityName());
						icmpPing.setCityId(pingList.get(i).getCityId());
						icmpPing.setCountyName(pingList.get(i).getAreaName());
						icmpPing.setCountyId(pingList.get(i).getCountyId());
						icmpPing.setProbeName(pingList.get(i).getProbeName());
						icmpPing.setProbeId(pingList.get(i).getProbeId());
						icmpPing.setServiceType(pingList.get(i).getServiceType());
						icmpPing.setTargetName(pingList.get(i).getTargetName());
						icmpPing.setTargetId(pingList.get(i).getTargetId());
						icmpPing.setPort(pingList.get(i).getPort());
						icmpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						icmpPing.setRecordDate(pingList.get(i).getRecordDate());
						icmpPing.setRecordTime(pingList.get(i).getRecordTime());
						icmpPing.setExit(pingList.get(i).getExit());
						RecordFailEntity failEntity = new RecordFailEntity();
						failEntity.setCityId(pingList.get(i).getCityId());
						failEntity.setCountyId(pingList.get(i).getCountyId());
						failEntity.setProbeId(pingList.get(i).getProbeId());
						failEntity.setTargetId(pingList.get(i).getTargetId());
						failEntity.setPort(pingList.get(i).getPort());
						failEntity.setRecordDate(pingList.get(i).getRecordDate());
						failEntity.setRecordTime(pingList.get(i).getRecordTime());
						if(failMap.containsKey(failEntity)){
							icmpPing.setFail(failMap.get(failEntity).getFail());
							icmpPing.setTotal(failMap.get(failEntity).getTotal());
						}else{
							icmpPing.setFail(pingList.get(i).getFail());
							icmpPing.setTotal(pingList.get(i).getTotal());
						}
						icmpPing.setPingIcmpDelay(pingList.get(i).getDelay());
						icmpPing.setPingIcmpDelayStd(pingList.get(i).getDelayStd());
						icmpPing.setPingIcmpDelayVar(pingList.get(i).getDelayVar());
						icmpPing.setPingIcmpJitter(pingList.get(i).getJitter());
						icmpPing.setPingIcmpJitterStd(pingList.get(i).getJitterStd());
						icmpPing.setPingIcmpJitterVar(pingList.get(i).getJitterVar());
						icmpPing.setPingIcmpLossRate(pingList.get(i).getLossRate());
						double fail = (double)icmpPing.getFail()/icmpPing.getTotal();
						icmpPing.setScore(score*(1-fail));
						icmpPing.setBase(Double.parseDouble(pros.getValue("ping_icmp")));

						pingIcmp.add(icmpPing);

					} catch (IOException e) {
					}
				}}
			}catch (IOException e) {
			}

		return pingIcmp;
	}

	@Override
	public List<ScoreEntity> calculatePingTcp(List<RecordHourPingEntity> pingList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> pingTcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",2);
			map.put("type",1);
			List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			for(int i=0;i<recordFail.size();i++){
				failMap.put(recordFail.get(i),recordFail.get(i));
			}
			//RecordFailEntity recordFail = recordFailService.queryFail1(map);
			//double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < pingList.size(); i++) {
				double score= 0;
				//Ping(TCP)
				if ((pingList.get(i).getServiceType()) == 2) {
					try {
						//delay 100
						if ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT22"))) <= 0) {
							score+= 100 * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT23"))) <= 0)) {
							score+= (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT23")))) * 20) / ((Double.parseDouble(pros.getValue("pingT22")) - (Double.parseDouble(pros.getValue("pingT23"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT24"))) <= 0)) {
							score+= (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT24")))) * 20) / ((Double.parseDouble(pros.getValue("pingT23")) - (Double.parseDouble(pros.getValue("pingT24"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT25"))) <= 0)) {
							score+= (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT25")))) * 20) / ((Double.parseDouble(pros.getValue("pingT24")) - (Double.parseDouble(pros.getValue("pingT25"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT26"))) <= 0)) {
							score+= (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT26")))) * 20) / ((Double.parseDouble(pros.getValue("pingT25")) - (Double.parseDouble(pros.getValue("pingT26"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT27"))) <= 0)) {
							score+= ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT27")))) * 20) / ((Double.parseDouble(pros.getValue("pingT26")) - (Double.parseDouble(pros.getValue("pingT27")))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 0
						else {
							score+= 0;
						}

						//delay_std 100
						if ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT32"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay_std 80-100
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT32"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT33"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT33")))) * 20) / ((Double.parseDouble(pros.getValue("pingT32")) - (Double.parseDouble(pros.getValue("pingT33"))))))) * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay_std 60-80
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT33"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT34"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT34")))) * 20) / ((Double.parseDouble(pros.getValue("pingT33")) - (Double.parseDouble(pros.getValue("pingT34"))))))) * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT34"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT35"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT35")))) * 20) / ((Double.parseDouble(pros.getValue("pingT34")) - (Double.parseDouble(pros.getValue("pingT35"))))))) * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT35"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT36"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT36")))) * 20) / ((Double.parseDouble(pros.getValue("pingT35")) - (Double.parseDouble(pros.getValue("pingT36"))))))) * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT36"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingT37"))) <= 0)) {
							score += ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT37")))) * 20) / ((Double.parseDouble(pros.getValue("pingT36")) - (Double.parseDouble(pros.getValue("pingT37")))))) * (Double.parseDouble(pros.getValue("pingT31")));
						}
						//delay 0
						else {
							score += 0;
						}

						//delay_var 100
						if ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT42"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 80-100
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT42"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT43"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT43")))) * 20) / ((Double.parseDouble(pros.getValue("pingT42")) - (Double.parseDouble(pros.getValue("pingT43"))))))) * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 60-80
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT43"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT44"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT44")))) * 20) / ((Double.parseDouble(pros.getValue("pingT43")) - (Double.parseDouble(pros.getValue("pingT44"))))))) * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 40-60
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT44"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT45"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT45")))) * 20) / ((Double.parseDouble(pros.getValue("pingT44")) - (Double.parseDouble(pros.getValue("pingT45"))))))) * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 20-40
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT45"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT46"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT46")))) * 20) / ((Double.parseDouble(pros.getValue("pingT45")) - (Double.parseDouble(pros.getValue("pingT46"))))))) * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 0-20
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT46"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingT47"))) <= 0)) {
							score += ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT47")))) * 20) / ((Double.parseDouble(pros.getValue("pingT46")) - (Double.parseDouble(pros.getValue("pingT47")))))) * (Double.parseDouble(pros.getValue("pingT41")));
						}
						//delay_var 0
						else {
							score += 0;
						}

						//jitter 100
						if ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT52"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 80-100
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT52"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT53"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingT53")))) * 20) / ((Double.parseDouble(pros.getValue("pingT52")) - (Double.parseDouble(pros.getValue("pingT53"))))))) * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 60-80
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT53"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT54"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingT54")))) * 20) / ((Double.parseDouble(pros.getValue("pingT53")) - (Double.parseDouble(pros.getValue("pingT54"))))))) * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 40-60
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT54"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT55"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingT55")))) * 20) / ((Double.parseDouble(pros.getValue("pingT54")) - (Double.parseDouble(pros.getValue("pingT55"))))))) * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 20-40
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT55"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT56"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingT56")))) * 20) / ((Double.parseDouble(pros.getValue("pingT55")) - (Double.parseDouble(pros.getValue("pingT56"))))))) * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 0-20
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT56"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingT57"))) <= 0)) {
							score += ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingT57")))) * 20) / ((Double.parseDouble(pros.getValue("pingT56")) - (Double.parseDouble(pros.getValue("pingT57")))))) * (Double.parseDouble(pros.getValue("pingT51")));
						}
						//jitter 0
						else {
							score += 0;
						}


						//jitter_std 100
						if ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT62"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 80-100
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT62"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT63"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT63")))) * 20) / ((Double.parseDouble(pros.getValue("pingT62")) - (Double.parseDouble(pros.getValue("pingT63"))))))) * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 60-80
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT63"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT64"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT64")))) * 20) / ((Double.parseDouble(pros.getValue("pingT63")) - (Double.parseDouble(pros.getValue("pingT64"))))))) * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 40-60
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT64"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT65"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT65")))) * 20) / ((Double.parseDouble(pros.getValue("pingT64")) - (Double.parseDouble(pros.getValue("pingT65"))))))) * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 20-40
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT65"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT66"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT66")))) * 20) / ((Double.parseDouble(pros.getValue("pingT65")) - (Double.parseDouble(pros.getValue("pingT66"))))))) * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 0-20
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT66"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingT67"))) <= 0)) {
							score += ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingT67")))) * 20) / ((Double.parseDouble(pros.getValue("pingT66")) - (Double.parseDouble(pros.getValue("pingT67")))))) * (Double.parseDouble(pros.getValue("pingT61")));
						}
						//jitter_std 0
						else {
							score += 0;
						}


						//jitter_var 100
						if ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 80-100
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT72"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT73"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT73")))) * 20) / ((Double.parseDouble(pros.getValue("pingT72")) - (Double.parseDouble(pros.getValue("pingT73"))))))) * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 60-80
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT73"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT74"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT74")))) * 20) / ((Double.parseDouble(pros.getValue("pingT73")) - (Double.parseDouble(pros.getValue("pingT74"))))))) * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 40-60
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT74"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT75"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT75")))) * 20) / ((Double.parseDouble(pros.getValue("pingT74")) - (Double.parseDouble(pros.getValue("pingT75"))))))) * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 20-40
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT75"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT76"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT76")))) * 20) / ((Double.parseDouble(pros.getValue("pingT75")) - (Double.parseDouble(pros.getValue("pingT76"))))))) * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 0-20
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT76"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingT77"))) <= 0)) {
							score += ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingT77")))) * 20) / ((Double.parseDouble(pros.getValue("pingT76")) - (Double.parseDouble(pros.getValue("pingT77")))))) * (Double.parseDouble(pros.getValue("pingT71")));
						}
						//jitter_var 0
						else {
							score += 0;
						}

						//loss 100
						Double lossRate = pingList.get(i).getLossRate()*100;
						if (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 80-100
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingT82"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT83"))) <= 0)) {
							score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingT83")))) * 20) / ((Double.parseDouble(pros.getValue("pingT82")) - (Double.parseDouble(pros.getValue("pingT83"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 60-80
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingT83"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT84"))) <= 0)) {
							score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingT84")))) * 20) / ((Double.parseDouble(pros.getValue("pingT83")) - (Double.parseDouble(pros.getValue("pingT84"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 40-60
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingT84"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT85"))) <= 0)) {
							score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingT85")))) * 20) / ((Double.parseDouble(pros.getValue("pingT84")) - (Double.parseDouble(pros.getValue("pingT85"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 20-40
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingT85"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT86"))) <= 0)) {
							score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingT86")))) * 20) / ((Double.parseDouble(pros.getValue("pingT85")) - (Double.parseDouble(pros.getValue("pingT86"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 0-20
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingT86"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingT87"))) <= 0)) {
							score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingT87")))) * 20) / ((Double.parseDouble(pros.getValue("pingT86")) - (Double.parseDouble(pros.getValue("pingT87")))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 0
						else {
							score += 0;
						}

						System.out.println("Ping Tcp:" + score);

						ScoreEntity tcpPing = new ScoreEntity();
						tcpPing.setId(pingList.get(i).getId());
						tcpPing.setCityName(pingList.get(i).getCityName());
						tcpPing.setCityId(pingList.get(i).getCityId());
						tcpPing.setCountyName(pingList.get(i).getAreaName());
						tcpPing.setCountyId(pingList.get(i).getCountyId());
						tcpPing.setProbeName(pingList.get(i).getProbeName());
						tcpPing.setProbeId(pingList.get(i).getProbeId());
						tcpPing.setServiceType(pingList.get(i).getServiceType());
						tcpPing.setTargetName(pingList.get(i).getTargetName());
						tcpPing.setTargetId(pingList.get(i).getTargetId());
						tcpPing.setPort(pingList.get(i).getPort());
						tcpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						tcpPing.setRecordDate(pingList.get(i).getRecordDate());
						tcpPing.setRecordTime(pingList.get(i).getRecordTime());
						tcpPing.setExit(pingList.get(i).getExit());
						RecordFailEntity failEntity = new RecordFailEntity();
						failEntity.setCityId(pingList.get(i).getCityId());
						failEntity.setCountyId(pingList.get(i).getCountyId());
						failEntity.setProbeId(pingList.get(i).getProbeId());
						failEntity.setTargetId(pingList.get(i).getTargetId());
						failEntity.setPort(pingList.get(i).getPort());
						failEntity.setRecordDate(pingList.get(i).getRecordDate());
						failEntity.setRecordTime(pingList.get(i).getRecordTime());
						if(failMap.containsKey(failEntity)){
							tcpPing.setFail(failMap.get(failEntity).getFail());
							tcpPing.setTotal(failMap.get(failEntity).getTotal());
						}else{
							tcpPing.setFail(pingList.get(i).getFail());
							tcpPing.setTotal(pingList.get(i).getTotal());
						}
						tcpPing.setPingTcpDelay(pingList.get(i).getDelay());
						tcpPing.setPingTcpDelayStd(pingList.get(i).getDelayStd());
						tcpPing.setPingTcpDelayVar(pingList.get(i).getDelayVar());
						tcpPing.setPingTcpJitter(pingList.get(i).getJitter());
						tcpPing.setPingTcpJitterStd(pingList.get(i).getJitterStd());
						tcpPing.setPingTcpJitterVar(pingList.get(i).getJitterVar());
						tcpPing.setPingTcpLossRate(pingList.get(i).getLossRate());
						double fail = (double) tcpPing.getFail()/tcpPing.getTotal();
						tcpPing.setScore(score*(1-fail));
						tcpPing.setBase(Double.parseDouble(pros.getValue("ping_tcp")));

						pingTcp.add(tcpPing);

					} catch (IOException e) {
					}
				}}
		}catch (IOException e) {
		}

		return pingTcp;
	}

	@Override
	public List<ScoreEntity> calculatePingUdp(List<RecordHourPingEntity> pingList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		map.put("service_type",3);
		map.put("type",1);
		List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
		Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
		for(int i=0;i<recordFail.size();i++){
			failMap.put(recordFail.get(i),recordFail.get(i));
		}
		List<ScoreEntity> pingUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for (int i = 0; i < pingList.size(); i++) {
				double score= 0;
				//Ping(Udp)
				if ((pingList.get(i).getServiceType()) == 3) {
					try {
						//delay 100
						if ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU22"))) <= 0) {
							score+= 100 * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU23"))) <= 0)) {
							score+= (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU23")))) * 20) / ((Double.parseDouble(pros.getValue("pingU22")) - (Double.parseDouble(pros.getValue("pingU23"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU24"))) <= 0)) {
							score+= (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU24")))) * 20) / ((Double.parseDouble(pros.getValue("pingU23")) - (Double.parseDouble(pros.getValue("pingU24"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU25"))) <= 0)) {
							score+= (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU25")))) * 20) / ((Double.parseDouble(pros.getValue("pingU24")) - (Double.parseDouble(pros.getValue("pingU25"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU26"))) <= 0)) {
							score+= (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU26")))) * 20) / ((Double.parseDouble(pros.getValue("pingU25")) - (Double.parseDouble(pros.getValue("pingU26"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU27"))) <= 0)) {
							score+= ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU27")))) * 20) / ((Double.parseDouble(pros.getValue("pingU26")) - (Double.parseDouble(pros.getValue("pingU27")))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 0
						else {
							score+= 0;
						}

						//delay_std 100
						if ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU32"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay_std 80-100
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU32"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU33"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU33")))) * 20) / ((Double.parseDouble(pros.getValue("pingU32")) - (Double.parseDouble(pros.getValue("pingU33"))))))) * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay_std 60-80
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU33"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU34"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU34")))) * 20) / ((Double.parseDouble(pros.getValue("pingU33")) - (Double.parseDouble(pros.getValue("pingU34"))))))) * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU34"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU35"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU35")))) * 20) / ((Double.parseDouble(pros.getValue("pingU34")) - (Double.parseDouble(pros.getValue("pingU35"))))))) * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU35"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU36"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU36")))) * 20) / ((Double.parseDouble(pros.getValue("pingU35")) - (Double.parseDouble(pros.getValue("pingU36"))))))) * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU36"))) > 0) && ((pingList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("pingU37"))) <= 0)) {
							score += ((((pingList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU37")))) * 20) / ((Double.parseDouble(pros.getValue("pingU36")) - (Double.parseDouble(pros.getValue("pingU37")))))) * (Double.parseDouble(pros.getValue("pingU31")));
						}
						//delay 0
						else {
							score += 0;
						}

						//delay_var 100
						if ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU42"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 80-100
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU42"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU43"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU43")))) * 20) / ((Double.parseDouble(pros.getValue("pingU42")) - (Double.parseDouble(pros.getValue("pingU43"))))))) * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 60-80
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU43"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU44"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU44")))) * 20) / ((Double.parseDouble(pros.getValue("pingU43")) - (Double.parseDouble(pros.getValue("pingU44"))))))) * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 40-60
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU44"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU45"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU45")))) * 20) / ((Double.parseDouble(pros.getValue("pingU44")) - (Double.parseDouble(pros.getValue("pingU45"))))))) * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 20-40
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU45"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU46"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU46")))) * 20) / ((Double.parseDouble(pros.getValue("pingU45")) - (Double.parseDouble(pros.getValue("pingU46"))))))) * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 0-20
						else if (((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU46"))) > 0) && ((pingList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("pingU47"))) <= 0)) {
							score += ((((pingList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU47")))) * 20) / ((Double.parseDouble(pros.getValue("pingU46")) - (Double.parseDouble(pros.getValue("pingU47")))))) * (Double.parseDouble(pros.getValue("pingU41")));
						}
						//delay_var 0
						else {
							score += 0;
						}

						//jitter 100
						if ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU52"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 80-100
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU52"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU53"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingU53")))) * 20) / ((Double.parseDouble(pros.getValue("pingU52")) - (Double.parseDouble(pros.getValue("pingU53"))))))) * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 60-80
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU53"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU54"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingU54")))) * 20) / ((Double.parseDouble(pros.getValue("pingU53")) - (Double.parseDouble(pros.getValue("pingU54"))))))) * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 40-60
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU54"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU55"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingU55")))) * 20) / ((Double.parseDouble(pros.getValue("pingU54")) - (Double.parseDouble(pros.getValue("pingU55"))))))) * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 20-40
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU55"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU56"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingU56")))) * 20) / ((Double.parseDouble(pros.getValue("pingU55")) - (Double.parseDouble(pros.getValue("pingU56"))))))) * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 0-20
						else if (((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU56"))) > 0) && ((pingList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("pingU57"))) <= 0)) {
							score += ((((pingList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("pingU57")))) * 20) / ((Double.parseDouble(pros.getValue("pingU56")) - (Double.parseDouble(pros.getValue("pingU57")))))) * (Double.parseDouble(pros.getValue("pingU51")));
						}
						//jitter 0
						else {
							score += 0;
						}


						//jitter_std 100
						if ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU62"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 80-100
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU62"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU63"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU63")))) * 20) / ((Double.parseDouble(pros.getValue("pingU62")) - (Double.parseDouble(pros.getValue("pingU63"))))))) * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 60-80
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU63"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU64"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU64")))) * 20) / ((Double.parseDouble(pros.getValue("pingU63")) - (Double.parseDouble(pros.getValue("pingU64"))))))) * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 40-60
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU64"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU65"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU65")))) * 20) / ((Double.parseDouble(pros.getValue("pingU64")) - (Double.parseDouble(pros.getValue("pingU65"))))))) * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 20-40
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU65"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU66"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU66")))) * 20) / ((Double.parseDouble(pros.getValue("pingU65")) - (Double.parseDouble(pros.getValue("pingU66"))))))) * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 0-20
						else if (((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU66"))) > 0) && ((pingList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("pingU67"))) <= 0)) {
							score += ((((pingList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("pingU67"))))* 20) / ((Double.parseDouble(pros.getValue("pingU66")) - (Double.parseDouble(pros.getValue("pingU67")))))) * (Double.parseDouble(pros.getValue("pingU61")));
						}
						//jitter_std 0
						else {
							score += 0;
						}


						//jitter_var 100
						if ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 80-100
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU72"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU73"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU73")))) * 20) / ((Double.parseDouble(pros.getValue("pingU72")) - (Double.parseDouble(pros.getValue("pingU73"))))))) * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 60-80
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU73"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU74"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU74")))) * 20) / ((Double.parseDouble(pros.getValue("pingU73")) - (Double.parseDouble(pros.getValue("pingU74"))))))) * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 40-60
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU74"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU75"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU75")))) * 20) / ((Double.parseDouble(pros.getValue("pingU74")) - (Double.parseDouble(pros.getValue("pingU75"))))))) * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 20-40
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU75"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU76"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU76")))) * 20) / ((Double.parseDouble(pros.getValue("pingU75")) - (Double.parseDouble(pros.getValue("pingU76"))))))) * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 0-20
						else if (((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU76"))) > 0) && ((pingList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("pingU77"))) <= 0)) {
							score += ((((pingList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("pingU77")))) * 20) / ((Double.parseDouble(pros.getValue("pingU76")) - (Double.parseDouble(pros.getValue("pingU77")))))) * (Double.parseDouble(pros.getValue("pingU71")));
						}
						//jitter_var 0
						else {
							score += 0;
						}

						Double lossRate = pingList.get(i).getLossRate()*100;
						//loss 100
						if (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 80-100
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingU82"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU83"))) <= 0)) {
							score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingU83")))) * 20) / ((Double.parseDouble(pros.getValue("pingU82")) - (Double.parseDouble(pros.getValue("pingU83"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 60-80
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingU83"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU84"))) <= 0)) {
							score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingU84")))) * 20) / ((Double.parseDouble(pros.getValue("pingU83")) - (Double.parseDouble(pros.getValue("pingU84"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 40-60
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingU84"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU85"))) <= 0)) {
							score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingU85")))) * 20) / ((Double.parseDouble(pros.getValue("pingU84")) - (Double.parseDouble(pros.getValue("pingU85"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 20-40
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingU85"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU86"))) <= 0)) {
							score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingU86")))) * 20) / ((Double.parseDouble(pros.getValue("pingU85")) - (Double.parseDouble(pros.getValue("pingU86"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 0-20
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("pingU86"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("pingU87"))) <= 0)) {
							score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("pingU87")))) * 20) / ((Double.parseDouble(pros.getValue("pingU86")) - (Double.parseDouble(pros.getValue("pingU87")))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 0
						else {
							score += 0;
						}

						System.out.println("Ping Udp:"+score);

						ScoreEntity udpPing = new ScoreEntity();
						udpPing.setId(pingList.get(i).getId());
						udpPing.setCityName(pingList.get(i).getCityName());
						udpPing.setCityId(pingList.get(i).getCityId());
						udpPing.setCountyName(pingList.get(i).getAreaName());
						udpPing.setCountyId(pingList.get(i).getCountyId());
						udpPing.setProbeName(pingList.get(i).getProbeName());
						udpPing.setProbeId(pingList.get(i).getProbeId());
						udpPing.setServiceType(pingList.get(i).getServiceType());
						udpPing.setTargetName(pingList.get(i).getTargetName());
						udpPing.setTargetId(pingList.get(i).getTargetId());
						udpPing.setPort(pingList.get(i).getPort());
						udpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						udpPing.setRecordDate(pingList.get(i).getRecordDate());
						udpPing.setRecordTime(pingList.get(i).getRecordTime());
						udpPing.setExit(pingList.get(i).getExit());
						RecordFailEntity failEntity = new RecordFailEntity();
						failEntity.setCityId(pingList.get(i).getCityId());
						failEntity.setCountyId(pingList.get(i).getCountyId());
						failEntity.setProbeId(pingList.get(i).getProbeId());
						failEntity.setTargetId(pingList.get(i).getTargetId());
						failEntity.setPort(pingList.get(i).getPort());
						failEntity.setRecordDate(pingList.get(i).getRecordDate());
						failEntity.setRecordTime(pingList.get(i).getRecordTime());
						if(failMap.containsKey(failEntity)){
							udpPing.setFail(failMap.get(failEntity).getFail());
							udpPing.setTotal(failMap.get(failEntity).getTotal());
						}else{
							udpPing.setFail(pingList.get(i).getFail());
							udpPing.setTotal(pingList.get(i).getTotal());
						}
						udpPing.setPingUdpDelay(pingList.get(i).getDelay());
						udpPing.setPingUdpDelayStd(pingList.get(i).getDelayStd());
						udpPing.setPingUdpDelayVar(pingList.get(i).getDelayVar());
						udpPing.setPingUdpJitter(pingList.get(i).getJitter());
						udpPing.setPingUdpJitterStd(pingList.get(i).getJitterStd());
						udpPing.setPingUdpJitterVar(pingList.get(i).getJitterVar());
						udpPing.setPingUdpLossRate(pingList.get(i).getLossRate());
						double fail = (double) udpPing.getFail()/udpPing.getTotal();
						udpPing.setScore(score*(1-fail));
						udpPing.setBase(Double.parseDouble(pros.getValue("ping_udp")));

						pingUdp.add(udpPing);
					} catch (IOException e) {
					}
				}}
		}catch (IOException e) {
		}
		return pingUdp;
	}

	@Override
	public List<ScoreEntity> calculateTracertIcmp(List<RecordHourTracertEntity> tracertList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		map.put("service_type",4);
		map.put("type",1);
		List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
		Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
		for(int i=0;i<recordFail.size();i++){
			failMap.put(recordFail.get(i),recordFail.get(i));
		}
		List<ScoreEntity> tracertIcmp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for (int i = 0; i < tracertList.size(); i++) {
				double score = 0;
				//Tracert(Icmp)
				if ((tracertList.get(i).getServiceType()) == 4) {
					try {
						//delay 100
						if ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI12"))) <= 0) {
							score+= 100 * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 80-100
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI12"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI13"))) <= 0)) {
							score+= (80 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI13")))) * 20) / ((Double.parseDouble(pros.getValue("trI12")) - (Double.parseDouble(pros.getValue("trI13"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 60-80
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI13"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI14"))) <= 0)) {
							score+= (60 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI14")))) * 20) / ((Double.parseDouble(pros.getValue("trI13")) - (Double.parseDouble(pros.getValue("trI14"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI14"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI15"))) <= 0)) {
							score+= (40 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI15")))) * 20) / ((Double.parseDouble(pros.getValue("trI14")) - (Double.parseDouble(pros.getValue("trI15"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI15"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI16"))) <= 0)) {
							score+= (20 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI16")))) * 20) / ((Double.parseDouble(pros.getValue("trI15")) - (Double.parseDouble(pros.getValue("trI16"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI16"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI17"))) <= 0)) {
							score+= ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI17")))) * 20) / ((Double.parseDouble(pros.getValue("trI16")) - (Double.parseDouble(pros.getValue("trI17")))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 0
						else {
							score+= 0;
						}

						//delay_std 100
						if ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI22"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay_std 80-100
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI22"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI23"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI23")))) * 20) / ((Double.parseDouble(pros.getValue("trI22")) - (Double.parseDouble(pros.getValue("trI23"))))))) * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay_std 60-80
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI23"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI24"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI24")))) * 20) / ((Double.parseDouble(pros.getValue("trI23")) - (Double.parseDouble(pros.getValue("trI24"))))))) * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI24"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI25"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI25")))) * 20) / ((Double.parseDouble(pros.getValue("trI24")) - (Double.parseDouble(pros.getValue("trI25"))))))) * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI25"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI26"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI26")))) * 20) / ((Double.parseDouble(pros.getValue("trI25")) - (Double.parseDouble(pros.getValue("trI26"))))))) * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI26"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trI27"))) <= 0)) {
							score += ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI27")))) * 20) / ((Double.parseDouble(pros.getValue("trI26")) - (Double.parseDouble(pros.getValue("trI27")))))) * (Double.parseDouble(pros.getValue("trI21")));
						}
						//delay 0
						else {
							score += 0;
						}

						//delay_var 100
						if ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI32"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 80-100
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI32"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI33"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI33")))) * 20) / ((Double.parseDouble(pros.getValue("trI32")) - (Double.parseDouble(pros.getValue("trI33"))))))) * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 60-80
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI33"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI34"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI34")))) * 20) / ((Double.parseDouble(pros.getValue("trI33")) - (Double.parseDouble(pros.getValue("trI34"))))))) * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 40-60
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI34"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI35"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI35")))) * 20) / ((Double.parseDouble(pros.getValue("trI34")) - (Double.parseDouble(pros.getValue("trI35"))))))) * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 20-40
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI35"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI36"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI36")))) * 20) / ((Double.parseDouble(pros.getValue("trI35")) - (Double.parseDouble(pros.getValue("trI36"))))))) * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 0-20
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI36"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trI37"))) <= 0)) {
							score += ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI37")))) * 20) / ((Double.parseDouble(pros.getValue("trI36")) - (Double.parseDouble(pros.getValue("trI37")))))) * (Double.parseDouble(pros.getValue("trI31")));
						}
						//delay_var 0
						else {
							score += 0;
						}

						//jitter 100
						if ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI42"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 80-100
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI42"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI43"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trI43")))) * 20) / ((Double.parseDouble(pros.getValue("trI42")) - (Double.parseDouble(pros.getValue("trI43"))))))) * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 60-80
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI43"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI44"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trI44")))) * 20) / ((Double.parseDouble(pros.getValue("trI43")) - (Double.parseDouble(pros.getValue("trI44"))))))) * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 40-60
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI44"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI45"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trI45")))) * 20) / ((Double.parseDouble(pros.getValue("trI44")) - (Double.parseDouble(pros.getValue("trI45"))))))) * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 20-40
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI45"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI46"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trI46")))) * 20) / ((Double.parseDouble(pros.getValue("trI45")) - (Double.parseDouble(pros.getValue("trI46"))))))) * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 0-20
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI46"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trI47"))) <= 0)) {
							score += ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trI47")))) * 20) / ((Double.parseDouble(pros.getValue("trI46")) - (Double.parseDouble(pros.getValue("trI47")))))) * (Double.parseDouble(pros.getValue("trI41")));
						}
						//jitter 0
						else {
							score += 0;
						}


						//jitter_std 100
						if ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI52"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 80-100
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI52"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI53"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI53")))) * 20) / ((Double.parseDouble(pros.getValue("trI52")) - (Double.parseDouble(pros.getValue("trI53"))))))) * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 60-80
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI53"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI54"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI54")))) * 20) / ((Double.parseDouble(pros.getValue("trI53")) - (Double.parseDouble(pros.getValue("trI54"))))))) * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 40-60
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI54"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI55"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI55")))) * 20) / ((Double.parseDouble(pros.getValue("trI54")) - (Double.parseDouble(pros.getValue("trI55"))))))) * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 20-40
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI55"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI56"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI56")))) * 20) / ((Double.parseDouble(pros.getValue("trI55")) - (Double.parseDouble(pros.getValue("trI56"))))))) * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 0-20
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI56"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trI57"))) <= 0)) {
							score += ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trI57")))) * 20) / ((Double.parseDouble(pros.getValue("trI56")) - (Double.parseDouble(pros.getValue("trI57")))))) * (Double.parseDouble(pros.getValue("trI51")));
						}
						//jitter_std 0
						else {
							score += 0;
						}


						//jitter_var 100
						if ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI62"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 80-100
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI62"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI63"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI63")))) * 20) / ((Double.parseDouble(pros.getValue("trI62")) - (Double.parseDouble(pros.getValue("trI63"))))))) * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 60-80
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI63"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI64"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI64")))) * 20) / ((Double.parseDouble(pros.getValue("trI63")) - (Double.parseDouble(pros.getValue("trI64"))))))) * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 40-60
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI64"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI65"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI65")))) * 20) / ((Double.parseDouble(pros.getValue("trI64")) - (Double.parseDouble(pros.getValue("trI65"))))))) * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 20-40
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI65"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI66"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI66")))) * 20) / ((Double.parseDouble(pros.getValue("trI65")) - (Double.parseDouble(pros.getValue("trI66"))))))) * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 0-20
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI66"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trI67"))) <= 0)) {
							score += ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trI67")))) * 20) / ((Double.parseDouble(pros.getValue("trI66")) - (Double.parseDouble(pros.getValue("trI67")))))) * (Double.parseDouble(pros.getValue("trI61")));
						}
						//jitter_var 0
						else {
							score += 0;
						}

						//loss 100
						Double lossRate = tracertList.get(i).getLossRate()*100;
						if (lossRate.compareTo(Double.parseDouble(pros.getValue("trI72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 80-100
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trI72"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trI73"))) <= 0)) {
							score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trI73")))) * 20) / ((Double.parseDouble(pros.getValue("trI72")) - (Double.parseDouble(pros.getValue("trI73"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 60-80
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trI73"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trI74"))) <= 0)) {
							score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trI74")))) * 20) / ((Double.parseDouble(pros.getValue("trI73")) - (Double.parseDouble(pros.getValue("trI74"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 40-60
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trI74"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trI75"))) <= 0)) {
							score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trI75")))) * 20) / ((Double.parseDouble(pros.getValue("trI74")) - (Double.parseDouble(pros.getValue("trI75"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 20-40
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trI75"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trI76"))) <= 0)) {
							score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trI76")))) * 20) / ((Double.parseDouble(pros.getValue("trI75")) - (Double.parseDouble(pros.getValue("trI76"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 0-20
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trI76"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trI77"))) <= 0)) {
							score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trI77")))) * 20) / ((Double.parseDouble(pros.getValue("trI76")) - (Double.parseDouble(pros.getValue("trI77")))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 0
						else {
							score += 0;
						}

						System.out.println("TraceRoute Icmp:"+score);

						ScoreEntity icmpTracert = new ScoreEntity();
						icmpTracert.setId(tracertList.get(i).getId());
						icmpTracert.setCityName(tracertList.get(i).getCityName());
						icmpTracert.setCityId(tracertList.get(i).getCityId());
						icmpTracert.setCountyName(tracertList.get(i).getAreaName());
						icmpTracert.setCountyId(tracertList.get(i).getCountyId());
						icmpTracert.setProbeName(tracertList.get(i).getProbeName());
						icmpTracert.setProbeId(tracertList.get(i).getProbeId());
						icmpTracert.setServiceType(tracertList.get(i).getServiceType());
						icmpTracert.setTargetName(tracertList.get(i).getTargetName());
						icmpTracert.setTargetId(tracertList.get(i).getTargetId());
						icmpTracert.setPort(tracertList.get(i).getPort());
						icmpTracert.setAccessLayer(tracertList.get(i).getAccessLayer());
						icmpTracert.setRecordDate(tracertList.get(i).getRecordDate());
						icmpTracert.setRecordTime(tracertList.get(i).getRecordTime());
						icmpTracert.setExit(tracertList.get(i).getExit());
						RecordFailEntity failEntity = new RecordFailEntity();
						failEntity.setCityId(tracertList.get(i).getCityId());
						failEntity.setCountyId(tracertList.get(i).getCountyId());
						failEntity.setProbeId(tracertList.get(i).getProbeId());
						failEntity.setTargetId(tracertList.get(i).getTargetId());
						failEntity.setPort(tracertList.get(i).getPort());
						failEntity.setRecordDate(tracertList.get(i).getRecordDate());
						failEntity.setRecordTime(tracertList.get(i).getRecordTime());
						if(failMap.containsKey(failEntity)){
							icmpTracert.setFail(failMap.get(failEntity).getFail());
							icmpTracert.setTotal(failMap.get(failEntity).getTotal());
						}else{
							icmpTracert.setFail(tracertList.get(i).getFail());
							icmpTracert.setTotal(tracertList.get(i).getTotal());
						}
						double fail=(double)icmpTracert.getFail()/icmpTracert.getTotal();
						icmpTracert.setScore(score*(1-fail));
						icmpTracert.setTracertIcmpDelay(tracertList.get(i).getDelay());
						icmpTracert.setTracertIcmpDelayStd(tracertList.get(i).getDelayStd());
						icmpTracert.setTracertIcmpDelayVar(tracertList.get(i).getDelayVar());
						icmpTracert.setTracertIcmpJitter(tracertList.get(i).getJitter());
						icmpTracert.setTracertIcmpJitterStd(tracertList.get(i).getJitterStd());
						icmpTracert.setTracertIcmpJitterVar(tracertList.get(i).getJitterVar());
						icmpTracert.setTracertIcmpLossRate(tracertList.get(i).getLossRate());
						icmpTracert.setBase(Double.parseDouble(pros.getValue("tr_icmp")));

						tracertIcmp.add(icmpTracert);
					} catch (IOException e) {
					}
				}}
		}catch (IOException e) {
		}
		return tracertIcmp;
	}

	@Override
	public List<ScoreEntity> calculateTracertUdp(List<RecordHourTracertEntity> tracertList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> tracertUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",5);
			map.put("type",1);
			List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			for(int i=0;i<recordFail.size();i++){
				failMap.put(recordFail.get(i),recordFail.get(i));
			}
			for (int i = 0; i < tracertList.size(); i++) {
				double score = 0;
				//Tracert(Udp)
				if ((tracertList.get(i).getServiceType()) == 5) {
					try {
						//delay 100
						if ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT12"))) <= 0) {
							score = 100 * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 80-100
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT12"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT13"))) <= 0)) {
							score+= (80 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT13")))) * 20) / ((Double.parseDouble(pros.getValue("trT12")) - (Double.parseDouble(pros.getValue("trT13"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 60-80
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT13"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT14"))) <= 0)) {
							score+= (60 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT14")))) * 20) / ((Double.parseDouble(pros.getValue("trT13")) - (Double.parseDouble(pros.getValue("trT14"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT14"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT15"))) <= 0)) {
							score+= (40 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT15")))) * 20) / ((Double.parseDouble(pros.getValue("trT14")) - (Double.parseDouble(pros.getValue("trT15"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT15"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT16"))) <= 0)) {
							score+= (20 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT16")))) * 20) / ((Double.parseDouble(pros.getValue("trT15")) - (Double.parseDouble(pros.getValue("trT16"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT16"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT17"))) <= 0)) {
							score+= ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT17")))) * 20) / ((Double.parseDouble(pros.getValue("trT16")) - (Double.parseDouble(pros.getValue("trT17")))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 0
						else {
							score+= 0;
						}

						//delay_std 100
						if ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT22"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay_std 80-100
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT22"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT23"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT23")))) * 20) / ((Double.parseDouble(pros.getValue("trT22")) - (Double.parseDouble(pros.getValue("trT23"))))))) * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay_std 60-80
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT23"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT24"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT24")))) * 20) / ((Double.parseDouble(pros.getValue("trT23")) - (Double.parseDouble(pros.getValue("trT24"))))))) * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT24"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT25"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT25")))) * 20) / ((Double.parseDouble(pros.getValue("trT24")) - (Double.parseDouble(pros.getValue("trT25"))))))) * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT25"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT26"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT26")))) * 20) / ((Double.parseDouble(pros.getValue("trT25")) - (Double.parseDouble(pros.getValue("trT26"))))))) * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT26"))) > 0) && ((tracertList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("trT27"))) <= 0)) {
							score += ((((tracertList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT27")))) * 20) / ((Double.parseDouble(pros.getValue("trT26")) - (Double.parseDouble(pros.getValue("trT27")))))) * (Double.parseDouble(pros.getValue("trT21")));
						}
						//delay 0
						else {
							score += 0;
						}

						//delay_var 100
						if ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT32"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 80-100
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT32"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT33"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT33")))) * 20) / ((Double.parseDouble(pros.getValue("trT32")) - (Double.parseDouble(pros.getValue("trT33"))))))) * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 60-80
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT33"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT34"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT34")))) * 20) / ((Double.parseDouble(pros.getValue("trT33")) - (Double.parseDouble(pros.getValue("trT34"))))))) * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 40-60
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT34"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT35"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT35")))) * 20) / ((Double.parseDouble(pros.getValue("trT34")) - (Double.parseDouble(pros.getValue("trT35"))))))) * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 20-40
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT35"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT36"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT36")))) * 20) / ((Double.parseDouble(pros.getValue("trT35")) - (Double.parseDouble(pros.getValue("trT36"))))))) * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 0-20
						else if (((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT36"))) > 0) && ((tracertList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("trT37"))) <= 0)) {
							score += ((((tracertList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT37")))) * 20) / ((Double.parseDouble(pros.getValue("trT36")) - (Double.parseDouble(pros.getValue("trT37")))))) * (Double.parseDouble(pros.getValue("trT31")));
						}
						//delay_var 0
						else {
							score += 0;
						}

						//jitter 100
						if ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT42"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 80-100
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT42"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT43"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trT43")))) * 20) / ((Double.parseDouble(pros.getValue("trT42")) - (Double.parseDouble(pros.getValue("trT43"))))))) * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 60-80
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT43"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT44"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trT44")))) * 20) / ((Double.parseDouble(pros.getValue("trT43")) - (Double.parseDouble(pros.getValue("trT44"))))))) * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 40-60
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT44"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT45"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trT45")))) * 20) / ((Double.parseDouble(pros.getValue("trT44")) - (Double.parseDouble(pros.getValue("trT45"))))))) * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 20-40
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT45"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT46"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trT46")))) * 20) / ((Double.parseDouble(pros.getValue("trT45")) - (Double.parseDouble(pros.getValue("trT46"))))))) * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 0-20
						else if (((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT46"))) > 0) && ((tracertList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("trT47"))) <= 0)) {
							score += ((((tracertList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("trT47")))) * 20) / ((Double.parseDouble(pros.getValue("trT46")) - (Double.parseDouble(pros.getValue("trT47")))))) * (Double.parseDouble(pros.getValue("trT41")));
						}
						//jitter 0
						else {
							score += 0;
						}


						//jitter_std 100
						if ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT52"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 80-100
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT52"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT53"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT53")))) * 20) / ((Double.parseDouble(pros.getValue("trT52")) - (Double.parseDouble(pros.getValue("trT53"))))))) * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 60-80
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT53"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT54"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT54")))) * 20) / ((Double.parseDouble(pros.getValue("trT53")) - (Double.parseDouble(pros.getValue("trT54"))))))) * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 40-60
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT54"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT55"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT55")))) * 20) / ((Double.parseDouble(pros.getValue("trT54")) - (Double.parseDouble(pros.getValue("trT55"))))))) * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 20-40
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT55"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT56"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT56")))) * 20) / ((Double.parseDouble(pros.getValue("trT55")) - (Double.parseDouble(pros.getValue("trT56"))))))) * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 0-20
						else if (((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT56"))) > 0) && ((tracertList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("trT57"))) <= 0)) {
							score += ((((tracertList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("trT57")))) * 20) / ((Double.parseDouble(pros.getValue("trT56")) - (Double.parseDouble(pros.getValue("trT57")))))) * (Double.parseDouble(pros.getValue("trT51")));
						}
						//jitter_std 0
						else {
							score += 0;
						}


						//jitter_var 100
						if ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT62"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 80-100
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT62"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT63"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT63")))) * 20) / ((Double.parseDouble(pros.getValue("trT62")) - (Double.parseDouble(pros.getValue("trT63"))))))) * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 60-80
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT63"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT64"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT64")))) * 20) / ((Double.parseDouble(pros.getValue("trT63")) - (Double.parseDouble(pros.getValue("trT64"))))))) * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 40-60
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT64"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT65"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT65")))) * 20) / ((Double.parseDouble(pros.getValue("trT64")) - (Double.parseDouble(pros.getValue("trT65"))))))) * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 20-40
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT65"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT66"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT66")))) * 20) / ((Double.parseDouble(pros.getValue("trT65")) - (Double.parseDouble(pros.getValue("trT66"))))))) * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 0-20
						else if (((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT66"))) > 0) && ((tracertList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("trT67"))) <= 0)) {
							score += ((((tracertList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("trT67")))) * 20) / ((Double.parseDouble(pros.getValue("trT66")) - (Double.parseDouble(pros.getValue("trT67")))))) * (Double.parseDouble(pros.getValue("trT61")));
						}
						//jitter_var 0
						else {
							score += 0;
						}

						Double lossRate = tracertList.get(i).getLossRate()*100;
						//loss 100
						if (lossRate.compareTo(Double.parseDouble(pros.getValue("trT72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 80-100
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trT72"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trT73"))) <= 0)) {
							score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trT73")))) * 20) / ((Double.parseDouble(pros.getValue("trT72")) - (Double.parseDouble(pros.getValue("trT73"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 60-80
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trT73"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trT74"))) <= 0)) {
							score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trT74")))) * 20) / ((Double.parseDouble(pros.getValue("trT73")) - (Double.parseDouble(pros.getValue("trT74"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 40-60
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trT74"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trT75"))) <= 0)) {
							score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trT75")))) * 20) / ((Double.parseDouble(pros.getValue("trT74")) - (Double.parseDouble(pros.getValue("trT75"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 20-40
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trT75"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trT76"))) <= 0)) {
							score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trT76")))) * 20) / ((Double.parseDouble(pros.getValue("trT75")) - (Double.parseDouble(pros.getValue("trT76"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 0-20
						else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("trT76"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("trT77"))) <= 0)) {
							score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("trT77")))) * 20) / ((Double.parseDouble(pros.getValue("trT76")) - (Double.parseDouble(pros.getValue("trT77")))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 0
						else {
							score += 0;
						}

						System.out.println("TraceRoute Udp:"+score);

						ScoreEntity tcpTracert = new ScoreEntity();
						tcpTracert.setId(tracertList.get(i).getId());
						tcpTracert.setCityName(tracertList.get(i).getCityName());
						tcpTracert.setCityId(tracertList.get(i).getCityId());
						tcpTracert.setCountyName(tracertList.get(i).getAreaName());
						tcpTracert.setCountyId(tracertList.get(i).getCountyId());
						tcpTracert.setProbeName(tracertList.get(i).getProbeName());
						tcpTracert.setProbeId(tracertList.get(i).getProbeId());
						tcpTracert.setServiceType(tracertList.get(i).getServiceType());
						tcpTracert.setTargetName(tracertList.get(i).getTargetName());
						tcpTracert.setTargetId(tracertList.get(i).getTargetId());
						tcpTracert.setPort(tracertList.get(i).getPort());
						tcpTracert.setAccessLayer(tracertList.get(i).getAccessLayer());
						tcpTracert.setRecordDate(tracertList.get(i).getRecordDate());
						tcpTracert.setRecordTime(tracertList.get(i).getRecordTime());
						tcpTracert.setExit(tracertList.get(i).getExit());
						RecordFailEntity failEntity = new RecordFailEntity();
						failEntity.setCityId(tracertList.get(i).getCityId());
						failEntity.setCountyId(tracertList.get(i).getCountyId());
						failEntity.setProbeId(tracertList.get(i).getProbeId());
						failEntity.setTargetId(tracertList.get(i).getTargetId());
						failEntity.setPort(tracertList.get(i).getPort());
						failEntity.setRecordDate(tracertList.get(i).getRecordDate());
						failEntity.setRecordTime(tracertList.get(i).getRecordTime());
						if(failMap.containsKey(failEntity)){
							tcpTracert.setFail(failMap.get(failEntity).getFail());
							tcpTracert.setTotal(failMap.get(failEntity).getTotal());
						}else{
							tcpTracert.setFail(tracertList.get(i).getFail());
							tcpTracert.setTotal(tracertList.get(i).getTotal());
						}
						tcpTracert.setTracertTcpDelay(tracertList.get(i).getDelay());
						tcpTracert.setTracertTcpDelayStd(tracertList.get(i).getDelayStd());
						tcpTracert.setTracertTcpDelayVar(tracertList.get(i).getDelayVar());
						tcpTracert.setTracertTcpJitter(tracertList.get(i).getJitter());
						tcpTracert.setTracertTcpJitterStd(tracertList.get(i).getJitterStd());
						tcpTracert.setTracertTcpJitterVar(tracertList.get(i).getJitterVar());
						tcpTracert.setTracertTcpLossRate(tracertList.get(i).getLossRate());
						double fail = (double) tcpTracert.getFail()/tcpTracert.getTotal();
						tcpTracert.setScore(score*(1-fail));
						tcpTracert.setBase(Double.parseDouble(pros.getValue("tr_tcp")));

						tracertUdp.add(tcpTracert);
					} catch (IOException e) {
					}
				}}
		}catch (IOException e) {
		}
		return tracertUdp;
	}

	@Override
	//For quality rank page
	public List<ScoreEntity> calculateService1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreTargetEntity scoreTarget = new ScoreTargetEntity();
				scoreTarget.setCityId(pingIcmp.get(i).getCityId());
				scoreTarget.setCountyId(pingIcmp.get(i).getCountyId());
				scoreTarget.setProbeId(pingIcmp.get(i).getProbeId());
				scoreTarget.setTargetId(pingIcmp.get(i).getTargetId());
				scoreTarget.setCityName(pingIcmp.get(i).getCityName());
				scoreTarget.setCountyName(pingIcmp.get(i).getCountyName());
				scoreTarget.setProbeName(pingIcmp.get(i).getProbeName());
				scoreTarget.setTargetName(pingIcmp.get(i).getTargetName());
				scoreTarget.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				scoreTarget.setPort(pingIcmp.get(i).getPort());
				scoreTarget.setRecordDate(pingIcmp.get(i).getRecordDate());
				scoreTarget.setRecordTime(pingIcmp.get(i).getRecordTime());
				scoreTarget.setFail(pingIcmp.get(i).getFail());
				scoreTarget.setTotal(pingIcmp.get(i).getTotal());
				scoreTarget.setExit(pingIcmp.get(i).getExit());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setPingIcmpDelay(pingIcmp.get(i).getPingIcmpDelay());
				scoreBase.setPingIcmpDelayStd(pingIcmp.get(i).getPingIcmpDelayStd());
				scoreBase.setPingIcmpDelayVar(pingIcmp.get(i).getPingIcmpDelayVar());
				scoreBase.setPingIcmpJitter(pingIcmp.get(i).getPingIcmpJitter());
				scoreBase.setPingIcmpJitterStd(pingIcmp.get(i).getPingIcmpJitterStd());
				scoreBase.setPingIcmpJitterVar(pingIcmp.get(i).getPingIcmpJitterVar());
				scoreBase.setPingIcmpLossRate(pingIcmp.get(i).getPingIcmpLossRate());
				scoreBase.setPingIcmpFail(pingIcmp.get(i).getFail());
				scoreBase.setPingIcmpTotal(pingIcmp.get(i).getTotal());
				scoreBase.setScore(pingIcmp.get(i).getScore());
				scoreBase.setBase(pingIcmp.get(i).getBase());
				Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
				ping1.put("pingIcmp",scoreBase);
				connection.put(scoreTarget,ping1 );
			}
			connection=putMap(pingTcp,connection,"pingTcp");
			connection=putMap(pingUdp,connection,"pingUdp");
			connection=putMap(tracertIcmp,connection,"tracertIcmp");
			connection=putMap(tracertUdp,connection,"tracertUdp");

			System.out.println("MAP:"+connection);

			Set<ScoreTargetEntity> key = connection.keySet();
			Iterator<ScoreTargetEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreTargetEntity ite = iterator.next();
				try {
					ScoreEntity finalScore = new ScoreEntity();
					finalScore.setId(id);
					finalScore.setCityId(ite.getCityId());
					finalScore.setCityName(ite.getCityName());
					finalScore.setCountyId(ite.getCountyId());
					finalScore.setCountyName(ite.getCountyName());
					finalScore.setProbeId(ite.getProbeId());
					finalScore.setProbeName(ite.getProbeName());
					finalScore.setServiceType(1);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setExit(ite.getExit());
					finalScore.setScore(0.0);
					finalScore.setBase(0.0);
					finalScore.setFail(ite.getFail());
					finalScore.setTotal(ite.getTotal());
					Map<String, ScoreBaseEntity> map1 = connection.get(ite);
					Set<String> keyType = map1.keySet();
					Iterator<String> iterator1 = keyType.iterator();
					int i=1;
					while(iterator1.hasNext()) {
						String typ = iterator1.next();
						if (typ.equals("pingIcmp")) {
							finalScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
							finalScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
							finalScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
							finalScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
							finalScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
							finalScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
							finalScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
							finalScore.setPingIcmpFail(map1.get(typ).getPingIcmpFail());
							finalScore.setPingIcmpTotal(map1.get(typ).getPingIcmpTotal());
						} else if (typ.equals("pingTcp")) {
							finalScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
							finalScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
							finalScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
							finalScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
							finalScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
							finalScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
							finalScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
							finalScore.setPingTcpFail(map1.get(typ).getPingTcpFail());
							finalScore.setPingTcpTotal(map1.get(typ).getPingTcpTotal());
						} else if (typ.equals("pingUdp")) {
							finalScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
							finalScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
							finalScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
							finalScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
							finalScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
							finalScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
							finalScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
							finalScore.setPingUdpFail(map1.get(typ).getPingUdpFail());
							finalScore.setPingUdpTotal(map1.get(typ).getPingUdpTotal());
						} else if (typ.equals("tracertIcmp")) {
							finalScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
							finalScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
							finalScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
							finalScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
							finalScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
							finalScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
							finalScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
							finalScore.setTracertIcmpFail(map1.get(typ).getTracertIcmpFail());
							finalScore.setTracertIcmpTotal(map1.get(typ).getTracertIcmpTotal());
						} else if (typ.equals("tracertUdp")) {
							finalScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
							finalScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
							finalScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
							finalScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
							finalScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
							finalScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
							finalScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
							finalScore.setTracertUdpFail(map1.get(typ).getTracertUdpFail());
							finalScore.setTracertUdpTotal(map1.get(typ).getTracertUdpTotal());
						} else {
						}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("connectionweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateDate1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreDateEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreDateEntity scoreDate = new ScoreDateEntity();
				scoreDate.setCityId(pingIcmp.get(i).getCityId());
				scoreDate.setCountyId(pingIcmp.get(i).getCountyId());
				scoreDate.setProbeId(pingIcmp.get(i).getProbeId());
				scoreDate.setTargetId(pingIcmp.get(i).getTargetId());
				scoreDate.setCityName(pingIcmp.get(i).getCityName());
				scoreDate.setCountyName(pingIcmp.get(i).getCountyName());
				scoreDate.setProbeName(pingIcmp.get(i).getProbeName());
				scoreDate.setTargetName(pingIcmp.get(i).getTargetName());
				scoreDate.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				scoreDate.setRecordDate(pingIcmp.get(i).getRecordDate());
				scoreDate.setRecordTime(pingIcmp.get(i).getRecordTime());
				scoreDate.setPort(pingIcmp.get(i).getPort());
				scoreDate.setFail(pingIcmp.get(i).getFail());
				scoreDate.setTotal(pingIcmp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setPingIcmpDelay(pingIcmp.get(i).getPingIcmpDelay());
				scoreBase.setPingIcmpDelayStd(pingIcmp.get(i).getPingIcmpDelayStd());
				scoreBase.setPingIcmpDelayVar(pingIcmp.get(i).getPingIcmpDelayVar());
				scoreBase.setPingIcmpJitter(pingIcmp.get(i).getPingIcmpJitter());
				scoreBase.setPingIcmpJitterStd(pingIcmp.get(i).getPingIcmpJitterStd());
				scoreBase.setPingIcmpJitterVar(pingIcmp.get(i).getPingIcmpJitterVar());
				scoreBase.setPingIcmpLossRate(pingIcmp.get(i).getPingIcmpLossRate());
				scoreBase.setPingIcmpFail(pingIcmp.get(i).getFail());
				scoreBase.setPingIcmpTotal(pingIcmp.get(i).getTotal());
				scoreBase.setScore(pingIcmp.get(i).getScore());
				scoreBase.setBase(pingIcmp.get(i).getBase());
				Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
				ping1.put("pingIcmp",scoreBase);
				connection.put(scoreDate,ping1 );
			}
			connection=putMapDate(pingTcp,connection,"pingTcp");
			connection=putMapDate(pingUdp,connection,"pingUdp");
			connection=putMapDate(tracertIcmp,connection,"tracertIcmp");
			connection=putMapDate(tracertUdp,connection,"tracertUdp");

			Set<ScoreDateEntity> key = connection.keySet();
			Iterator<ScoreDateEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreDateEntity ite = iterator.next();
				try {
					ScoreEntity finalScore = new ScoreEntity();
					finalScore.setId(id);
					finalScore.setCityId(ite.getCityId());
					finalScore.setCityName(ite.getCityName());
					finalScore.setCountyId(ite.getCountyId());
					finalScore.setCountyName(ite.getCountyName());
					finalScore.setProbeId(ite.getProbeId());
					finalScore.setProbeName(ite.getProbeName());
					finalScore.setServiceType(1);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setScore(0.0);
					finalScore.setBase(0.0);
					finalScore.setFail(ite.getFail());
					finalScore.setTotal(ite.getTotal());
					Map<String, ScoreBaseEntity> map1 = connection.get(ite);
					Set<String> keyType = map1.keySet();
					Iterator<String> iterator1 = keyType.iterator();
					int i=1;
					while(iterator1.hasNext()) {
						String typ = iterator1.next();
						if (typ.equals("pingIcmp")) {
							finalScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
							finalScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
							finalScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
							finalScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
							finalScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
							finalScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
							finalScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
							finalScore.setPingIcmpFail(map1.get(typ).getPingIcmpFail());
							finalScore.setPingIcmpTotal(map1.get(typ).getPingIcmpTotal());
						} else if (typ.equals("pingTcp")) {
							finalScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
							finalScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
							finalScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
							finalScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
							finalScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
							finalScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
							finalScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
							finalScore.setPingTcpFail(map1.get(typ).getPingTcpFail());
							finalScore.setPingTcpTotal(map1.get(typ).getPingTcpTotal());
						} else if (typ.equals("pingUdp")) {
							finalScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
							finalScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
							finalScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
							finalScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
							finalScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
							finalScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
							finalScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
							finalScore.setPingUdpFail(map1.get(typ).getPingUdpFail());
							finalScore.setPingUdpTotal(map1.get(typ).getPingUdpTotal());
						} else if (typ.equals("tracertIcmp")) {
							finalScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
							finalScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
							finalScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
							finalScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
							finalScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
							finalScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
							finalScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
							finalScore.setTracertIcmpFail(map1.get(typ).getTracertIcmpFail());
							finalScore.setTracertIcmpTotal(map1.get(typ).getTracertIcmpTotal());
						} else if (typ.equals("tracertUdp")) {
							finalScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
							finalScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
							finalScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
							finalScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
							finalScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
							finalScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
							finalScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
							finalScore.setTracertUdpFail(map1.get(typ).getTracertUdpFail());
							finalScore.setTracertUdpTotal(map1.get(typ).getTracertUdpTotal());
						} else {
						}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("connectionweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateLayer1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreLayerEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				if(pingIcmp.get(i).getAccessLayer()==null){
					continue;
				}
				ScoreLayerEntity scoreLayer = new ScoreLayerEntity();
				scoreLayer.setCityId(pingIcmp.get(i).getCityId());
				scoreLayer.setCountyId(pingIcmp.get(i).getCountyId());
				scoreLayer.setProbeId(pingIcmp.get(i).getProbeId());
				scoreLayer.setTargetId(pingIcmp.get(i).getTargetId());
				scoreLayer.setCityName(pingIcmp.get(i).getCityName());
				scoreLayer.setCountyName(pingIcmp.get(i).getCountyName());
				scoreLayer.setProbeName(pingIcmp.get(i).getProbeName());
				scoreLayer.setTargetName(pingIcmp.get(i).getTargetName());
				scoreLayer.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				scoreLayer.setRecordDate(pingIcmp.get(i).getRecordDate());
				scoreLayer.setRecordTime(pingIcmp.get(i).getRecordTime());
				scoreLayer.setPort(pingIcmp.get(i).getPort());
				scoreLayer.setFail(pingIcmp.get(i).getFail());
				scoreLayer.setTotal(pingIcmp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setPingIcmpDelay(pingIcmp.get(i).getPingIcmpDelay());
				scoreBase.setPingIcmpDelayStd(pingIcmp.get(i).getPingIcmpDelayStd());
				scoreBase.setPingIcmpDelayVar(pingIcmp.get(i).getPingIcmpDelayVar());
				scoreBase.setPingIcmpJitter(pingIcmp.get(i).getPingIcmpJitter());
				scoreBase.setPingIcmpJitterStd(pingIcmp.get(i).getPingIcmpJitterStd());
				scoreBase.setPingIcmpJitterVar(pingIcmp.get(i).getPingIcmpJitterVar());
				scoreBase.setPingIcmpLossRate(pingIcmp.get(i).getPingIcmpLossRate());
				scoreBase.setIcmpPingScore(pingIcmp.get(i).getScore());
				scoreBase.setPingIcmpFail(pingIcmp.get(i).getFail());
				scoreBase.setPingIcmpTotal(pingIcmp.get(i).getTotal());
				scoreBase.setScore(pingIcmp.get(i).getScore());
				scoreBase.setBase(pingIcmp.get(i).getBase());
				Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
				ping1.put("pingIcmp",scoreBase);
				connection.put(scoreLayer,ping1 );
			}
			connection=putMapLayer(pingTcp,connection,"pingTcp");
			connection=putMapLayer(pingUdp,connection,"pingUdp");
			connection=putMapLayer(tracertIcmp,connection,"tracertIcmp");
			connection=putMapLayer(tracertUdp,connection,"tracertUdp");

			Set<ScoreLayerEntity> key = connection.keySet();
			Iterator<ScoreLayerEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreLayerEntity ite = iterator.next();
				try {
					ScoreEntity finalScore = new ScoreEntity();
					finalScore.setId(id);
					finalScore.setCityId(ite.getCityId());
					finalScore.setCityName(ite.getCityName());
					finalScore.setCountyId(ite.getCountyId());
					finalScore.setCountyName(ite.getCountyName());
					finalScore.setProbeId(ite.getProbeId());
					finalScore.setProbeName(ite.getProbeName());
					finalScore.setServiceType(1);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setScore(0.0);
					finalScore.setBase(0.0);
					finalScore.setFail(ite.getFail());
					finalScore.setTotal(ite.getTotal());
					Map<String, ScoreBaseEntity> map1 = connection.get(ite);
					Set<String> keyType = map1.keySet();
					Iterator<String> iterator1 = keyType.iterator();
					int i=1;
					while(iterator1.hasNext()) {
						String typ = iterator1.next();
						if (typ.equals("pingIcmp")) {
							finalScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
							finalScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
							finalScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
							finalScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
							finalScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
							finalScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
							finalScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
							finalScore.setIcmpPingScore(map1.get(typ).getIcmpPingScore());
							finalScore.setPingIcmpFail(map1.get(typ).getPingIcmpFail());
							finalScore.setPingIcmpTotal(map1.get(typ).getPingIcmpTotal());
						} else if (typ.equals("pingTcp")) {
							finalScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
							finalScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
							finalScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
							finalScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
							finalScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
							finalScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
							finalScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
							finalScore.setTcpPingScore(map1.get(typ).getTcpPingScore());
							finalScore.setPingTcpFail(map1.get(typ).getPingTcpFail());
							finalScore.setPingTcpTotal(map1.get(typ).getPingTcpTotal());
						} else if (typ.equals("pingUdp")) {
							finalScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
							finalScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
							finalScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
							finalScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
							finalScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
							finalScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
							finalScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
							finalScore.setUdpPingScore(map1.get(typ).getUdpPingScore());
							finalScore.setPingUdpFail(map1.get(typ).getPingUdpFail());
							finalScore.setPingUdpTotal(map1.get(typ).getPingUdpTotal());
						} else if (typ.equals("tracertIcmp")) {
							finalScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
							finalScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
							finalScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
							finalScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
							finalScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
							finalScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
							finalScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
							finalScore.setIcmpTracertScore(map1.get(typ).getIcmpTracertScore());
							finalScore.setTracertIcmpFail(map1.get(typ).getTracertIcmpFail());
							finalScore.setTracertIcmpTotal(map1.get(typ).getTracertIcmpTotal());
						} else if (typ.equals("tracertUdp")) {
							finalScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
							finalScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
							finalScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
							finalScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
							finalScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
							finalScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
							finalScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
							finalScore.setUdpTracertScore(map1.get(typ).getUdpTracertScore());
							finalScore.setTracertUdpFail(map1.get(typ).getTracertUdpFail());
							finalScore.setTracertUdpTotal(map1.get(typ).getTracertUdpTotal());
						} else {
						}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("connectionweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}



	@Override
	//For quality rank page
	public List<ScoreEntity> calculateArea1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreAreaEntity scoreArea = new ScoreAreaEntity();
				scoreArea.setCityId(pingIcmp.get(i).getCityId());
				scoreArea.setCountyId(pingIcmp.get(i).getCountyId());
				scoreArea.setProbeId(pingIcmp.get(i).getProbeId());
				scoreArea.setTargetId(pingIcmp.get(i).getTargetId());
				scoreArea.setCityName(pingIcmp.get(i).getCityName());
				scoreArea.setCountyName(pingIcmp.get(i).getCountyName());
				scoreArea.setProbeName(pingIcmp.get(i).getProbeName());
				scoreArea.setTargetName(pingIcmp.get(i).getTargetName());
				scoreArea.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				scoreArea.setRecordDate(pingIcmp.get(i).getRecordDate());
				scoreArea.setRecordTime(pingIcmp.get(i).getRecordTime());
				scoreArea.setPort(pingIcmp.get(i).getPort());
				scoreArea.setFail(pingIcmp.get(i).getFail());
				scoreArea.setTotal(pingIcmp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setPingIcmpDelay(pingIcmp.get(i).getPingIcmpDelay());
				scoreBase.setPingIcmpDelayStd(pingIcmp.get(i).getPingIcmpDelayStd());
				scoreBase.setPingIcmpDelayVar(pingIcmp.get(i).getPingIcmpDelayVar());
				scoreBase.setPingIcmpJitter(pingIcmp.get(i).getPingIcmpJitter());
				scoreBase.setPingIcmpJitterStd(pingIcmp.get(i).getPingIcmpJitterStd());
				scoreBase.setPingIcmpJitterVar(pingIcmp.get(i).getPingIcmpJitterVar());
				scoreBase.setPingIcmpLossRate(pingIcmp.get(i).getPingIcmpLossRate());
				scoreBase.setPingIcmpTotal(pingIcmp.get(i).getTotal());
				scoreBase.setPingIcmpFail(pingIcmp.get(i).getFail());
				scoreBase.setScore(pingIcmp.get(i).getScore());
				scoreBase.setBase(pingIcmp.get(i).getBase());
				Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
				ping1.put("pingIcmp",scoreBase);
				connection.put(scoreArea,ping1 );
			}
			connection=putMapArea(pingTcp,connection,"pingTcp");
			connection=putMapArea(pingUdp,connection,"pingUdp");
			connection=putMapArea(tracertIcmp,connection,"tracertIcmp");
			connection=putMapArea(tracertUdp,connection,"tracertUdp");

			System.out.println("MAP:"+connection);

			Set<ScoreAreaEntity> key = connection.keySet();
			Iterator<ScoreAreaEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreAreaEntity ite = iterator.next();
				try {
					ScoreEntity finalScore = new ScoreEntity();
					finalScore.setId(id);
					finalScore.setCityId(ite.getCityId());
					finalScore.setCityName(ite.getCityName());
					finalScore.setCountyId(ite.getCountyId());
					finalScore.setCountyName(ite.getCountyName());
					finalScore.setProbeId(ite.getProbeId());
					finalScore.setProbeName(ite.getProbeName());
					finalScore.setServiceType(1);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setScore(0.0);
					finalScore.setBase(0.0);
					finalScore.setFail(ite.getFail());
					finalScore.setTotal(ite.getTotal());
					Map<String, ScoreBaseEntity> map1 = connection.get(ite);
					Set<String> keyType = map1.keySet();
					Iterator<String> iterator1 = keyType.iterator();
					int i=1;
					while(iterator1.hasNext()) {
						String typ = iterator1.next();
						if (typ.equals("pingIcmp")) {
							finalScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
							finalScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
							finalScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
							finalScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
							finalScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
							finalScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
							finalScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
							finalScore.setPingIcmpFail(map1.get(typ).getPingIcmpFail());
							finalScore.setPingIcmpTotal(map1.get(typ).getPingIcmpTotal());
						} else if (typ.equals("pingTcp")) {
							finalScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
							finalScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
							finalScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
							finalScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
							finalScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
							finalScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
							finalScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
							finalScore.setPingTcpFail(map1.get(typ).getPingTcpFail());
							finalScore.setPingTcpTotal(map1.get(typ).getPingTcpTotal());
						} else if (typ.equals("pingUdp")) {
							finalScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
							finalScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
							finalScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
							finalScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
							finalScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
							finalScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
							finalScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
							finalScore.setPingUdpFail(map1.get(typ).getPingUdpFail());
							finalScore.setPingUdpTotal(map1.get(typ).getPingUdpTotal());
						} else if (typ.equals("tracertIcmp")) {
							finalScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
							finalScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
							finalScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
							finalScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
							finalScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
							finalScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
							finalScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
							finalScore.setTracertIcmpFail(map1.get(typ).getTracertIcmpFail());
							finalScore.setTracertIcmpTotal(map1.get(typ).getTracertIcmpTotal());
						} else if (typ.equals("tracertUdp")) {
							finalScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
							finalScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
							finalScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
							finalScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
							finalScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
							finalScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
							finalScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
							finalScore.setTracertUdpFail(map1.get(typ).getTracertUdpFail());
							finalScore.setTracertUdpTotal(map1.get(typ).getTracertUdpTotal());
						} else {
						}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("connectionweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}

	@Override
	//For quality rank page
	public List<ScoreEntity> calculateTarget1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetRankEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreTargetRankEntity targetRank = new ScoreTargetRankEntity();
				targetRank.setCityId(pingIcmp.get(i).getCityId());
				targetRank.setCountyId(pingIcmp.get(i).getCountyId());
				targetRank.setProbeId(pingIcmp.get(i).getProbeId());
				targetRank.setTargetId(pingIcmp.get(i).getTargetId());
				targetRank.setCityName(pingIcmp.get(i).getCityName());
				targetRank.setCountyName(pingIcmp.get(i).getCountyName());
				targetRank.setProbeName(pingIcmp.get(i).getProbeName());
				targetRank.setTargetName(pingIcmp.get(i).getTargetName());
				targetRank.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				targetRank.setRecordDate(pingIcmp.get(i).getRecordDate());
				targetRank.setRecordTime(pingIcmp.get(i).getRecordTime());
				targetRank.setPort(pingIcmp.get(i).getPort());
				targetRank.setFail(pingIcmp.get(i).getFail());
				targetRank.setTotal(pingIcmp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setPingIcmpDelay(pingIcmp.get(i).getPingIcmpDelay());
				scoreBase.setPingIcmpDelayStd(pingIcmp.get(i).getPingIcmpDelayStd());
				scoreBase.setPingIcmpDelayVar(pingIcmp.get(i).getPingIcmpDelayVar());
				scoreBase.setPingIcmpJitter(pingIcmp.get(i).getPingIcmpJitter());
				scoreBase.setPingIcmpJitterStd(pingIcmp.get(i).getPingIcmpJitterStd());
				scoreBase.setPingIcmpJitterVar(pingIcmp.get(i).getPingIcmpJitterVar());
				scoreBase.setPingIcmpLossRate(pingIcmp.get(i).getPingIcmpLossRate());
				scoreBase.setPingIcmpFail(pingIcmp.get(i).getFail());
				scoreBase.setPingIcmpTotal(pingIcmp.get(i).getTotal());
				scoreBase.setScore(pingIcmp.get(i).getScore());
				scoreBase.setBase(pingIcmp.get(i).getBase());
				Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
				ping1.put("pingIcmp",scoreBase);
				connection.put(targetRank,ping1 );
			}
			connection=putMapTarget(pingTcp,connection,"pingTcp");
			connection=putMapTarget(pingUdp,connection,"pingUdp");
			connection=putMapTarget(tracertIcmp,connection,"tracertIcmp");
			connection=putMapTarget(tracertUdp,connection,"tracertUdp");

			System.out.println("MAP:"+connection);

			Set<ScoreTargetRankEntity> key = connection.keySet();
			Iterator<ScoreTargetRankEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreTargetRankEntity ite = iterator.next();
				try {
					ScoreEntity finalScore = new ScoreEntity();
					finalScore.setId(id);
					finalScore.setCityId(ite.getCityId());
					finalScore.setCityName(ite.getCityName());
					finalScore.setCountyId(ite.getCountyId());
					finalScore.setCountyName(ite.getCountyName());
					finalScore.setProbeId(ite.getProbeId());
					finalScore.setProbeName(ite.getProbeName());
					finalScore.setServiceType(1);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setScore(0.0);
					finalScore.setBase(0.0);
					finalScore.setFail(ite.getFail());
					finalScore.setTotal(ite.getTotal());
					Map<String, ScoreBaseEntity> map1 = connection.get(ite);
					Set<String> keyType = map1.keySet();
					Iterator<String> iterator1 = keyType.iterator();
					int i=1;
					while(iterator1.hasNext()) {
						String typ = iterator1.next();
						if (typ.equals("pingIcmp")) {
							finalScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
							finalScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
							finalScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
							finalScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
							finalScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
							finalScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
							finalScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
							finalScore.setPingIcmpFail(map1.get(typ).getPingIcmpFail());
							finalScore.setPingIcmpTotal(map1.get(typ).getPingIcmpTotal());
						} else if (typ.equals("pingTcp")) {
							finalScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
							finalScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
							finalScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
							finalScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
							finalScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
							finalScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
							finalScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
							finalScore.setPingTcpFail(map1.get(typ).getPingTcpFail());
							finalScore.setPingTcpTotal(map1.get(typ).getPingTcpTotal());
						} else if (typ.equals("pingUdp")) {
							finalScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
							finalScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
							finalScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
							finalScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
							finalScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
							finalScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
							finalScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
							finalScore.setPingUdpFail(map1.get(typ).getPingUdpFail());
							finalScore.setPingUdpTotal(map1.get(typ).getPingUdpTotal());
						} else if (typ.equals("tracertIcmp")) {
							finalScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
							finalScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
							finalScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
							finalScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
							finalScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
							finalScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
							finalScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
							finalScore.setTracertIcmpFail(map1.get(typ).getTracertIcmpFail());
							finalScore.setTracertIcmpTotal(map1.get(typ).getTracertIcmpTotal());
						} else if (typ.equals("tracertUdp")) {
							finalScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
							finalScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
							finalScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
							finalScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
							finalScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
							finalScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
							finalScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
							finalScore.setTracertUdpFail(map1.get(typ).getTracertUdpFail());
							finalScore.setTracertUdpTotal(map1.get(typ).getTracertUdpTotal());
						} else {
						}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("connectionweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}


	//Map Function
	public Map<ScoreTargetEntity,Map<String,ScoreBaseEntity>> putMap(List<ScoreEntity> list,Map<ScoreTargetEntity,Map<String,ScoreBaseEntity>> map,String type){
		for (int i = 0; i < list.size(); i++) {
			ScoreTargetEntity scoreTarget = new ScoreTargetEntity();
			scoreTarget.setCityId(list.get(i).getCityId());
			scoreTarget.setCountyId(list.get(i).getCountyId());
			scoreTarget.setProbeId(list.get(i).getProbeId());
			scoreTarget.setTargetId(list.get(i).getTargetId());
			scoreTarget.setCityName(list.get(i).getCityName());
			scoreTarget.setCountyName(list.get(i).getCountyName());
			scoreTarget.setProbeName(list.get(i).getProbeName());
			scoreTarget.setTargetName(list.get(i).getTargetName());
			scoreTarget.setAccessLayer(list.get(i).getAccessLayer());
			scoreTarget.setPort(list.get(i).getPort());
			scoreTarget.setRecordDate(list.get(i).getRecordDate());
			scoreTarget.setRecordTime(list.get(i).getRecordTime());
			scoreTarget.setFail(list.get(i).getFail());
			scoreTarget.setTotal(list.get(i).getTotal());
			scoreTarget.setExit(list.get(i).getExit());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("pingTcp")){
				scoreBase.setPingTcpDelay(list.get(i).getPingTcpDelay());
				scoreBase.setPingTcpDelayStd(list.get(i).getPingTcpDelayStd());
				scoreBase.setPingTcpDelayVar(list.get(i).getPingTcpDelayVar());
				scoreBase.setPingTcpJitter(list.get(i).getPingTcpJitter());
				scoreBase.setPingTcpJitterStd(list.get(i).getPingTcpJitterStd());
				scoreBase.setPingTcpJitterVar(list.get(i).getPingTcpJitterVar());
				scoreBase.setPingTcpLossRate(list.get(i).getPingTcpLossRate());
				scoreBase.setPingTcpFail(list.get(i).getFail());
				scoreBase.setPingTcpTotal(list.get(i).getTotal());
			}else if(type.equals("pingUdp")){
				scoreBase.setPingUdpDelay(list.get(i).getPingUdpDelay());
				scoreBase.setPingUdpDelayStd(list.get(i).getPingUdpDelayStd());
				scoreBase.setPingUdpDelayVar(list.get(i).getPingUdpDelayVar());
				scoreBase.setPingUdpJitter(list.get(i).getPingUdpJitter());
				scoreBase.setPingUdpJitterStd(list.get(i).getPingUdpJitterStd());
				scoreBase.setPingUdpJitterVar(list.get(i).getPingUdpJitterVar());
				scoreBase.setPingUdpLossRate(list.get(i).getPingUdpLossRate());
				scoreBase.setPingUdpFail(list.get(i).getFail());
				scoreBase.setPingUdpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertIcmp")){
				scoreBase.setTracertIcmpDelay(list.get(i).getTracertIcmpDelay());
				scoreBase.setTracertIcmpDelayStd(list.get(i).getTracertIcmpDelayStd());
				scoreBase.setTracertIcmpDelayVar(list.get(i).getTracertIcmpDelayVar());
				scoreBase.setTracertIcmpJitter(list.get(i).getTracertIcmpJitter());
				scoreBase.setTracertIcmpJitterStd(list.get(i).getTracertIcmpJitterStd());
				scoreBase.setTracertIcmpJitterVar(list.get(i).getTracertIcmpJitterVar());
				scoreBase.setTracertIcmpLossRate(list.get(i).getTracertIcmpLossRate());
				scoreBase.setTracertIcmpFail(list.get(i).getFail());
				scoreBase.setTracertIcmpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertUdp")){
				scoreBase.setTracertTcpDelay(list.get(i).getTracertTcpDelay());
				scoreBase.setTracertTcpDelayStd(list.get(i).getTracertTcpDelayStd());
				scoreBase.setTracertTcpDelayVar(list.get(i).getTracertTcpDelayVar());
				scoreBase.setTracertTcpJitter(list.get(i).getTracertTcpJitter());
				scoreBase.setTracertTcpJitterStd(list.get(i).getTracertTcpJitterStd());
				scoreBase.setTracertTcpJitterVar(list.get(i).getTracertTcpJitterVar());
				scoreBase.setTracertTcpLossRate(list.get(i).getTracertTcpLossRate());
				scoreBase.setTracertUdpFail(list.get(i).getFail());
				scoreBase.setTracertUdpTotal(list.get(i).getTotal());
			}else if(type.equals("slaUdp")){
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setSlaUdpFail(list.get(i).getFail());
				scoreBase.setSlaUdpTotal(list.get(i).getTotal());
			}else if(type.equals("dns")){
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDnsFail(list.get(i).getFail());
				scoreBase.setDnsTotal(list.get(i).getTotal());
			}else if(type.equals("dhcp")){
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setDhcpFail(list.get(i).getFail());
				scoreBase.setDhcpTotal(list.get(i).getTotal());
			}else if(type.equals("pppoe")){
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setPppoeFail(list.get(i).getFail());
				scoreBase.setPppoeTotal(list.get(i).getTotal());
			}else if(type.equals("radius")){
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setRadiusFail(list.get(i).getFail());
				scoreBase.setRadiusTotal(list.get(i).getTotal());
			}else if(type.equals("ftpDownload")){
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadFail(list.get(i).getFail());
				scoreBase.setFtpDownloadTotal(list.get(i).getTotal());
			}else if(type.equals("ftpUpload")){
				scoreBase.setFtpUploadDnsDelay(list.get(i).getFtpUploadDnsDelay());
				scoreBase.setFtpUploadConnDelay(list.get(i).getFtpUploadConnDelay());
				scoreBase.setFtpUploadLoginDelay(list.get(i).getFtpUploadLoginDelay());
				scoreBase.setFtpUploadHeadbyteDelay(list.get(i).getFtpUploadHeadbyteDelay());
				scoreBase.setFtpUploadUploadRate(list.get(i).getFtpUploadUploadRate());
				scoreBase.setFtpUploadFail(list.get(i).getFail());
				scoreBase.setFtpUploadTotal(list.get(i).getTotal());
			}else{}
			scoreBase.setScore(list.get(i).getScore());
			scoreBase.setBase(list.get(i).getBase());


			if (!map.containsKey(scoreTarget)) {
				Map<String,ScoreBaseEntity> map1 = new HashMap<>();

				map1.put(type,scoreBase);
				map.put(scoreTarget,map1);

			} else {
				Map<String,ScoreBaseEntity> map1= map.get(scoreTarget);
				map1.put(type,scoreBase);
				map.put(scoreTarget,map1);
			}
		}
		return map;
	}

	//Map Date function
	public Map<ScoreDateEntity,Map<String,ScoreBaseEntity>> putMapDate(List<ScoreEntity> list,Map<ScoreDateEntity,Map<String,ScoreBaseEntity>> map,String type){
		for (int i = 0; i < list.size(); i++) {
			ScoreDateEntity scoreDate = new ScoreDateEntity();
			scoreDate.setCityId(list.get(i).getCityId());
			scoreDate.setCountyId(list.get(i).getCountyId());
			scoreDate.setProbeId(list.get(i).getProbeId());
			scoreDate.setTargetId(list.get(i).getTargetId());
			scoreDate.setCityName(list.get(i).getCityName());
			scoreDate.setCountyName(list.get(i).getCountyName());
			scoreDate.setProbeName(list.get(i).getProbeName());
			scoreDate.setTargetName(list.get(i).getTargetName());
			scoreDate.setAccessLayer(list.get(i).getAccessLayer());
			scoreDate.setPort(list.get(i).getPort());
			scoreDate.setRecordDate(list.get(i).getRecordDate());
			scoreDate.setRecordTime(list.get(i).getRecordTime());
			scoreDate.setFail(list.get(i).getFail());
			scoreDate.setTotal(list.get(i).getTotal());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("pingTcp")){
				scoreBase.setPingTcpDelay(list.get(i).getPingTcpDelay());
				scoreBase.setPingTcpDelayStd(list.get(i).getPingTcpDelayStd());
				scoreBase.setPingTcpDelayVar(list.get(i).getPingTcpDelayVar());
				scoreBase.setPingTcpJitter(list.get(i).getPingTcpJitter());
				scoreBase.setPingTcpJitterStd(list.get(i).getPingTcpJitterStd());
				scoreBase.setPingTcpJitterVar(list.get(i).getPingTcpJitterVar());
				scoreBase.setPingTcpLossRate(list.get(i).getPingTcpLossRate());
				scoreBase.setPingTcpFail(list.get(i).getFail());
				scoreBase.setPingTcpTotal(list.get(i).getTotal());
			}else if(type.equals("pingUdp")){
				scoreBase.setPingUdpDelay(list.get(i).getPingUdpDelay());
				scoreBase.setPingUdpDelayStd(list.get(i).getPingUdpDelayStd());
				scoreBase.setPingUdpDelayVar(list.get(i).getPingUdpDelayVar());
				scoreBase.setPingUdpJitter(list.get(i).getPingUdpJitter());
				scoreBase.setPingUdpJitterStd(list.get(i).getPingUdpJitterStd());
				scoreBase.setPingUdpJitterVar(list.get(i).getPingUdpJitterVar());
				scoreBase.setPingUdpLossRate(list.get(i).getPingUdpLossRate());
				scoreBase.setPingUdpFail(list.get(i).getFail());
				scoreBase.setPingUdpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertIcmp")){
				scoreBase.setTracertIcmpDelay(list.get(i).getTracertIcmpDelay());
				scoreBase.setTracertIcmpDelayStd(list.get(i).getTracertIcmpDelayStd());
				scoreBase.setTracertIcmpDelayVar(list.get(i).getTracertIcmpDelayVar());
				scoreBase.setTracertIcmpJitter(list.get(i).getTracertIcmpJitter());
				scoreBase.setTracertIcmpJitterStd(list.get(i).getTracertIcmpJitterStd());
				scoreBase.setTracertIcmpJitterVar(list.get(i).getTracertIcmpJitterVar());
				scoreBase.setTracertIcmpLossRate(list.get(i).getTracertIcmpLossRate());
				scoreBase.setTracertIcmpFail(list.get(i).getFail());
				scoreBase.setTracertIcmpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertUdp")){
				scoreBase.setTracertTcpDelay(list.get(i).getTracertTcpDelay());
				scoreBase.setTracertTcpDelayStd(list.get(i).getTracertTcpDelayStd());
				scoreBase.setTracertTcpDelayVar(list.get(i).getTracertTcpDelayVar());
				scoreBase.setTracertTcpJitter(list.get(i).getTracertTcpJitter());
				scoreBase.setTracertTcpJitterStd(list.get(i).getTracertTcpJitterStd());
				scoreBase.setTracertTcpJitterVar(list.get(i).getTracertTcpJitterVar());
				scoreBase.setTracertTcpLossRate(list.get(i).getTracertTcpLossRate());
				scoreBase.setTracertUdpFail(list.get(i).getFail());
				scoreBase.setTracertUdpTotal(list.get(i).getTotal());
			}else if(type.equals("slaUdp")){
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setSlaUdpFail(list.get(i).getFail());
				scoreBase.setSlaUdpTotal(list.get(i).getTotal());
			}else if(type.equals("dns")){
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDnsFail(list.get(i).getFail());
				scoreBase.setDnsTotal(list.get(i).getTotal());
			}else if(type.equals("dhcp")){
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setDhcpFail(list.get(i).getFail());
				scoreBase.setDhcpTotal(list.get(i).getTotal());
			}else if(type.equals("pppoe")){
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setPppoeFail(list.get(i).getFail());
				scoreBase.setPppoeTotal(list.get(i).getTotal());
			}else if(type.equals("radius")){
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setRadiusFail(list.get(i).getFail());
				scoreBase.setRadiusTotal(list.get(i).getTotal());
			}else if(type.equals("ftpDownload")){
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadFail(list.get(i).getFail());
				scoreBase.setFtpDownloadTotal(list.get(i).getTotal());
			}else if(type.equals("ftpUpload")){
				scoreBase.setFtpUploadDnsDelay(list.get(i).getFtpUploadDnsDelay());
				scoreBase.setFtpUploadConnDelay(list.get(i).getFtpUploadConnDelay());
				scoreBase.setFtpUploadLoginDelay(list.get(i).getFtpUploadLoginDelay());
				scoreBase.setFtpUploadHeadbyteDelay(list.get(i).getFtpUploadHeadbyteDelay());
				scoreBase.setFtpUploadUploadRate(list.get(i).getFtpUploadUploadRate());
				scoreBase.setFtpUploadFail(list.get(i).getFail());
				scoreBase.setFtpUploadTotal(list.get(i).getTotal());
			}else{}
			scoreBase.setScore(list.get(i).getScore());
			scoreBase.setBase(list.get(i).getBase());


			if (!map.containsKey(scoreDate)) {
				Map<String,ScoreBaseEntity> map1 = new HashMap<>();

				map1.put(type,scoreBase);
				map.put(scoreDate,map1);

			} else {
				Map<String,ScoreBaseEntity> map1= map.get(scoreDate);
				map1.put(type,scoreBase);
				map.put(scoreDate,map1);
			}
		}
		return map;
	}

	//Map Date function
	public Map<ScoreLayerEntity,Map<String,ScoreBaseEntity>> putMapLayer(List<ScoreEntity> list,Map<ScoreLayerEntity,Map<String,ScoreBaseEntity>> map,String type){
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getAccessLayer()==null){
				continue;
			}
			ScoreLayerEntity scoreLayer = new ScoreLayerEntity();
			scoreLayer.setCityId(list.get(i).getCityId());
			scoreLayer.setCountyId(list.get(i).getCountyId());
			scoreLayer.setProbeId(list.get(i).getProbeId());
			scoreLayer.setTargetId(list.get(i).getTargetId());
			scoreLayer.setCityName(list.get(i).getCityName());
			scoreLayer.setCountyName(list.get(i).getCountyName());
			scoreLayer.setProbeName(list.get(i).getProbeName());
			scoreLayer.setTargetName(list.get(i).getTargetName());
			scoreLayer.setAccessLayer(list.get(i).getAccessLayer());
			scoreLayer.setPort(list.get(i).getPort());
			scoreLayer.setRecordDate(list.get(i).getRecordDate());
			scoreLayer.setRecordTime(list.get(i).getRecordTime());
			scoreLayer.setFail(list.get(i).getFail());
			scoreLayer.setTotal(list.get(i).getTotal());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("pingTcp")){
				scoreBase.setPingTcpDelay(list.get(i).getPingTcpDelay());
				scoreBase.setPingTcpDelayStd(list.get(i).getPingTcpDelayStd());
				scoreBase.setPingTcpDelayVar(list.get(i).getPingTcpDelayVar());
				scoreBase.setPingTcpJitter(list.get(i).getPingTcpJitter());
				scoreBase.setPingTcpJitterStd(list.get(i).getPingTcpJitterStd());
				scoreBase.setPingTcpJitterVar(list.get(i).getPingTcpJitterVar());
				scoreBase.setPingTcpLossRate(list.get(i).getPingTcpLossRate());
				scoreBase.setTcpPingScore(list.get(i).getScore());
				scoreBase.setPingTcpFail(list.get(i).getFail());
				scoreBase.setPingTcpTotal(list.get(i).getTotal());
			}else if(type.equals("pingUdp")){
				scoreBase.setPingUdpDelay(list.get(i).getPingUdpDelay());
				scoreBase.setPingUdpDelayStd(list.get(i).getPingUdpDelayStd());
				scoreBase.setPingUdpDelayVar(list.get(i).getPingUdpDelayVar());
				scoreBase.setPingUdpJitter(list.get(i).getPingUdpJitter());
				scoreBase.setPingUdpJitterStd(list.get(i).getPingUdpJitterStd());
				scoreBase.setPingUdpJitterVar(list.get(i).getPingUdpJitterVar());
				scoreBase.setPingUdpLossRate(list.get(i).getPingUdpLossRate());
				scoreBase.setUdpPingScore(list.get(i).getScore());
				scoreBase.setPingUdpFail(list.get(i).getFail());
				scoreBase.setPingUdpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertIcmp")){
				scoreBase.setTracertIcmpDelay(list.get(i).getTracertIcmpDelay());
				scoreBase.setTracertIcmpDelayStd(list.get(i).getTracertIcmpDelayStd());
				scoreBase.setTracertIcmpDelayVar(list.get(i).getTracertIcmpDelayVar());
				scoreBase.setTracertIcmpJitter(list.get(i).getTracertIcmpJitter());
				scoreBase.setTracertIcmpJitterStd(list.get(i).getTracertIcmpJitterStd());
				scoreBase.setTracertIcmpJitterVar(list.get(i).getTracertIcmpJitterVar());
				scoreBase.setTracertIcmpLossRate(list.get(i).getTracertIcmpLossRate());
				scoreBase.setIcmpTracertScore(list.get(i).getScore());
				scoreBase.setTracertIcmpFail(list.get(i).getFail());
				scoreBase.setTracertIcmpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertUdp")){
				scoreBase.setTracertTcpDelay(list.get(i).getTracertTcpDelay());
				scoreBase.setTracertTcpDelayStd(list.get(i).getTracertTcpDelayStd());
				scoreBase.setTracertTcpDelayVar(list.get(i).getTracertTcpDelayVar());
				scoreBase.setTracertTcpJitter(list.get(i).getTracertTcpJitter());
				scoreBase.setTracertTcpJitterStd(list.get(i).getTracertTcpJitterStd());
				scoreBase.setTracertTcpJitterVar(list.get(i).getTracertTcpJitterVar());
				scoreBase.setTracertTcpLossRate(list.get(i).getTracertTcpLossRate());
				scoreBase.setUdpTracertScore(list.get(i).getScore());
				scoreBase.setTracertUdpFail(list.get(i).getFail());
				scoreBase.setTracertUdpTotal(list.get(i).getTotal());
			}else if(type.equals("slaUdp")){
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setUdpSlaScore(list.get(i).getScore());
				scoreBase.setSlaUdpFail(list.get(i).getFail());
				scoreBase.setSlaUdpTotal(list.get(i).getTotal());
			}else if(type.equals("dns")){
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDnsScore(list.get(i).getScore());
				scoreBase.setDnsFail(list.get(i).getFail());
				scoreBase.setDnsTotal(list.get(i).getTotal());
			}else if(type.equals("dhcp")){
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setDhcpScore(list.get(i).getScore());
				scoreBase.setDhcpFail(list.get(i).getFail());
				scoreBase.setDhcpTotal(list.get(i).getTotal());
			}else if(type.equals("pppoe")){
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setPppoeScore(list.get(i).getScore());
				scoreBase.setPppoeFail(list.get(i).getFail());
				scoreBase.setPppoeTotal(list.get(i).getTotal());
			}else if(type.equals("radius")){
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setRadiusScore(list.get(i).getScore());
				scoreBase.setRadiusFail(list.get(i).getFail());
				scoreBase.setRadiusTotal(list.get(i).getTotal());
			}else if(type.equals("ftpDownload")){
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadScore(list.get(i).getScore());
				scoreBase.setFtpDownloadFail(list.get(i).getFail());
				scoreBase.setFtpDownloadTotal(list.get(i).getTotal());
			}else if(type.equals("ftpUpload")){
				scoreBase.setFtpUploadDnsDelay(list.get(i).getFtpUploadDnsDelay());
				scoreBase.setFtpUploadConnDelay(list.get(i).getFtpUploadConnDelay());
				scoreBase.setFtpUploadLoginDelay(list.get(i).getFtpUploadLoginDelay());
				scoreBase.setFtpUploadHeadbyteDelay(list.get(i).getFtpUploadHeadbyteDelay());
				scoreBase.setFtpUploadUploadRate(list.get(i).getFtpUploadUploadRate());
				scoreBase.setFtpUploadScore(list.get(i).getScore());
				scoreBase.setFtpUploadFail(list.get(i).getFail());
				scoreBase.setFtpUploadTotal(list.get(i).getTotal());
			}else{}
			scoreBase.setScore(list.get(i).getScore());
			scoreBase.setBase(list.get(i).getBase());


			if (!map.containsKey(scoreLayer)) {
				Map<String,ScoreBaseEntity> map1 = new HashMap<>();

				map1.put(type,scoreBase);
				map.put(scoreLayer,map1);

			} else {
				Map<String,ScoreBaseEntity> map1= map.get(scoreLayer);
				map1.put(type,scoreBase);
				map.put(scoreLayer,map1);
			}
		}
		return map;
	}

	//Map Area Function
	public Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> putMapArea(List<ScoreEntity> list,Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> map,String type){
		for (int i = 0; i < list.size(); i++) {
			ScoreAreaEntity scoreArea = new ScoreAreaEntity();
			scoreArea.setCityId(list.get(i).getCityId());
			scoreArea.setCountyId(list.get(i).getCountyId());
			scoreArea.setProbeId(list.get(i).getProbeId());
			scoreArea.setTargetId(list.get(i).getTargetId());
			scoreArea.setCityName(list.get(i).getCityName());
			scoreArea.setCountyName(list.get(i).getCountyName());
			scoreArea.setProbeName(list.get(i).getProbeName());
			scoreArea.setTargetName(list.get(i).getTargetName());
			scoreArea.setAccessLayer(list.get(i).getAccessLayer());
			scoreArea.setPort(list.get(i).getPort());
			scoreArea.setRecordDate(list.get(i).getRecordDate());
			scoreArea.setRecordTime(list.get(i).getRecordTime());
			scoreArea.setFail(list.get(i).getFail());
			scoreArea.setTotal(list.get(i).getTotal());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("pingTcp")){
				scoreBase.setPingTcpDelay(list.get(i).getPingTcpDelay());
				scoreBase.setPingTcpDelayStd(list.get(i).getPingTcpDelayStd());
				scoreBase.setPingTcpDelayVar(list.get(i).getPingTcpDelayVar());
				scoreBase.setPingTcpJitter(list.get(i).getPingTcpJitter());
				scoreBase.setPingTcpJitterStd(list.get(i).getPingTcpJitterStd());
				scoreBase.setPingTcpJitterVar(list.get(i).getPingTcpJitterVar());
				scoreBase.setPingTcpLossRate(list.get(i).getPingTcpLossRate());
				scoreBase.setPingTcpFail(list.get(i).getFail());
				scoreBase.setPingTcpTotal(list.get(i).getTotal());
			}else if(type.equals("pingUdp")){
				scoreBase.setPingUdpDelay(list.get(i).getPingUdpDelay());
				scoreBase.setPingUdpDelayStd(list.get(i).getPingUdpDelayStd());
				scoreBase.setPingUdpDelayVar(list.get(i).getPingUdpDelayVar());
				scoreBase.setPingUdpJitter(list.get(i).getPingUdpJitter());
				scoreBase.setPingUdpJitterStd(list.get(i).getPingUdpJitterStd());
				scoreBase.setPingUdpJitterVar(list.get(i).getPingUdpJitterVar());
				scoreBase.setPingUdpLossRate(list.get(i).getPingUdpLossRate());
				scoreBase.setPingUdpFail(list.get(i).getFail());
				scoreBase.setPingUdpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertIcmp")){
				scoreBase.setTracertIcmpDelay(list.get(i).getTracertIcmpDelay());
				scoreBase.setTracertIcmpDelayStd(list.get(i).getTracertIcmpDelayStd());
				scoreBase.setTracertIcmpDelayVar(list.get(i).getTracertIcmpDelayVar());
				scoreBase.setTracertIcmpJitter(list.get(i).getTracertIcmpJitter());
				scoreBase.setTracertIcmpJitterStd(list.get(i).getTracertIcmpJitterStd());
				scoreBase.setTracertIcmpJitterVar(list.get(i).getTracertIcmpJitterVar());
				scoreBase.setTracertIcmpLossRate(list.get(i).getTracertIcmpLossRate());
				scoreBase.setTracertIcmpFail(list.get(i).getFail());
				scoreBase.setTracertIcmpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertUdp")){
				scoreBase.setTracertTcpDelay(list.get(i).getTracertTcpDelay());
				scoreBase.setTracertTcpDelayStd(list.get(i).getTracertTcpDelayStd());
				scoreBase.setTracertTcpDelayVar(list.get(i).getTracertTcpDelayVar());
				scoreBase.setTracertTcpJitter(list.get(i).getTracertTcpJitter());
				scoreBase.setTracertTcpJitterStd(list.get(i).getTracertTcpJitterStd());
				scoreBase.setTracertTcpJitterVar(list.get(i).getTracertTcpJitterVar());
				scoreBase.setTracertTcpLossRate(list.get(i).getTracertTcpLossRate());
				scoreBase.setTracertUdpFail(list.get(i).getFail());
				scoreBase.setTracertUdpTotal(list.get(i).getTotal());
			}else if(type.equals("slaUdp")){
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setSlaUdpFail(list.get(i).getFail());
				scoreBase.setSlaUdpTotal(list.get(i).getTotal());
			}else if(type.equals("dns")){
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDnsFail(list.get(i).getFail());
				scoreBase.setDnsTotal(list.get(i).getTotal());
			}else if(type.equals("dhcp")){
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setDhcpFail(list.get(i).getFail());
				scoreBase.setDhcpTotal(list.get(i).getTotal());
			}else if(type.equals("pppoe")){
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setPppoeFail(list.get(i).getFail());
				scoreBase.setPppoeTotal(list.get(i).getTotal());
			}else if(type.equals("radius")){
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setRadiusFail(list.get(i).getFail());
				scoreBase.setRadiusTotal(list.get(i).getTotal());
			}else if(type.equals("ftpDownload")){
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadFail(list.get(i).getFail());
				scoreBase.setFtpDownloadTotal(list.get(i).getTotal());
			}else if(type.equals("ftpUpload")){
				scoreBase.setFtpUploadDnsDelay(list.get(i).getFtpUploadDnsDelay());
				scoreBase.setFtpUploadConnDelay(list.get(i).getFtpUploadConnDelay());
				scoreBase.setFtpUploadLoginDelay(list.get(i).getFtpUploadLoginDelay());
				scoreBase.setFtpUploadHeadbyteDelay(list.get(i).getFtpUploadHeadbyteDelay());
				scoreBase.setFtpUploadUploadRate(list.get(i).getFtpUploadUploadRate());
				scoreBase.setFtpUploadFail(list.get(i).getFail());
				scoreBase.setFtpUploadTotal(list.get(i).getTotal());
			}else{}
			scoreBase.setScore(list.get(i).getScore());
			scoreBase.setBase(list.get(i).getBase());


			if (!map.containsKey(scoreArea)) {
				Map<String,ScoreBaseEntity> map1 = new HashMap<>();

				map1.put(type,scoreBase);
				map.put(scoreArea,map1);

			} else {
				Map<String,ScoreBaseEntity> map1= map.get(scoreArea);
				map1.put(type,scoreBase);
				map.put(scoreArea,map1);
			}
		}
		return map;
	}

	//Map Target Function
	public Map<ScoreTargetRankEntity,Map<String,ScoreBaseEntity>> putMapTarget(List<ScoreEntity> list,Map<ScoreTargetRankEntity,Map<String,ScoreBaseEntity>> map,String type){
		for (int i = 0; i < list.size(); i++) {
			ScoreTargetRankEntity targetRank = new ScoreTargetRankEntity();
			targetRank.setCityId(list.get(i).getCityId());
			targetRank.setCountyId(list.get(i).getCountyId());
			targetRank.setProbeId(list.get(i).getProbeId());
			targetRank.setTargetId(list.get(i).getTargetId());
			targetRank.setCityName(list.get(i).getCityName());
			targetRank.setCountyName(list.get(i).getCountyName());
			targetRank.setProbeName(list.get(i).getProbeName());
			targetRank.setTargetName(list.get(i).getTargetName());
			targetRank.setAccessLayer(list.get(i).getAccessLayer());
			targetRank.setPort(list.get(i).getPort());
			targetRank.setRecordDate(list.get(i).getRecordDate());
			targetRank.setRecordTime(list.get(i).getRecordTime());
			targetRank.setFail(list.get(i).getFail());
			targetRank.setTotal(list.get(i).getTotal());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("pingTcp")){
				scoreBase.setPingTcpDelay(list.get(i).getPingTcpDelay());
				scoreBase.setPingTcpDelayStd(list.get(i).getPingTcpDelayStd());
				scoreBase.setPingTcpDelayVar(list.get(i).getPingTcpDelayVar());
				scoreBase.setPingTcpJitter(list.get(i).getPingTcpJitter());
				scoreBase.setPingTcpJitterStd(list.get(i).getPingTcpJitterStd());
				scoreBase.setPingTcpJitterVar(list.get(i).getPingTcpJitterVar());
				scoreBase.setPingTcpLossRate(list.get(i).getPingTcpLossRate());
				scoreBase.setPingTcpFail(list.get(i).getFail());
				scoreBase.setPingTcpTotal(list.get(i).getTotal());
			}else if(type.equals("pingUdp")){
				scoreBase.setPingUdpDelay(list.get(i).getPingUdpDelay());
				scoreBase.setPingUdpDelayStd(list.get(i).getPingUdpDelayStd());
				scoreBase.setPingUdpDelayVar(list.get(i).getPingUdpDelayVar());
				scoreBase.setPingUdpJitter(list.get(i).getPingUdpJitter());
				scoreBase.setPingUdpJitterStd(list.get(i).getPingUdpJitterStd());
				scoreBase.setPingUdpJitterVar(list.get(i).getPingUdpJitterVar());
				scoreBase.setPingUdpLossRate(list.get(i).getPingUdpLossRate());
				scoreBase.setPingUdpFail(list.get(i).getFail());
				scoreBase.setPingUdpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertIcmp")){
				scoreBase.setTracertIcmpDelay(list.get(i).getTracertIcmpDelay());
				scoreBase.setTracertIcmpDelayStd(list.get(i).getTracertIcmpDelayStd());
				scoreBase.setTracertIcmpDelayVar(list.get(i).getTracertIcmpDelayVar());
				scoreBase.setTracertIcmpJitter(list.get(i).getTracertIcmpJitter());
				scoreBase.setTracertIcmpJitterStd(list.get(i).getTracertIcmpJitterStd());
				scoreBase.setTracertIcmpJitterVar(list.get(i).getTracertIcmpJitterVar());
				scoreBase.setTracertIcmpLossRate(list.get(i).getTracertIcmpLossRate());
				scoreBase.setTracertIcmpFail(list.get(i).getFail());
				scoreBase.setTracertIcmpTotal(list.get(i).getTotal());
			}else if(type.equals("tracertUdp")){
				scoreBase.setTracertTcpDelay(list.get(i).getTracertTcpDelay());
				scoreBase.setTracertTcpDelayStd(list.get(i).getTracertTcpDelayStd());
				scoreBase.setTracertTcpDelayVar(list.get(i).getTracertTcpDelayVar());
				scoreBase.setTracertTcpJitter(list.get(i).getTracertTcpJitter());
				scoreBase.setTracertTcpJitterStd(list.get(i).getTracertTcpJitterStd());
				scoreBase.setTracertTcpJitterVar(list.get(i).getTracertTcpJitterVar());
				scoreBase.setTracertTcpLossRate(list.get(i).getTracertTcpLossRate());
				scoreBase.setTracertUdpFail(list.get(i).getFail());
				scoreBase.setTracertUdpTotal(list.get(i).getTotal());
			}else if(type.equals("slaUdp")){
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setSlaUdpFail(list.get(i).getFail());
				scoreBase.setSlaUdpTotal(list.get(i).getTotal());
			}else if(type.equals("dns")){
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDnsFail(list.get(i).getFail());
				scoreBase.setDnsTotal(list.get(i).getTotal());
			}else if(type.equals("dhcp")){
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setDhcpFail(list.get(i).getFail());
				scoreBase.setDhcpTotal(list.get(i).getTotal());
			}else if(type.equals("pppoe")){
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setPppoeFail(list.get(i).getFail());
				scoreBase.setPppoeTotal(list.get(i).getTotal());
			}else if(type.equals("radius")){
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setRadiusFail(list.get(i).getFail());
				scoreBase.setRadiusTotal(list.get(i).getTotal());
			}else if(type.equals("ftpDownload")){
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadFail(list.get(i).getFail());
				scoreBase.setFtpDownloadTotal(list.get(i).getTotal());
			}else if(type.equals("ftpUpload")){
				scoreBase.setFtpUploadDnsDelay(list.get(i).getFtpUploadDnsDelay());
				scoreBase.setFtpUploadConnDelay(list.get(i).getFtpUploadConnDelay());
				scoreBase.setFtpUploadLoginDelay(list.get(i).getFtpUploadLoginDelay());
				scoreBase.setFtpUploadHeadbyteDelay(list.get(i).getFtpUploadHeadbyteDelay());
				scoreBase.setFtpUploadUploadRate(list.get(i).getFtpUploadUploadRate());
				scoreBase.setFtpUploadFail(list.get(i).getFail());
				scoreBase.setFtpUploadTotal(list.get(i).getTotal());
			}else{}
			scoreBase.setScore(list.get(i).getScore());
			scoreBase.setBase(list.get(i).getBase());


			if (!map.containsKey(targetRank)) {
				Map<String,ScoreBaseEntity> map1 = new HashMap<>();

				map1.put(type,scoreBase);
				map.put(targetRank,map1);

			} else {
				Map<String,ScoreBaseEntity> map1= map.get(targetRank);
				map1.put(type,scoreBase);
				map.put(targetRank,map1);
			}
		}
		return map;
	}


	@Override
	public int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}

	@Override
	public Map queryTime(){
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		System.out.println("正在执行"+df.format(new Date()));// new Date()为获取当前系统时间
		Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		System.out.println("一个小时前的时间：" + df2.format(calendar.getTime()));
		System.out.println("当前的时间：" + df2.format(new Date()));
		SimpleDateFormat df3 = new SimpleDateFormat("HH");
		if(df3.format(new Date()).equals("00")){
			map.put("start_time","23:59:59");
			map.put("terminal_time","23:59:00");
			String beforeDay=queryBeforeDay(df.format(new Date()));
			map.put("record_date",beforeDay);
		}else {
			map.put("start_time", df2.format(calendar.getTime()));
			map.put("terminal_time", df2.format(new Date()));
			map.put("record_date", df.format(new Date()));
		}

		return map;
	}

	@Override
	public Map queryDay() {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		System.out.println("正在执行" + df.format(new Date()));// new Date()为获取当前系统时间
		String beforeDay = queryBeforeDay(df.format(new Date()));
		map.put("record_date",beforeDay);
		return map;
	}

	@Override
	public String queryBeforeDay(String specifiedDay) {//可以用new Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	@Override
	public String queryAfterDay(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourPingDao.queryTotal(map);
	}

	@Override
	public void save(RecordHourPingEntity recordHourPing){
		recordHourPingDao.save(recordHourPing);
	}
	
	@Override
	public void update(RecordHourPingEntity recordHourPing){
		recordHourPingDao.update(recordHourPing);
	}
	
	@Override
	public void delete(Integer id){
		recordHourPingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourPingDao.deleteBatch(ids);
	}
	
}
