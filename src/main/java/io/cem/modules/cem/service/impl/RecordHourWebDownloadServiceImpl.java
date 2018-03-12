package io.cem.modules.cem.service.impl;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordWebDownloadDao;
import io.cem.modules.cem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
	public List<RecordHourWebDownloadEntity> queryWebDownload(Map<String, Object> map){
		return recordWebDownloadDao.queryWebDownload(map);
	}


	@Override
	public List<RecordHourWebDownloadEntity> queryWebDownloadList(Map<String, Object> map){
		return recordHourWebDownloadDao.queryWebDownloadList(map);
	}

	@Override
	public List<RecordHourWebDownloadEntity> queryDayList(Map<String, Object> map){
		return recordHourWebDownloadDao.queryDayList(map);
	}

	@Override
	public List<ScoreEntity> calculateWebDownload(List<RecordHourWebDownloadEntity> webDownloadList){
		List<ScoreEntity> webDownload = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for(int i=0;i<webDownloadList.size();i++) {
				double score = 0;
				//dns_delay 100
				if ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD12"))) <= 0) {
					score = 100 * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 80-100
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD12"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD13"))) <= 0)) {
					score = (80 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD13")))) * 20) / ((Double.parseDouble(pros.getValue("webD12")) - (Double.parseDouble(pros.getValue("webD13"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 60-80
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD13"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD14"))) <= 0)) {
					score = (60 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD14")))) * 20) / ((Double.parseDouble(pros.getValue("webD13")) - (Double.parseDouble(pros.getValue("webD14"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 40-60
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD14"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD15"))) <= 0)) {
					score = (40 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD15")))) * 20) / ((Double.parseDouble(pros.getValue("webD14")) - (Double.parseDouble(pros.getValue("webD15"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 20-40
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD15"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD16"))) <= 0)) {
					score = (20 + ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD16")))) * 20) / ((Double.parseDouble(pros.getValue("webD15")) - (Double.parseDouble(pros.getValue("webD16"))))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 0-20
				else if (((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD16"))) > 0) && ((webDownloadList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("webD17"))) <= 0)) {
					score = ((((webDownloadList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD17")))) * 20) / ((Double.parseDouble(pros.getValue("webD16")) - (Double.parseDouble(pros.getValue("webD17")))))) * (Double.parseDouble(pros.getValue("webD11")));
				}
				//dns_delay 0
				else {
					score = 0;
				}

				//conn_delay 100
				if ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD22"))) <= 0) {
					score = 100 * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 80-100
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD22"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD23"))) <= 0)) {
					score = (80 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD23")))) * 20) / ((Double.parseDouble(pros.getValue("webD22")) - (Double.parseDouble(pros.getValue("webD23"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 60-80
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD23"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD24"))) <= 0)) {
					score = (60 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD24")))) * 20) / ((Double.parseDouble(pros.getValue("webD23")) - (Double.parseDouble(pros.getValue("webD24"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 40-60
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD24"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD25"))) <= 0)) {
					score = (40 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD25")))) * 20) / ((Double.parseDouble(pros.getValue("webD24")) - (Double.parseDouble(pros.getValue("webD25"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 20-40
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD25"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD26"))) <= 0)) {
					score = (20 + ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD26")))) * 20) / ((Double.parseDouble(pros.getValue("webD25")) - (Double.parseDouble(pros.getValue("webD26"))))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 0-20
				else if (((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD26"))) > 0) && ((webDownloadList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("webD27"))) <= 0)) {
					score = ((((webDownloadList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD27")))) * 20) / ((Double.parseDouble(pros.getValue("webD26")) - (Double.parseDouble(pros.getValue("webD27")))))) * (Double.parseDouble(pros.getValue("webD21")));
				}
				//conn_delay 0
				else {
					score = 0;
				}

				//head_byty_delay 100
				if ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD32"))) <= 0) {
					score = 100 * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 80-100
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD32"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD33"))) <= 0)) {
					score = (80 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD33")))) * 20) / ((Double.parseDouble(pros.getValue("webD32")) - (Double.parseDouble(pros.getValue("webD33"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 60-80
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD33"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD34"))) <= 0)) {
					score = (60 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD34")))) * 20) / ((Double.parseDouble(pros.getValue("webD33")) - (Double.parseDouble(pros.getValue("webD34"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 40-60
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD34"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD35"))) <= 0)) {
					score = (40 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD35")))) * 20) / ((Double.parseDouble(pros.getValue("webD34")) - (Double.parseDouble(pros.getValue("webD35"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 20-40
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD35"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD36"))) <= 0)) {
					score = (20 + ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD36")))) * 20) / ((Double.parseDouble(pros.getValue("webD35")) - (Double.parseDouble(pros.getValue("webD36"))))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 0-20
				else if (((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD36"))) > 0) && ((webDownloadList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webD37"))) <= 0)) {
					score = ((((webDownloadList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webD37")))) * 20) / ((Double.parseDouble(pros.getValue("webD36")) - (Double.parseDouble(pros.getValue("webD37")))))) * (Double.parseDouble(pros.getValue("webD31")));
				}
				//head_byty_delay 0
				else {
					score = 0;
				}

				//download_rate 100
				if ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD42"))) <= 0) {
					score = 100 * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 80-100
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD42"))) > 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD43"))) <= 0)) {
					score = (80 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD43")))) * 20) / ((Double.parseDouble(pros.getValue("webD42")) - (Double.parseDouble(pros.getValue("webD43"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 60-80
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD43"))) > 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD44"))) <= 0)) {
					score = (60 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD44")))) * 20) / ((Double.parseDouble(pros.getValue("webD43")) - (Double.parseDouble(pros.getValue("webD44"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 40-60
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD44"))) > 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD45"))) <= 0)) {
					score = (40 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD45")))) * 20) / ((Double.parseDouble(pros.getValue("webD44")) - (Double.parseDouble(pros.getValue("webD45"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 20-40
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD45"))) > 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD46"))) <= 0)) {
					score = (20 + ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD46")))) * 20) / ((Double.parseDouble(pros.getValue("webD45")) - (Double.parseDouble(pros.getValue("webD46"))))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 0-20
				else if (((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD46"))) > 0) && ((webDownloadList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webD47"))) <= 0)) {
					score = ((((webDownloadList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webD47")))) * 20) / ((Double.parseDouble(pros.getValue("webD46")) - (Double.parseDouble(pros.getValue("webD47")))))) * (Double.parseDouble(pros.getValue("webD41")));
				}
				//head_byty_delay 0
				else {
					score = 0;
				}
				System.out.println("Web Download:" + score);

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
				WEBDL.setFail(webDownloadList.get(i).getFail());
				WEBDL.setTotal(webDownloadList.get(i).getTotal());
				WEBDL.setScore(score);
				WEBDL.setBase(Double.parseDouble(pros.getValue("web_download")));

				webDownload.add(WEBDL);
			}
		}catch (IOException e){}
		
		return webDownload;
	} 
	
	@Override
	public List<ScoreEntity> calculateFtpDownload(List<RecordHourFtpEntity> ftpList){
		List<ScoreEntity> ftpDownload = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for(int i=0;i<ftpList.size();i++){
				double score=0;
				//ftp download
				if(ftpList.get(i).getServiceType()==31){
					//dns_delay 100
					if ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD12"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 80-100
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD12"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD13"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD13")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD12")) - (Double.parseDouble(pros.getValue("ftpD13"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 60-80
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD13"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD14"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD14")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD13")) - (Double.parseDouble(pros.getValue("ftpD14"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 40-60
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD14"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD15"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD15")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD14")) - (Double.parseDouble(pros.getValue("ftpD15"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 20-40
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD15"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD16"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD16")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD15")) - (Double.parseDouble(pros.getValue("ftpD16"))))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 0-20
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD16"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD17"))) <= 0)) {
						score = ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD17")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD16")) - (Double.parseDouble(pros.getValue("ftpD17")))))) * (Double.parseDouble(pros.getValue("ftpD11")));
					}
					//dns_delay 0
					else {
						score = 0;
					}

					//conn_delay 100
					if ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD22")) - (Double.parseDouble(pros.getValue("ftpD23"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD23")) - (Double.parseDouble(pros.getValue("ftpD24"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD24")) - (Double.parseDouble(pros.getValue("ftpD25"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD25")) - (Double.parseDouble(pros.getValue("ftpD26"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD27"))) <= 0)) {
						score = ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD26")) - (Double.parseDouble(pros.getValue("ftpD27")))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0
					else {
						score = 0;
					}

					//login_delay 100
					if ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD22"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD22")) - (Double.parseDouble(pros.getValue("ftpD23"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD23"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD23")) - (Double.parseDouble(pros.getValue("ftpD24"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD24"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD24")) - (Double.parseDouble(pros.getValue("ftpD25"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD25"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD25")) - (Double.parseDouble(pros.getValue("ftpD26"))))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD26"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD27"))) <= 0)) {
						score = ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD26")) - (Double.parseDouble(pros.getValue("ftpD27")))))) * (Double.parseDouble(pros.getValue("ftpD21")));
					}
					//conn_delay 0
					else {
						score = 0;
					}

					//head_byty_delay 100
					if ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD42"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 80-100
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD42"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD43"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD43")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD42")) - (Double.parseDouble(pros.getValue("ftpD43"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 60-80
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD43"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD44"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD44")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD43")) - (Double.parseDouble(pros.getValue("ftpD44"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 40-60
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD44"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD45"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD45")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD44")) - (Double.parseDouble(pros.getValue("ftpD45"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 20-40
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD45"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD46"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD46")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD45")) - (Double.parseDouble(pros.getValue("ftpD46"))))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 0-20
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD46"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpD47"))) <= 0)) {
						score = ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD47")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD46")) - (Double.parseDouble(pros.getValue("ftpD47")))))) * (Double.parseDouble(pros.getValue("ftpD41")));
					}
					//head_byty_delay 0
					else {
						score = 0;
					}

					//download_rate 100
					if ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD52"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 80-100
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD52"))) > 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD53"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD53")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD52")) - (Double.parseDouble(pros.getValue("ftpD53"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 60-80
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD53"))) > 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD54"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD54")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD53")) - (Double.parseDouble(pros.getValue("ftpD54"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 40-60
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD54"))) > 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD55"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD55")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD54")) - (Double.parseDouble(pros.getValue("ftpD55"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 20-40
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD55"))) > 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD56"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD56")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD55")) - (Double.parseDouble(pros.getValue("ftpD56"))))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 0-20
					else if (((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD56"))) > 0) && ((ftpList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("ftpD57"))) <= 0)) {
						score = ((((ftpList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpD57")))) * 20) / ((Double.parseDouble(pros.getValue("ftpD56")) - (Double.parseDouble(pros.getValue("ftpD57")))))) * (Double.parseDouble(pros.getValue("ftpD51")));
					}
					//download_rate 0
					else {
						score = 0;
					}
					System.out.println("FTP download:"+score);

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
					FTPD.setFail(ftpList.get(i).getFail());
					FTPD.setTotal(ftpList.get(i).getTotal());
					FTPD.setScore(score);
					FTPD.setBase(Double.parseDouble(pros.getValue("ftp_download")));

					ftpDownload.add(FTPD);

				}else{}
			}
		}catch(IOException e){}
		return ftpDownload;
	}

	@Override
	public List<ScoreEntity> calculateFtpUpload(List<RecordHourFtpEntity> ftpList){
		List<ScoreEntity> ftpUpload = new ArrayList<>();
		try{
			PropertiesUtils pros = new PropertiesUtils();
			for(int i=0;i<ftpList.size();i++) {
				double score = 0;
				if(ftpList.get(i).getServiceType()==32){
					//dns_delay 100
					if ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU12"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 80-100
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU12"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU13"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU13")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU12")) - (Double.parseDouble(pros.getValue("ftpU13"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 60-80
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU13"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU14"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU14")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU13")) - (Double.parseDouble(pros.getValue("ftpU14"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 40-60
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU14"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU15"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU15")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU14")) - (Double.parseDouble(pros.getValue("ftpU15"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 20-40
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU15"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU16"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU16")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU15")) - (Double.parseDouble(pros.getValue("ftpU16"))))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 0-20
					else if (((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU16"))) > 0) && ((ftpList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU17"))) <= 0)) {
						score = ((((ftpList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU17")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU16")) - (Double.parseDouble(pros.getValue("ftpU17")))))) * (Double.parseDouble(pros.getValue("ftpU11")));
					}
					//dns_delay 0
					else {
						score = 0;
					}

					//conn_delay 100
					if ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU22")) - (Double.parseDouble(pros.getValue("ftpU23"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU23")) - (Double.parseDouble(pros.getValue("ftpU24"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU24")) - (Double.parseDouble(pros.getValue("ftpU25"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU25")) - (Double.parseDouble(pros.getValue("ftpU26"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) > 0) && ((ftpList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU27"))) <= 0)) {
						score = ((((ftpList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU26")) - (Double.parseDouble(pros.getValue("ftpU27")))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0
					else {
						score = 0;
					}

					//login_delay 100
					if ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 80-100
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU22"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU23")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU22")) - (Double.parseDouble(pros.getValue("ftpU23"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 60-80
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU23"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU24")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU23")) - (Double.parseDouble(pros.getValue("ftpU24"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 40-60
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU24"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU25")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU24")) - (Double.parseDouble(pros.getValue("ftpU25"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 20-40
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU25"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU26")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU25")) - (Double.parseDouble(pros.getValue("ftpU26"))))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0-20
					else if (((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU26"))) > 0) && ((ftpList.get(i).getLoginDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU27"))) <= 0)) {
						score = ((((ftpList.get(i).getLoginDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU27")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU26")) - (Double.parseDouble(pros.getValue("ftpU27")))))) * (Double.parseDouble(pros.getValue("ftpU21")));
					}
					//conn_delay 0
					else {
						score = 0;
					}

					//head_byty_delay 100
					if ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU42"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 80-100
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU42"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU43"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU43")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU42")) - (Double.parseDouble(pros.getValue("ftpU43"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 60-80
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU43"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU44"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU44")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU43")) - (Double.parseDouble(pros.getValue("ftpU44"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 40-60
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU44"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU45"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU45")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU44")) - (Double.parseDouble(pros.getValue("ftpU45"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 20-40
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU45"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU46"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU46")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU45")) - (Double.parseDouble(pros.getValue("ftpU46"))))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 0-20
					else if (((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU46"))) > 0) && ((ftpList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("ftpU47"))) <= 0)) {
						score = ((((ftpList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU47")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU46")) - (Double.parseDouble(pros.getValue("ftpU47")))))) * (Double.parseDouble(pros.getValue("ftpU41")));
					}
					//head_byty_delay 0
					else {
						score = 0;
					}

					//download_rate 100
					if ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU52"))) <= 0) {
						score = 100 * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 80-100
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU52"))) > 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU53"))) <= 0)) {
						score = (80 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU53")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU52")) - (Double.parseDouble(pros.getValue("ftpU53"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 60-80
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU53"))) > 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU54"))) <= 0)) {
						score = (60 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU54")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU53")) - (Double.parseDouble(pros.getValue("ftpU54"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 40-60
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU54"))) > 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU55"))) <= 0)) {
						score = (40 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU55")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU54")) - (Double.parseDouble(pros.getValue("ftpU55"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 20-40
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU55"))) > 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU56"))) <= 0)) {
						score = (20 + ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU56")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU55")) - (Double.parseDouble(pros.getValue("ftpU56"))))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 0-20
					else if (((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU56"))) > 0) && ((ftpList.get(i).getUploadRate()).compareTo(Double.parseDouble(pros.getValue("ftpU57"))) <= 0)) {
						score = ((((ftpList.get(i).getUploadRate().doubleValue()) - (Double.parseDouble(pros.getValue("ftpU57")))) * 20) / ((Double.parseDouble(pros.getValue("ftpU56")) - (Double.parseDouble(pros.getValue("ftpU57")))))) * (Double.parseDouble(pros.getValue("ftpU51")));
					}
					//download_rate 0
					else {
						score = 0;
					}
					System.out.println("FTP upload:"+score);

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
					FTPU.setFail(ftpList.get(i).getFail());
					FTPU.setTotal(ftpList.get(i).getTotal());
					FTPU.setScore(score);
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
			Map<ScoreTargetEntity, ScoreBaseEntity> connection = new HashMap<>();
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
				scoreTarget.setFail(webDownload.get(i).getFail());
				scoreTarget.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((webDownload.get(i).getScore()) * (webDownload.get(i).getBase()));
				scoreBase.setBase(webDownload.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMap(ftpDownload,connection);
			connection=pingService.putMap(ftpUpload,connection);

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
					finalScore.setServiceType(3);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setScore(((connection.get(ite).getScore()) / (connection.get(ite).getBase()))*(1-(ite.getFail())/ite.getTotal()));
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
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreDateEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreDateEntity scoreTarget = new ScoreDateEntity();
				scoreTarget.setCityId(webDownload.get(i).getCityId());
				scoreTarget.setCountyId(webDownload.get(i).getCountyId());
				scoreTarget.setProbeId(webDownload.get(i).getProbeId());
				scoreTarget.setTargetId(webDownload.get(i).getTargetId());
				scoreTarget.setCityName(webDownload.get(i).getCityName());
				scoreTarget.setCountyName(webDownload.get(i).getCountyName());
				scoreTarget.setProbeName(webDownload.get(i).getProbeName());
				scoreTarget.setTargetName(webDownload.get(i).getTargetName());
				scoreTarget.setFail(webDownload.get(i).getFail());
				scoreTarget.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((webDownload.get(i).getScore()) * (webDownload.get(i).getBase()));
				scoreBase.setBase(webDownload.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMapDate(ftpDownload,connection);
			connection=pingService.putMapDate(ftpUpload,connection);

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
					finalScore.setScore(((connection.get(ite).getScore()) / (connection.get(ite).getBase()))*(1-(ite.getFail())/ite.getTotal()));
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		} catch (IOException e) {
		}
		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateArea4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload) {
		List<ScoreEntity> connectionScore = new ArrayList<>();
		RecordHourPingServiceImpl pingService = new RecordHourPingServiceImpl();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			Map<ScoreAreaEntity, ScoreBaseEntity> connection = new HashMap<>();
			for (int i = 0; i < webDownload.size(); i++) {
				ScoreAreaEntity scoreTarget = new ScoreAreaEntity();
				scoreTarget.setCityId(webDownload.get(i).getCityId());
				scoreTarget.setCountyId(webDownload.get(i).getCountyId());
				scoreTarget.setProbeId(webDownload.get(i).getProbeId());
				scoreTarget.setTargetId(webDownload.get(i).getTargetId());
				scoreTarget.setCityName(webDownload.get(i).getCityName());
				scoreTarget.setCountyName(webDownload.get(i).getCountyName());
				scoreTarget.setProbeName(webDownload.get(i).getProbeName());
				scoreTarget.setTargetName(webDownload.get(i).getTargetName());
				scoreTarget.setFail(webDownload.get(i).getFail());
				scoreTarget.setTotal(webDownload.get(i).getTotal());
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((webDownload.get(i).getScore()) * (webDownload.get(i).getBase()));
				scoreBase.setBase(webDownload.get(i).getBase());
				connection.put(scoreTarget, scoreBase);
			}
			connection=pingService.putMapArea(ftpDownload,connection);
			connection=pingService.putMapArea(ftpUpload,connection);

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
					finalScore.setScore(((connection.get(ite).getScore()) / (connection.get(ite).getBase()))*(1-(ite.getFail())/ite.getTotal()));
					finalScore.setBase(Double.parseDouble(pros.getValue("downloadweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {}
				id++;
			}
		} catch (IOException e) {
		}
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
