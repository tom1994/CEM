package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordHourWebPageDao;
import io.cem.modules.cem.dao.RecordWebPageDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordFailService;
import io.cem.modules.cem.service.RecordHourWebPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;



@Service("recordHourWebPageService")
public class RecordHourWebPageServiceImpl implements RecordHourWebPageService {
	@Autowired
	private RecordHourWebPageDao recordHourWebPageDao;
	@Autowired
	private RecordWebPageDao recordWebPageDao;
	
	@Override
	public RecordHourWebPageEntity queryObject(Integer id){
		return recordHourWebPageDao.queryObject(id);
	}

	@Override
	public List<RecordHourWebPageEntity> queryWebPage(Map<String, Object> map){
		return recordWebPageDao.queryWebPage(map);
	}

	@Override
	public List<RecordHourWebPageEntity> queryList(Map<String, Object> map){
		return recordHourWebPageDao.queryList(map);
	}


	@Override
	@Async
	public Future< List<RecordHourWebPageEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryExitList(map));
	}

	@Override
	@Async
	public Future< List<RecordHourWebPageEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryWebList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryWebList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryWebAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryWebAreaList(map));
	}


	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryWebTargetList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryWebTargetList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryWebRankList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourWebPageDao.queryWebRankList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryDayAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryDayAreaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryDayRankList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourWebPageDao.queryDayRankList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryDayTargetList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourWebPageDao.queryDayTargetList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebPageEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebPageDao.queryDayList(map));
	}
	
	@Override
	public List<ScoreEntity> calculateService3(List<RecordHourWebPageEntity> webPageList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");

		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",20);
			//map.put("type",1);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			if(Integer.parseInt(map.get("type").toString())==1){
				List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
				for(int i=0;i<recordFail.size();i++){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(recordFail.get(i).getCityId());
					failEntity.setCountyId(recordFail.get(i).getCountyId());
					failEntity.setProbeId(recordFail.get(i).getProbeId());
					failEntity.setPort(recordFail.get(i).getPort());
					failEntity.setRecordDate(recordFail.get(i).getRecordDate());
					failEntity.setRecordTime(recordFail.get(i).getRecordTime());
					failMap.put(failEntity,recordFail.get(i));
				}
			}else if(Integer.parseInt(map.get("type").toString())==2){
				List<RecordFailEntity> recordFail = recordFailService.queryFail2(map);
				for(int i=0;i<recordFail.size();i++){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(recordFail.get(i).getCityId());
					failEntity.setCountyId(recordFail.get(i).getCountyId());
					failEntity.setProbeId(recordFail.get(i).getProbeId());
					failMap.put(failEntity,recordFail.get(i));
				}
			}else if(Integer.parseInt(map.get("type").toString())==3){
				List<RecordFailEntity> recordFail = recordFailService.queryFail3(map);
				for(int i=0;i<recordFail.size();i++){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(recordFail.get(i).getCityId());
					failEntity.setCountyId(recordFail.get(i).getCountyId());
					failMap.put(failEntity,recordFail.get(i));
				}
			} else if(Integer.parseInt(map.get("type").toString())==4){
				List<RecordFailEntity> recordFail = recordFailService.queryFail4(map);
				for(int i=0;i<recordFail.size();i++){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setTargetId(recordFail.get(i).getTargetId());
					failMap.put(failEntity,recordFail.get(i));
				}
			}
			for (int i=0;i<webPageList.size();i++) {
				double score = 0;
				//dns_delay 100
				if ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 80-100
				else if (((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP12"))) > 0) && ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP13"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP13")))) * 20) / ((Double.parseDouble(pros.getValue("webP12")) - (Double.parseDouble(pros.getValue("webP13"))))))) * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 60-80
				else if (((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP13"))) > 0) && ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP14"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP14")))) * 20) / ((Double.parseDouble(pros.getValue("webP13")) - (Double.parseDouble(pros.getValue("webP14"))))))) * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 40-60
				else if (((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP14"))) > 0) && ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP15"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP15")))) * 20) / ((Double.parseDouble(pros.getValue("webP14")) - (Double.parseDouble(pros.getValue("webP15"))))))) * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 20-40
				else if (((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP15"))) > 0) && ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP16"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP16")))) * 20) / ((Double.parseDouble(pros.getValue("webP15")) - (Double.parseDouble(pros.getValue("webP16"))))))) * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 0-20
				else if (((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP16"))) > 0) && ((webPageList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webP17"))) <= 0)) {
					score += ((((webPageList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP17")))) * 20) / ((Double.parseDouble(pros.getValue("webP16")) - (Double.parseDouble(pros.getValue("webP17")))))) * (Double.parseDouble(pros.getValue("webP11")));
				}
				//dns_delay 0
				else {
					score += 0;
				}

				//conn_delay 100
				if ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 80-100
				else if (((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP22"))) > 0) && ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP23"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP23")))) * 20) / ((Double.parseDouble(pros.getValue("webP22")) - (Double.parseDouble(pros.getValue("webP23"))))))) * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 60-80
				else if (((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP23"))) > 0) && ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP24"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP24")))) * 20) / ((Double.parseDouble(pros.getValue("webP23")) - (Double.parseDouble(pros.getValue("webP24"))))))) * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 40-60
				else if (((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP24"))) > 0) && ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP25"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP25")))) * 20) / ((Double.parseDouble(pros.getValue("webP24")) - (Double.parseDouble(pros.getValue("webP25"))))))) * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 20-40
				else if (((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP25"))) > 0) && ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP26"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP26")))) * 20) / ((Double.parseDouble(pros.getValue("webP25")) - (Double.parseDouble(pros.getValue("webP26"))))))) * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 0-20
				else if (((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP26"))) > 0) && ((webPageList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webP27"))) <= 0)) {
					score += ((((webPageList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP27")))) * 20) / ((Double.parseDouble(pros.getValue("webP26")) - (Double.parseDouble(pros.getValue("webP27")))))) * (Double.parseDouble(pros.getValue("webP21")));
				}
				//conn_delay 0
				else {
					score += 0;
				}

				//head_byty_delay 100
				if ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 80-100
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP42"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP43"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP43")))) * 20) / ((Double.parseDouble(pros.getValue("webP42")) - (Double.parseDouble(pros.getValue("webP43"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 60-80
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP43"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP44"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP44")))) * 20) / ((Double.parseDouble(pros.getValue("webP43")) - (Double.parseDouble(pros.getValue("webP44"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 40-60
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP44"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP45"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP45")))) * 20) / ((Double.parseDouble(pros.getValue("webP44")) - (Double.parseDouble(pros.getValue("webP45"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 20-40
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP45"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP46"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP46")))) * 20) / ((Double.parseDouble(pros.getValue("webP45")) - (Double.parseDouble(pros.getValue("webP46"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 0-20
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP46"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP47"))) <= 0)) {
					score += ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP47")))) * 20) / ((Double.parseDouble(pros.getValue("webP46")) - (Double.parseDouble(pros.getValue("webP47")))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//head_byty_delay 0
				else {
					score += 0;
				}

				//page_file_delay 100
				if ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP52"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 80-100
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP52"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP53"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP53")))) * 20) / ((Double.parseDouble(pros.getValue("webP52")) - (Double.parseDouble(pros.getValue("webP53"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 60-80
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP53"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP54"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP54")))) * 20) / ((Double.parseDouble(pros.getValue("webP53")) - (Double.parseDouble(pros.getValue("webP54"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 40-60
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP54"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP55"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP55")))) * 20) / ((Double.parseDouble(pros.getValue("webP54")) - (Double.parseDouble(pros.getValue("webP55"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 20-40
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP55"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP56"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP56")))) * 20) / ((Double.parseDouble(pros.getValue("webP55")) - (Double.parseDouble(pros.getValue("webP56"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 0-20
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP56"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP57"))) <= 0)) {
					score += ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP57")))) * 20) / ((Double.parseDouble(pros.getValue("webP56")) - (Double.parseDouble(pros.getValue("webP57")))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//page_file_delay 0
				else {
					score += 0;
				}

				//redirect_delay 100
				if ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP62"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 80-100
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP62"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP63"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP63")))) * 20) / ((Double.parseDouble(pros.getValue("webP62")) - (Double.parseDouble(pros.getValue("webP63"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 60-80
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP63"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP64"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP64")))) * 20) / ((Double.parseDouble(pros.getValue("webP63")) - (Double.parseDouble(pros.getValue("webP64"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 40-60
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP64"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP65"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP65")))) * 20) / ((Double.parseDouble(pros.getValue("webP64")) - (Double.parseDouble(pros.getValue("webP65"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 20-40
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP65"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP66"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP66")))) * 20) / ((Double.parseDouble(pros.getValue("webP65")) - (Double.parseDouble(pros.getValue("webP66"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 0-20
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP66"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP67"))) <= 0)) {
					score += ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP67")))) * 20) / ((Double.parseDouble(pros.getValue("webP66")) - (Double.parseDouble(pros.getValue("webP67")))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//redirect_delay 0
				else {
					score += 0;
				}

				//above_fold_delay 100
				if ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP72"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP71")));
				}
				//above_fold_delay 80-100
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP72"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP73"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP73")))) * 20) / ((Double.parseDouble(pros.getValue("webP72")) - (Double.parseDouble(pros.getValue("webP73"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//above_fold_delay 60-80
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP73"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP74"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP74")))) * 20) / ((Double.parseDouble(pros.getValue("webP73")) - (Double.parseDouble(pros.getValue("webP74"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//above_fold_delay 40-60
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP74"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP75"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP75")))) * 20) / ((Double.parseDouble(pros.getValue("webP74")) - (Double.parseDouble(pros.getValue("webP75"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//above_fold_delay 20-40
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP75"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP76"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP76")))) * 20) / ((Double.parseDouble(pros.getValue("webP75")) - (Double.parseDouble(pros.getValue("webP76"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//above_fold_delay 0-20
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP76"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP77"))) <= 0)) {
					score += ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP77")))) * 20) / ((Double.parseDouble(pros.getValue("webP76")) - (Double.parseDouble(pros.getValue("webP77")))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//above_fold_delay 0
				else {
					score += 0;
				}

				//page_element_delay 100
				if ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP82"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 80-100
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP82"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP83"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP83")))) * 20) / ((Double.parseDouble(pros.getValue("webP82")) - (Double.parseDouble(pros.getValue("webP83"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 60-80
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP83"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP84"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP84")))) * 20) / ((Double.parseDouble(pros.getValue("webP83")) - (Double.parseDouble(pros.getValue("webP84"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 40-60
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP84"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP85"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP85")))) * 20) / ((Double.parseDouble(pros.getValue("webP84")) - (Double.parseDouble(pros.getValue("webP85"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 20-40
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP85"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP86"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP86")))) * 20) / ((Double.parseDouble(pros.getValue("webP85")) - (Double.parseDouble(pros.getValue("webP86"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 0-20
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP86"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP87"))) <= 0)) {
					score += ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP87")))) * 20) / ((Double.parseDouble(pros.getValue("webP86")) - (Double.parseDouble(pros.getValue("webP87")))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 0
				else {
					score += 0;
				}

				//download_rate 100
				if ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP32"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 80-100
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP32"))) < 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP33"))) >= 0)) {
					score += (80 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP33")))) * 20) / ((Double.parseDouble(pros.getValue("webP32")) - (Double.parseDouble(pros.getValue("webP33"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 60-80
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP33"))) < 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP34"))) >= 0)) {
					score += (60 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP34")))) * 20) / ((Double.parseDouble(pros.getValue("webP33")) - (Double.parseDouble(pros.getValue("webP34"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 40-60
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP34"))) < 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP35"))) >= 0)) {
					score += (40 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP35")))) * 20) / ((Double.parseDouble(pros.getValue("webP34")) - (Double.parseDouble(pros.getValue("webP35"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 20-40
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP35"))) < 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP36"))) >= 0)) {
					score += (20 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP36")))) * 20) / ((Double.parseDouble(pros.getValue("webP35")) - (Double.parseDouble(pros.getValue("webP36"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 0-20
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP36"))) < 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP37"))) >= 0)) {
					score += ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP37")))) * 20) / ((Double.parseDouble(pros.getValue("webP36")) - (Double.parseDouble(pros.getValue("webP37")))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//page_element_delay 0
				else {
					score += 0;
				}

				ScoreEntity finalScore = new ScoreEntity();
				finalScore.setId(i+1);
				finalScore.setCityId(webPageList.get(i).getCityId());
				finalScore.setCityName(webPageList.get(i).getCityName());
				finalScore.setCountyId(webPageList.get(i).getCountyId());
				finalScore.setCountyName(webPageList.get(i).getAreaName());
				finalScore.setProbeId(webPageList.get(i).getProbeId());
				finalScore.setProbeName(webPageList.get(i).getProbeName());
				finalScore.setServiceType(3);
				finalScore.setTargetId(webPageList.get(i).getTargetId());
				finalScore.setTargetName(webPageList.get(i).getTargetName());
				finalScore.setRecordTime(webPageList.get(i).getRecordTime());
				finalScore.setExit(webPageList.get(i).getExit());
				finalScore.setAccessLayer(webPageList.get(i).getAccessLayer());
				finalScore.setPort(webPageList.get(i).getPort());
				finalScore.setRecordDate(webPageList.get(i).getRecordDate());
				finalScore.setWebpageDnsDelay(webPageList.get(i).getDnsDelay());
				finalScore.setWebpageConnDelay(webPageList.get(i).getConnDelay());
				finalScore.setWebpageHeadbyteDelay(webPageList.get(i).getHeadbyteDelay());
				finalScore.setWebpagePageFileDelay(webPageList.get(i).getPageFileDelay());
				finalScore.setWebpageRedirectDelay(webPageList.get(i).getRedirectDelay());
				finalScore.setWebpageAboveFoldDelay(webPageList.get(i).getAboveFoldDelay());
				finalScore.setWebpagePageElementDelay(webPageList.get(i).getPageElementDelay());
				finalScore.setWebpageLoadDelay(webPageList.get(i).getLoadDelay());
				finalScore.setWebpageDownloadRate(webPageList.get(i).getDownloadRate());
				if(Integer.parseInt(map.get("type").toString())==1){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(webPageList.get(i).getCityId());
					failEntity.setCountyId(webPageList.get(i).getCountyId());
					failEntity.setProbeId(webPageList.get(i).getProbeId());
					failEntity.setPort(webPageList.get(i).getPort());
					failEntity.setRecordDate(webPageList.get(i).getRecordDate());
					failEntity.setRecordTime(webPageList.get(i).getRecordTime());
					if(failMap.containsKey(failEntity)){
						finalScore.setFail(failMap.get(failEntity).getFail());
						finalScore.setTotal(failMap.get(failEntity).getTotal());
					}else{
						finalScore.setFail(webPageList.get(i).getFail());
						finalScore.setTotal(webPageList.get(i).getTotal());
					}
				}else if(Integer.parseInt(map.get("type").toString())==2){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(webPageList.get(i).getCityId());
					failEntity.setCountyId(webPageList.get(i).getCountyId());
					failEntity.setProbeId(webPageList.get(i).getProbeId());
					if(failMap.containsKey(failEntity)){
						finalScore.setFail(failMap.get(failEntity).getFail());
						finalScore.setTotal(failMap.get(failEntity).getTotal());
					}else{
						finalScore.setFail(webPageList.get(i).getFail());
						finalScore.setTotal(webPageList.get(i).getTotal());
					}
				}else if(Integer.parseInt(map.get("type").toString())==3){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(webPageList.get(i).getCityId());
					failEntity.setCountyId(webPageList.get(i).getCountyId());
					if(failMap.containsKey(failEntity)){
						finalScore.setFail(failMap.get(failEntity).getFail());
						finalScore.setTotal(failMap.get(failEntity).getTotal());
					}else{
						finalScore.setFail(webPageList.get(i).getFail());
						finalScore.setTotal(webPageList.get(i).getTotal());
					}
				} else if(Integer.parseInt(map.get("type").toString())==4){
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setTargetId(webPageList.get(i).getTargetId());
					if(failMap.containsKey(failEntity)){
						finalScore.setFail(failMap.get(failEntity).getFail());
						finalScore.setTotal(failMap.get(failEntity).getTotal());
					}else{
						finalScore.setFail(webPageList.get(i).getFail());
						finalScore.setTotal(webPageList.get(i).getTotal());
					}
				}else{
					finalScore.setFail(webPageList.get(i).getFail());
					finalScore.setTotal(webPageList.get(i).getTotal());
				}
				double fail = (double) finalScore.getFail()/finalScore.getTotal();
				finalScore.setScore(score*(1-fail));
				finalScore.setBase(Double.parseDouble(pros.getValue("browseweight")));
				connectionScore.add(finalScore);

				
				
			}
		
		}catch (IOException e){}
		
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateDate3(List<ScoreEntity> webPageList){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreDateEntity,ScoreBaseEntity> connection= new HashMap<>();
			for (int i = 0; i < webPageList.size(); i++) {
				ScoreDateEntity scoreDate = new ScoreDateEntity();
				scoreDate.setCityId(webPageList.get(i).getCityId());
				scoreDate.setCountyId(webPageList.get(i).getCountyId());
				scoreDate.setProbeId(webPageList.get(i).getProbeId());
				scoreDate.setTargetId(webPageList.get(i).getTargetId());
				scoreDate.setCityName(webPageList.get(i).getCityName());
				scoreDate.setCountyName(webPageList.get(i).getCountyName());
				scoreDate.setProbeName(webPageList.get(i).getProbeName());
				scoreDate.setTargetName(webPageList.get(i).getTargetName());
				scoreDate.setAccessLayer(webPageList.get(i).getAccessLayer());
				scoreDate.setRecordDate(webPageList.get(i).getRecordDate());
				scoreDate.setRecordTime(webPageList.get(i).getRecordTime());
				scoreDate.setPort(webPageList.get(i).getPort());
				scoreDate.setFail(webPageList.get(i).getFail());
				scoreDate.setTotal(webPageList.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebpageDnsDelay(webPageList.get(i).getWebpageDnsDelay());
				scoreBase.setWebpageConnDelay(webPageList.get(i).getWebpageConnDelay());
				scoreBase.setWebpageHeadbyteDelay(webPageList.get(i).getWebpageHeadbyteDelay());
				scoreBase.setWebpagePageFileDelay(webPageList.get(i).getWebpagePageFileDelay());
				scoreBase.setWebpageRedirectDelay(webPageList.get(i).getWebpageRedirectDelay());
				scoreBase.setWebpageAboveFoldDelay(webPageList.get(i).getWebpageAboveFoldDelay());
				scoreBase.setWebpagePageElementDelay(webPageList.get(i).getWebpagePageElementDelay());
				scoreBase.setWebpageLoadDelay(webPageList.get(i).getWebpageLoadDelay());
				scoreBase.setWebpageDownloadRate(webPageList.get(i).getWebpageDownloadRate());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreDate)) {

					connection.put(scoreDate,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreDate);
					scoreBase.setWebpageDnsDelay(scoreBase.getWebpageDnsDelay());
					scoreBase.setWebpageConnDelay(scoreBase.getWebpageConnDelay());
					scoreBase.setWebpageHeadbyteDelay(scoreBase.getWebpageHeadbyteDelay());
					scoreBase.setWebpagePageFileDelay(scoreBase.getWebpagePageFileDelay());
					scoreBase.setWebpageRedirectDelay(scoreBase.getWebpageRedirectDelay());
					scoreBase.setWebpageAboveFoldDelay(scoreBase.getWebpageAboveFoldDelay());
					scoreBase.setWebpagePageElementDelay(scoreBase.getWebpagePageElementDelay());
					scoreBase.setWebpageLoadDelay(scoreBase.getWebpageLoadDelay());
					scoreBase.setWebpageDownloadRate(scoreBase.getWebpageDownloadRate());
					scoreBase.setScore((scoreBase.getScore()+scoreBaseDul.getScore())/2);
					scoreBase.setBase(scoreBase.getBase());

					connection.put(scoreDate,scoreBase);
				}

			}
			

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
					finalScore.setServiceType(3);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setWebpageDnsDelay(connection.get(ite).getWebpageDnsDelay());
					finalScore.setWebpageConnDelay(connection.get(ite).getWebpageConnDelay());
					finalScore.setWebpageHeadbyteDelay(connection.get(ite).getWebpageHeadbyteDelay());
					finalScore.setWebpagePageFileDelay(connection.get(ite).getWebpagePageFileDelay());
					finalScore.setWebpageRedirectDelay(connection.get(ite).getWebpageRedirectDelay());
					finalScore.setWebpageAboveFoldDelay(connection.get(ite).getWebpageAboveFoldDelay());
					finalScore.setWebpagePageElementDelay(connection.get(ite).getWebpagePageElementDelay());
					finalScore.setWebpageLoadDelay(connection.get(ite).getWebpageLoadDelay());
					finalScore.setWebpageDownloadRate(connection.get(ite).getWebpageDownloadRate());
					finalScore.setScore(connection.get(ite).getScore());
					finalScore.setBase(connection.get(ite).getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("browseweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}


	@Override
	public List<ScoreEntity> calculateLayer3(List<ScoreEntity> webPageList){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreLayerEntity,ScoreBaseEntity> connection= new HashMap<>();
			for (int i = 0; i < webPageList.size(); i++) {
				if(webPageList.get(i).getAccessLayer()==null){
					continue;
				}
				ScoreLayerEntity scoreLayer = new ScoreLayerEntity();
				scoreLayer.setCityId(webPageList.get(i).getCityId());
				scoreLayer.setCountyId(webPageList.get(i).getCountyId());
				scoreLayer.setProbeId(webPageList.get(i).getProbeId());
				scoreLayer.setTargetId(webPageList.get(i).getTargetId());
				scoreLayer.setCityName(webPageList.get(i).getCityName());
				scoreLayer.setCountyName(webPageList.get(i).getCountyName());
				scoreLayer.setProbeName(webPageList.get(i).getProbeName());
				scoreLayer.setTargetName(webPageList.get(i).getTargetName());
				scoreLayer.setAccessLayer(webPageList.get(i).getAccessLayer());
				scoreLayer.setRecordDate(webPageList.get(i).getRecordDate());
				scoreLayer.setRecordTime(webPageList.get(i).getRecordTime());
				scoreLayer.setPort(webPageList.get(i).getPort());
				scoreLayer.setFail(webPageList.get(i).getFail());
				scoreLayer.setTotal(webPageList.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebpageDnsDelay(webPageList.get(i).getWebpageDnsDelay());
				scoreBase.setWebpageConnDelay(webPageList.get(i).getWebpageConnDelay());
				scoreBase.setWebpageHeadbyteDelay(webPageList.get(i).getWebpageHeadbyteDelay());
				scoreBase.setWebpagePageFileDelay(webPageList.get(i).getWebpagePageFileDelay());
				scoreBase.setWebpageRedirectDelay(webPageList.get(i).getWebpageRedirectDelay());
				scoreBase.setWebpageAboveFoldDelay(webPageList.get(i).getWebpageAboveFoldDelay());
				scoreBase.setWebpagePageElementDelay(webPageList.get(i).getWebpagePageElementDelay());
				scoreBase.setWebpageLoadDelay(webPageList.get(i).getWebpageLoadDelay());
				scoreBase.setWebpageDownloadRate(webPageList.get(i).getWebpageDownloadRate());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreLayer)) {

					connection.put(scoreLayer,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreLayer);
					scoreBase.setWebpageDnsDelay(scoreBase.getWebpageDnsDelay());
					scoreBase.setWebpageConnDelay(scoreBase.getWebpageConnDelay());
					scoreBase.setWebpageHeadbyteDelay(scoreBase.getWebpageHeadbyteDelay());
					scoreBase.setWebpagePageFileDelay(scoreBase.getWebpagePageFileDelay());
					scoreBase.setWebpageRedirectDelay(scoreBase.getWebpageRedirectDelay());
					scoreBase.setWebpageAboveFoldDelay(scoreBase.getWebpageAboveFoldDelay());
					scoreBase.setWebpagePageElementDelay(scoreBase.getWebpagePageElementDelay());
					scoreBase.setWebpageLoadDelay(scoreBase.getWebpageLoadDelay());
					scoreBase.setWebpageDownloadRate(scoreBase.getWebpageDownloadRate());
					scoreBase.setScore((scoreBase.getScore()+scoreBaseDul.getScore())/2);
					scoreBase.setBase(scoreBase.getBase());

					connection.put(scoreLayer,scoreBase);
				}

			}


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
					finalScore.setServiceType(3);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setWebpageDnsDelay(connection.get(ite).getWebpageDnsDelay());
					finalScore.setWebpageConnDelay(connection.get(ite).getWebpageConnDelay());
					finalScore.setWebpageHeadbyteDelay(connection.get(ite).getWebpageHeadbyteDelay());
					finalScore.setWebpagePageFileDelay(connection.get(ite).getWebpagePageFileDelay());
					finalScore.setWebpageRedirectDelay(connection.get(ite).getWebpageRedirectDelay());
					finalScore.setWebpageAboveFoldDelay(connection.get(ite).getWebpageAboveFoldDelay());
					finalScore.setWebpagePageElementDelay(connection.get(ite).getWebpagePageElementDelay());
					finalScore.setWebpageLoadDelay(connection.get(ite).getWebpageLoadDelay());
					finalScore.setWebpageDownloadRate(connection.get(ite).getWebpageDownloadRate());
					finalScore.setScore(connection.get(ite).getScore());
					finalScore.setBase(connection.get(ite).getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("browseweight")));
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
		return recordHourWebPageDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourWebPageEntity recordHourWebPage){
		recordHourWebPageDao.save(recordHourWebPage);
	}
	
	@Override
	public void update(RecordHourWebPageEntity recordHourWebPage){
		recordHourWebPageDao.update(recordHourWebPage);
	}
	
	@Override
	public void delete(Integer id){
		recordHourWebPageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourWebPageDao.deleteBatch(ids);
	}
	
}
