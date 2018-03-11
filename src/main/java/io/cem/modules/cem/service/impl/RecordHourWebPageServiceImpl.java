package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordWebPageDao;
import io.cem.modules.cem.entity.ScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourWebPageDao;
import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.service.RecordHourWebPageService;



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
	public List<RecordHourWebPageEntity> queryWebList(Map<String, Object> map){
		return recordHourWebPageDao.queryWebList(map);
	}

	@Override
	public List<RecordHourWebPageEntity> queryDayList(Map<String, Object> map){
		return recordHourWebPageDao.queryDayList(map);
	}
	
	@Override
	public List<ScoreEntity> calculateService3(List<RecordHourWebPageEntity> webPageList){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			
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
				if ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP32"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 80-100
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP32"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP33"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP33")))) * 20) / ((Double.parseDouble(pros.getValue("webP32")) - (Double.parseDouble(pros.getValue("webP33"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 60-80
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP33"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP34"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP34")))) * 20) / ((Double.parseDouble(pros.getValue("webP33")) - (Double.parseDouble(pros.getValue("webP34"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 40-60
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP34"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP35"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP35")))) * 20) / ((Double.parseDouble(pros.getValue("webP34")) - (Double.parseDouble(pros.getValue("webP35"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 20-40
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP35"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP36"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP36")))) * 20) / ((Double.parseDouble(pros.getValue("webP35")) - (Double.parseDouble(pros.getValue("webP36"))))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 0-20
				else if (((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP36"))) > 0) && ((webPageList.get(i).getHeadbyteDelay()).compareTo(Double.parseDouble(pros.getValue("webP37"))) <= 0)) {
					score += ((((webPageList.get(i).getHeadbyteDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP37")))) * 20) / ((Double.parseDouble(pros.getValue("webP36")) - (Double.parseDouble(pros.getValue("webP37")))))) * (Double.parseDouble(pros.getValue("webP31")));
				}
				//head_byty_delay 0
				else {
					score += 0;
				}

				//page_file_delay 100
				if ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 80-100
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP42"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP43"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP43")))) * 20) / ((Double.parseDouble(pros.getValue("webP42")) - (Double.parseDouble(pros.getValue("webP43"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 60-80
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP43"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP44"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP44")))) * 20) / ((Double.parseDouble(pros.getValue("webP43")) - (Double.parseDouble(pros.getValue("webP44"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 40-60
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP44"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP45"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP45")))) * 20) / ((Double.parseDouble(pros.getValue("webP44")) - (Double.parseDouble(pros.getValue("webP45"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 20-40
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP45"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP46"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP46")))) * 20) / ((Double.parseDouble(pros.getValue("webP45")) - (Double.parseDouble(pros.getValue("webP46"))))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 0-20
				else if (((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP46"))) > 0) && ((webPageList.get(i).getPageFileDelay()).compareTo(Double.parseDouble(pros.getValue("webP47"))) <= 0)) {
					score += ((((webPageList.get(i).getPageFileDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP47")))) * 20) / ((Double.parseDouble(pros.getValue("webP46")) - (Double.parseDouble(pros.getValue("webP47")))))) * (Double.parseDouble(pros.getValue("webP41")));
				}
				//page_file_delay 0
				else {
					score += 0;
				}

				//redirect_delay 100
				if ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP52"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 80-100
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP52"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP53"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP53")))) * 20) / ((Double.parseDouble(pros.getValue("webP52")) - (Double.parseDouble(pros.getValue("webP53"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 60-80
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP53"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP54"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP54")))) * 20) / ((Double.parseDouble(pros.getValue("webP53")) - (Double.parseDouble(pros.getValue("webP54"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 40-60
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP54"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP55"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP55")))) * 20) / ((Double.parseDouble(pros.getValue("webP54")) - (Double.parseDouble(pros.getValue("webP55"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 20-40
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP55"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP56"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP56")))) * 20) / ((Double.parseDouble(pros.getValue("webP55")) - (Double.parseDouble(pros.getValue("webP56"))))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 0-20
				else if (((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP56"))) > 0) && ((webPageList.get(i).getRedirectDelay()).compareTo(Double.parseDouble(pros.getValue("webP57"))) <= 0)) {
					score += ((((webPageList.get(i).getRedirectDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP57")))) * 20) / ((Double.parseDouble(pros.getValue("webP56")) - (Double.parseDouble(pros.getValue("webP57")))))) * (Double.parseDouble(pros.getValue("webP51")));
				}
				//redirect_delay 0
				else {
					score += 0;
				}

				//above_fold_delay 100
				if ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP62"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 80-100
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP62"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP63"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP63")))) * 20) / ((Double.parseDouble(pros.getValue("webP62")) - (Double.parseDouble(pros.getValue("webP63"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 60-80
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP63"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP64"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP64")))) * 20) / ((Double.parseDouble(pros.getValue("webP63")) - (Double.parseDouble(pros.getValue("webP64"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 40-60
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP64"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP65"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP65")))) * 20) / ((Double.parseDouble(pros.getValue("webP64")) - (Double.parseDouble(pros.getValue("webP65"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 20-40
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP65"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP66"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP66")))) * 20) / ((Double.parseDouble(pros.getValue("webP65")) - (Double.parseDouble(pros.getValue("webP66"))))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 0-20
				else if (((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP66"))) > 0) && ((webPageList.get(i).getAboveFoldDelay()).compareTo(Double.parseDouble(pros.getValue("webP67"))) <= 0)) {
					score += ((((webPageList.get(i).getAboveFoldDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP67")))) * 20) / ((Double.parseDouble(pros.getValue("webP66")) - (Double.parseDouble(pros.getValue("webP67")))))) * (Double.parseDouble(pros.getValue("webP61")));
				}
				//above_fold_delay 0
				else {
					score += 0;
				}

				//page_element_delay 100
				if ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP72"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 80-100
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP72"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP73"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP73")))) * 20) / ((Double.parseDouble(pros.getValue("webP72")) - (Double.parseDouble(pros.getValue("webP73"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 60-80
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP73"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP74"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP74")))) * 20) / ((Double.parseDouble(pros.getValue("webP73")) - (Double.parseDouble(pros.getValue("webP74"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 40-60
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP74"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP75"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP75")))) * 20) / ((Double.parseDouble(pros.getValue("webP74")) - (Double.parseDouble(pros.getValue("webP75"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 20-40
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP75"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP76"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP76")))) * 20) / ((Double.parseDouble(pros.getValue("webP75")) - (Double.parseDouble(pros.getValue("webP76"))))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 0-20
				else if (((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP76"))) > 0) && ((webPageList.get(i).getPageElementDelay()).compareTo(Double.parseDouble(pros.getValue("webP77"))) <= 0)) {
					score += ((((webPageList.get(i).getPageElementDelay().doubleValue()) - (Double.parseDouble(pros.getValue("webP77")))) * 20) / ((Double.parseDouble(pros.getValue("webP76")) - (Double.parseDouble(pros.getValue("webP77")))))) * (Double.parseDouble(pros.getValue("webP71")));
				}
				//page_element_delay 0
				else {
					score += 0;
				}

				//download_rate 100
				if ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP82"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 80-100
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP82"))) > 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP83"))) <= 0)) {
					score += (80 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP83")))) * 20) / ((Double.parseDouble(pros.getValue("webP82")) - (Double.parseDouble(pros.getValue("webP83"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 60-80
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP83"))) > 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP84"))) <= 0)) {
					score += (60 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP84")))) * 20) / ((Double.parseDouble(pros.getValue("webP83")) - (Double.parseDouble(pros.getValue("webP84"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 40-60
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP84"))) > 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP85"))) <= 0)) {
					score += (40 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP85")))) * 20) / ((Double.parseDouble(pros.getValue("webP84")) - (Double.parseDouble(pros.getValue("webP85"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 20-40
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP85"))) > 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP86"))) <= 0)) {
					score += (20 + ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP86")))) * 20) / ((Double.parseDouble(pros.getValue("webP85")) - (Double.parseDouble(pros.getValue("webP86"))))))) * (Double.parseDouble(pros.getValue("webP81")));
				}
				//page_element_delay 0-20
				else if (((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP86"))) > 0) && ((webPageList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("webP87"))) <= 0)) {
					score += ((((webPageList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("webP87")))) * 20) / ((Double.parseDouble(pros.getValue("webP86")) - (Double.parseDouble(pros.getValue("webP87")))))) * (Double.parseDouble(pros.getValue("webP81")));
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
				finalScore.setServiceType(4);
				finalScore.setTargetId(webPageList.get(i).getTargetId());
				finalScore.setTargetName(webPageList.get(i).getTargetName());
				finalScore.setRecordTime(webPageList.get(i).getRecordTime());
				finalScore.setRecordDate(webPageList.get(i).getRecordDate());
				finalScore.setFail(webPageList.get(i).getFail());
				finalScore.setTotal(webPageList.get(i).getTotal());
				finalScore.setScore(score*(webPageList.get(i).getFail()/webPageList.get(i).getTotal()));
				finalScore.setBase(Double.parseDouble(pros.getValue("browseweight")));
				connectionScore.add(finalScore);

				
				
			}
		
		}catch (IOException e){}
		
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
