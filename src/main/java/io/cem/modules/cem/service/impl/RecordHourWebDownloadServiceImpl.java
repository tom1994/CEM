package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordWebDownloadDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordFailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourWebDownloadDao;
import io.cem.modules.cem.service.RecordHourWebDownloadService;


@Service("recordHourWebDownloadService")
public class RecordHourWebDownloadServiceImpl implements RecordHourWebDownloadService {
    @Autowired
    private RecordHourWebDownloadDao recordHourWebDownloadDao;
    @Autowired
    private RecordWebDownloadDao recordWebDownloadDao;

	@Override
	public RecordHourWebDownloadEntity queryObject(Integer id){
		return recordHourWebDownloadDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourWebDownloadEntity> queryList(Map<String, Object> map){
		return recordHourWebDownloadDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourWebDownloadEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebDownloadDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebDownloadEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebDownloadDao.queryDayExitList(map));
	}
	@Override
	public List<RecordHourWebDownloadEntity> queryWebDownload(Map<String, Object> map){
		return recordWebDownloadDao.queryWebDownload(map);
	}

    @Override
    @Async
    public Future<List<RecordHourWebDownloadEntity>> queryWebDownloadList(Map<String, Object> map) {
        return new AsyncResult<>
                (recordHourWebDownloadDao.queryWebDownloadList(map));
    }

	@Override
	@Async
	public Future<List<RecordHourWebDownloadEntity>> queryTargetHourList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourWebDownloadDao.queryTargetHourList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebDownloadEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebDownloadDao.queryDayList(map));
	}

	@Override
	public List<ScoreEntity> calculateWebDownload(List<RecordHourWebDownloadEntity> webDownloadList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> webDownload = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",30);
			map.put("type",1);
			List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			for(int i=0;i<recordFail.size();i++){
				failMap.put(recordFail.get(i),recordFail.get(i));
			}
			for(int i=0;i<webDownloadList.size();i++) {
				double score = 0;
				//dns_delay 100
				if ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD12"))) <= 0) {
					score+= 100 * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 80-100
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD12"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD13"))) <= 0)) {
					score+= (80 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD13")))) * 20) / ((Double.parseDouble(pros.getValue("webD12")) - (Double.parseDouble(pros.getValue("webD13"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 60-80
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD13"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD14"))) <= 0)) {
					score+= (60 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD14")))) * 20) / ((Double.parseDouble(pros.getValue("webD13")) - (Double.parseDouble(pros.getValue("webD14"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 40-60
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD14"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD15"))) <= 0)) {
					score+= (40 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD15")))) * 20) / ((Double.parseDouble(pros.getValue("webD14")) - (Double.parseDouble(pros.getValue("webD15"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 20-40
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD15"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD16"))) <= 0)) {
					score+= (20 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD16")))) * 20) / ((Double.parseDouble(pros.getValue("webD15")) - (Double.parseDouble(pros.getValue("webD16"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 0-20
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD16"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD17"))) <= 0)) {
					score+= ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD17")))) * 20) / ((Double.parseDouble(pros.getValue("webD16")) - (Double.parseDouble(pros.getValue("webD17")))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 0
				else {
					score+= 0;
				}

				//conn_delay 100
				if ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD22"))) <= 0) {
					score+= 100 * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 80-100
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD22"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD23"))) <= 0)) {
					score+= (80 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD23")))) * 20) / ((Double.parseDouble(pros.getValue("webD22")) - (Double.parseDouble(pros.getValue("webD23"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 60-80
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD23"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD24"))) <= 0)) {
					score+= (60 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD24")))) * 20) / ((Double.parseDouble(pros.getValue("webD23")) - (Double.parseDouble(pros.getValue("webD24"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 40-60
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD24"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD25"))) <= 0)) {
					score+= (40 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD25")))) * 20) / ((Double.parseDouble(pros.getValue("webD24")) - (Double.parseDouble(pros.getValue("webD25"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 20-40
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD25"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD26"))) <= 0)) {
					score+= (20 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD26")))) * 20) / ((Double.parseDouble(pros.getValue("webD25")) - (Double.parseDouble(pros.getValue("webD26"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 0-20
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD26"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD27"))) <= 0)) {
					score+= ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD27")))) * 20) / ((Double.parseDouble(pros.getValue("webD26")) - (Double.parseDouble(pros.getValue("webD27")))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 0
				else {
					score+= 0;
				}

				//download_rate 100
				if ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD32"))) >= 0) {
					score+= 100 * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 80-100
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD32"))) < 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD33"))) >= 0)) {
					score+= (80 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD33")))) * 20) / ((Double.parseDouble(pros.getValue("webD32")) - (Double.parseDouble(pros.getValue("webD33"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 60-80
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD33"))) < 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD34"))) >= 0)) {
					score+= (60 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD34")))) * 20) / ((Double.parseDouble(pros.getValue("webD33")) - (Double.parseDouble(pros.getValue("webD34"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 40-60
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD34"))) < 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD35"))) >= 0)) {
					score+= (40 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD35")))) * 20) / ((Double.parseDouble(pros.getValue("webD34")) - (Double.parseDouble(pros.getValue("webD35"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 20-40
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD35"))) < 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD36"))) >= 0)) {
					score+= (20 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD36")))) * 20) / ((Double.parseDouble(pros.getValue("webD35")) - (Double.parseDouble(pros.getValue("webD36"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 0-20
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD36"))) < 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD37"))) >= 0)) {
					score+= ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD37")))) * 20) / ((Double.parseDouble(pros.getValue("webD36")) - (Double.parseDouble(pros.getValue("webD37")))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 0
				else {
					score+= 0;
				}

				//head_byty_delay 100
				if ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD42"))) <= 0) {
					score+= 100 * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 80-100
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD42"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD43"))) <= 0)) {
					score+= (80 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD43")))) * 20) / ((Double.parseDouble(pros.getValue("webD42")) - (Double.parseDouble(pros.getValue("webD43"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 60-80
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD43"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD44"))) <= 0)) {
					score+= (60 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD44")))) * 20) / ((Double.parseDouble(pros.getValue("webD43")) - (Double.parseDouble(pros.getValue("webD44"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 40-60
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD44"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD45"))) <= 0)) {
					score+= (40 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD45")))) * 20) / ((Double.parseDouble(pros.getValue("webD44")) - (Double.parseDouble(pros.getValue("webD45"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 20-40
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD45"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD46"))) <= 0)) {
					score+= (20 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD46")))) * 20) / ((Double.parseDouble(pros.getValue("webD45")) - (Double.parseDouble(pros.getValue("webD46"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 0-20
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD46"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD47"))) <= 0)) {
					score+= ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD47")))) * 20) / ((Double.parseDouble(pros.getValue("webD46")) - (Double.parseDouble(pros.getValue("webD47")))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 0
				else {
					score+= 0;
				}


				ScoreEntity WEBDL = new ScoreEntity();
				WEBDL.setId(webDownloadList.get(i).getId());
				WEBDL.setCityName(webDownloadList.get(i).getCityName());
				WEBDL.setCityId(webDownloadList.get(i).getCityId());
				WEBDL.setCountyName(webDownloadList.get(i).getAreaName());
				WEBDL.setCountyId(webDownloadList.get(i).getCountyId());
				WEBDL.setProbeName(webDownloadList.get(i).getProbeName());
				WEBDL.setProbeId(webDownloadList.get(i).getProbeId());
				WEBDL.setServiceType(webDownloadList.get(i).getServiceType());
				WEBDL.setTargetName(webDownloadList.get(i).getTargetName());
				WEBDL.setTargetId(webDownloadList.get(i).getTargetId());
				WEBDL.setAccessLayer(webDownloadList.get(i).getAccessLayer());
				WEBDL.setPort(webDownloadList.get(i).getPort());
				WEBDL.setRecordDate(webDownloadList.get(i).getRecordDate());
				WEBDL.setRecordTime(webDownloadList.get(i).getRecordTime());
				WEBDL.setExit(webDownloadList.get(i).getExit());
				WEBDL.setWebDownloadDnsDelay(webDownloadList.get(i).getDnsDelay());
				WEBDL.setWebDownloadConnDelay(webDownloadList.get(i).getConnDelay());
				WEBDL.setWebDownloadHeadbyteDelay(webDownloadList.get(i).getHeadbyteDelay());
				WEBDL.setWebDownloadDownloadRate(webDownloadList.get(i).getDownloadRate());
				RecordFailEntity failEntity = new RecordFailEntity();
				failEntity.setCityId(webDownloadList.get(i).getCityId());
				failEntity.setCountyId(webDownloadList.get(i).getCountyId());
				failEntity.setProbeId(webDownloadList.get(i).getProbeId());
				failEntity.setTargetId(webDownloadList.get(i).getTargetId());
				failEntity.setPort(webDownloadList.get(i).getPort());
				failEntity.setRecordDate(webDownloadList.get(i).getRecordDate());
				failEntity.setRecordTime(webDownloadList.get(i).getRecordTime());
				if(failMap.containsKey(failEntity)){
					WEBDL.setFail(failMap.get(failEntity).getFail());
					WEBDL.setTotal(failMap.get(failEntity).getTotal());
				}else{
					WEBDL.setFail(webDownloadList.get(i).getFail());
					WEBDL.setTotal(webDownloadList.get(i).getTotal());
				}
				double fail = (double) WEBDL.getFail()/WEBDL.getTotal();
				WEBDL.setScore(score*(1-fail));
				WEBDL.setBase(Double.parseDouble(pros.getValue("web_download")));

				webDownload.add(WEBDL);
			}
		}catch (IOException e){}
		
		return webDownload;
	} 
	
	@Override
	public List<ScoreEntity> calculateFtpDownload(List<RecordHourFtpEntity> ftpList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> ftpDownload = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",31);
			map.put("type",1);
			List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			for(int i=0;i<recordFail.size();i++){
				failMap.put(recordFail.get(i),recordFail.get(i));
			}
			for(int i=0;i<ftpList.size();i++){
				double score=0;
				//ftp download
				if(ftpList.get(i).getServiceType()==31){
					//dns_delay 100
					if ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD12"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 80-100
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD12"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD13"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD13")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD12")) - (Double.parseDouble(pros.getValue("ftpD13"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 60-80
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD13"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD14"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD14")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD13")) - (Double.parseDouble(pros.getValue("ftpD14"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 40-60
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD14"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD15"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD15")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD14")) - (Double.parseDouble(pros.getValue("ftpD15"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 20-40
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD15"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD16"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD16")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD15")) - (Double.parseDouble(pros.getValue("ftpD16"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 0-20
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD16"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD17"))) <= 0)) {
						score+= ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD17")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD16")) - (Double.parseDouble(pros.getValue("ftpD17")))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 0
					else {
						score+= 0;
					}

					//conn_delay 100
					if ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD22")) - (Double.parseDouble(pros.getValue("ftpD23"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD23")) - (Double.parseDouble(pros.getValue("ftpD24"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD24")) - (Double.parseDouble(pros.getValue("ftpD25"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD25")) - (Double.parseDouble(pros.getValue("ftpD26"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD27"))) <= 0)) {
						score+= ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD26")) - (Double.parseDouble(pros.getValue("ftpD27")))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0
					else {
						score+= 0;
					}

					//login_delay 100
					if ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD32"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD32"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD33"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD33")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD32")) - (Double.parseDouble(pros.getValue("ftpD33"))))))) * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD33"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD34"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD34")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD33")) - (Double.parseDouble(pros.getValue("ftpD34"))))))) * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD34"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD35"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD35")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD34")) - (Double.parseDouble(pros.getValue("ftpD35"))))))) * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD35"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD36"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD36")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD35")) - (Double.parseDouble(pros.getValue("ftpD36"))))))) * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD36"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD37"))) <= 0)) {
						score+= ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD37")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD36")) - (Double.parseDouble(pros.getValue("ftpD37")))))) * (Double.parseDouble(pros.getValue("ftpD31")));
					}
					//conn_delay 0
					else {
						score+= 0;
					}

					//head_byty_delay 100
					if ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD42"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 80-100
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD42"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD43"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD43")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD42")) - (Double.parseDouble(pros.getValue("ftpD43"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 60-80
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD43"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD44"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD44")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD43")) - (Double.parseDouble(pros.getValue("ftpD44"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 40-60
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD44"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD45"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD45")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD44")) - (Double.parseDouble(pros.getValue("ftpD45"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 20-40
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD45"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD46"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD46")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD45")) - (Double.parseDouble(pros.getValue("ftpD46"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 0-20
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD46"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD47"))) <= 0)) {
						score+= ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD47")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD46")) - (Double.parseDouble(pros.getValue("ftpD47")))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 0
					else {
						score+= 0;
					}

					//download_rate 100 降序
					if ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD52"))) >= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 80-100
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD52"))) < 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD53"))) >= 0)) {
						score+= (80 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD53")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD52")) - (Double.parseDouble(pros.getValue("ftpD53"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 60-80
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD53"))) < 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD54"))) >= 0)) {
						score+= (60 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD54")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD53")) - (Double.parseDouble(pros.getValue("ftpD54"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 40-60
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD54"))) < 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD55"))) >= 0)) {
						score+= (40 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD55")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD54")) - (Double.parseDouble(pros.getValue("ftpD55"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 20-40
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD55"))) < 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD56"))) >= 0)) {
						score+= (20 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD56")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD55")) - (Double.parseDouble(pros.getValue("ftpD56"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 0-20
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD56"))) < 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD57"))) >= 0)) {
						score+= ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD57")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD56")) - (Double.parseDouble(pros.getValue("ftpD57")))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 0
					else {
						score+= 0;
					}

					ScoreEntity FTPD = new ScoreEntity();
					FTPD.setId(ftpList.get(i).getId());
					FTPD.setCityName(ftpList.get(i).getCityName());
					FTPD.setCityId(ftpList.get(i).getCityId());
					FTPD.setCountyName(ftpList.get(i).getAreaName());
					FTPD.setCountyId(ftpList.get(i).getCountyId());
					FTPD.setProbeName(ftpList.get(i).getProbeName());
					FTPD.setProbeId(ftpList.get(i).getProbeId());
					FTPD.setServiceType(ftpList.get(i).getServiceType());
					FTPD.setTargetName(ftpList.get(i).getTargetName());
					FTPD.setTargetId(ftpList.get(i).getTargetId());
					FTPD.setAccessLayer(ftpList.get(i).getAccessLayer());
					FTPD.setPort(ftpList.get(i).getPort());
					FTPD.setRecordTime(ftpList.get(i).getRecordTime());
					FTPD.setRecordDate(ftpList.get(i).getRecordDate());
					FTPD.setExit(ftpList.get(i).getExit());
					FTPD.setFtpDownloadDnsDelay(ftpList.get(i).getDnsDelay());
					FTPD.setFtpDownloadConnDelay(ftpList.get(i).getConnDelay());
					FTPD.setFtpDownloadLoginDelay(ftpList.get(i).getLoginDelay());
					FTPD.setFtpDownloadHeadbyteDelay(ftpList.get(i).getHeadbyteDelay());
					FTPD.setFtpDownloadDownloadRate(ftpList.get(i).getDownloadRate());
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(ftpList.get(i).getCityId());
					failEntity.setCountyId(ftpList.get(i).getCountyId());
					failEntity.setProbeId(ftpList.get(i).getProbeId());
					failEntity.setTargetId(ftpList.get(i).getTargetId());
					failEntity.setPort(ftpList.get(i).getPort());
					failEntity.setRecordDate(ftpList.get(i).getRecordDate());
					failEntity.setRecordTime(ftpList.get(i).getRecordTime());
					if(failMap.containsKey(failEntity)){
						FTPD.setFail(failMap.get(failEntity).getFail());
						FTPD.setTotal(failMap.get(failEntity).getTotal());
					}else{
						FTPD.setFail(ftpList.get(i).getFail());
						FTPD.setTotal(ftpList.get(i).getTotal());
					}
					double fail = (double) FTPD.getFail()/FTPD.getTotal();
					FTPD.setScore(score*(1-fail));
					FTPD.setBase(Double.parseDouble(pros.getValue("ftp_download")));

					ftpDownload.add(FTPD);

				}else{}
			}
		}catch(IOException e){}
		return ftpDownload;
	}

	@Override
	public List<ScoreEntity> calculateFtpUpload(List<RecordHourFtpEntity> ftpList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> ftpUpload = new ArrayList<>();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			map.put("service_type",32);
			map.put("type",1);
			List<RecordFailEntity> recordFail = recordFailService.queryFail1(map);
			Map<RecordFailEntity,RecordFailEntity> failMap = new HashMap<>();
			for(int i=0;i<recordFail.size();i++){
				failMap.put(recordFail.get(i),recordFail.get(i));
			}
			for(int i=0;i<ftpList.size();i++) {
				double score = 0;
				if(ftpList.get(i).getServiceType()==32){
					//dns_delay 100
					if ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU12"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 80-100
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU12"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU13"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU13")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU12")) - (Double.parseDouble(pros.getValue("ftpU13"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 60-80
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU13"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU14"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU14")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU13")) - (Double.parseDouble(pros.getValue("ftpU14"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 40-60
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU14"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU15"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU15")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU14")) - (Double.parseDouble(pros.getValue("ftpU15"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 20-40
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU15"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU16"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU16")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU15")) - (Double.parseDouble(pros.getValue("ftpU16"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 0-20
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU16"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU17"))) <= 0)) {
						score+= ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU17")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU16")) - (Double.parseDouble(pros.getValue("ftpU17")))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 0
					else {
						score+= 0;
					}

					//conn_delay 100
					if ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) <= 0) {
						score += 100 * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) <= 0)) {
						score += (80 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU22")) - (Double.parseDouble(pros.getValue("ftpU23"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) <= 0)) {
						score += (60 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU23")) - (Double.parseDouble(pros.getValue("ftpU24"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) <= 0)) {
						score += (40 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU24")) - (Double.parseDouble(pros.getValue("ftpU25"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) <= 0)) {
						score += (20 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU25")) - (Double.parseDouble(pros.getValue("ftpU26"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU27"))) <= 0)) {
						score += ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU26")) - (Double.parseDouble(pros.getValue("ftpU27")))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0
					else {
						score += 0;
					}

					//login_delay 100
					if ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU32"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU32"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU33"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU33")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU32")) - (Double.parseDouble(pros.getValue("ftpU33"))))))) * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU33"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU34"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU34")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU33")) - (Double.parseDouble(pros.getValue("ftpU34"))))))) * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU34"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU35"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU35")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU34")) - (Double.parseDouble(pros.getValue("ftpU35"))))))) * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU35"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU36"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU36")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU35")) - (Double.parseDouble(pros.getValue("ftpU36"))))))) * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU36"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU37"))) <= 0)) {
						score+= ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU37")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU36")) - (Double.parseDouble(pros.getValue("ftpU37")))))) * (Double.parseDouble(pros.getValue("ftpU31")));
					}
					//conn_delay 0
					else {
						score+= 0;
					}

					//head_byty_delay 100
					if ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU42"))) <= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 80-100
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU42"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU43"))) <= 0)) {
						score+= (80 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU43")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU42")) - (Double.parseDouble(pros.getValue("ftpU43"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 60-80
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU43"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU44"))) <= 0)) {
						score+= (60 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU44")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU43")) - (Double.parseDouble(pros.getValue("ftpU44"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 40-60
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU44"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU45"))) <= 0)) {
						score+= (40 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU45")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU44")) - (Double.parseDouble(pros.getValue("ftpU45"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 20-40
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU45"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU46"))) <= 0)) {
						score+= (20 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU46")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU45")) - (Double.parseDouble(pros.getValue("ftpU46"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 0-20
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU46"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU47"))) <= 0)) {
						score+= ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU47")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU46")) - (Double.parseDouble(pros.getValue("ftpU47")))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 0
					else {
						score+= 0;
					}

					//download_rate 100
					if ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU52"))) >= 0) {
						score+= 100 * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 80-100
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU52"))) < 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU53"))) >= 0)) {
						score+= (80 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU53")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU52")) - (Double.parseDouble(pros.getValue("ftpU53"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 60-80
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU53"))) < 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU54"))) >= 0)) {
						score+= (60 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU54")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU53")) - (Double.parseDouble(pros.getValue("ftpU54"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 40-60
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU54"))) < 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU55"))) >= 0)) {
						score+= (40 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU55")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU54")) - (Double.parseDouble(pros.getValue("ftpU55"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 20-40
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU55"))) < 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU56"))) >= 0)) {
						score+= (20 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU56")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU55")) - (Double.parseDouble(pros.getValue("ftpU56"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 0-20
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU56"))) < 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU57"))) >= 0)) {
						score+= ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU57")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU56")) - (Double.parseDouble(pros.getValue("ftpU57")))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 0
					else {
						score+= 0;
					}

					ScoreEntity FTPU = new ScoreEntity();
					FTPU.setId(ftpList.get(i).getId());
					FTPU.setCityName(ftpList.get(i).getCityName());
					FTPU.setCityId(ftpList.get(i).getCityId());
					FTPU.setCountyName(ftpList.get(i).getAreaName());
					FTPU.setCountyId(ftpList.get(i).getCountyId());
					FTPU.setProbeName(ftpList.get(i).getProbeName());
					FTPU.setProbeId(ftpList.get(i).getProbeId());
					FTPU.setServiceType(ftpList.get(i).getServiceType());
					FTPU.setTargetName(ftpList.get(i).getTargetName());
					FTPU.setTargetId(ftpList.get(i).getTargetId());
					FTPU.setRecordTime(ftpList.get(i).getRecordTime());
					FTPU.setRecordDate(ftpList.get(i).getRecordDate());
					FTPU.setFtpUploadDnsDelay(ftpList.get(i).getDnsDelay());
					FTPU.setFtpUploadConnDelay(ftpList.get(i).getConnDelay());
					FTPU.setFtpUploadLoginDelay(ftpList.get(i).getLoginDelay());
					FTPU.setFtpUploadHeadbyteDelay(ftpList.get(i).getHeadbyteDelay());
					FTPU.setFtpUploadUploadRate(ftpList.get(i).getUploadRate());
					FTPU.setAccessLayer(ftpList.get(i).getAccessLayer());
					FTPU.setExit(ftpList.get(i).getExit());
					FTPU.setPort(ftpList.get(i).getPort());
					RecordFailEntity failEntity = new RecordFailEntity();
					failEntity.setCityId(ftpList.get(i).getCityId());
					failEntity.setCountyId(ftpList.get(i).getCountyId());
					failEntity.setProbeId(ftpList.get(i).getProbeId());
					failEntity.setTargetId(ftpList.get(i).getTargetId());
					failEntity.setPort(ftpList.get(i).getPort());
					failEntity.setRecordDate(ftpList.get(i).getRecordDate());
					failEntity.setRecordTime(ftpList.get(i).getRecordTime());
					if(failMap.containsKey(failEntity)){
						FTPU.setFail(failMap.get(failEntity).getFail());
						FTPU.setTotal(failMap.get(failEntity).getTotal());
					}else{
						FTPU.setFail(ftpList.get(i).getFail());
						FTPU.setTotal(ftpList.get(i).getTotal());
					}
					double fail = (double) FTPU.getFail()/FTPU.getTotal();
					FTPU.setScore(score*(1-fail));
					FTPU.setBase(Double.parseDouble(pros.getValue("ftp_upload")));

					ftpUpload.add(FTPU);
				}else{}
			}
		}catch (IOException e){}
		return ftpUpload;
	}

	@Override
	public List<ScoreEntity> calculateService4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreTargetEntity scoreTarget = new ScoreTargetEntity();
				scoreTarget.setCityId(webDownload.get(i).getCityId());
				scoreTarget.setCountyId(webDownload.get(i).getCountyId());
				scoreTarget.setProbeId(webDownload.get(i).getProbeId());
				scoreTarget.setTargetId(webDownload.get(i).getTargetId());
				scoreTarget.setCityName(webDownload.get(i).getCityName());
				scoreTarget.setCountyName(webDownload.get(i).getCountyName());
				scoreTarget.setProbeName(webDownload.get(i).getProbeName());
				scoreTarget.setTargetName(webDownload.get(i).getTargetName());
				scoreTarget.setRecordDate(webDownload.get(i).getRecordDate());
				scoreTarget.setRecordTime(webDownload.get(i).getRecordTime());
				scoreTarget.setExit(webDownload.get(i).getExit());
				scoreTarget.setAccessLayer(webDownload.get(i).getAccessLayer());
				scoreTarget.setPort(webDownload.get(i).getPort());
				scoreTarget.setFail(webDownload.get(i).getFail());
				scoreTarget.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebDownloadDnsDelay(webDownload.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(webDownload.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(webDownload.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(webDownload.get(i).getWebDownloadDownloadRate());
				scoreBase.setWebDownloadFail(webDownload.get(i).getFail());
				scoreBase.setWebDownloadTotal(webDownload.get(i).getTotal());
				scoreBase.setScore(webDownload.get(i).getScore());
				scoreBase.setBase(webDownload.get(i).getBase());
				Map<String,ScoreBaseEntity> webdownload = new HashMap<>();
				webdownload.put("webDownload",scoreBase);
				connection.put(scoreTarget,webdownload);
			}
			connection=pingService.putMap(ftpDownload,connection,"ftpDownload");
			connection=pingService.putMap(ftpUpload,connection,"ftpUpload");

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
					finalScore.setServiceType(4);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setExit(ite.getExit());
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
						if (typ.equals("webDownload")) {
							finalScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
							finalScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
							finalScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
							finalScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
							finalScore.setWebDownloadFail(map1.get(typ).getWebDownloadFail());
							finalScore.setWebDownloadTotal(map1.get(typ).getWebDownloadTotal());
						}else if(typ.equals("ftpDownload")){
							finalScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
							finalScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
							finalScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
							finalScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
							finalScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
							finalScore.setFtpDownloadFail(map1.get(typ).getFtpDownloadFail());
							finalScore.setFtpDownloadTotal(map1.get(typ).getFtpDownloadTotal());
						}else if(typ.equals("ftpUpload")){
							finalScore.setFtpUploadDnsDelay(map1.get(typ).getFtpUploadDnsDelay());
							finalScore.setFtpUploadConnDelay(map1.get(typ).getFtpUploadConnDelay());
							finalScore.setFtpUploadLoginDelay(map1.get(typ).getFtpUploadLoginDelay());
							finalScore.setFtpUploadHeadbyteDelay(map1.get(typ).getFtpUploadHeadbyteDelay());
							finalScore.setFtpUploadUploadRate(map1.get(typ).getFtpUploadUploadRate());
							finalScore.setFtpUploadFail(map1.get(typ).getFtpUploadFail());
							finalScore.setFtpUploadTotal(map1.get(typ).getFtpUploadTotal());
						}else{}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		}catch (IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateDate4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreDateEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreDateEntity scoreDate = new ScoreDateEntity();
				scoreDate.setCityId(webDownload.get(i).getCityId());
				scoreDate.setCountyId(webDownload.get(i).getCountyId());
				scoreDate.setProbeId(webDownload.get(i).getProbeId());
				scoreDate.setTargetId(webDownload.get(i).getTargetId());
				scoreDate.setCityName(webDownload.get(i).getCityName());
				scoreDate.setCountyName(webDownload.get(i).getCountyName());
				scoreDate.setProbeName(webDownload.get(i).getProbeName());
				scoreDate.setTargetName(webDownload.get(i).getTargetName());
				scoreDate.setRecordDate(webDownload.get(i).getRecordDate());
				scoreDate.setRecordTime(webDownload.get(i).getRecordTime());
				scoreDate.setAccessLayer(webDownload.get(i).getAccessLayer());
				scoreDate.setPort(webDownload.get(i).getPort());
				scoreDate.setFail(webDownload.get(i).getFail());
				scoreDate.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebDownloadDnsDelay(webDownload.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(webDownload.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(webDownload.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(webDownload.get(i).getWebDownloadDownloadRate());
				scoreBase.setScore(webDownload.get(i).getScore());
				scoreBase.setBase(webDownload.get(i).getBase());
				scoreBase.setWebDownloadTotal(webDownload.get(i).getTotal());
				scoreBase.setWebDownloadFail(webDownload.get(i).getFail());
				Map<String,ScoreBaseEntity> webdownload = new HashMap<>();
				webdownload.put("webDownload",scoreBase);
				connection.put(scoreDate,webdownload);
			}
			connection=pingService.putMapDate(ftpDownload,connection,"ftpDownload");
			connection=pingService.putMapDate(ftpUpload,connection,"ftpUpload");

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
					finalScore.setServiceType(4);
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
						if (typ.equals("webDownload")) {
							finalScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
							finalScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
							finalScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
							finalScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
							finalScore.setWebDownloadFail(map1.get(typ).getWebDownloadFail());
							finalScore.setWebDownloadTotal(map1.get(typ).getWebDownloadTotal());
						}else if(typ.equals("ftpDownload")){
							finalScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
							finalScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
							finalScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
							finalScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
							finalScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
							finalScore.setFtpDownloadFail(map1.get(typ).getFtpDownloadFail());
							finalScore.setFtpDownloadTotal(map1.get(typ).getFtpDownloadTotal());
						}else if(typ.equals("ftpUpload")){
							finalScore.setFtpUploadDnsDelay(map1.get(typ).getFtpUploadDnsDelay());
							finalScore.setFtpUploadConnDelay(map1.get(typ).getFtpUploadConnDelay());
							finalScore.setFtpUploadLoginDelay(map1.get(typ).getFtpUploadLoginDelay());
							finalScore.setFtpUploadHeadbyteDelay(map1.get(typ).getFtpUploadHeadbyteDelay());
							finalScore.setFtpUploadUploadRate(map1.get(typ).getFtpUploadUploadRate());
							finalScore.setFtpUploadFail(map1.get(typ).getFtpUploadFail());
							finalScore.setFtpUploadTotal(map1.get(typ).getFtpUploadTotal());
						}else{}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		}catch (IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateLayer4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreLayerEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				if(webDownload.get(i).getAccessLayer()==null){
					continue;
				}
				ScoreLayerEntity scoreLayer = new ScoreLayerEntity();
				scoreLayer.setCityId(webDownload.get(i).getCityId());
				scoreLayer.setCountyId(webDownload.get(i).getCountyId());
				scoreLayer.setProbeId(webDownload.get(i).getProbeId());
				scoreLayer.setTargetId(webDownload.get(i).getTargetId());
				scoreLayer.setCityName(webDownload.get(i).getCityName());
				scoreLayer.setCountyName(webDownload.get(i).getCountyName());
				scoreLayer.setProbeName(webDownload.get(i).getProbeName());
				scoreLayer.setTargetName(webDownload.get(i).getTargetName());
				scoreLayer.setRecordDate(webDownload.get(i).getRecordDate());
				scoreLayer.setRecordTime(webDownload.get(i).getRecordTime());
				scoreLayer.setAccessLayer(webDownload.get(i).getAccessLayer());
				scoreLayer.setPort(webDownload.get(i).getPort());
				scoreLayer.setFail(webDownload.get(i).getFail());
				scoreLayer.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebDownloadDnsDelay(webDownload.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(webDownload.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(webDownload.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(webDownload.get(i).getWebDownloadDownloadRate());
				scoreBase.setWebDownloadFail(webDownload.get(i).getFail());
				scoreBase.setWebDownloadTotal(webDownload.get(i).getTotal());
				scoreBase.setWebDownloadScore(webDownload.get(i).getScore());
				scoreBase.setScore(webDownload.get(i).getScore());
				scoreBase.setBase(webDownload.get(i).getBase());
				Map<String,ScoreBaseEntity> webdownload = new HashMap<>();
				webdownload.put("webDownload",scoreBase);
				connection.put(scoreLayer,webdownload);
			}
			connection=pingService.putMapLayer(ftpDownload,connection,"ftpDownload");
			connection=pingService.putMapLayer(ftpUpload,connection,"ftpUpload");

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
					finalScore.setServiceType(4);
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
						if (typ.equals("webDownload")) {
							finalScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
							finalScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
							finalScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
							finalScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
							finalScore.setWebDownloadScore(map1.get(typ).getWebDownloadScore());
							finalScore.setWebDownloadFail(map1.get(typ).getWebDownloadFail());
							finalScore.setWebDownloadTotal(map1.get(typ).getWebDownloadTotal());
						}else if(typ.equals("ftpDownload")){
							finalScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
							finalScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
							finalScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
							finalScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
							finalScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
							finalScore.setFtpDownloadScore(map1.get(typ).getFtpDownloadScore());
							finalScore.setFtpDownloadFail(map1.get(typ).getFtpDownloadFail());
							finalScore.setFtpDownloadTotal(map1.get(typ).getFtpDownloadTotal());
						}else if(typ.equals("ftpUpload")){
							finalScore.setFtpUploadDnsDelay(map1.get(typ).getFtpUploadDnsDelay());
							finalScore.setFtpUploadConnDelay(map1.get(typ).getFtpUploadConnDelay());
							finalScore.setFtpUploadLoginDelay(map1.get(typ).getFtpUploadLoginDelay());
							finalScore.setFtpUploadHeadbyteDelay(map1.get(typ).getFtpUploadHeadbyteDelay());
							finalScore.setFtpUploadUploadRate(map1.get(typ).getFtpUploadUploadRate());
							finalScore.setFtpUploadScore(map1.get(typ).getFtpUploadScore());
							finalScore.setFtpUploadFail(map1.get(typ).getFtpUploadFail());
							finalScore.setFtpUploadTotal(map1.get(typ).getFtpUploadTotal());
						}else{}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		}catch (IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateArea4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreAreaEntity scoreArea = new ScoreAreaEntity();
				scoreArea.setCityId(webDownload.get(i).getCityId());
				scoreArea.setCountyId(webDownload.get(i).getCountyId());
				scoreArea.setProbeId(webDownload.get(i).getProbeId());
				scoreArea.setTargetId(webDownload.get(i).getTargetId());
				scoreArea.setCityName(webDownload.get(i).getCityName());
				scoreArea.setCountyName(webDownload.get(i).getCountyName());
				scoreArea.setProbeName(webDownload.get(i).getProbeName());
				scoreArea.setTargetName(webDownload.get(i).getTargetName());
				scoreArea.setRecordDate(webDownload.get(i).getRecordDate());
				scoreArea.setRecordTime(webDownload.get(i).getRecordTime());
				scoreArea.setAccessLayer(webDownload.get(i).getAccessLayer());
				scoreArea.setPort(webDownload.get(i).getPort());
				scoreArea.setFail(webDownload.get(i).getFail());
				scoreArea.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebDownloadDnsDelay(webDownload.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(webDownload.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(webDownload.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(webDownload.get(i).getWebDownloadDownloadRate());
				scoreBase.setScore(webDownload.get(i).getScore());
				scoreBase.setBase(webDownload.get(i).getBase());
				scoreBase.setWebDownloadTotal(webDownload.get(i).getTotal());
				scoreBase.setWebDownloadFail(webDownload.get(i).getFail());
				Map<String,ScoreBaseEntity> webdownload = new HashMap<>();
				webdownload.put("webDownload",scoreBase);
				connection.put(scoreArea,webdownload);
			}
			connection=pingService.putMapArea(ftpDownload,connection,"ftpDownload");
			connection=pingService.putMapArea(ftpUpload,connection,"ftpUpload");

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
					finalScore.setServiceType(4);
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
						if (typ.equals("webDownload")) {
							finalScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
							finalScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
							finalScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
							finalScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
							finalScore.setWebDownloadFail(map1.get(typ).getWebDownloadFail());
							finalScore.setWebDownloadTotal(map1.get(typ).getWebDownloadTotal());
						}else if(typ.equals("ftpDownload")){
							finalScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
							finalScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
							finalScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
							finalScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
							finalScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
							finalScore.setFtpDownloadFail(map1.get(typ).getFtpDownloadFail());
							finalScore.setFtpDownloadTotal(map1.get(typ).getFtpDownloadTotal());
						}else if(typ.equals("ftpUpload")){
							finalScore.setFtpUploadDnsDelay(map1.get(typ).getFtpUploadDnsDelay());
							finalScore.setFtpUploadConnDelay(map1.get(typ).getFtpUploadConnDelay());
							finalScore.setFtpUploadLoginDelay(map1.get(typ).getFtpUploadLoginDelay());
							finalScore.setFtpUploadHeadbyteDelay(map1.get(typ).getFtpUploadHeadbyteDelay());
							finalScore.setFtpUploadUploadRate(map1.get(typ).getFtpUploadUploadRate());
							finalScore.setFtpUploadFail(map1.get(typ).getFtpUploadFail());
							finalScore.setFtpUploadTotal(map1.get(typ).getFtpUploadTotal());
						}else{}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		}catch (IOException e){}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateTarget4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreTargetRankEntity,Map<String,ScoreBaseEntity>> connection= new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreTargetRankEntity targetRank = new ScoreTargetRankEntity();
				targetRank.setCityId(webDownload.get(i).getCityId());
				targetRank.setCountyId(webDownload.get(i).getCountyId());
				targetRank.setProbeId(webDownload.get(i).getProbeId());
				targetRank.setTargetId(webDownload.get(i).getTargetId());
				targetRank.setCityName(webDownload.get(i).getCityName());
				targetRank.setCountyName(webDownload.get(i).getCountyName());
				targetRank.setProbeName(webDownload.get(i).getProbeName());
				targetRank.setTargetName(webDownload.get(i).getTargetName());
				targetRank.setRecordDate(webDownload.get(i).getRecordDate());
				targetRank.setRecordTime(webDownload.get(i).getRecordTime());
				targetRank.setAccessLayer(webDownload.get(i).getAccessLayer());
				targetRank.setPort(webDownload.get(i).getPort());
				targetRank.setFail(webDownload.get(i).getFail());
				targetRank.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setWebDownloadDnsDelay(webDownload.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(webDownload.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(webDownload.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(webDownload.get(i).getWebDownloadDownloadRate());
				scoreBase.setScore(webDownload.get(i).getScore());
				scoreBase.setBase(webDownload.get(i).getBase());
				scoreBase.setWebDownloadFail(webDownload.get(i).getFail());
				scoreBase.setWebDownloadTotal(webDownload.get(i).getTotal());
				Map<String,ScoreBaseEntity> webdownload = new HashMap<>();
				webdownload.put("webDownload",scoreBase);
				connection.put(targetRank,webdownload);
			}
			connection=pingService.putMapTarget(ftpDownload,connection,"ftpDownload");
			connection=pingService.putMapTarget(ftpUpload,connection,"ftpUpload");

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
					finalScore.setServiceType(4);
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
						if (typ.equals("webDownload")) {
							finalScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
							finalScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
							finalScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
							finalScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
							finalScore.setWebDownloadFail(map1.get(typ).getWebDownloadFail());
							finalScore.setWebDownloadTotal(map1.get(typ).getWebDownloadTotal());
						}else if(typ.equals("ftpDownload")){
							finalScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
							finalScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
							finalScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
							finalScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
							finalScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
							finalScore.setFtpDownloadFail(map1.get(typ).getFtpDownloadFail());
							finalScore.setFtpDownloadTotal(map1.get(typ).getFtpDownloadTotal());
						}else if(typ.equals("ftpUpload")){
							finalScore.setFtpUploadDnsDelay(map1.get(typ).getFtpUploadDnsDelay());
							finalScore.setFtpUploadConnDelay(map1.get(typ).getFtpUploadConnDelay());
							finalScore.setFtpUploadLoginDelay(map1.get(typ).getFtpUploadLoginDelay());
							finalScore.setFtpUploadHeadbyteDelay(map1.get(typ).getFtpUploadHeadbyteDelay());
							finalScore.setFtpUploadUploadRate(map1.get(typ).getFtpUploadUploadRate());
							finalScore.setFtpUploadFail(map1.get(typ).getFtpUploadFail());
							finalScore.setFtpUploadTotal(map1.get(typ).getFtpUploadTotal());
						}else{}
						finalScore.setScore(finalScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
						finalScore.setBase(finalScore.getBase()+map1.get(typ).getBase());
						i++;
					}
					finalScore.setScore(finalScore.getScore()/finalScore.getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		}catch (IOException e){}
		return connectionScore;
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourWebDownloadDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourWebDownloadEntity recordHourWebDownload){
		recordHourWebDownloadDao.save(recordHourWebDownload);
	}
	
	@Override
	public void update(RecordHourWebDownloadEntity recordHourWebDownload){
		recordHourWebDownloadDao.update(recordHourWebDownload);
	}
	
	@Override
	public void delete(Integer id){
		recordHourWebDownloadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourWebDownloadDao.deleteBatch(ids);
	}

}
