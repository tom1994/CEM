package io.cem.modules.cem.service.impl;


import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.*;
import io.cem.modules.cem.dao.RecordPingDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordHourTracertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import io.cem.modules.cem.dao.RecordHourPingDao;
import io.cem.modules.cem.service.RecordHourPingService;



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
	public List<RecordHourPingEntity> queryPing(Map<String, Object> map){
		return recordPingDao.queryPing(map);
	}

	@Override
	public List<RecordHourPingEntity> queryPingList(Map<String, Object> map){ return recordHourPingDao.queryPingList(map); }

	@Override
	public List<RecordHourPingEntity> queryDayList(Map<String, Object> map){ return recordHourPingDao.queryDayList(map); }

	@Override
	public List<ScoreEntity> calculatePingIcmp(List<RecordHourPingEntity> pingList){
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
							score = 100 * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(PropertiesUtils.getValue("pingI22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI23"))) <= 0)) {
							score = (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI23")))) * 20) / ((Double.parseDouble(pros.getValue("pingI22")) - (Double.parseDouble(pros.getValue("pingI23"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI24"))) <= 0)) {
							score = (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI24")))) * 20) / ((Double.parseDouble(pros.getValue("pingI23")) - (Double.parseDouble(pros.getValue("pingI24"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI25"))) <= 0)) {
							score = (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI25")))) * 20) / ((Double.parseDouble(pros.getValue("pingI24")) - (Double.parseDouble(pros.getValue("pingI25"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI26"))) <= 0)) {
							score = (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI26")))) * 20) / ((Double.parseDouble(pros.getValue("pingI25")) - (Double.parseDouble(pros.getValue("pingI26"))))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingI27"))) <= 0)) {
							score = ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingI27")))) * 20) / ((Double.parseDouble(pros.getValue("pingI26")) - (Double.parseDouble(pros.getValue("pingI27")))))) * (Double.parseDouble(pros.getValue("pingI21")));
						}
						//delay 0
						else {
							score = 0;
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
						if ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 80-100
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI82"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI83"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingI83")))) * 20) / ((Double.parseDouble(pros.getValue("pingI82")) - (Double.parseDouble(pros.getValue("pingI83"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 60-80
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI83"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI84"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingI84")))) * 20) / ((Double.parseDouble(pros.getValue("pingI83")) - (Double.parseDouble(pros.getValue("pingI84"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 40-60
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI84"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI85"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingI85")))) * 20) / ((Double.parseDouble(pros.getValue("pingI84")) - (Double.parseDouble(pros.getValue("pingI85"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 20-40
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI85"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI86"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingI86")))) * 20) / ((Double.parseDouble(pros.getValue("pingI85")) - (Double.parseDouble(pros.getValue("pingI86"))))))) * (Double.parseDouble(pros.getValue("pingI81")));
						}
						//loss 0-20
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI86"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingI87"))) <= 0)) {
							score += ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingI87")))) * 20) / ((Double.parseDouble(pros.getValue("pingI86")) - (Double.parseDouble(pros.getValue("pingI87")))))) * (Double.parseDouble(pros.getValue("pingI81")));
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
						icmpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						icmpPing.setRecordDate(pingList.get(i).getRecordDate());
						icmpPing.setRecordTime(pingList.get(i).getRecordTime());
						icmpPing.setScore(score);
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
	public List<ScoreEntity> calculatePingTcp(List<RecordHourPingEntity> pingList){
		List<ScoreEntity> pingTcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for (int i = 0; i < pingList.size(); i++) {
				double score = 0;
				//Ping(TCP)
				if ((pingList.get(i).getServiceType()) == 2) {
					try {
						//delay 100
						if ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT22"))) <= 0) {
							score = 100 * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT23"))) <= 0)) {
							score = (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT23")))) * 20) / ((Double.parseDouble(pros.getValue("pingT22")) - (Double.parseDouble(pros.getValue("pingT23"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT24"))) <= 0)) {
							score = (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT24")))) * 20) / ((Double.parseDouble(pros.getValue("pingT23")) - (Double.parseDouble(pros.getValue("pingT24"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT25"))) <= 0)) {
							score = (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT25")))) * 20) / ((Double.parseDouble(pros.getValue("pingT24")) - (Double.parseDouble(pros.getValue("pingT25"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT26"))) <= 0)) {
							score = (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT26")))) * 20) / ((Double.parseDouble(pros.getValue("pingT25")) - (Double.parseDouble(pros.getValue("pingT26"))))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingT27"))) <= 0)) {
							score = ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingT27")))) * 20) / ((Double.parseDouble(pros.getValue("pingT26")) - (Double.parseDouble(pros.getValue("pingT27")))))) * (Double.parseDouble(pros.getValue("pingT21")));
						}
						//delay 0
						else {
							score = 0;
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
						if ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 80-100
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT82"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT83"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingT83")))) * 20) / ((Double.parseDouble(pros.getValue("pingT82")) - (Double.parseDouble(pros.getValue("pingT83"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 60-80
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT83"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT84"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingT84")))) * 20) / ((Double.parseDouble(pros.getValue("pingT83")) - (Double.parseDouble(pros.getValue("pingT84"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 40-60
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT84"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT85"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingT85")))) * 20) / ((Double.parseDouble(pros.getValue("pingT84")) - (Double.parseDouble(pros.getValue("pingT85"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 20-40
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT85"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT86"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingT86")))) * 20) / ((Double.parseDouble(pros.getValue("pingT85")) - (Double.parseDouble(pros.getValue("pingT86"))))))) * (Double.parseDouble(pros.getValue("pingT81")));
						}
						//loss 0-20
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT86"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingT87"))) <= 0)) {
							score += ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingT87")))) * 20) / ((Double.parseDouble(pros.getValue("pingT86")) - (Double.parseDouble(pros.getValue("pingT87")))))) * (Double.parseDouble(pros.getValue("pingT81")));
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
						tcpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						tcpPing.setRecordDate(pingList.get(i).getRecordDate());
						tcpPing.setRecordTime(pingList.get(i).getRecordTime());
						tcpPing.setScore(score);
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
	public List<ScoreEntity> calculatePingUdp(List<RecordHourPingEntity> pingList){
		List<ScoreEntity> pingUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for (int i = 0; i < pingList.size(); i++) {
				double score = 0;
				//Ping(Udp)
				if ((pingList.get(i).getServiceType()) == 3) {
					try {
						//delay 100
						if ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU22"))) <= 0) {
							score = 100 * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 80-100
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU22"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU23"))) <= 0)) {
							score = (80 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU23")))) * 20) / ((Double.parseDouble(pros.getValue("pingU22")) - (Double.parseDouble(pros.getValue("pingU23"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 60-80
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU23"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU24"))) <= 0)) {
							score = (60 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU24")))) * 20) / ((Double.parseDouble(pros.getValue("pingU23")) - (Double.parseDouble(pros.getValue("pingU24"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 40-60
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU24"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU25"))) <= 0)) {
							score = (40 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU25")))) * 20) / ((Double.parseDouble(pros.getValue("pingU24")) - (Double.parseDouble(pros.getValue("pingU25"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 20-40
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU25"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU26"))) <= 0)) {
							score = (20 + ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU26")))) * 20) / ((Double.parseDouble(pros.getValue("pingU25")) - (Double.parseDouble(pros.getValue("pingU26"))))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 0-20
						else if (((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU26"))) > 0) && ((pingList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("pingU27"))) <= 0)) {
							score = ((((pingList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("pingU27")))) * 20) / ((Double.parseDouble(pros.getValue("pingU26")) - (Double.parseDouble(pros.getValue("pingU27")))))) * (Double.parseDouble(pros.getValue("pingU21")));
						}
						//delay 0
						else {
							score = 0;
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

						//loss 100
						if ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU82"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 80-100
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU82"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU83"))) <= 0)) {
							score += (80 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingU83")))) * 20) / ((Double.parseDouble(pros.getValue("pingU82")) - (Double.parseDouble(pros.getValue("pingU83"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 60-80
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU83"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU84"))) <= 0)) {
							score += (60 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingU84")))) * 20) / ((Double.parseDouble(pros.getValue("pingU83")) - (Double.parseDouble(pros.getValue("pingU84"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 40-60
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU84"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU85"))) <= 0)) {
							score += (40 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingU85")))) * 20) / ((Double.parseDouble(pros.getValue("pingU84")) - (Double.parseDouble(pros.getValue("pingU85"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 20-40
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU85"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU86"))) <= 0)) {
							score += (20 + ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingU86")))) * 20) / ((Double.parseDouble(pros.getValue("pingU85")) - (Double.parseDouble(pros.getValue("pingU86"))))))) * (Double.parseDouble(pros.getValue("pingU81")));
						}
						//loss 0-20
						else if (((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU86"))) > 0) && ((pingList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("pingU87"))) <= 0)) {
							score += ((((pingList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("pingU87")))) * 20) / ((Double.parseDouble(pros.getValue("pingU86")) - (Double.parseDouble(pros.getValue("pingU87")))))) * (Double.parseDouble(pros.getValue("pingU81")));
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
						udpPing.setAccessLayer(pingList.get(i).getAccessLayer());
						udpPing.setRecordDate(pingList.get(i).getRecordDate());
						udpPing.setRecordTime(pingList.get(i).getRecordTime());
						udpPing.setScore(score);
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
	public List<ScoreEntity> calculateTracertIcmp(List<RecordHourTracertEntity> tracertList){
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
							score = 100 * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 80-100
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI12"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI13"))) <= 0)) {
							score = (80 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI13")))) * 20) / ((Double.parseDouble(pros.getValue("trI12")) - (Double.parseDouble(pros.getValue("trI13"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 60-80
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI13"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI14"))) <= 0)) {
							score = (60 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI14")))) * 20) / ((Double.parseDouble(pros.getValue("trI13")) - (Double.parseDouble(pros.getValue("trI14"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI14"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI15"))) <= 0)) {
							score = (40 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI15")))) * 20) / ((Double.parseDouble(pros.getValue("trI14")) - (Double.parseDouble(pros.getValue("trI15"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI15"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI16"))) <= 0)) {
							score = (20 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI16")))) * 20) / ((Double.parseDouble(pros.getValue("trI15")) - (Double.parseDouble(pros.getValue("trI16"))))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI16"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trI17"))) <= 0)) {
							score = ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trI17")))) * 20) / ((Double.parseDouble(pros.getValue("trI16")) - (Double.parseDouble(pros.getValue("trI17")))))) * (Double.parseDouble(pros.getValue("trI11")));
						}
						//delay 0
						else {
							score = 0;
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
						if ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 80-100
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI72"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI73"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trI73")))) * 20) / ((Double.parseDouble(pros.getValue("trI72")) - (Double.parseDouble(pros.getValue("trI73"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 60-80
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI73"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI74"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trI74")))) * 20) / ((Double.parseDouble(pros.getValue("trI73")) - (Double.parseDouble(pros.getValue("trI74"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 40-60
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI74"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI75"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trI75")))) * 20) / ((Double.parseDouble(pros.getValue("trI74")) - (Double.parseDouble(pros.getValue("trI75"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 20-40
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI75"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI76"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trI76")))) * 20) / ((Double.parseDouble(pros.getValue("trI75")) - (Double.parseDouble(pros.getValue("trI76"))))))) * (Double.parseDouble(pros.getValue("trI71")));
						}
						//loss 0-20
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI76"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trI77"))) <= 0)) {
							score += ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trI77")))) * 20) / ((Double.parseDouble(pros.getValue("trI76")) - (Double.parseDouble(pros.getValue("trI77")))))) * (Double.parseDouble(pros.getValue("trI71")));
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
						icmpTracert.setAccessLayer(tracertList.get(i).getAccessLayer());
						icmpTracert.setRecordDate(tracertList.get(i).getRecordDate());
						icmpTracert.setRecordTime(tracertList.get(i).getRecordTime());
						icmpTracert.setScore(score);
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
	public List<ScoreEntity> calculateTracertUdp(List<RecordHourTracertEntity> tracertList){
		List<ScoreEntity> tracertUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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
							score = (80 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT13")))) * 20) / ((Double.parseDouble(pros.getValue("trT12")) - (Double.parseDouble(pros.getValue("trT13"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 60-80
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT13"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT14"))) <= 0)) {
							score = (60 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT14")))) * 20) / ((Double.parseDouble(pros.getValue("trT13")) - (Double.parseDouble(pros.getValue("trT14"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 40-60
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT14"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT15"))) <= 0)) {
							score = (40 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT15")))) * 20) / ((Double.parseDouble(pros.getValue("trT14")) - (Double.parseDouble(pros.getValue("trT15"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 20-40
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT15"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT16"))) <= 0)) {
							score = (20 + ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT16")))) * 20) / ((Double.parseDouble(pros.getValue("trT15")) - (Double.parseDouble(pros.getValue("trT16"))))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 0-20
						else if (((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT16"))) > 0) && ((tracertList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("trT17"))) <= 0)) {
							score = ((((tracertList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("trT17")))) * 20) / ((Double.parseDouble(pros.getValue("trT16")) - (Double.parseDouble(pros.getValue("trT17")))))) * (Double.parseDouble(pros.getValue("trT11")));
						}
						//delay 0
						else {
							score = 0;
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

						//loss 100
						if ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT72"))) <= 0) {
							score += 100 * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 80-100
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT72"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT73"))) <= 0)) {
							score += (80 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trT73")))) * 20) / ((Double.parseDouble(pros.getValue("trT72")) - (Double.parseDouble(pros.getValue("trT73"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 60-80
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT73"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT74"))) <= 0)) {
							score += (60 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trT74")))) * 20) / ((Double.parseDouble(pros.getValue("trT73")) - (Double.parseDouble(pros.getValue("trT74"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 40-60
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT74"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT75"))) <= 0)) {
							score += (40 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trT75")))) * 20) / ((Double.parseDouble(pros.getValue("trT74")) - (Double.parseDouble(pros.getValue("trT75"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 20-40
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT75"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT76"))) <= 0)) {
							score += (20 + ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trT76")))) * 20) / ((Double.parseDouble(pros.getValue("trT75")) - (Double.parseDouble(pros.getValue("trT76"))))))) * (Double.parseDouble(pros.getValue("trT71")));
						}
						//loss 0-20
						else if (((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT76"))) > 0) && ((tracertList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("trT77"))) <= 0)) {
							score += ((((tracertList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("trT77")))) * 20) / ((Double.parseDouble(pros.getValue("trT76")) - (Double.parseDouble(pros.getValue("trT77")))))) * (Double.parseDouble(pros.getValue("trT71")));
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
						tcpTracert.setAccessLayer(tracertList.get(i).getAccessLayer());
						tcpTracert.setRecordDate(tracertList.get(i).getRecordDate());
						tcpTracert.setRecordTime(tracertList.get(i).getRecordTime());
						tcpTracert.setScore(score);
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
			Map<ScoreTargetEntity, ScoreBaseEntity> connection = new HashMap<>();
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
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((pingIcmp.get(i).getScore()) * (pingIcmp.get(i).getBase()));
				scoreBase.setBase(pingIcmp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=putMap(pingTcp,connection);
			connection=putMap(pingUdp,connection);
			connection=putMap(tracertIcmp,connection);
			connection=putMap(tracertUdp,connection);

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
					finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
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
			Map<ScoreDateEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreDateEntity scoreTarget = new ScoreDateEntity();
				scoreTarget.setCityId(pingIcmp.get(i).getCityId());
				scoreTarget.setCountyId(pingIcmp.get(i).getCountyId());
				scoreTarget.setProbeId(pingIcmp.get(i).getProbeId());
				scoreTarget.setTargetId(pingIcmp.get(i).getTargetId());
				scoreTarget.setCityName(pingIcmp.get(i).getCityName());
				scoreTarget.setCountyName(pingIcmp.get(i).getCountyName());
				scoreTarget.setProbeName(pingIcmp.get(i).getProbeName());
				scoreTarget.setTargetName(pingIcmp.get(i).getTargetName());
				scoreTarget.setRecordDate(pingIcmp.get(i).getRecordDate());
				scoreTarget.setRecordTime(pingIcmp.get(i).getRecordTime());
				scoreTarget.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((pingIcmp.get(i).getScore()) * (pingIcmp.get(i).getBase()));
				scoreBase.setBase(pingIcmp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=putMapDate(pingTcp,connection);
			connection=putMapDate(pingUdp,connection);
			connection=putMapDate(tracertIcmp,connection);
			connection=putMapDate(tracertUdp,connection);

			System.out.println("MAP:"+connection);

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
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
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
	public List<ScoreEntity> calculateArea1(List<ScoreEntity> pingIcmp,List<ScoreEntity> pingTcp,List<ScoreEntity> pingUdp,List<ScoreEntity> tracertIcmp,List<ScoreEntity> tracertUdp){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreAreaEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < pingIcmp.size(); i++) {
				ScoreAreaEntity scoreTarget = new ScoreAreaEntity();
				scoreTarget.setCityId(pingIcmp.get(i).getCityId());
				scoreTarget.setCountyId(pingIcmp.get(i).getCountyId());
				scoreTarget.setProbeId(pingIcmp.get(i).getProbeId());
				scoreTarget.setTargetId(pingIcmp.get(i).getTargetId());
				scoreTarget.setCityName(pingIcmp.get(i).getCityName());
				scoreTarget.setCountyName(pingIcmp.get(i).getCountyName());
				scoreTarget.setProbeName(pingIcmp.get(i).getProbeName());
				scoreTarget.setTargetName(pingIcmp.get(i).getTargetName());
				scoreTarget.setAccessLayer(pingIcmp.get(i).getAccessLayer());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((pingIcmp.get(i).getScore()) * (pingIcmp.get(i).getBase()));
				scoreBase.setBase(pingIcmp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=putMapArea(pingTcp,connection);
			connection=putMapArea(pingUdp,connection);
			connection=putMapArea(tracertIcmp,connection);
			connection=putMapArea(tracertUdp,connection);

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
					finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
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
	public Map<ScoreTargetEntity,ScoreBaseEntity> putMap(List<ScoreEntity> list,Map<ScoreTargetEntity,ScoreBaseEntity> map){
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

			if (!map.containsKey(scoreTarget)) {
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase(list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			} else {
				ScoreBaseEntity scoreBaseDul = map.get(scoreTarget);
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((scoreBaseDul.getScore()) + (list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase((scoreBaseDul.getBase()) + list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			}
		}
		return map;
	}

	//MapDate Function
	public Map<ScoreDateEntity,ScoreBaseEntity> putMapDate(List<ScoreEntity> list,Map<ScoreDateEntity,ScoreBaseEntity> map){
		for (int i = 0; i < list.size(); i++) {
			ScoreDateEntity scoreTarget = new ScoreDateEntity();
			scoreTarget.setCityId(list.get(i).getCityId());
			scoreTarget.setCountyId(list.get(i).getCountyId());
			scoreTarget.setProbeId(list.get(i).getProbeId());
			scoreTarget.setTargetId(list.get(i).getTargetId());
			scoreTarget.setCityName(list.get(i).getCityName());
			scoreTarget.setCountyName(list.get(i).getCountyName());
			scoreTarget.setProbeName(list.get(i).getProbeName());
			scoreTarget.setTargetName(list.get(i).getTargetName());
			scoreTarget.setAccessLayer(list.get(i).getAccessLayer());
			scoreTarget.setRecordDate(list.get(i).getRecordDate());
			scoreTarget.setRecordTime(list.get(i).getRecordTime());

			if (!map.containsKey(scoreTarget)) {
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase(list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			} else {
				ScoreBaseEntity scoreBaseDul = map.get(scoreTarget);
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((scoreBaseDul.getScore()) + (list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase((scoreBaseDul.getBase()) + list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			}
		}
		return map;
	}

	//MapArea Function
	public Map<ScoreAreaEntity,ScoreBaseEntity> putMapArea(List<ScoreEntity> list,Map<ScoreAreaEntity,ScoreBaseEntity> map){
		for (int i = 0; i < list.size(); i++) {
			ScoreAreaEntity scoreTarget = new ScoreAreaEntity();
			scoreTarget.setCityId(list.get(i).getCityId());
			scoreTarget.setCountyId(list.get(i).getCountyId());
			scoreTarget.setProbeId(list.get(i).getProbeId());
			scoreTarget.setTargetId(list.get(i).getTargetId());
			scoreTarget.setCityName(list.get(i).getCityName());
			scoreTarget.setCountyName(list.get(i).getCountyName());
			scoreTarget.setProbeName(list.get(i).getProbeName());
			scoreTarget.setTargetName(list.get(i).getTargetName());
			scoreTarget.setAccessLayer(list.get(i).getAccessLayer());
			scoreTarget.setRecordDate(list.get(i).getRecordDate());
			scoreTarget.setRecordTime(list.get(i).getRecordTime());

			if (!map.containsKey(scoreTarget)) {
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase(list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			} else {
				ScoreBaseEntity scoreBaseDul = map.get(scoreTarget);
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((scoreBaseDul.getScore()) + (list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase((scoreBaseDul.getBase()) + list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
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

		map.put("start_time",df2.format(calendar.getTime()));
		map.put("terminal_time",df2.format(new Date()));
		map.put("record_date",df.format(new Date()));
		return map;
	}

	@Override
	public Map queryDay() {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		System.out.println("正在执行" + df.format(new Date()));// new Date()为获取当前系统时间
		map.put("record_date",df.format(new Date()));
		return map;
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
