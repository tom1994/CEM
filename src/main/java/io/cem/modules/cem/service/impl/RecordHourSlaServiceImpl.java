package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordSlaDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordFailService;
import io.cem.modules.cem.service.RecordHourPingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourSlaDao;
import io.cem.modules.cem.service.RecordHourSlaService;



@Service("recordHourSlaService")
public class RecordHourSlaServiceImpl implements RecordHourSlaService {
	@Autowired
	private RecordHourSlaDao recordHourSlaDao;
	@Autowired
	private RecordSlaDao recordSlaDao;
	
	@Override
	public RecordHourSlaEntity queryObject(Integer id){
		return recordHourSlaDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourSlaEntity> queryList(Map<String, Object> map){
		return recordHourSlaDao.queryList(map);
	}

	@Override
	public List<RecordHourSlaEntity> querySla(Map<String, Object> map){
		return recordSlaDao.querySla(map);
	}

	@Override
	@Async
	public Future<List<RecordHourSlaEntity>> querySlaList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourSlaDao.querySlaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourSlaEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourSlaDao.queryTargetHourList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourSlaEntity>> queryExitList(Map<String, Object> map){return new AsyncResult<> (recordHourSlaDao.queryExitList(map));}

	@Override
	@Async
	public Future<List<RecordHourSlaEntity>> queryDayExitList(Map<String, Object> map){return new AsyncResult<> (recordHourSlaDao.queryDayExitList(map));}

	@Override
	@Async
	public Future<List<RecordHourSlaEntity>> queryDayList(Map<String, Object> map){return new AsyncResult<> (recordHourSlaDao.queryDayList(map));}

	@Override
	public List<ScoreEntity> calculateSlaTcp(List<RecordHourSlaEntity> slaList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> slaTcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < slaList.size(); i++) {
				double score = 0;
				//Sla Tcp
				if ((slaList.get(i).getServiceType()) == 10) {
					//delay 100
					if ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT12"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 80-100
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT12"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT13"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT13")))) * 20) / ((Double.parseDouble(pros.getValue("slaT12")) - (Double.parseDouble(pros.getValue("slaT13"))))))) * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 60-80
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT13"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT14"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT14")))) * 20) / ((Double.parseDouble(pros.getValue("slaT13")) - (Double.parseDouble(pros.getValue("slaT14"))))))) * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 40-60
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT14"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT15"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT15")))) * 20) / ((Double.parseDouble(pros.getValue("slaT14")) - (Double.parseDouble(pros.getValue("slaT15"))))))) * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 20-40
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT15"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT16"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT16")))) * 20) / ((Double.parseDouble(pros.getValue("slaT15")) - (Double.parseDouble(pros.getValue("slaT16"))))))) * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 0-20
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT16"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaT17"))) <= 0)) {
						score += ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT17")))) * 20) / ((Double.parseDouble(pros.getValue("slaT16")) - (Double.parseDouble(pros.getValue("slaT17")))))) * (Double.parseDouble(pros.getValue("slaT11")));
					}
					//delay 0
					else {
						score += 0;
					}

					//g_delay 100
					if ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT22"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 80-100
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT22"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT23"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT23")))) * 20) / ((Double.parseDouble(pros.getValue("slaT22")) - (Double.parseDouble(pros.getValue("slaT23"))))))) * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 60-80
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT23"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT24"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT24")))) * 20) / ((Double.parseDouble(pros.getValue("slaT23")) - (Double.parseDouble(pros.getValue("slaT24"))))))) * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 40-60
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT24"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT25"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT25")))) * 20) / ((Double.parseDouble(pros.getValue("slaT24")) - (Double.parseDouble(pros.getValue("slaT25"))))))) * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 20-40
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT25"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT26"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT26")))) * 20) / ((Double.parseDouble(pros.getValue("slaT25")) - (Double.parseDouble(pros.getValue("slaT26"))))))) * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 0-20
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT26"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaT27"))) <= 0)) {
						score += ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT27")))) * 20) / ((Double.parseDouble(pros.getValue("slaT26")) - (Double.parseDouble(pros.getValue("slaT27")))))) * (Double.parseDouble(pros.getValue("slaT21")));
					}
					//g_delay 0
					else {
						score += 0;
					}

					//r_delay 100
					if ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT32"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 80-100
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT32"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT33"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT33")))) * 20) / ((Double.parseDouble(pros.getValue("slaT32")) - (Double.parseDouble(pros.getValue("slaT33"))))))) * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 60-80
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT33"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT34"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT34")))) * 20) / ((Double.parseDouble(pros.getValue("slaT33")) - (Double.parseDouble(pros.getValue("slaT34"))))))) * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 40-60
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT34"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT35"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT35")))) * 20) / ((Double.parseDouble(pros.getValue("slaT34")) - (Double.parseDouble(pros.getValue("slaT35"))))))) * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 20-40
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT35"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT36"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT36")))) * 20) / ((Double.parseDouble(pros.getValue("slaT35")) - (Double.parseDouble(pros.getValue("slaT36"))))))) * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 0-20
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT36"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaT37"))) <= 0)) {
						score += ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaT37")))) * 20) / ((Double.parseDouble(pros.getValue("slaT36")) - (Double.parseDouble(pros.getValue("slaT37")))))) * (Double.parseDouble(pros.getValue("slaT31")));
					}
					//r_delay 0
					else {
						score += 0;
					}

					//jitter 100
					if ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT42"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 80-100
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT42"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT43"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT43")))) * 20) / ((Double.parseDouble(pros.getValue("slaT42")) - (Double.parseDouble(pros.getValue("slaT43"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 60-80
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT43"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT44"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT44")))) * 20) / ((Double.parseDouble(pros.getValue("slaT43")) - (Double.parseDouble(pros.getValue("slaT44"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 40-60
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT44"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT45"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT45")))) * 20) / ((Double.parseDouble(pros.getValue("slaT44")) - (Double.parseDouble(pros.getValue("slaT45"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 20-40
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT45"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT46"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT46")))) * 20) / ((Double.parseDouble(pros.getValue("slaT45")) - (Double.parseDouble(pros.getValue("slaT46"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 0-20
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT46"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT47"))) <= 0)) {
						score += ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT47")))) * 20) / ((Double.parseDouble(pros.getValue("slaT46")) - (Double.parseDouble(pros.getValue("slaT47")))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//jitter 0
					else {
						score += 0;
					}

					//g_jitter 100
					if ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT52"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 80-100
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT52"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT53"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT53")))) * 20) / ((Double.parseDouble(pros.getValue("slaT52")) - (Double.parseDouble(pros.getValue("slaT53"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 60-80
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT53"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT54"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT54")))) * 20) / ((Double.parseDouble(pros.getValue("slaT53")) - (Double.parseDouble(pros.getValue("slaT54"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 40-60
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT54"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT55"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT55")))) * 20) / ((Double.parseDouble(pros.getValue("slaT54")) - (Double.parseDouble(pros.getValue("slaT55"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 20-40
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT55"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT56"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT56")))) * 20) / ((Double.parseDouble(pros.getValue("slaT55")) - (Double.parseDouble(pros.getValue("slaT56"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 0-20
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT56"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT57"))) <= 0)) {
						score += ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT57")))) * 20) / ((Double.parseDouble(pros.getValue("slaT56")) - (Double.parseDouble(pros.getValue("slaT57")))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_jitter 0
					else {
						score += 0;
					}

					//r_jitter 100
					if ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT62"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 80-100
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT62"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT63"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT63")))) * 20) / ((Double.parseDouble(pros.getValue("slaT62")) - (Double.parseDouble(pros.getValue("slaT63"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 60-80
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT63"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT64"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT64")))) * 20) / ((Double.parseDouble(pros.getValue("slaT63")) - (Double.parseDouble(pros.getValue("slaT64"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 40-60
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT64"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT65"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT65")))) * 20) / ((Double.parseDouble(pros.getValue("slaT64")) - (Double.parseDouble(pros.getValue("slaT65"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 20-40
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT65"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT66"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT66")))) * 20) / ((Double.parseDouble(pros.getValue("slaT65")) - (Double.parseDouble(pros.getValue("slaT66"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 0-20
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT66"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT67"))) <= 0)) {
						score += ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT67")))) * 20) / ((Double.parseDouble(pros.getValue("slaT66")) - (Double.parseDouble(pros.getValue("slaT67")))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_jitter 0
					else {
						score += 0;
					}

					//loss_rate 100
					if ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT72"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 80-100
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT72"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT73"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT73")))) * 20) / ((Double.parseDouble(pros.getValue("slaT72")) - (Double.parseDouble(pros.getValue("slaT73"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 60-80
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT73"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT74"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT74")))) * 20) / ((Double.parseDouble(pros.getValue("slaT73")) - (Double.parseDouble(pros.getValue("slaT74"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 40-60
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT74"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT75"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT75")))) * 20) / ((Double.parseDouble(pros.getValue("slaT74")) - (Double.parseDouble(pros.getValue("slaT75"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 20-40
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT75"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT76"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT76")))) * 20) / ((Double.parseDouble(pros.getValue("slaT75")) - (Double.parseDouble(pros.getValue("slaT76"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 0-20
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT76"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT77"))) <= 0)) {
						score += ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT77")))) * 20) / ((Double.parseDouble(pros.getValue("slaT76")) - (Double.parseDouble(pros.getValue("slaT77")))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//loss_rate 0
					else {
						score += 0;
					}

					System.out.println("SLA TCP:"+score);

					ScoreEntity tcpSla = new ScoreEntity();
					tcpSla.setId(slaList.get(i).getId());
					tcpSla.setCityName(slaList.get(i).getCityName());
					tcpSla.setCityId(slaList.get(i).getCityId());
					tcpSla.setCountyName(slaList.get(i).getAreaName());
					tcpSla.setCountyId(slaList.get(i).getCountyId());
					tcpSla.setProbeName(slaList.get(i).getProbeName());
					tcpSla.setProbeId(slaList.get(i).getProbeId());
					tcpSla.setServiceType(slaList.get(i).getServiceType());
					tcpSla.setTargetName(slaList.get(i).getTargetName());
					tcpSla.setTargetId(slaList.get(i).getTargetId());
					tcpSla.setAccessLayer(slaList.get(i).getAccessLayer());
					tcpSla.setPort(slaList.get(i).getPort());
					tcpSla.setRecordDate(slaList.get(i).getRecordDate());
					tcpSla.setRecordTime(slaList.get(i).getRecordTime());
					tcpSla.setSlaTcpDelay(slaList.get(i).getDelay());
					tcpSla.setSlaTcpGDelay(slaList.get(i).getGDelay());
					tcpSla.setSlaTcpRDelay(slaList.get(i).getRDelay());
					tcpSla.setSlaTcpJitter(slaList.get(i).getJitter());
					tcpSla.setSlaTcpGJitter(slaList.get(i).getGJitter());
					tcpSla.setSlaTcpRJitter(slaList.get(i).getRJitter());
					tcpSla.setSlaTcpLossRate(slaList.get(i).getLossRate());
					tcpSla.setFail(recordFail.getFail());
					tcpSla.setTotal(recordFail.getTotal());
					map.put("service_type",10);
					tcpSla.setScore(score*(1-fail));
					tcpSla.setBase(Double.parseDouble(pros.getValue("sla_tcp")));

					slaTcp.add(tcpSla);
				}}
		}catch (IOException e) {
		}

		return slaTcp;
	}

	@Override
	public List<ScoreEntity> calculateSlaUdp(List<RecordHourSlaEntity> slaList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> slaUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < slaList.size(); i++) {
				double score = 0;
				//Sla Udp
				if(slaList.get(i).getServiceType()==11){
					//delay 100
					if ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU12"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 80-100
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU12"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU13"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU13")))) * 20) / ((Double.parseDouble(pros.getValue("slaU12")) - (Double.parseDouble(pros.getValue("slaU13"))))))) * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 60-80
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU13"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU14"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU14")))) * 20) / ((Double.parseDouble(pros.getValue("slaU13")) - (Double.parseDouble(pros.getValue("slaU14"))))))) * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 40-60
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU14"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU15"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU15")))) * 20) / ((Double.parseDouble(pros.getValue("slaU14")) - (Double.parseDouble(pros.getValue("slaU15"))))))) * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 20-40
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU15"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU16"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU16")))) * 20) / ((Double.parseDouble(pros.getValue("slaU15")) - (Double.parseDouble(pros.getValue("slaU16"))))))) * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 0-20
					else if (((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU16"))) > 0) && ((slaList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("slaU17"))) <= 0)) {
						score += ((((slaList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU17")))) * 20) / ((Double.parseDouble(pros.getValue("slaU16")) - (Double.parseDouble(pros.getValue("slaU17")))))) * (Double.parseDouble(pros.getValue("slaU11")));
					}
					//delay 0
					else {
						score += 0;
					}

					//g_delay 100
					if ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU22"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 80-100
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU22"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU23"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU23")))) * 20) / ((Double.parseDouble(pros.getValue("slaU22")) - (Double.parseDouble(pros.getValue("slaU23"))))))) * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 60-80
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU23"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU24"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU24")))) * 20) / ((Double.parseDouble(pros.getValue("slaU23")) - (Double.parseDouble(pros.getValue("slaU24"))))))) * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 40-60
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU24"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU25"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU25")))) * 20) / ((Double.parseDouble(pros.getValue("slaU24")) - (Double.parseDouble(pros.getValue("slaU25"))))))) * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 20-40
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU25"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU26"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU26")))) * 20) / ((Double.parseDouble(pros.getValue("slaU25")) - (Double.parseDouble(pros.getValue("slaU26"))))))) * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 0-20
					else if (((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU26"))) > 0) && ((slaList.get(i).getGDelay()).compareTo(Double.parseDouble(pros.getValue("slaU27"))) <= 0)) {
						score += ((((slaList.get(i).getGDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU27")))) * 20) / ((Double.parseDouble(pros.getValue("slaU26")) - (Double.parseDouble(pros.getValue("slaU27")))))) * (Double.parseDouble(pros.getValue("slaU21")));
					}
					//g_delay 0
					else {
						score += 0;
					}

					//r_delay 100
					if ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU32"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 80-100
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU32"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU33"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU33")))) * 20) / ((Double.parseDouble(pros.getValue("slaU32")) - (Double.parseDouble(pros.getValue("slaU33"))))))) * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 60-80
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU33"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU34"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU34")))) * 20) / ((Double.parseDouble(pros.getValue("slaU33")) - (Double.parseDouble(pros.getValue("slaU34"))))))) * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 40-60
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU34"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU35"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU35")))) * 20) / ((Double.parseDouble(pros.getValue("slaU34")) - (Double.parseDouble(pros.getValue("slaU35"))))))) * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 20-40
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU35"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU36"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU36")))) * 20) / ((Double.parseDouble(pros.getValue("slaU35")) - (Double.parseDouble(pros.getValue("slaU36"))))))) * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 0-20
					else if (((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU36"))) > 0) && ((slaList.get(i).getRDelay()).compareTo(Double.parseDouble(pros.getValue("slaU37"))) <= 0)) {
						score += ((((slaList.get(i).getRDelay().doubleValue()) - (Double.parseDouble(pros.getValue("slaU37")))) * 20) / ((Double.parseDouble(pros.getValue("slaU36")) - (Double.parseDouble(pros.getValue("slaU37")))))) * (Double.parseDouble(pros.getValue("slaU31")));
					}
					//r_delay 0
					else {
						score += 0;
					}

					//jitter 100
					if ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU42"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 80-100
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU42"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU43"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU43")))) * 20) / ((Double.parseDouble(pros.getValue("slaU42")) - (Double.parseDouble(pros.getValue("slaU43"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 60-80
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU43"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU44"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU44")))) * 20) / ((Double.parseDouble(pros.getValue("slaU43")) - (Double.parseDouble(pros.getValue("slaU44"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 40-60
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU44"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU45"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU45")))) * 20) / ((Double.parseDouble(pros.getValue("slaU44")) - (Double.parseDouble(pros.getValue("slaU45"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 20-40
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU45"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU46"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU46")))) * 20) / ((Double.parseDouble(pros.getValue("slaU45")) - (Double.parseDouble(pros.getValue("slaU46"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 0-20
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU46"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU47"))) <= 0)) {
						score += ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU47")))) * 20) / ((Double.parseDouble(pros.getValue("slaU46")) - (Double.parseDouble(pros.getValue("slaU47")))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//jitter 0
					else {
						score += 0;
					}

					//g_jitter 100
					if ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU52"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 80-100
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU52"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU53"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU53")))) * 20) / ((Double.parseDouble(pros.getValue("slaU52")) - (Double.parseDouble(pros.getValue("slaU53"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 60-80
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU53"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU54"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU54")))) * 20) / ((Double.parseDouble(pros.getValue("slaU53")) - (Double.parseDouble(pros.getValue("slaU54"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 40-60
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU54"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU55"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU55")))) * 20) / ((Double.parseDouble(pros.getValue("slaU54")) - (Double.parseDouble(pros.getValue("slaU55"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 20-40
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU55"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU56"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU56")))) * 20) / ((Double.parseDouble(pros.getValue("slaU55")) - (Double.parseDouble(pros.getValue("slaU56"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 0-20
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU56"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU57"))) <= 0)) {
						score += ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU57")))) * 20) / ((Double.parseDouble(pros.getValue("slaU56")) - (Double.parseDouble(pros.getValue("slaU57")))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_jitter 0
					else {
						score += 0;
					}

					//r_jitter 100
					if ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU62"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 80-100
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU62"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU63"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU63")))) * 20) / ((Double.parseDouble(pros.getValue("slaU62")) - (Double.parseDouble(pros.getValue("slaU63"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 60-80
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU63"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU64"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU64")))) * 20) / ((Double.parseDouble(pros.getValue("slaU63")) - (Double.parseDouble(pros.getValue("slaU64"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 40-60
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU64"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU65"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU65")))) * 20) / ((Double.parseDouble(pros.getValue("slaU64")) - (Double.parseDouble(pros.getValue("slaU65"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 20-40
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU65"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU66"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU66")))) * 20) / ((Double.parseDouble(pros.getValue("slaU65")) - (Double.parseDouble(pros.getValue("slaU66"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 0-20
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU66"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU67"))) <= 0)) {
						score += ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU67")))) * 20) / ((Double.parseDouble(pros.getValue("slaU66")) - (Double.parseDouble(pros.getValue("slaU67")))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_jitter 0
					else {
						score += 0;
					}

					//loss_rate 100
					if ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU72"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 80-100
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU72"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU73"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU73")))) * 20) / ((Double.parseDouble(pros.getValue("slaU72")) - (Double.parseDouble(pros.getValue("slaU73"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 60-80
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU73"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU74"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU74")))) * 20) / ((Double.parseDouble(pros.getValue("slaU73")) - (Double.parseDouble(pros.getValue("slaU74"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 40-60
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU74"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU75"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU75")))) * 20) / ((Double.parseDouble(pros.getValue("slaU74")) - (Double.parseDouble(pros.getValue("slaU75"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 20-40
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU75"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU76"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU76")))) * 20) / ((Double.parseDouble(pros.getValue("slaU75")) - (Double.parseDouble(pros.getValue("slaU76"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 0-20
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU76"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU77"))) <= 0)) {
						score += ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU77")))) * 20) / ((Double.parseDouble(pros.getValue("slaU76")) - (Double.parseDouble(pros.getValue("slaU77")))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//loss_rate 0
					else {
						score += 0;
					}

					System.out.println("Sla Udp:"+score);

					ScoreEntity udpSla = new ScoreEntity();
					udpSla.setId(slaList.get(i).getId());
					udpSla.setCityName(slaList.get(i).getCityName());
					udpSla.setCityId(slaList.get(i).getCityId());
					udpSla.setCountyName(slaList.get(i).getAreaName());
					udpSla.setCountyId(slaList.get(i).getCountyId());
					udpSla.setProbeName(slaList.get(i).getProbeName());
					udpSla.setProbeId(slaList.get(i).getProbeId());
					udpSla.setServiceType(slaList.get(i).getServiceType());
					udpSla.setTargetName(slaList.get(i).getTargetName());
					udpSla.setTargetId(slaList.get(i).getTargetId());
					udpSla.setAccessLayer(slaList.get(i).getAccessLayer());
					udpSla.setPort(slaList.get(i).getPort());
					udpSla.setRecordDate(slaList.get(i).getRecordDate());
					udpSla.setRecordTime(slaList.get(i).getRecordTime());
					udpSla.setSlaUdpDelay(slaList.get(i).getDelay());
					udpSla.setSlaUdpGDelay(slaList.get(i).getGDelay());
					udpSla.setSlaUdpRDelay(slaList.get(i).getRDelay());
					udpSla.setSlaUdpJitter(slaList.get(i).getJitter());
					udpSla.setSlaUdpGJitter(slaList.get(i).getGJitter());
					udpSla.setSlaUdpRJitter(slaList.get(i).getRJitter());
					udpSla.setSlaUdpLossRate(slaList.get(i).getLossRate());
					udpSla.setFail(recordFail.getFail());
					udpSla.setTotal(recordFail.getTotal());
					map.put("service_type",11);
					udpSla.setScore(score*(1-fail));
					udpSla.setBase(Double.parseDouble(pros.getValue("sla_udp")));

					slaUdp.add(udpSla);
				}
			}
		} catch (IOException e) {
		}
		return slaUdp;
	}

	@Override
	public List<ScoreEntity> calculateDns(List<RecordHourDnsEntity> dnsList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> dns = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < dnsList.size(); i++) {
				double score=0;
				//delay 100
				if ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 80-100
				else if (((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns12"))) > 0) && ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns13"))) <= 0)) {
					score += (80 + ((((dnsList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dns13")))) * 20) / ((Double.parseDouble(pros.getValue("dns12")) - (Double.parseDouble(pros.getValue("dns13"))))))) * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 60-80
				else if (((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns13"))) > 0) && ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns14"))) <= 0)) {
					score += (60 + ((((dnsList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dns14")))) * 20) / ((Double.parseDouble(pros.getValue("dns13")) - (Double.parseDouble(pros.getValue("dns14"))))))) * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 40-60
				else if (((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns14"))) > 0) && ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns15"))) <= 0)) {
					score += (40 + ((((dnsList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dns15")))) * 20) / ((Double.parseDouble(pros.getValue("dns14")) - (Double.parseDouble(pros.getValue("dns15"))))))) * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 20-40
				else if (((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns15"))) > 0) && ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns16"))) <= 0)) {
					score += (20 + ((((dnsList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dns16")))) * 20) / ((Double.parseDouble(pros.getValue("dns15")) - (Double.parseDouble(pros.getValue("dns16"))))))) * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 0-20
				else if (((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns16"))) > 0) && ((dnsList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dns17"))) <= 0)) {
					score += ((((dnsList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dns17")))) * 20) / ((Double.parseDouble(pros.getValue("dns16")) - (Double.parseDouble(pros.getValue("dns17")))))) * (Double.parseDouble(pros.getValue("dns11")));
				}
				//delay 0
				else {
					score += 0;
				}

				//success rate 100
				Double success = dnsList.get(i).getSuccessRate()*100.00;
				if ((success).compareTo(Double.parseDouble(pros.getValue("dns22"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 80-100
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dns22"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dns23"))) >= 0)) {
					score += (80 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dns23")))) * 20) / ((Double.parseDouble(pros.getValue("dns22")) - (Double.parseDouble(pros.getValue("dns23"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 60-80
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dns23"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dns24"))) >= 0)) {
					score += (60 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dns24")))) * 20) / ((Double.parseDouble(pros.getValue("dns23")) - (Double.parseDouble(pros.getValue("dns24"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 40-60
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dns24"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dns25"))) >= 0)) {
					score += (40 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dns25")))) * 20) / ((Double.parseDouble(pros.getValue("dns24")) - (Double.parseDouble(pros.getValue("dns25"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 20-40
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dns25"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dns26"))) >= 0)) {
					score += (20 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dns26")))) * 20) / ((Double.parseDouble(pros.getValue("dns25")) - (Double.parseDouble(pros.getValue("dns26"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 0-20
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dns26"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dns27"))) >= 0)) {
					score += ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dns27")))) * 20) / ((Double.parseDouble(pros.getValue("dns26")) - (Double.parseDouble(pros.getValue("dns27")))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 0
				else {
					score += 0;
				}


				ScoreEntity DNS = new ScoreEntity();
				DNS.setId(dnsList.get(i).getId());
				DNS.setCityName(dnsList.get(i).getCityName());
				DNS.setCityId(dnsList.get(i).getCityId());
				DNS.setCountyName(dnsList.get(i).getAreaName());
				DNS.setCountyId(dnsList.get(i).getCountyId());
				DNS.setProbeName(dnsList.get(i).getProbeName());
				DNS.setProbeId(dnsList.get(i).getProbeId());
				DNS.setServiceType(dnsList.get(i).getServiceType());
				DNS.setTargetName(dnsList.get(i).getTargetName());
				DNS.setTargetId(dnsList.get(i).getTargetId());
				DNS.setAccessLayer(dnsList.get(i).getAccessLayer());
				DNS.setPort(dnsList.get(i).getPort());
				DNS.setRecordDate(dnsList.get(i).getRecordDate());
				DNS.setRecordTime(dnsList.get(i).getRecordTime());
				DNS.setDnsDelay(dnsList.get(i).getDelay());
				DNS.setDnsSuccessRate(dnsList.get(i).getSuccessRate());
				DNS.setFail(recordFail.getFail());
				DNS.setTotal(recordFail.getTotal());
				map.put("service_type",14);
				DNS.setScore(score*(1-fail));
				DNS.setBase(Double.parseDouble(pros.getValue("dns")));

				dns.add(DNS);

			}
		} catch (IOException e) {
		}
		return dns;
	}

	@Override
	public List<ScoreEntity> calculateDhcp(List<RecordHourDhcpEntity> dhcpList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> dhcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < dhcpList.size(); i++) {
				double score=0;
				//delay 100
				if ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 80-100
				else if (((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp12"))) > 0) && ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp13"))) <= 0)) {
					score += (80 + ((((dhcpList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp13")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp12")) - (Double.parseDouble(pros.getValue("dhcp13"))))))) * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 60-80
				else if (((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp13"))) > 0) && ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp14"))) <= 0)) {
					score += (60 + ((((dhcpList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp14")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp13")) - (Double.parseDouble(pros.getValue("dhcp14"))))))) * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 40-60
				else if (((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp14"))) > 0) && ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp15"))) <= 0)) {
					score += (40 + ((((dhcpList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp15")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp14")) - (Double.parseDouble(pros.getValue("dhcp15"))))))) * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 20-40
				else if (((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp15"))) > 0) && ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp16"))) <= 0)) {
					score += (20 + ((((dhcpList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp16")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp15")) - (Double.parseDouble(pros.getValue("dhcp16"))))))) * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 0-20
				else if (((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp16"))) > 0) && ((dhcpList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("dhcp17"))) <= 0)) {
					score += ((((dhcpList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp17")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp16")) - (Double.parseDouble(pros.getValue("dhcp17")))))) * (Double.parseDouble(pros.getValue("dhcp11")));
				}
				//delay 0
				else {
					score += 0;
				}

				Double success = dhcpList.get(i).getSuccessRate()*100.00;
				//success rate 100
				if ((success).compareTo(Double.parseDouble(pros.getValue("dhcp22"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 80-100
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dhcp22"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dhcp23"))) >= 0)) {
					score += (80 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dhcp23")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp22")) - (Double.parseDouble(pros.getValue("dhcp23"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 60-80
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dhcp23"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dhcp24"))) >= 0)) {
					score += (60 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dhcp24")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp23")) - (Double.parseDouble(pros.getValue("dhcp24"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 40-60
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dhcp24"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dhcp25"))) >= 0)) {
					score += (40 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dhcp25")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp24")) - (Double.parseDouble(pros.getValue("dhcp25"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 20-40
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dhcp25"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dhcp26"))) >= 0)) {
					score += (20 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dhcp26")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp25")) - (Double.parseDouble(pros.getValue("dhcp26"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 0-20
				else if (((success).compareTo(Double.parseDouble(pros.getValue("dhcp26"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("dhcp27")))>= 0)) {
					score += ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("dhcp27")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp26")) - (Double.parseDouble(pros.getValue("dhcp27")))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 0
				else {
					score += 0;
				}
				System.out.println("DHCP:"+score);


				ScoreEntity DHCP = new ScoreEntity();
				DHCP.setId(dhcpList.get(i).getId());
				DHCP.setCityName(dhcpList.get(i).getCityName());
				DHCP.setCityId(dhcpList.get(i).getCityId());
				DHCP.setCountyName(dhcpList.get(i).getAreaName());
				DHCP.setCountyId(dhcpList.get(i).getCountyId());
				DHCP.setProbeName(dhcpList.get(i).getProbeName());
				DHCP.setProbeId(dhcpList.get(i).getProbeId());
				DHCP.setServiceType(dhcpList.get(i).getServiceType());
				DHCP.setTargetName(dhcpList.get(i).getTargetName());
				DHCP.setTargetId(dhcpList.get(i).getTargetId());
				DHCP.setAccessLayer(dhcpList.get(i).getAccessLayer());
				DHCP.setPort(dhcpList.get(i).getPort());
				DHCP.setRecordDate(dhcpList.get(i).getRecordDate());
				DHCP.setRecordTime(dhcpList.get(i).getRecordTime());
				DHCP.setDhcpDelay(dhcpList.get(i).getDelay());
				DHCP.setDhcpSuccessRate(dhcpList.get(i).getSuccessRate());
				DHCP.setFail(recordFail.getFail());
				DHCP.setTotal(recordFail.getTotal());
				map.put("service_type",13);
				DHCP.setScore(score*(1-fail));
				DHCP.setBase(Double.parseDouble(pros.getValue("dhcp")));

				dhcp.add(DHCP);
			}
		} catch (IOException e) {
		}
		return dhcp;
	}

	@Override
	public List<ScoreEntity> calculatePppoe(List<RecordHourPppoeEntity> pppoeList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> pppoe = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < pppoeList.size(); i++) {
				double score = 0;
				//delay 100
				if ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 80-100
				else if (((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl12"))) > 0) && ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl13"))) <= 0)) {
					score += (80 + ((((pppoeList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("adsl13")))) * 20) / ((Double.parseDouble(pros.getValue("adsl12")) - (Double.parseDouble(pros.getValue("adsl13"))))))) * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 60-80
				else if (((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl13"))) > 0) && ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl14"))) <= 0)) {
					score += (60 + ((((pppoeList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("adsl14")))) * 20) / ((Double.parseDouble(pros.getValue("adsl13")) - (Double.parseDouble(pros.getValue("adsl14"))))))) * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 40-60
				else if (((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl14"))) > 0) && ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl15"))) <= 0)) {
					score += (40 + ((((pppoeList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("adsl15")))) * 20) / ((Double.parseDouble(pros.getValue("adsl14")) - (Double.parseDouble(pros.getValue("adsl125"))))))) * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 20-40
				else if (((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl15"))) > 0) && ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl16"))) <= 0)) {
					score += (20 + ((((pppoeList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("adsl16")))) * 20) / ((Double.parseDouble(pros.getValue("adsl15")) - (Double.parseDouble(pros.getValue("adsl16"))))))) * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 0-20
				else if (((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl16"))) > 0) && ((pppoeList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("adsl17"))) <= 0)) {
					score += ((((pppoeList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("adsl17")))) * 20) / ((Double.parseDouble(pros.getValue("adsl16")) - (Double.parseDouble(pros.getValue("adsl17")))))) * (Double.parseDouble(pros.getValue("adsl11")));
				}
				//delay 0
				else {
					score += 0;
				}

				Double loss = pppoeList.get(i).getDropRate()*100.00;
				//loss rate 100
				if ((loss).compareTo(Double.parseDouble(pros.getValue("adsl22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 80-100
				else if (((loss).compareTo(Double.parseDouble(pros.getValue("adsl22"))) > 0) && ((loss).compareTo(Double.parseDouble(pros.getValue("adsl23"))) <= 0)) {
					score += (80 + ((((loss.doubleValue()) - (Double.parseDouble(pros.getValue("adsl23")))) * 20) / ((Double.parseDouble(pros.getValue("adsl22")) - (Double.parseDouble(pros.getValue("adsl23"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 60-80
				else if (((loss).compareTo(Double.parseDouble(pros.getValue("adsl23"))) > 0) && ((loss).compareTo(Double.parseDouble(pros.getValue("adsl24"))) <= 0)) {
					score += (60 + ((((loss.doubleValue()) - (Double.parseDouble(pros.getValue("adsl24")))) * 20) / ((Double.parseDouble(pros.getValue("adsl23")) - (Double.parseDouble(pros.getValue("adsl24"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 40-60
				else if (((loss).compareTo(Double.parseDouble(pros.getValue("adsl24"))) > 0) && ((loss).compareTo(Double.parseDouble(pros.getValue("adsl25"))) <= 0)) {
					score += (40 + ((((loss.doubleValue()) - (Double.parseDouble(pros.getValue("adsl25")))) * 20) / ((Double.parseDouble(pros.getValue("adsl24")) - (Double.parseDouble(pros.getValue("adsl25"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 20-40
				else if (((loss).compareTo(Double.parseDouble(pros.getValue("adsl25"))) > 0) && ((loss).compareTo(Double.parseDouble(pros.getValue("adsl26"))) <= 0)) {
					score += (20 + ((((loss.doubleValue()) - (Double.parseDouble(pros.getValue("adsl26")))) * 20) / ((Double.parseDouble(pros.getValue("adsl25")) - (Double.parseDouble(pros.getValue("adsl26"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 0-20
				else if (((loss).compareTo(Double.parseDouble(pros.getValue("adsl26"))) > 0) && ((loss).compareTo(Double.parseDouble(pros.getValue("adsl27"))) <= 0)) {
					score += ((((loss.doubleValue()) - (Double.parseDouble(pros.getValue("adsl27")))) * 20) / ((Double.parseDouble(pros.getValue("adsl26")) - (Double.parseDouble(pros.getValue("adsl27")))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 0
				else {
					score += 0;
				}

				Double success = pppoeList.get(i).getSuccessRate()*100.00;
				//success rate 100
				if ((success).compareTo(Double.parseDouble(pros.getValue("adsl32"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 80-100
				else if (((success).compareTo(Double.parseDouble(pros.getValue("adsl32"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("adsl33"))) >= 0)) {
					score += (80 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("adsl33")))) * 20) / ((Double.parseDouble(pros.getValue("adsl32")) - (Double.parseDouble(pros.getValue("adsl33"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 60-80
				else if (((success).compareTo(Double.parseDouble(pros.getValue("adsl33"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("adsl34"))) >= 0)) {
					score += (60 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("adsl34")))) * 20) / ((Double.parseDouble(pros.getValue("adsl33")) - (Double.parseDouble(pros.getValue("adsl34"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 40-60
				else if (((success).compareTo(Double.parseDouble(pros.getValue("adsl34"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("adsl35"))) >= 0)) {
					score += (40 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("adsl35")))) * 20) / ((Double.parseDouble(pros.getValue("adsl34")) - (Double.parseDouble(pros.getValue("adsl35"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 20-40
				else if (((success).compareTo(Double.parseDouble(pros.getValue("adsl35"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("adsl36"))) >= 0)) {
					score += (20 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("adsl36")))) * 20) / ((Double.parseDouble(pros.getValue("adsl35")) - (Double.parseDouble(pros.getValue("adsl36"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 0-20
				else if (((success).compareTo(Double.parseDouble(pros.getValue("adsl36"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("adsl37"))) >= 0)) {
					score += ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("adsl37")))) * 20) / ((Double.parseDouble(pros.getValue("adsl36")) - (Double.parseDouble(pros.getValue("adsl37")))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 0
				else {
					score += 0;
				}
				System.out.println("PppOe:"+score);


				ScoreEntity PPPOE = new ScoreEntity();
				PPPOE.setId(pppoeList.get(i).getId());
				PPPOE.setCityName(pppoeList.get(i).getCityName());
				PPPOE.setCityId(pppoeList.get(i).getCityId());
				PPPOE.setCountyName(pppoeList.get(i).getAreaName());
				PPPOE.setCountyId(pppoeList.get(i).getCountyId());
				PPPOE.setProbeName(pppoeList.get(i).getProbeName());
				PPPOE.setProbeId(pppoeList.get(i).getProbeId());
				PPPOE.setServiceType(pppoeList.get(i).getServiceType());
				PPPOE.setTargetName(pppoeList.get(i).getTargetName());
				PPPOE.setTargetId(pppoeList.get(i).getTargetId());
				PPPOE.setAccessLayer(pppoeList.get(i).getAccessLayer());
				PPPOE.setPort(pppoeList.get(i).getPort());
				PPPOE.setRecordDate(pppoeList.get(i).getRecordDate());
				PPPOE.setRecordTime(pppoeList.get(i).getRecordTime());
				PPPOE.setPppoeDelay(pppoeList.get(i).getDelay());
				PPPOE.setPppoeDropRate(pppoeList.get(i).getDropRate());
				PPPOE.setPppoeSuccessRate(pppoeList.get(i).getSuccessRate());
				PPPOE.setFail(recordFail.getFail());
				PPPOE.setTotal(recordFail.getTotal());
				map.put("service_type",12);
				PPPOE.setScore(score*(1-fail));
				PPPOE.setBase(Double.parseDouble(pros.getValue("adsl")));

				pppoe.add(PPPOE);
			}
		} catch (IOException e) {
		}
		return pppoe;
	}

	@Override
	public List<ScoreEntity> calculateRadius(List<RecordHourRadiusEntity> radiusList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> radius = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for (int i = 0; i < radiusList.size(); i++) {
				double score = 0;
				//delay 100
				if ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 80-100
				else if (((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius12"))) > 0) && ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius13"))) <= 0)) {
					score += (80 + ((((radiusList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("radius13")))) * 20) / ((Double.parseDouble(pros.getValue("radius12")) - (Double.parseDouble(pros.getValue("radius13"))))))) * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 60-80
				else if (((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius13"))) > 0) && ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius14"))) <= 0)) {
					score += (60 + ((((radiusList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("radius14")))) * 20) / ((Double.parseDouble(pros.getValue("radius13")) - (Double.parseDouble(pros.getValue("radius14"))))))) * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 40-60
				else if (((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius14"))) > 0) && ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius15"))) <= 0)) {
					score += (40 + ((((radiusList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("radius15")))) * 20) / ((Double.parseDouble(pros.getValue("radius14")) - (Double.parseDouble(pros.getValue("radius15"))))))) * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 20-40
				else if (((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius15"))) > 0) && ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius16"))) <= 0)) {
					score += (20 + ((((radiusList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("radius16")))) * 20) / ((Double.parseDouble(pros.getValue("radius15")) - (Double.parseDouble(pros.getValue("radius16"))))))) * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 0-20
				else if (((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius16"))) > 0) && ((radiusList.get(i).getDelay()).compareTo(Double.parseDouble(pros.getValue("radius17"))) <= 0)) {
					score += ((((radiusList.get(i).getDelay().doubleValue()) - (Double.parseDouble(pros.getValue("radius17")))) * 20) / ((Double.parseDouble(pros.getValue("radius16")) - (Double.parseDouble(pros.getValue("radius17")))))) * (Double.parseDouble(pros.getValue("radius11")));
				}
				//delay 0
				else {
					score += 0;
				}

				Double success = radiusList.get(i).getSuccessRate()*100.00;
				//success rate 100
				if ((success).compareTo(Double.parseDouble(pros.getValue("radius22"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 80-100
				else if (((success).compareTo(Double.parseDouble(pros.getValue("radius22"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("radius23"))) >= 0)) {
					score += (80 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("radius23")))) * 20) / ((Double.parseDouble(pros.getValue("radius22")) - (Double.parseDouble(pros.getValue("radius23"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 60-80
				else if (((success).compareTo(Double.parseDouble(pros.getValue("radius23"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("radius24")))  >= 0)) {
					score += (60 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("radius24")))) * 20) / ((Double.parseDouble(pros.getValue("radius23")) - (Double.parseDouble(pros.getValue("radius24"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 40-60
				else if (((success).compareTo(Double.parseDouble(pros.getValue("radius24"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("radius25")))  >= 0)) {
					score += (40 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("radius25")))) * 20) / ((Double.parseDouble(pros.getValue("radius24")) - (Double.parseDouble(pros.getValue("radius25"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 20-40
				else if (((success).compareTo(Double.parseDouble(pros.getValue("radius25"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("radius26")))  >= 0)) {
					score += (20 + ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("radius26")))) * 20) / ((Double.parseDouble(pros.getValue("radius25")) - (Double.parseDouble(pros.getValue("radius26"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 0-20
				else if (((success).compareTo(Double.parseDouble(pros.getValue("radius26"))) < 0) && ((success).compareTo(Double.parseDouble(pros.getValue("radius27")))  >= 0)) {
					score += ((((success.doubleValue()) - (Double.parseDouble(pros.getValue("radius27")))) * 20) / ((Double.parseDouble(pros.getValue("radius26")) - (Double.parseDouble(pros.getValue("radius27")))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 0
				else {
					score += 0;
				}
				System.out.println("RADIUS:"+score);


				ScoreEntity RADIUS = new ScoreEntity();
				RADIUS.setId(radiusList.get(i).getId());
				RADIUS.setCityName(radiusList.get(i).getCityName());
				RADIUS.setCityId(radiusList.get(i).getCityId());
				RADIUS.setCountyName(radiusList.get(i).getAreaName());
				RADIUS.setCountyId(radiusList.get(i).getCountyId());
				RADIUS.setProbeName(radiusList.get(i).getProbeName());
				RADIUS.setProbeId(radiusList.get(i).getProbeId());
				RADIUS.setServiceType(radiusList.get(i).getServiceType());
				RADIUS.setTargetName(radiusList.get(i).getTargetName());
				RADIUS.setTargetId(radiusList.get(i).getTargetId());
				RADIUS.setAccessLayer(radiusList.get(i).getAccessLayer());
				RADIUS.setPort(radiusList.get(i).getPort());
				RADIUS.setRecordDate(radiusList.get(i).getRecordDate());
				RADIUS.setRecordTime(radiusList.get(i).getRecordTime());
				RADIUS.setRadiusDelay(radiusList.get(i).getDelay());
				RADIUS.setRadiusSuccessRate(radiusList.get(i).getSuccessRate());
				RADIUS.setFail(recordFail.getFail());
				RADIUS.setTotal(recordFail.getTotal());
				map.put("service_type",15);
				RADIUS.setScore(score*(1-fail));
				RADIUS.setBase(Double.parseDouble(pros.getValue("radius")));

				radius.add(RADIUS);
			}
		} catch (IOException e) {
		}
		return radius;
	}

	@Override
	public List<ScoreEntity> calculateService2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius){
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetEntity, Map<String,ScoreBaseEntity>> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreTargetEntity scoreTarget = new ScoreTargetEntity();
				scoreTarget.setCityId(slaTcp.get(i).getCityId());
				scoreTarget.setCountyId(slaTcp.get(i).getCountyId());
				scoreTarget.setProbeId(slaTcp.get(i).getProbeId());
				scoreTarget.setTargetId(slaTcp.get(i).getTargetId());
				scoreTarget.setCityName(slaTcp.get(i).getCityName());
				scoreTarget.setCountyName(slaTcp.get(i).getCountyName());
				scoreTarget.setProbeName(slaTcp.get(i).getProbeName());
				scoreTarget.setTargetName(slaTcp.get(i).getTargetName());
				scoreTarget.setRecordTime(slaTcp.get(i).getRecordTime());
				scoreTarget.setRecordDate(slaTcp.get(i).getRecordDate());
				scoreTarget.setAccessLayer(slaTcp.get(i).getAccessLayer());
				scoreTarget.setPort(slaTcp.get(i).getPort());
				scoreTarget.setFail(slaTcp.get(i).getFail());
				scoreTarget.setTotal(slaTcp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setSlaTcpDelay(slaTcp.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(slaTcp.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(slaTcp.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(slaTcp.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(slaTcp.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(slaTcp.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(slaTcp.get(i).getSlaTcpLossRate());
				scoreBase.setScore(slaTcp.get(i).getScore());
				scoreBase.setBase(slaTcp.get(i).getBase());
				Map<String,ScoreBaseEntity> sla1 = new HashMap<>();
				sla1.put("slaTcp",scoreBase);
				connection.put(scoreTarget, sla1);
			}
			connection=pingService.putMap(slaUdp,connection,"slaUdp");
			connection=pingService.putMap(dns,connection,"dns");
			connection=pingService.putMap(dhcp,connection,"dhcp");
			connection=pingService.putMap(pppoe,connection,"pppoe");
			connection=pingService.putMap(radius,connection,"radius");

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
					finalScore.setServiceType(2);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setPort(ite.getPort());
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
						if(typ.equals("slaTcp")){
							finalScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
							finalScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
							finalScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
							finalScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
							finalScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
							finalScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
							finalScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
						}else if(typ.equals("slaUdp")){
							finalScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
							finalScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
							finalScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
							finalScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
							finalScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
							finalScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
							finalScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
						}else if(typ.equals("dns")){
							finalScore.setDnsDelay(map1.get(typ).getDnsDelay());
							finalScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
						}else if(typ.equals("dhcp")){
							finalScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
							finalScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
						}else if(typ.equals("pppoe")){
							finalScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
							finalScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
							finalScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
						}else if(typ.equals("radius")){
							finalScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
							finalScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
						}else{}
						finalScore.setScore(finalScore.getScore()+map1.get(typ).getScore()*map1.get(typ).getBase());
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}

		}catch(IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateArea2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius){
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreAreaEntity, Map<String,ScoreBaseEntity>> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreAreaEntity scoreArea = new ScoreAreaEntity();
				scoreArea.setCityId(slaTcp.get(i).getCityId());
				scoreArea.setCountyId(slaTcp.get(i).getCountyId());
				scoreArea.setProbeId(slaTcp.get(i).getProbeId());
				scoreArea.setTargetId(slaTcp.get(i).getTargetId());
				scoreArea.setCityName(slaTcp.get(i).getCityName());
				scoreArea.setCountyName(slaTcp.get(i).getCountyName());
				scoreArea.setProbeName(slaTcp.get(i).getProbeName());
				scoreArea.setTargetName(slaTcp.get(i).getTargetName());
				scoreArea.setAccessLayer(slaTcp.get(i).getAccessLayer());
				scoreArea.setRecordTime(slaTcp.get(i).getRecordTime());
				scoreArea.setRecordDate(slaTcp.get(i).getRecordDate());
				scoreArea.setPort(slaTcp.get(i).getPort());
				scoreArea.setFail(slaTcp.get(i).getFail());
				scoreArea.setTotal(slaTcp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setSlaTcpDelay(slaTcp.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(slaTcp.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(slaTcp.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(slaTcp.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(slaTcp.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(slaTcp.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(slaTcp.get(i).getSlaTcpLossRate());
				scoreBase.setScore(slaTcp.get(i).getScore());
				scoreBase.setBase(slaTcp.get(i).getBase());
				Map<String,ScoreBaseEntity> sla1 = new HashMap<>();
				sla1.put("slaTcp",scoreBase);
				connection.put(scoreArea, sla1);
			}
			connection=pingService.putMapArea(slaUdp,connection,"slaUdp");
			connection=pingService.putMapArea(dns,connection,"dns");
			connection=pingService.putMapArea(dhcp,connection,"dhcp");
			connection=pingService.putMapArea(pppoe,connection,"pppoe");
			connection=pingService.putMapArea(radius,connection,"radius");

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
					finalScore.setServiceType(2);
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
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
						if(typ.equals("slaTcp")){
							finalScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
							finalScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
							finalScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
							finalScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
							finalScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
							finalScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
							finalScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
						}else if(typ.equals("slaUdp")){
							finalScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
							finalScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
							finalScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
							finalScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
							finalScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
							finalScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
							finalScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
						}else if(typ.equals("dns")){
							finalScore.setDnsDelay(map1.get(typ).getDnsDelay());
							finalScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
						}else if(typ.equals("dhcp")){
							finalScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
							finalScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
						}else if(typ.equals("pppoe")){
							finalScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
							finalScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
							finalScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
						}else if(typ.equals("radius")){
							finalScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
							finalScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
						}else{}
						finalScore.setScore(finalScore.getScore()+map1.get(typ).getScore()*map1.get(typ).getBase());
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}

		}catch(IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateTarget2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius){
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetRankEntity, Map<String,ScoreBaseEntity>> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreTargetRankEntity targetRank = new ScoreTargetRankEntity();
				targetRank.setCityId(slaTcp.get(i).getCityId());
				targetRank.setCountyId(slaTcp.get(i).getCountyId());
				targetRank.setProbeId(slaTcp.get(i).getProbeId());
				targetRank.setTargetId(slaTcp.get(i).getTargetId());
				targetRank.setCityName(slaTcp.get(i).getCityName());
				targetRank.setCountyName(slaTcp.get(i).getCountyName());
				targetRank.setProbeName(slaTcp.get(i).getProbeName());
				targetRank.setTargetName(slaTcp.get(i).getTargetName());
				targetRank.setAccessLayer(slaTcp.get(i).getAccessLayer());
				targetRank.setRecordTime(slaTcp.get(i).getRecordTime());
				targetRank.setRecordDate(slaTcp.get(i).getRecordDate());
				targetRank.setPort(slaTcp.get(i).getPort());
				targetRank.setFail(slaTcp.get(i).getFail());
				targetRank.setTotal(slaTcp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setSlaTcpDelay(slaTcp.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(slaTcp.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(slaTcp.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(slaTcp.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(slaTcp.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(slaTcp.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(slaTcp.get(i).getSlaTcpLossRate());
				scoreBase.setScore(slaTcp.get(i).getScore());
				scoreBase.setBase(slaTcp.get(i).getBase());
				Map<String,ScoreBaseEntity> sla1 = new HashMap<>();
				sla1.put("slaTcp",scoreBase);
				connection.put(targetRank, sla1);
			}
			connection=pingService.putMapTarget(slaUdp,connection,"slaUdp");
			connection=pingService.putMapTarget(dns,connection,"dns");
			connection=pingService.putMapTarget(dhcp,connection,"dhcp");
			connection=pingService.putMapTarget(pppoe,connection,"pppoe");
			connection=pingService.putMapTarget(radius,connection,"radius");

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
					finalScore.setServiceType(2);
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
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
						if(typ.equals("slaTcp")){
							finalScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
							finalScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
							finalScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
							finalScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
							finalScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
							finalScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
							finalScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
						}else if(typ.equals("slaUdp")){
							finalScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
							finalScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
							finalScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
							finalScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
							finalScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
							finalScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
							finalScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
						}else if(typ.equals("dns")){
							finalScore.setDnsDelay(map1.get(typ).getDnsDelay());
							finalScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
						}else if(typ.equals("dhcp")){
							finalScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
							finalScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
						}else if(typ.equals("pppoe")){
							finalScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
							finalScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
							finalScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
						}else if(typ.equals("radius")){
							finalScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
							finalScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
						}else{}
						finalScore.setScore(finalScore.getScore()+map1.get(typ).getScore()*map1.get(typ).getBase());
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}

		}catch(IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateDate2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius){
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreDateEntity, Map<String,ScoreBaseEntity>> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreDateEntity scoreDate = new ScoreDateEntity();
				scoreDate.setCityId(slaTcp.get(i).getCityId());
				scoreDate.setCountyId(slaTcp.get(i).getCountyId());
				scoreDate.setProbeId(slaTcp.get(i).getProbeId());
				scoreDate.setTargetId(slaTcp.get(i).getTargetId());
				scoreDate.setCityName(slaTcp.get(i).getCityName());
				scoreDate.setCountyName(slaTcp.get(i).getCountyName());
				scoreDate.setProbeName(slaTcp.get(i).getProbeName());
				scoreDate.setTargetName(slaTcp.get(i).getTargetName());
				scoreDate.setRecordDate(slaTcp.get(i).getRecordDate());
				scoreDate.setRecordTime(slaTcp.get(i).getRecordTime());
				scoreDate.setAccessLayer(slaTcp.get(i).getAccessLayer());
				scoreDate.setPort(slaTcp.get(i).getPort());
				scoreDate.setFail(slaTcp.get(i).getFail());
				scoreDate.setTotal(slaTcp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setSlaTcpDelay(slaTcp.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(slaTcp.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(slaTcp.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(slaTcp.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(slaTcp.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(slaTcp.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(slaTcp.get(i).getSlaTcpLossRate());
				scoreBase.setScore(slaTcp.get(i).getScore());
				scoreBase.setBase(slaTcp.get(i).getBase());
				Map<String,ScoreBaseEntity> sla1 = new HashMap<>();
				sla1.put("slaTcp",scoreBase);
				connection.put(scoreDate, sla1);
			}
			connection=pingService.putMapDate(slaUdp,connection,"slaUdp");
			connection=pingService.putMapDate(dns,connection,"dns");
			connection=pingService.putMapDate(dhcp,connection,"dhcp");
			connection=pingService.putMapDate(pppoe,connection,"pppoe");
			connection=pingService.putMapDate(radius,connection,"radius");

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
					finalScore.setServiceType(2);
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
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
						if(typ.equals("slaTcp")){
							finalScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
							finalScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
							finalScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
							finalScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
							finalScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
							finalScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
							finalScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
							finalScore.setTcpSlaScore(map1.get(typ).getScore());
						}else if(typ.equals("slaUdp")){
							finalScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
							finalScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
							finalScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
							finalScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
							finalScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
							finalScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
							finalScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
							finalScore.setUdpSlaScore(map1.get(typ).getScore());
						}else if(typ.equals("dns")){
							finalScore.setDnsDelay(map1.get(typ).getDnsDelay());
							finalScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
							finalScore.setDnsScore(map1.get(typ).getScore());
						}else if(typ.equals("dhcp")){
							finalScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
							finalScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
							finalScore.setDhcpScore(map1.get(typ).getScore());
						}else if(typ.equals("pppoe")){
							finalScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
							finalScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
							finalScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
							finalScore.setPppoeScore(map1.get(typ).getScore());
						}else if(typ.equals("radius")){
							finalScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
							finalScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
							finalScore.setRadiusScore(map1.get(typ).getScore());
						}else{}
						finalScore.setScore(finalScore.getScore()+map1.get(typ).getScore()*map1.get(typ).getBase());
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}

		}catch(IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateLayer2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius){
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreLayerEntity, Map<String,ScoreBaseEntity>> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				if(slaTcp.get(i).getAccessLayer()==null){
					continue;
				}
				ScoreLayerEntity scoreLayer = new ScoreLayerEntity();
				scoreLayer.setCityId(slaTcp.get(i).getCityId());
				scoreLayer.setCountyId(slaTcp.get(i).getCountyId());
				scoreLayer.setProbeId(slaTcp.get(i).getProbeId());
				scoreLayer.setTargetId(slaTcp.get(i).getTargetId());
				scoreLayer.setCityName(slaTcp.get(i).getCityName());
				scoreLayer.setCountyName(slaTcp.get(i).getCountyName());
				scoreLayer.setProbeName(slaTcp.get(i).getProbeName());
				scoreLayer.setTargetName(slaTcp.get(i).getTargetName());
				scoreLayer.setRecordDate(slaTcp.get(i).getRecordDate());
				scoreLayer.setRecordTime(slaTcp.get(i).getRecordTime());
				scoreLayer.setAccessLayer(slaTcp.get(i).getAccessLayer());
				scoreLayer.setPort(slaTcp.get(i).getPort());
				scoreLayer.setFail(slaTcp.get(i).getFail());
				scoreLayer.setTotal(slaTcp.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setSlaTcpDelay(slaTcp.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(slaTcp.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(slaTcp.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(slaTcp.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(slaTcp.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(slaTcp.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(slaTcp.get(i).getSlaTcpLossRate());
				scoreBase.setScore(slaTcp.get(i).getScore());
				scoreBase.setBase(slaTcp.get(i).getBase());
				Map<String,ScoreBaseEntity> sla1 = new HashMap<>();
				sla1.put("slaTcp",scoreBase);
				connection.put(scoreLayer, sla1);
			}
			connection=pingService.putMapLayer(slaUdp,connection,"slaUdp");
			connection=pingService.putMapLayer(dns,connection,"dns");
			connection=pingService.putMapLayer(dhcp,connection,"dhcp");
			connection=pingService.putMapLayer(pppoe,connection,"pppoe");
			connection=pingService.putMapLayer(radius,connection,"radius");

			System.out.println("MAP:"+connection);

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
					finalScore.setServiceType(2);
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
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
						if(typ.equals("slaTcp")){
							finalScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
							finalScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
							finalScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
							finalScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
							finalScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
							finalScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
							finalScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
							finalScore.setTcpSlaScore(map1.get(typ).getScore());
						}else if(typ.equals("slaUdp")){
							finalScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
							finalScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
							finalScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
							finalScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
							finalScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
							finalScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
							finalScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
							finalScore.setUdpSlaScore(map1.get(typ).getScore());
						}else if(typ.equals("dns")){
							finalScore.setDnsDelay(map1.get(typ).getDnsDelay());
							finalScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
							finalScore.setDnsScore(map1.get(typ).getScore());
						}else if(typ.equals("dhcp")){
							finalScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
							finalScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
							finalScore.setDhcpScore(map1.get(typ).getScore());
						}else if(typ.equals("pppoe")){
							finalScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
							finalScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
							finalScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
							finalScore.setPppoeScore(map1.get(typ).getScore());
						}else if(typ.equals("radius")){
							finalScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
							finalScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
							finalScore.setRadiusScore(map1.get(typ).getScore());
						}else{}
						finalScore.setScore(finalScore.getScore()+map1.get(typ).getScore()*map1.get(typ).getBase());
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}

		}catch(IOException e){}
		return connectionScore;
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourSlaDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourSlaEntity recordHourSla){
		recordHourSlaDao.save(recordHourSla);
	}
	
	@Override
	public void update(RecordHourSlaEntity recordHourSla){
		recordHourSlaDao.update(recordHourSla);
	}
	
	@Override
	public void delete(Integer id){
		recordHourSlaDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourSlaDao.deleteBatch(ids);
	}
	
}
