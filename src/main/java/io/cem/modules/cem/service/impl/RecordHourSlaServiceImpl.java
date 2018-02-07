package io.cem.modules.cem.service.impl;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordSlaDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordHourPingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
	public List<RecordHourSlaEntity> querySlaList(Map<String, Object> map){return recordHourSlaDao.querySlaList(map);}

	@Override
	public List<RecordHourSlaEntity> queryDayList(Map<String, Object> map){return recordHourSlaDao.queryDayList(map);}

	@Override
	public List<ScoreEntity> calculateSlaTcp(List<RecordHourSlaEntity> slaList){
		List<ScoreEntity> slaTcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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

					//delay_std 100
					if ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT42"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 80-100
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT42"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT43"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT43")))) * 20) / ((Double.parseDouble(pros.getValue("slaT42")) - (Double.parseDouble(pros.getValue("slaT43"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 60-80
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT43"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT44"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT44")))) * 20) / ((Double.parseDouble(pros.getValue("slaT43")) - (Double.parseDouble(pros.getValue("slaT44"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 40-60
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT44"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT45"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT45")))) * 20) / ((Double.parseDouble(pros.getValue("slaT44")) - (Double.parseDouble(pros.getValue("slaT45"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 20-40
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT45"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT46"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT46")))) * 20) / ((Double.parseDouble(pros.getValue("slaT45")) - (Double.parseDouble(pros.getValue("slaT46"))))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 0-20
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT46"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT47"))) <= 0)) {
						score += ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT47")))) * 20) / ((Double.parseDouble(pros.getValue("slaT46")) - (Double.parseDouble(pros.getValue("slaT47")))))) * (Double.parseDouble(pros.getValue("slaT41")));
					}
					//delay_std 0
					else {
						score += 0;
					}

					//g_delay_std 100
					if ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT52"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 80-100
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT52"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT53"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT53")))) * 20) / ((Double.parseDouble(pros.getValue("slaT52")) - (Double.parseDouble(pros.getValue("slaT53"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 60-80
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT53"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT54"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT54")))) * 20) / ((Double.parseDouble(pros.getValue("slaT53")) - (Double.parseDouble(pros.getValue("slaT54"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 40-60
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT54"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT55"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT55")))) * 20) / ((Double.parseDouble(pros.getValue("slaT54")) - (Double.parseDouble(pros.getValue("slaT55"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 20-40
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT55"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT56"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT56")))) * 20) / ((Double.parseDouble(pros.getValue("slaT55")) - (Double.parseDouble(pros.getValue("slaT56"))))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 0-20
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT56"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT57"))) <= 0)) {
						score += ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT57")))) * 20) / ((Double.parseDouble(pros.getValue("slaT56")) - (Double.parseDouble(pros.getValue("slaT57")))))) * (Double.parseDouble(pros.getValue("slaT51")));
					}
					//g_delay_std 0
					else {
						score += 0;
					}

					//r_delay_std 100
					if ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT62"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 80-100
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT62"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT63"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT63")))) * 20) / ((Double.parseDouble(pros.getValue("slaT62")) - (Double.parseDouble(pros.getValue("slaT63"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 60-80
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT63"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT64"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT64")))) * 20) / ((Double.parseDouble(pros.getValue("slaT63")) - (Double.parseDouble(pros.getValue("slaT64"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 40-60
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT64"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT65"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT65")))) * 20) / ((Double.parseDouble(pros.getValue("slaT64")) - (Double.parseDouble(pros.getValue("slaT65"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 20-40
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT65"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT66"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT66")))) * 20) / ((Double.parseDouble(pros.getValue("slaT65")) - (Double.parseDouble(pros.getValue("slaT66"))))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 0-20
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT66"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaT67"))) <= 0)) {
						score += ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT67")))) * 20) / ((Double.parseDouble(pros.getValue("slaT66")) - (Double.parseDouble(pros.getValue("slaT67")))))) * (Double.parseDouble(pros.getValue("slaT61")));
					}
					//r_delay_std 0
					else {
						score += 0;
					}

					//delay_var 100
					if ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT72"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 80-100
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT72"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT73"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT73")))) * 20) / ((Double.parseDouble(pros.getValue("slaT72")) - (Double.parseDouble(pros.getValue("slaT73"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 60-80
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT73"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT74"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT74")))) * 20) / ((Double.parseDouble(pros.getValue("slaT73")) - (Double.parseDouble(pros.getValue("slaT74"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 40-60
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT74"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT75"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT75")))) * 20) / ((Double.parseDouble(pros.getValue("slaT74")) - (Double.parseDouble(pros.getValue("slaT75"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 20-40
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT75"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT76"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT76")))) * 20) / ((Double.parseDouble(pros.getValue("slaT75")) - (Double.parseDouble(pros.getValue("slaT76"))))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 0-20
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT76"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT77"))) <= 0)) {
						score += ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT77")))) * 20) / ((Double.parseDouble(pros.getValue("slaT76")) - (Double.parseDouble(pros.getValue("slaT77")))))) * (Double.parseDouble(pros.getValue("slaT71")));
					}
					//delay_var 0
					else {
						score += 0;
					}

					//g_delay_var 100
					if ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT82"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 80-100
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT82"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT83"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT83")))) * 20) / ((Double.parseDouble(pros.getValue("slaT82")) - (Double.parseDouble(pros.getValue("slaT83"))))))) * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 60-80
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT83"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT84"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT84")))) * 20) / ((Double.parseDouble(pros.getValue("slaT83")) - (Double.parseDouble(pros.getValue("slaT84"))))))) * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 40-60
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT84"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT85"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT85")))) * 20) / ((Double.parseDouble(pros.getValue("slaT84")) - (Double.parseDouble(pros.getValue("slaT85"))))))) * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 20-40
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT85"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT86"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT86")))) * 20) / ((Double.parseDouble(pros.getValue("slaT85")) - (Double.parseDouble(pros.getValue("slaT86"))))))) * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 0-20
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT86"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT87"))) <= 0)) {
						score += ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT87")))) * 20) / ((Double.parseDouble(pros.getValue("slaT86")) - (Double.parseDouble(pros.getValue("slaT87")))))) * (Double.parseDouble(pros.getValue("slaT81")));
					}
					//g_delay_var 0
					else {
						score += 0;
					}

					//r_delay_var 100
					if ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT92"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 80-100
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT92"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT93"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT93")))) * 20) / ((Double.parseDouble(pros.getValue("slaT92")) - (Double.parseDouble(pros.getValue("slaT93"))))))) * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 60-80
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT93"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT94"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT94")))) * 20) / ((Double.parseDouble(pros.getValue("slaT93")) - (Double.parseDouble(pros.getValue("slaT94"))))))) * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 40-60
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT94"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT95"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT95")))) * 20) / ((Double.parseDouble(pros.getValue("slaT94")) - (Double.parseDouble(pros.getValue("slaT95"))))))) * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 20-40
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT95"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT96"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT96")))) * 20) / ((Double.parseDouble(pros.getValue("slaT95")) - (Double.parseDouble(pros.getValue("slaT96"))))))) * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 0-20
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT96"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaT97"))) <= 0)) {
						score += ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT97")))) * 20) / ((Double.parseDouble(pros.getValue("slaT96")) - (Double.parseDouble(pros.getValue("slaT97")))))) * (Double.parseDouble(pros.getValue("slaT91")));
					}
					//r_delay_var 0
					else {
						score += 0;
					}

					//jitter 100
					if ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT102"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 80-100
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT102"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT103"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT103")))) * 20) / ((Double.parseDouble(pros.getValue("slaT102")) - (Double.parseDouble(pros.getValue("slaT103"))))))) * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 60-80
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT103"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT104"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT104")))) * 20) / ((Double.parseDouble(pros.getValue("slaT103")) - (Double.parseDouble(pros.getValue("slaT104"))))))) * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 40-60
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT104"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT105"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT105")))) * 20) / ((Double.parseDouble(pros.getValue("slaT104")) - (Double.parseDouble(pros.getValue("slaT105"))))))) * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 20-40
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT105"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT106"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT106")))) * 20) / ((Double.parseDouble(pros.getValue("slaT105")) - (Double.parseDouble(pros.getValue("slaT106"))))))) * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 0-20
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT106"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaT107"))) <= 0)) {
						score += ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT107")))) * 20) / ((Double.parseDouble(pros.getValue("slaT106")) - (Double.parseDouble(pros.getValue("slaT107")))))) * (Double.parseDouble(pros.getValue("slaT101")));
					}
					//jitter 0
					else {
						score += 0;
					}

					//g_jitter 100
					if ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT112"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 80-100
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT112"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT113"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT113")))) * 20) / ((Double.parseDouble(pros.getValue("slaT112")) - (Double.parseDouble(pros.getValue("slaT113"))))))) * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 60-80
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT113"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT114"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT114")))) * 20) / ((Double.parseDouble(pros.getValue("slaT113")) - (Double.parseDouble(pros.getValue("slaT114"))))))) * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 40-60
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT114"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT115"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT115")))) * 20) / ((Double.parseDouble(pros.getValue("slaT114")) - (Double.parseDouble(pros.getValue("slaT115"))))))) * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 20-40
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT115"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT116"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT116")))) * 20) / ((Double.parseDouble(pros.getValue("slaT105")) - (Double.parseDouble(pros.getValue("slaT116"))))))) * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 0-20
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT116"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaT117"))) <= 0)) {
						score += ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT117")))) * 20) / ((Double.parseDouble(pros.getValue("slaT116")) - (Double.parseDouble(pros.getValue("slaT117")))))) * (Double.parseDouble(pros.getValue("slaT111")));
					}
					//g_jitter 0
					else {
						score += 0;
					}

					//r_jitter 100
					if ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT122"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 80-100
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT122"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT123"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT123")))) * 20) / ((Double.parseDouble(pros.getValue("slaT122")) - (Double.parseDouble(pros.getValue("slaT123"))))))) * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 60-80
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT123"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT124"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT124")))) * 20) / ((Double.parseDouble(pros.getValue("slaT123")) - (Double.parseDouble(pros.getValue("slaT124"))))))) * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 40-60
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT124"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT125"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT125")))) * 20) / ((Double.parseDouble(pros.getValue("slaT124")) - (Double.parseDouble(pros.getValue("slaT125"))))))) * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 20-40
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT125"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT126"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT126")))) * 20) / ((Double.parseDouble(pros.getValue("slaT125")) - (Double.parseDouble(pros.getValue("slaT126"))))))) * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 0-20
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT126"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaT127"))) <= 0)) {
						score += ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaT127")))) * 20) / ((Double.parseDouble(pros.getValue("slaT126")) - (Double.parseDouble(pros.getValue("slaT127")))))) * (Double.parseDouble(pros.getValue("slaT121")));
					}
					//r_jitter 0
					else {
						score += 0;
					}

					//jitter_std 100
					if ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT132"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 80-100
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT132"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT133"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT133")))) * 20) / ((Double.parseDouble(pros.getValue("slaT132")) - (Double.parseDouble(pros.getValue("slaT133"))))))) * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 60-80
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT133"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT134"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT134")))) * 20) / ((Double.parseDouble(pros.getValue("slaT133")) - (Double.parseDouble(pros.getValue("slaT134"))))))) * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 40-60
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT134"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT135"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT135")))) * 20) / ((Double.parseDouble(pros.getValue("slaT134")) - (Double.parseDouble(pros.getValue("slaT135"))))))) * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 20-40
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT135"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT136"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT136")))) * 20) / ((Double.parseDouble(pros.getValue("slaT135")) - (Double.parseDouble(pros.getValue("slaT136"))))))) * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 0-20
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT136"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT137"))) <= 0)) {
						score += ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT137")))) * 20) / ((Double.parseDouble(pros.getValue("slaT136")) - (Double.parseDouble(pros.getValue("slaT137")))))) * (Double.parseDouble(pros.getValue("slaT131")));
					}
					//jitter_std 0
					else {
						score += 0;
					}

					//g_jitter_std 100
					if ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT142"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 80-100
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT142"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT143"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT143")))) * 20) / ((Double.parseDouble(pros.getValue("slaT142")) - (Double.parseDouble(pros.getValue("slaT143"))))))) * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 60-80
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT143"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT144"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT144")))) * 20) / ((Double.parseDouble(pros.getValue("slaT143")) - (Double.parseDouble(pros.getValue("slaT144"))))))) * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 40-60
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT144"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT145"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT145")))) * 20) / ((Double.parseDouble(pros.getValue("slaT144")) - (Double.parseDouble(pros.getValue("slaT145"))))))) * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 20-40
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT145"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT146"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT146")))) * 20) / ((Double.parseDouble(pros.getValue("slaT145")) - (Double.parseDouble(pros.getValue("slaT146"))))))) * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 0-20
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT146"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT147"))) <= 0)) {
						score += ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT147")))) * 20) / ((Double.parseDouble(pros.getValue("slaT146")) - (Double.parseDouble(pros.getValue("slaT147")))))) * (Double.parseDouble(pros.getValue("slaT141")));
					}
					//g_jitter_std 0
					else {
						score += 0;
					}

					//r_jitter_std 100
					if ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT152"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 80-100
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT152"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT153"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT153")))) * 20) / ((Double.parseDouble(pros.getValue("slaT152")) - (Double.parseDouble(pros.getValue("slaT153"))))))) * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 60-80
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT153"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT154"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT154")))) * 20) / ((Double.parseDouble(pros.getValue("slaT153")) - (Double.parseDouble(pros.getValue("slaT154"))))))) * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 40-60
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT154"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT155"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT155")))) * 20) / ((Double.parseDouble(pros.getValue("slaT154")) - (Double.parseDouble(pros.getValue("slaT155"))))))) * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 20-40
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT155"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT156"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT156")))) * 20) / ((Double.parseDouble(pros.getValue("slaT155")) - (Double.parseDouble(pros.getValue("slaT156"))))))) * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 0-20
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT156"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaT157"))) <= 0)) {
						score += ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaT157")))) * 20) / ((Double.parseDouble(pros.getValue("slaT156")) - (Double.parseDouble(pros.getValue("slaT157")))))) * (Double.parseDouble(pros.getValue("slaT151")));
					}
					//r_jitter_std 0
					else {
						score += 0;
					}

					//jitter_var 100
					if ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT162"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 80-100
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT162"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT163"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT163")))) * 20) / ((Double.parseDouble(pros.getValue("slaT162")) - (Double.parseDouble(pros.getValue("slaT163"))))))) * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 60-80
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT163"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT164"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT164")))) * 20) / ((Double.parseDouble(pros.getValue("slaT163")) - (Double.parseDouble(pros.getValue("slaT164"))))))) * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 40-60
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT164"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT165"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT165")))) * 20) / ((Double.parseDouble(pros.getValue("slaT164")) - (Double.parseDouble(pros.getValue("slaT165"))))))) * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 20-40
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT165"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT166"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT166")))) * 20) / ((Double.parseDouble(pros.getValue("slaT165")) - (Double.parseDouble(pros.getValue("slaT166"))))))) * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 0-20
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT166"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT167"))) <= 0)) {
						score += ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT167")))) * 20) / ((Double.parseDouble(pros.getValue("slaT166")) - (Double.parseDouble(pros.getValue("slaT167")))))) * (Double.parseDouble(pros.getValue("slaT161")));
					}
					//jitter_var 0
					else {
						score += 0;
					}

					//g_jitter_var 100
					if ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT172"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 80-100
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT172"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT173"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT173")))) * 20) / ((Double.parseDouble(pros.getValue("slaT172")) - (Double.parseDouble(pros.getValue("slaT173"))))))) * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 60-80
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT173"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT174"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT174")))) * 20) / ((Double.parseDouble(pros.getValue("slaT173")) - (Double.parseDouble(pros.getValue("slaT174"))))))) * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 40-60
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT174"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT175"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT175")))) * 20) / ((Double.parseDouble(pros.getValue("slaT174")) - (Double.parseDouble(pros.getValue("slaT175"))))))) * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 20-40
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT175"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT176"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT176")))) * 20) / ((Double.parseDouble(pros.getValue("slaT175")) - (Double.parseDouble(pros.getValue("slaT176"))))))) * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 0-20
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT176"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT177"))) <= 0)) {
						score += ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT177")))) * 20) / ((Double.parseDouble(pros.getValue("slaT176")) - (Double.parseDouble(pros.getValue("slaT177")))))) * (Double.parseDouble(pros.getValue("slaT171")));
					}
					//g_jitter_var 0
					else {
						score += 0;
					}

					//r_jitter_var 100
					if ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT182"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 80-100
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT182"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT183"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT183")))) * 20) / ((Double.parseDouble(pros.getValue("slaT182")) - (Double.parseDouble(pros.getValue("slaT183"))))))) * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 60-80
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT183"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT184"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT184")))) * 20) / ((Double.parseDouble(pros.getValue("slaT183")) - (Double.parseDouble(pros.getValue("slaT184"))))))) * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 40-60
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT184"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT185"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT185")))) * 20) / ((Double.parseDouble(pros.getValue("slaT184")) - (Double.parseDouble(pros.getValue("slaT185"))))))) * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 20-40
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT185"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT186"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT186")))) * 20) / ((Double.parseDouble(pros.getValue("slaT185")) - (Double.parseDouble(pros.getValue("slaT186"))))))) * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 0-20
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT186"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaT187"))) <= 0)) {
						score += ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaT187")))) * 20) / ((Double.parseDouble(pros.getValue("slaT186")) - (Double.parseDouble(pros.getValue("slaT187")))))) * (Double.parseDouble(pros.getValue("slaT181")));
					}
					//r_jitter_var 0
					else {
						score += 0;
					}

					//loss 100
					if ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT192"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 80-100
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT192"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT193"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT193")))) * 20) / ((Double.parseDouble(pros.getValue("slaT192")) - (Double.parseDouble(pros.getValue("slaT193"))))))) * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 60-80
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT193"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT194"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT194")))) * 20) / ((Double.parseDouble(pros.getValue("slaT193")) - (Double.parseDouble(pros.getValue("slaT194"))))))) * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 40-60
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT194"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT195"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT195")))) * 20) / ((Double.parseDouble(pros.getValue("slaT194")) - (Double.parseDouble(pros.getValue("slaT195"))))))) * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 20-40
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT195"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT196"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT196")))) * 20) / ((Double.parseDouble(pros.getValue("slaT195")) - (Double.parseDouble(pros.getValue("slaT196"))))))) * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 0-20
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT196"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaT197"))) <= 0)) {
						score += ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaT197")))) * 20) / ((Double.parseDouble(pros.getValue("slaT196")) - (Double.parseDouble(pros.getValue("slaT197")))))) * (Double.parseDouble(pros.getValue("slaT191")));
					}
					//loss 0
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
					tcpSla.setRecordDate(slaList.get(i).getRecordDate());
					tcpSla.setRecordTime(slaList.get(i).getRecordTime());
					tcpSla.setScore(score);
					tcpSla.setBase(Double.parseDouble(pros.getValue("sla_tcp")));

					slaTcp.add(tcpSla);
				}}
		}catch (IOException e) {
		}

		return slaTcp;
	}

	@Override
	public List<ScoreEntity> calculateSlaUdp(List<RecordHourSlaEntity> slaList) {
		List<ScoreEntity> slaUdp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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

					//delay_std 100
					if ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU42"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 80-100
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU42"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU43"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU43")))) * 20) / ((Double.parseDouble(pros.getValue("slaU42")) - (Double.parseDouble(pros.getValue("slaU43"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 60-80
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU43"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU44"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU44")))) * 20) / ((Double.parseDouble(pros.getValue("slaU43")) - (Double.parseDouble(pros.getValue("slaU44"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 40-60
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU44"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU45"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU45")))) * 20) / ((Double.parseDouble(pros.getValue("slaU44")) - (Double.parseDouble(pros.getValue("slaU45"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 20-40
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU45"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU46"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU46")))) * 20) / ((Double.parseDouble(pros.getValue("slaU45")) - (Double.parseDouble(pros.getValue("slaU46"))))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 0-20
					else if (((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU46"))) > 0) && ((slaList.get(i).getDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU47"))) <= 0)) {
						score += ((((slaList.get(i).getDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU47")))) * 20) / ((Double.parseDouble(pros.getValue("slaU46")) - (Double.parseDouble(pros.getValue("slaU47")))))) * (Double.parseDouble(pros.getValue("slaU41")));
					}
					//delay_std 0
					else {
						score += 0;
					}

					//g_delay_std 100
					if ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU52"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 80-100
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU52"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU53"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU53")))) * 20) / ((Double.parseDouble(pros.getValue("slaU52")) - (Double.parseDouble(pros.getValue("slaU53"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 60-80
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU53"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU54"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU54")))) * 20) / ((Double.parseDouble(pros.getValue("slaU53")) - (Double.parseDouble(pros.getValue("slaU54"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 40-60
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU54"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU55"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU55")))) * 20) / ((Double.parseDouble(pros.getValue("slaU54")) - (Double.parseDouble(pros.getValue("slaU55"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 20-40
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU55"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU56"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU56")))) * 20) / ((Double.parseDouble(pros.getValue("slaU55")) - (Double.parseDouble(pros.getValue("slaU56"))))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 0-20
					else if (((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU56"))) > 0) && ((slaList.get(i).getGDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU57"))) <= 0)) {
						score += ((((slaList.get(i).getGDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU57")))) * 20) / ((Double.parseDouble(pros.getValue("slaU56")) - (Double.parseDouble(pros.getValue("slaU57")))))) * (Double.parseDouble(pros.getValue("slaU51")));
					}
					//g_delay_std 0
					else {
						score += 0;
					}

					//r_delay_std 100
					if ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU62"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 80-100
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU62"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU63"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU63")))) * 20) / ((Double.parseDouble(pros.getValue("slaU62")) - (Double.parseDouble(pros.getValue("slaU63"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 60-80
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU63"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU64"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU64")))) * 20) / ((Double.parseDouble(pros.getValue("slaU63")) - (Double.parseDouble(pros.getValue("slaU64"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 40-60
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU64"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU65"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU65")))) * 20) / ((Double.parseDouble(pros.getValue("slaU64")) - (Double.parseDouble(pros.getValue("slaU65"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 20-40
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU65"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU66"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU66")))) * 20) / ((Double.parseDouble(pros.getValue("slaU65")) - (Double.parseDouble(pros.getValue("slaU66"))))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 0-20
					else if (((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU66"))) > 0) && ((slaList.get(i).getRDelayStd()).compareTo(Double.parseDouble(pros.getValue("slaU67"))) <= 0)) {
						score += ((((slaList.get(i).getRDelayStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU67")))) * 20) / ((Double.parseDouble(pros.getValue("slaU66")) - (Double.parseDouble(pros.getValue("slaU67")))))) * (Double.parseDouble(pros.getValue("slaU61")));
					}
					//r_delay_std 0
					else {
						score += 0;
					}

					//delay_var 100
					if ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU72"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 80-100
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU72"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU73"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU73")))) * 20) / ((Double.parseDouble(pros.getValue("slaU72")) - (Double.parseDouble(pros.getValue("slaU73"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 60-80
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU73"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU74"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU74")))) * 20) / ((Double.parseDouble(pros.getValue("slaU73")) - (Double.parseDouble(pros.getValue("slaU74"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 40-60
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU74"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU75"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU75")))) * 20) / ((Double.parseDouble(pros.getValue("slaU74")) - (Double.parseDouble(pros.getValue("slaU75"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 20-40
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU75"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU76"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU76")))) * 20) / ((Double.parseDouble(pros.getValue("slaU75")) - (Double.parseDouble(pros.getValue("slaU76"))))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 0-20
					else if (((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU76"))) > 0) && ((slaList.get(i).getDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU77"))) <= 0)) {
						score += ((((slaList.get(i).getDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU77")))) * 20) / ((Double.parseDouble(pros.getValue("slaU76")) - (Double.parseDouble(pros.getValue("slaU77")))))) * (Double.parseDouble(pros.getValue("slaU71")));
					}
					//delay_var 0
					else {
						score += 0;
					}

					//g_delay_var 100
					if ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU82"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 80-100
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU82"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU83"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU83")))) * 20) / ((Double.parseDouble(pros.getValue("slaU82")) - (Double.parseDouble(pros.getValue("slaU83"))))))) * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 60-80
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU83"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU84"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU84")))) * 20) / ((Double.parseDouble(pros.getValue("slaU83")) - (Double.parseDouble(pros.getValue("slaU84"))))))) * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 40-60
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU84"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU85"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU85")))) * 20) / ((Double.parseDouble(pros.getValue("slaU84")) - (Double.parseDouble(pros.getValue("slaU85"))))))) * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 20-40
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU85"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU86"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU86")))) * 20) / ((Double.parseDouble(pros.getValue("slaU85")) - (Double.parseDouble(pros.getValue("slaU86"))))))) * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 0-20
					else if (((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU86"))) > 0) && ((slaList.get(i).getGDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU87"))) <= 0)) {
						score += ((((slaList.get(i).getGDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU87")))) * 20) / ((Double.parseDouble(pros.getValue("slaU86")) - (Double.parseDouble(pros.getValue("slaU87")))))) * (Double.parseDouble(pros.getValue("slaU81")));
					}
					//g_delay_var 0
					else {
						score += 0;
					}

					//r_delay_var 100
					if ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU92"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 80-100
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU92"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU93"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU93")))) * 20) / ((Double.parseDouble(pros.getValue("slaU92")) - (Double.parseDouble(pros.getValue("slaU93"))))))) * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 60-80
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU93"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU94"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU94")))) * 20) / ((Double.parseDouble(pros.getValue("slaU93")) - (Double.parseDouble(pros.getValue("slaU94"))))))) * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 40-60
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU94"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU95"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU95")))) * 20) / ((Double.parseDouble(pros.getValue("slaU94")) - (Double.parseDouble(pros.getValue("slaU95"))))))) * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 20-40
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU95"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU96"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU96")))) * 20) / ((Double.parseDouble(pros.getValue("slaU95")) - (Double.parseDouble(pros.getValue("slaU96"))))))) * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 0-20
					else if (((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU96"))) > 0) && ((slaList.get(i).getRDelayVar()).compareTo(Double.parseDouble(pros.getValue("slaU97"))) <= 0)) {
						score += ((((slaList.get(i).getRDelayVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU97")))) * 20) / ((Double.parseDouble(pros.getValue("slaU96")) - (Double.parseDouble(pros.getValue("slaU97")))))) * (Double.parseDouble(pros.getValue("slaU91")));
					}
					//r_delay_var 0
					else {
						score += 0;
					}

					//jitter 100
					if ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU102"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 80-100
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU102"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU103"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU103")))) * 20) / ((Double.parseDouble(pros.getValue("slaU102")) - (Double.parseDouble(pros.getValue("slaU103"))))))) * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 60-80
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU103"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU104"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU104")))) * 20) / ((Double.parseDouble(pros.getValue("slaU103")) - (Double.parseDouble(pros.getValue("slaU104"))))))) * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 40-60
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU104"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU105"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU105")))) * 20) / ((Double.parseDouble(pros.getValue("slaU104")) - (Double.parseDouble(pros.getValue("slaU105"))))))) * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 20-40
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU105"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU106"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU106")))) * 20) / ((Double.parseDouble(pros.getValue("slaU105")) - (Double.parseDouble(pros.getValue("slaU106"))))))) * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 0-20
					else if (((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU106"))) > 0) && ((slaList.get(i).getJitter()).compareTo(Double.parseDouble(pros.getValue("slaU107"))) <= 0)) {
						score += ((((slaList.get(i).getJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU107")))) * 20) / ((Double.parseDouble(pros.getValue("slaU106")) - (Double.parseDouble(pros.getValue("slaU107")))))) * (Double.parseDouble(pros.getValue("slaU101")));
					}
					//jitter 0
					else {
						score += 0;
					}

					//g_jitter 100
					if ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU112"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 80-100
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU112"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU113"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU113")))) * 20) / ((Double.parseDouble(pros.getValue("slaU112")) - (Double.parseDouble(pros.getValue("slaU113"))))))) * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 60-80
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU113"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU114"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU114")))) * 20) / ((Double.parseDouble(pros.getValue("slaU113")) - (Double.parseDouble(pros.getValue("slaU114"))))))) * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 40-60
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU114"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU115"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU115")))) * 20) / ((Double.parseDouble(pros.getValue("slaU114")) - (Double.parseDouble(pros.getValue("slaU115"))))))) * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 20-40
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU115"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU116"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU116")))) * 20) / ((Double.parseDouble(pros.getValue("slaU105")) - (Double.parseDouble(pros.getValue("slaU116"))))))) * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 0-20
					else if (((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU116"))) > 0) && ((slaList.get(i).getGJitter()).compareTo(Double.parseDouble(pros.getValue("slaU117"))) <= 0)) {
						score += ((((slaList.get(i).getGJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU117")))) * 20) / ((Double.parseDouble(pros.getValue("slaU116")) - (Double.parseDouble(pros.getValue("slaU117")))))) * (Double.parseDouble(pros.getValue("slaU111")));
					}
					//g_jitter 0
					else {
						score += 0;
					}

					//r_jitter 100
					if ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU122"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 80-100
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU122"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU123"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU123")))) * 20) / ((Double.parseDouble(pros.getValue("slaU122")) - (Double.parseDouble(pros.getValue("slaU123"))))))) * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 60-80
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU123"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU124"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU124")))) * 20) / ((Double.parseDouble(pros.getValue("slaU123")) - (Double.parseDouble(pros.getValue("slaU124"))))))) * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 40-60
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU124"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU125"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU125")))) * 20) / ((Double.parseDouble(pros.getValue("slaU124")) - (Double.parseDouble(pros.getValue("slaU125"))))))) * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 20-40
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU125"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU126"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU126")))) * 20) / ((Double.parseDouble(pros.getValue("slaU125")) - (Double.parseDouble(pros.getValue("slaU126"))))))) * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 0-20
					else if (((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU126"))) > 0) && ((slaList.get(i).getRJitter()).compareTo(Double.parseDouble(pros.getValue("slaU127"))) <= 0)) {
						score += ((((slaList.get(i).getRJitter().doubleValue()) - (Double.parseDouble(pros.getValue("slaU127")))) * 20) / ((Double.parseDouble(pros.getValue("slaU126")) - (Double.parseDouble(pros.getValue("slaU127")))))) * (Double.parseDouble(pros.getValue("slaU121")));
					}
					//r_jitter 0
					else {
						score += 0;
					}

					//jitter_std 100
					if ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU132"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 80-100
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU132"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU133"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU133")))) * 20) / ((Double.parseDouble(pros.getValue("slaU132")) - (Double.parseDouble(pros.getValue("slaU133"))))))) * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 60-80
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU133"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU134"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU134")))) * 20) / ((Double.parseDouble(pros.getValue("slaU133")) - (Double.parseDouble(pros.getValue("slaU134"))))))) * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 40-60
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU134"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU135"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU135")))) * 20) / ((Double.parseDouble(pros.getValue("slaU134")) - (Double.parseDouble(pros.getValue("slaU135"))))))) * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 20-40
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU135"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU136"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU136")))) * 20) / ((Double.parseDouble(pros.getValue("slaU135")) - (Double.parseDouble(pros.getValue("slaU136"))))))) * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 0-20
					else if (((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU136"))) > 0) && ((slaList.get(i).getJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU137"))) <= 0)) {
						score += ((((slaList.get(i).getJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU137")))) * 20) / ((Double.parseDouble(pros.getValue("slaU136")) - (Double.parseDouble(pros.getValue("slaU137")))))) * (Double.parseDouble(pros.getValue("slaU131")));
					}
					//jitter_std 0
					else {
						score += 0;
					}

					//g_jitter_std 100
					if ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU142"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 80-100
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU142"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU143"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU143")))) * 20) / ((Double.parseDouble(pros.getValue("slaU142")) - (Double.parseDouble(pros.getValue("slaU143"))))))) * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 60-80
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU143"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU144"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU144")))) * 20) / ((Double.parseDouble(pros.getValue("slaU143")) - (Double.parseDouble(pros.getValue("slaU144"))))))) * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 40-60
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU144"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU145"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU145")))) * 20) / ((Double.parseDouble(pros.getValue("slaU144")) - (Double.parseDouble(pros.getValue("slaU145"))))))) * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 20-40
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU145"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU146"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU146")))) * 20) / ((Double.parseDouble(pros.getValue("slaU145")) - (Double.parseDouble(pros.getValue("slaU146"))))))) * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 0-20
					else if (((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU146"))) > 0) && ((slaList.get(i).getGJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU147"))) <= 0)) {
						score += ((((slaList.get(i).getGJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU147")))) * 20) / ((Double.parseDouble(pros.getValue("slaU146")) - (Double.parseDouble(pros.getValue("slaU147")))))) * (Double.parseDouble(pros.getValue("slaU141")));
					}
					//g_jitter_std 0
					else {
						score += 0;
					}

					//r_jitter_std 100
					if ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU152"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 80-100
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU152"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU153"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU153")))) * 20) / ((Double.parseDouble(pros.getValue("slaU152")) - (Double.parseDouble(pros.getValue("slaU153"))))))) * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 60-80
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU153"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU154"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU154")))) * 20) / ((Double.parseDouble(pros.getValue("slaU153")) - (Double.parseDouble(pros.getValue("slaU154"))))))) * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 40-60
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU154"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU155"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU155")))) * 20) / ((Double.parseDouble(pros.getValue("slaU154")) - (Double.parseDouble(pros.getValue("slaU155"))))))) * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 20-40
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU155"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU156"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU156")))) * 20) / ((Double.parseDouble(pros.getValue("slaU155")) - (Double.parseDouble(pros.getValue("slaU156"))))))) * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 0-20
					else if (((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU156"))) > 0) && ((slaList.get(i).getRJitterStd()).compareTo(Double.parseDouble(pros.getValue("slaU157"))) <= 0)) {
						score += ((((slaList.get(i).getRJitterStd().doubleValue()) - (Double.parseDouble(pros.getValue("slaU157")))) * 20) / ((Double.parseDouble(pros.getValue("slaU156")) - (Double.parseDouble(pros.getValue("slaU157")))))) * (Double.parseDouble(pros.getValue("slaU151")));
					}
					//r_jitter_std 0
					else {
						score += 0;
					}

					//jitter_var 100
					if ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU162"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 80-100
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU162"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU163"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU163")))) * 20) / ((Double.parseDouble(pros.getValue("slaU162")) - (Double.parseDouble(pros.getValue("slaU163"))))))) * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 60-80
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU163"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU164"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU164")))) * 20) / ((Double.parseDouble(pros.getValue("slaU163")) - (Double.parseDouble(pros.getValue("slaU164"))))))) * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 40-60
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU164"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU165"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU165")))) * 20) / ((Double.parseDouble(pros.getValue("slaU164")) - (Double.parseDouble(pros.getValue("slaU165"))))))) * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 20-40
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU165"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU166"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU166")))) * 20) / ((Double.parseDouble(pros.getValue("slaU165")) - (Double.parseDouble(pros.getValue("slaU166"))))))) * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 0-20
					else if (((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU166"))) > 0) && ((slaList.get(i).getJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU167"))) <= 0)) {
						score += ((((slaList.get(i).getJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU167")))) * 20) / ((Double.parseDouble(pros.getValue("slaU166")) - (Double.parseDouble(pros.getValue("slaU167")))))) * (Double.parseDouble(pros.getValue("slaU161")));
					}
					//jitter_var 0
					else {
						score += 0;
					}

					//g_jitter_var 100
					if ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU172"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 80-100
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU172"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU173"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU173")))) * 20) / ((Double.parseDouble(pros.getValue("slaU172")) - (Double.parseDouble(pros.getValue("slaU173"))))))) * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 60-80
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU173"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU174"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU174")))) * 20) / ((Double.parseDouble(pros.getValue("slaU173")) - (Double.parseDouble(pros.getValue("slaU174"))))))) * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 40-60
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU174"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU175"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU175")))) * 20) / ((Double.parseDouble(pros.getValue("slaU174")) - (Double.parseDouble(pros.getValue("slaU175"))))))) * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 20-40
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU175"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU176"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU176")))) * 20) / ((Double.parseDouble(pros.getValue("slaU175")) - (Double.parseDouble(pros.getValue("slaU176"))))))) * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 0-20
					else if (((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU176"))) > 0) && ((slaList.get(i).getGJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU177"))) <= 0)) {
						score += ((((slaList.get(i).getGJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU177")))) * 20) / ((Double.parseDouble(pros.getValue("slaU176")) - (Double.parseDouble(pros.getValue("slaU177")))))) * (Double.parseDouble(pros.getValue("slaU171")));
					}
					//g_jitter_var 0
					else {
						score += 0;
					}

					//r_jitter_var 100
					if ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU182"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 80-100
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU182"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU183"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU183")))) * 20) / ((Double.parseDouble(pros.getValue("slaU182")) - (Double.parseDouble(pros.getValue("slaU183"))))))) * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 60-80
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU183"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU184"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU184")))) * 20) / ((Double.parseDouble(pros.getValue("slaU183")) - (Double.parseDouble(pros.getValue("slaU184"))))))) * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 40-60
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU184"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU185"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU185")))) * 20) / ((Double.parseDouble(pros.getValue("slaU184")) - (Double.parseDouble(pros.getValue("slaU185"))))))) * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 20-40
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU185"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU186"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU186")))) * 20) / ((Double.parseDouble(pros.getValue("slaU185")) - (Double.parseDouble(pros.getValue("slaU186"))))))) * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 0-20
					else if (((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU186"))) > 0) && ((slaList.get(i).getRJitterVar()).compareTo(Double.parseDouble(pros.getValue("slaU187"))) <= 0)) {
						score += ((((slaList.get(i).getRJitterVar().doubleValue()) - (Double.parseDouble(pros.getValue("slaU187")))) * 20) / ((Double.parseDouble(pros.getValue("slaU186")) - (Double.parseDouble(pros.getValue("slaU187")))))) * (Double.parseDouble(pros.getValue("slaU181")));
					}
					//r_jitter_var 0
					else {
						score += 0;
					}

					//loss 100
					if ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU192"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 80-100
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU192"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU193"))) <= 0)) {
						score += (80 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU193")))) * 20) / ((Double.parseDouble(pros.getValue("slaU192")) - (Double.parseDouble(pros.getValue("slaU193"))))))) * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 60-80
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU193"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU194"))) <= 0)) {
						score += (60 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU194")))) * 20) / ((Double.parseDouble(pros.getValue("slaU193")) - (Double.parseDouble(pros.getValue("slaU194"))))))) * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 40-60
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU194"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU195"))) <= 0)) {
						score += (40 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU195")))) * 20) / ((Double.parseDouble(pros.getValue("slaU194")) - (Double.parseDouble(pros.getValue("slaU195"))))))) * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 20-40
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU195"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU196"))) <= 0)) {
						score += (20 + ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU196")))) * 20) / ((Double.parseDouble(pros.getValue("slaU195")) - (Double.parseDouble(pros.getValue("slaU196"))))))) * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 0-20
					else if (((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU196"))) > 0) && ((slaList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("slaU197"))) <= 0)) {
						score += ((((slaList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("slaU197")))) * 20) / ((Double.parseDouble(pros.getValue("slaU196")) - (Double.parseDouble(pros.getValue("slaU197")))))) * (Double.parseDouble(pros.getValue("slaU191")));
					}
					//loss 0
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
					udpSla.setRecordDate(slaList.get(i).getRecordDate());
					udpSla.setRecordTime(slaList.get(i).getRecordTime());
					udpSla.setScore(score);
					udpSla.setBase(Double.parseDouble(pros.getValue("sla_udp")));

					slaUdp.add(udpSla);
				}
			}
		} catch (IOException e) {
		}
		return slaUdp;
	}

	@Override
	public List<ScoreEntity> calculateDns(List<RecordHourDnsEntity> dnsList) {
		List<ScoreEntity> dns = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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
				if ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 80-100
				else if (((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns22"))) > 0) && ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns23"))) <= 0)) {
					score += (80 + ((((dnsList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dns23")))) * 20) / ((Double.parseDouble(pros.getValue("dns22")) - (Double.parseDouble(pros.getValue("dns23"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 60-80
				else if (((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns23"))) > 0) && ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns24"))) <= 0)) {
					score += (60 + ((((dnsList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dns24")))) * 20) / ((Double.parseDouble(pros.getValue("dns23")) - (Double.parseDouble(pros.getValue("dns24"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 40-60
				else if (((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns24"))) > 0) && ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns25"))) <= 0)) {
					score += (40 + ((((dnsList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dns25")))) * 20) / ((Double.parseDouble(pros.getValue("dns24")) - (Double.parseDouble(pros.getValue("dns25"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 20-40
				else if (((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns25"))) > 0) && ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns26"))) <= 0)) {
					score += (20 + ((((dnsList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dns26")))) * 20) / ((Double.parseDouble(pros.getValue("dns25")) - (Double.parseDouble(pros.getValue("dns26"))))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 0-20
				else if (((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns26"))) > 0) && ((dnsList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dns27"))) <= 0)) {
					score += ((((dnsList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dns27")))) * 20) / ((Double.parseDouble(pros.getValue("dns26")) - (Double.parseDouble(pros.getValue("dns27")))))) * (Double.parseDouble(pros.getValue("dns21")));
				}
				//delay 0
				else {
					score += 0;
				}
				System.out.println("Dns:"+score);


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
				DNS.setRecordDate(dnsList.get(i).getRecordDate());
				DNS.setRecordTime(dnsList.get(i).getRecordTime());
				DNS.setScore(score);
				DNS.setBase(Double.parseDouble(pros.getValue("dns")));

				dns.add(DNS);

			}
		} catch (IOException e) {
		}
		return dns;
	}

	@Override
	public List<ScoreEntity> calculateDhcp(List<RecordHourDhcpEntity> dhcpList) {
		List<ScoreEntity> dhcp = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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

				//success rate 100
				if ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 80-100
				else if (((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp22"))) > 0) && ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp23"))) <= 0)) {
					score += (80 + ((((dhcpList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp23")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp22")) - (Double.parseDouble(pros.getValue("dhcp23"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 60-80
				else if (((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp23"))) > 0) && ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp24"))) <= 0)) {
					score += (60 + ((((dhcpList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp24")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp23")) - (Double.parseDouble(pros.getValue("dhcp24"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 40-60
				else if (((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp24"))) > 0) && ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp25"))) <= 0)) {
					score += (40 + ((((dhcpList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp25")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp24")) - (Double.parseDouble(pros.getValue("dhcp25"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 20-40
				else if (((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp25"))) > 0) && ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp26"))) <= 0)) {
					score += (20 + ((((dhcpList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp26")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp25")) - (Double.parseDouble(pros.getValue("dhcp26"))))))) * (Double.parseDouble(pros.getValue("dhcp21")));
				}
				//delay 0-20
				else if (((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp26"))) > 0) && ((dhcpList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("dhcp27"))) <= 0)) {
					score += ((((dhcpList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("dhcp27")))) * 20) / ((Double.parseDouble(pros.getValue("dhcp26")) - (Double.parseDouble(pros.getValue("dhcp27")))))) * (Double.parseDouble(pros.getValue("dhcp21")));
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
				DHCP.setRecordDate(dhcpList.get(i).getRecordDate());
				DHCP.setRecordTime(dhcpList.get(i).getRecordTime());
				DHCP.setScore(score);
				DHCP.setBase(Double.parseDouble(pros.getValue("dhcp")));

				dhcp.add(DHCP);
			}
		} catch (IOException e) {
		}
		return dhcp;
	}

	@Override
	public List<ScoreEntity> calculatePppoe(List<RecordHourPppoeEntity> pppoeList) {
		List<ScoreEntity> pppoe = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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

				//loss rate 100
				if ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 80-100
				else if (((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl22"))) > 0) && ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl23"))) <= 0)) {
					score += (80 + ((((pppoeList.get(i).getDropRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl23")))) * 20) / ((Double.parseDouble(pros.getValue("adsl22")) - (Double.parseDouble(pros.getValue("adsl23"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 60-80
				else if (((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl23"))) > 0) && ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl24"))) <= 0)) {
					score += (60 + ((((pppoeList.get(i).getDropRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl24")))) * 20) / ((Double.parseDouble(pros.getValue("adsl23")) - (Double.parseDouble(pros.getValue("adsl24"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 40-60
				else if (((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl24"))) > 0) && ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl25"))) <= 0)) {
					score += (40 + ((((pppoeList.get(i).getDropRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl25")))) * 20) / ((Double.parseDouble(pros.getValue("adsl24")) - (Double.parseDouble(pros.getValue("adsl25"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 20-40
				else if (((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl25"))) > 0) && ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl26"))) <= 0)) {
					score += (20 + ((((pppoeList.get(i).getDropRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl26")))) * 20) / ((Double.parseDouble(pros.getValue("adsl25")) - (Double.parseDouble(pros.getValue("adsl26"))))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 0-20
				else if (((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl26"))) > 0) && ((pppoeList.get(i).getDropRate()).compareTo(Double.parseDouble(pros.getValue("adsl27"))) <= 0)) {
					score += ((((pppoeList.get(i).getDropRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl27")))) * 20) / ((Double.parseDouble(pros.getValue("adsl26")) - (Double.parseDouble(pros.getValue("adsl27")))))) * (Double.parseDouble(pros.getValue("adsl21")));
				}
				//delay 0
				else {
					score += 0;
				}

				//success rate 100
				if ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl32"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 80-100
				else if (((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl32"))) > 0) && ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl33"))) <= 0)) {
					score += (80 + ((((pppoeList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl33")))) * 20) / ((Double.parseDouble(pros.getValue("adsl32")) - (Double.parseDouble(pros.getValue("adsl33"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 60-80
				else if (((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl33"))) > 0) && ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl34"))) <= 0)) {
					score += (60 + ((((pppoeList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl34")))) * 20) / ((Double.parseDouble(pros.getValue("adsl33")) - (Double.parseDouble(pros.getValue("adsl34"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 40-60
				else if (((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl34"))) > 0) && ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl35"))) <= 0)) {
					score += (40 + ((((pppoeList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl35")))) * 20) / ((Double.parseDouble(pros.getValue("adsl34")) - (Double.parseDouble(pros.getValue("adsl35"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 20-40
				else if (((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl35"))) > 0) && ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl36"))) <= 0)) {
					score += (20 + ((((pppoeList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl36")))) * 20) / ((Double.parseDouble(pros.getValue("adsl35")) - (Double.parseDouble(pros.getValue("adsl36"))))))) * (Double.parseDouble(pros.getValue("adsl31")));
				}
				//delay 0-20
				else if (((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl36"))) > 0) && ((pppoeList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("adsl37"))) <= 0)) {
					score += ((((pppoeList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("adsl37")))) * 20) / ((Double.parseDouble(pros.getValue("adsl36")) - (Double.parseDouble(pros.getValue("adsl37")))))) * (Double.parseDouble(pros.getValue("adsl31")));
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
				PPPOE.setRecordDate(pppoeList.get(i).getRecordDate());
				PPPOE.setRecordTime(pppoeList.get(i).getRecordTime());
				PPPOE.setScore(score);
				PPPOE.setBase(Double.parseDouble(pros.getValue("adsl")));

				pppoe.add(PPPOE);
			}
		} catch (IOException e) {
		}
		return pppoe;
	}

	@Override
	public List<ScoreEntity> calculateRadius(List<RecordHourRadiusEntity> radiusList) {
		List<ScoreEntity> radius = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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

				//success rate 100
				if ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 80-100
				else if (((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius22"))) > 0) && ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius23"))) <= 0)) {
					score += (80 + ((((radiusList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("radius23")))) * 20) / ((Double.parseDouble(pros.getValue("radius22")) - (Double.parseDouble(pros.getValue("radius23"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 60-80
				else if (((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius23"))) > 0) && ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius24"))) <= 0)) {
					score += (60 + ((((radiusList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("radius24")))) * 20) / ((Double.parseDouble(pros.getValue("radius23")) - (Double.parseDouble(pros.getValue("radius24"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 40-60
				else if (((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius24"))) > 0) && ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius25"))) <= 0)) {
					score += (40 + ((((radiusList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("radius25")))) * 20) / ((Double.parseDouble(pros.getValue("radius24")) - (Double.parseDouble(pros.getValue("radius25"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 20-40
				else if (((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius25"))) > 0) && ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius26"))) <= 0)) {
					score += (20 + ((((radiusList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("radius26")))) * 20) / ((Double.parseDouble(pros.getValue("radius25")) - (Double.parseDouble(pros.getValue("radius26"))))))) * (Double.parseDouble(pros.getValue("radius21")));
				}
				//delay 0-20
				else if (((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius26"))) > 0) && ((radiusList.get(i).getSuccessRate()).compareTo(Double.parseDouble(pros.getValue("radius27"))) <= 0)) {
					score += ((((radiusList.get(i).getSuccessRate().doubleValue()) - (Double.parseDouble(pros.getValue("radius27")))) * 20) / ((Double.parseDouble(pros.getValue("radius26")) - (Double.parseDouble(pros.getValue("radius27")))))) * (Double.parseDouble(pros.getValue("radius21")));
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
				RADIUS.setRecordDate(radiusList.get(i).getRecordDate());
				RADIUS.setRecordTime(radiusList.get(i).getRecordTime());
				RADIUS.setScore(score);
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
			Map<ScoreTargetEntity, ScoreBaseEntity> connection = new HashMap<>();
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
				scoreTarget.setAccessLayer(slaTcp.get(i).getAccessLayer());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((slaTcp.get(i).getScore()) * (slaTcp.get(i).getBase()));
				scoreBase.setBase(slaTcp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMap(slaUdp,connection);
			connection=pingService.putMap(dns,connection);
			connection=pingService.putMap(dhcp,connection);
			connection=pingService.putMap(pppoe,connection);
			connection=pingService.putMap(radius,connection);

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
					finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
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
			Map<ScoreAreaEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreAreaEntity scoreTarget = new ScoreAreaEntity();
				scoreTarget.setCityId(slaTcp.get(i).getCityId());
				scoreTarget.setCountyId(slaTcp.get(i).getCountyId());
				scoreTarget.setProbeId(slaTcp.get(i).getProbeId());
				scoreTarget.setTargetId(slaTcp.get(i).getTargetId());
				scoreTarget.setCityName(slaTcp.get(i).getCityName());
				scoreTarget.setCountyName(slaTcp.get(i).getCountyName());
				scoreTarget.setProbeName(slaTcp.get(i).getProbeName());
				scoreTarget.setTargetName(slaTcp.get(i).getTargetName());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((slaTcp.get(i).getScore()) * (slaTcp.get(i).getBase()));
				scoreBase.setBase(slaTcp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMapArea(slaUdp,connection);
			connection=pingService.putMapArea(dns,connection);
			connection=pingService.putMapArea(dhcp,connection);
			connection=pingService.putMapArea(pppoe,connection);
			connection=pingService.putMapArea(radius,connection);

			System.out.println("MAP:"+connection);

			Set<ScoreAreaEntity> key = connection.keySet();
			Iterator<ScoreAreaEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreAreaEntity ite = iterator.next();
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
				finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
				finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
				connectionScore.add(finalScore);
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
			Map<ScoreDateEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < slaTcp.size(); i++) {
				ScoreDateEntity scoreTarget = new ScoreDateEntity();
				scoreTarget.setCityId(slaTcp.get(i).getCityId());
				scoreTarget.setCountyId(slaTcp.get(i).getCountyId());
				scoreTarget.setProbeId(slaTcp.get(i).getProbeId());
				scoreTarget.setTargetId(slaTcp.get(i).getTargetId());
				scoreTarget.setCityName(slaTcp.get(i).getCityName());
				scoreTarget.setCountyName(slaTcp.get(i).getCountyName());
				scoreTarget.setProbeName(slaTcp.get(i).getProbeName());
				scoreTarget.setTargetName(slaTcp.get(i).getTargetName());
				scoreTarget.setRecordDate(slaTcp.get(i).getRecordDate());
				scoreTarget.setRecordTime(slaTcp.get(i).getRecordTime());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((slaTcp.get(i).getScore()) * (slaTcp.get(i).getBase()));
				scoreBase.setBase(slaTcp.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMapDate(slaUdp,connection);
			connection=pingService.putMapDate(dns,connection);
			connection=pingService.putMapDate(dhcp,connection);
			connection=pingService.putMapDate(pppoe,connection);
			connection=pingService.putMapDate(radius,connection);

			System.out.println("MAP:"+connection);

			Set<ScoreDateEntity> key = connection.keySet();
			Iterator<ScoreDateEntity> iterator = key.iterator();
			int id = 1;
			while (iterator.hasNext()) {
				ScoreDateEntity ite = iterator.next();
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
				finalScore.setScore((connection.get(ite).getScore()) / (connection.get(ite).getBase()));
				finalScore.setBase(Double.parseDouble(pros.getValue("qualityweight")));
				connectionScore.add(finalScore);
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
