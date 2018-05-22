package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordHourWebVideoDao;
import io.cem.modules.cem.dao.RecordWebVideoDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordHourWebVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;



@Service("recordHourWebVideoService")
public class RecordHourWebVideoServiceImpl implements RecordHourWebVideoService {
	@Autowired
	private RecordHourWebVideoDao recordHourWebVideoDao;
	@Autowired
	private RecordWebVideoDao recordWebVideoDao;
	
	@Override
	public RecordHourWebVideoEntity queryObject(Integer id){
		return recordHourWebVideoDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourWebVideoEntity> queryList(Map<String, Object> map){
		return recordHourWebVideoDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryDayExitList(map));
	}

	@Override
	public List<RecordHourWebVideoEntity> queryWebVideo(Map<String, Object> map){
		return recordWebVideoDao.queryWebVideo(map);
	}


	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryVideoList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryVideoList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryVideoAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryVideoAreaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryVideoTargetList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryVideoTargetList(map));
	}



	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryVideoRankList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourWebVideoDao.queryVideoRankList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryDayList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryDayAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryDayAreaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryDayRankList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryDayRankList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourWebVideoEntity>> queryDayTargetList(Map<String, Object> map){
		return new AsyncResult<> (recordHourWebVideoDao.queryDayTargetList(map));
	}



	@Override
	public List<ScoreEntity> calculateService5 (List<RecordHourWebVideoEntity> videoList){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			for(int i=0;i<videoList.size();i++){
				double score = 0;
				//dns_delay 100
				if ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 80-100
				else if (((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video12"))) > 0) && ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video13"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video13")))) * 20) / ((Double.parseDouble(pros.getValue("video12")) - (Double.parseDouble(pros.getValue("video13"))))))) * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 60-80
				else if (((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video13"))) > 0) && ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video14"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video14")))) * 20) / ((Double.parseDouble(pros.getValue("video13")) - (Double.parseDouble(pros.getValue("video14"))))))) * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 40-60
				else if (((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video14"))) > 0) && ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video15"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video15")))) * 20) / ((Double.parseDouble(pros.getValue("video14")) - (Double.parseDouble(pros.getValue("video15"))))))) * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 20-40
				else if (((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video15"))) > 0) && ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video16"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video16")))) * 20) / ((Double.parseDouble(pros.getValue("video15")) - (Double.parseDouble(pros.getValue("video16"))))))) * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 0-20
				else if (((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video16"))) > 0) && ((videoList.get(i).getDnsDelay()).compareTo(Double.parseDouble(pros.getValue("video17"))) <= 0)) {
					score += ((((videoList.get(i).getDnsDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video17")))) * 20) / ((Double.parseDouble(pros.getValue("video16")) - (Double.parseDouble(pros.getValue("video17")))))) * (Double.parseDouble(pros.getValue("video11")));
				}
				//dns_delay 0
				else {
					score += 0;
				}

				//ws_cnn_delay 100
				if ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 80-100
				else if (((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video22"))) > 0) && ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video23"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getWsConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video23")))) * 20) / ((Double.parseDouble(pros.getValue("video22")) - (Double.parseDouble(pros.getValue("video23"))))))) * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 60-80
				else if (((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video23"))) > 0) && ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video24"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getWsConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video24")))) * 20) / ((Double.parseDouble(pros.getValue("video23")) - (Double.parseDouble(pros.getValue("video24"))))))) * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 40-60
				else if (((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video24"))) > 0) && ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video25"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getWsConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video25")))) * 20) / ((Double.parseDouble(pros.getValue("video24")) - (Double.parseDouble(pros.getValue("video25"))))))) * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 20-40
				else if (((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video25"))) > 0) && ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video26"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getWsConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video26")))) * 20) / ((Double.parseDouble(pros.getValue("video25")) - (Double.parseDouble(pros.getValue("video26"))))))) * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 0-20
				else if (((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video26"))) > 0) && ((videoList.get(i).getWsConnDelay()).compareTo(Double.parseDouble(pros.getValue("video27"))) <= 0)) {
					score += ((((videoList.get(i).getWsConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video27")))) * 20) / ((Double.parseDouble(pros.getValue("video26")) - (Double.parseDouble(pros.getValue("video27")))))) * (Double.parseDouble(pros.getValue("video21")));
				}
				//ws_cnn_delay 0
				else {
					score += 0;
				}

				//web_page_delay 100
				if ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video32"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 80-100
				else if (((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video32"))) > 0) && ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video33"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getWebPageDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video33")))) * 20) / ((Double.parseDouble(pros.getValue("video32")) - (Double.parseDouble(pros.getValue("video33"))))))) * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 60-80
				else if (((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video33"))) > 0) && ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video34"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getWebPageDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video34")))) * 20) / ((Double.parseDouble(pros.getValue("video33")) - (Double.parseDouble(pros.getValue("video34"))))))) * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 40-60
				else if (((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video34"))) > 0) && ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video35"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getWebPageDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video35")))) * 20) / ((Double.parseDouble(pros.getValue("video34")) - (Double.parseDouble(pros.getValue("video35"))))))) * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 20-40
				else if (((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video35"))) > 0) && ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video36"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getWebPageDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video36")))) * 20) / ((Double.parseDouble(pros.getValue("video35")) - (Double.parseDouble(pros.getValue("video36"))))))) * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 0-20
				else if (((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video36"))) > 0) && ((videoList.get(i).getWebPageDelay()).compareTo(Double.parseDouble(pros.getValue("video37"))) <= 0)) {
					score += ((((videoList.get(i).getWebPageDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video37")))) * 20) / ((Double.parseDouble(pros.getValue("video36")) - (Double.parseDouble(pros.getValue("video37")))))) * (Double.parseDouble(pros.getValue("video31")));
				}
				//web_page_delay 0
				else {
					score += 0;
				}

				//buffer_time 100
				if ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 80-100
				else if (((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video42"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video43"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Double.parseDouble(pros.getValue("video43")))) * 20) / ((Double.parseDouble(pros.getValue("video42")) - (Double.parseDouble(pros.getValue("video43"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 60-80
				else if (((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video43"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video44"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Double.parseDouble(pros.getValue("video44")))) * 20) / ((Double.parseDouble(pros.getValue("video43")) - (Double.parseDouble(pros.getValue("video44"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 40-60
				else if (((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video44"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video45"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Double.parseDouble(pros.getValue("video45")))) * 20) / ((Double.parseDouble(pros.getValue("video44")) - (Double.parseDouble(pros.getValue("video45"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 20-40
				else if (((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video45"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video46"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Double.parseDouble(pros.getValue("video46")))) * 20) / ((Double.parseDouble(pros.getValue("video45")) - (Double.parseDouble(pros.getValue("video46"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 0-20
				else if (((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video46"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Double.parseDouble(pros.getValue("video47"))) <= 0)) {
					score += ((((videoList.get(i).getBufferTime().doubleValue()) - (Double.parseDouble(pros.getValue("video47")))) * 20) / ((Double.parseDouble(pros.getValue("video46")) - (Double.parseDouble(pros.getValue("video47")))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 0
				else {
					score += 0;
				}

				//head_frame_delay 100
				if ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video52"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 80-100
				else if (((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video52"))) > 0) && ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video53"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getHeadFrameDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video53")))) * 20) / ((Double.parseDouble(pros.getValue("video52")) - (Double.parseDouble(pros.getValue("video53"))))))) * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 60-80
				else if (((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video53"))) > 0) && ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video54"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getHeadFrameDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video54")))) * 20) / ((Double.parseDouble(pros.getValue("video53")) - (Double.parseDouble(pros.getValue("video54"))))))) * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 40-60
				else if (((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video54"))) > 0) && ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video55"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getHeadFrameDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video55")))) * 20) / ((Double.parseDouble(pros.getValue("video54")) - (Double.parseDouble(pros.getValue("video55"))))))) * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 20-40
				else if (((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video55"))) > 0) && ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video56"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getHeadFrameDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video56")))) * 20) / ((Double.parseDouble(pros.getValue("video55")) - (Double.parseDouble(pros.getValue("video56"))))))) * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 0-20
				else if (((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video56"))) > 0) && ((videoList.get(i).getHeadFrameDelay()).compareTo(Double.parseDouble(pros.getValue("video57"))) <= 0)) {
					score += ((((videoList.get(i).getHeadFrameDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video57")))) * 20) / ((Double.parseDouble(pros.getValue("video56")) - (Double.parseDouble(pros.getValue("video57")))))) * (Double.parseDouble(pros.getValue("video51")));
				}
				//head_frame_delay 0
				else {
					score += 0;
				}

				//init_buffer_delay 100
				if ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video62"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 80-100
				else if (((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video62"))) > 0) && ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video63"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getInitBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video63")))) * 20) / ((Double.parseDouble(pros.getValue("video62")) - (Double.parseDouble(pros.getValue("video63"))))))) * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 60-80
				else if (((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video63"))) > 0) && ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video64"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getInitBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video64")))) * 20) / ((Double.parseDouble(pros.getValue("video63")) - (Double.parseDouble(pros.getValue("video64"))))))) * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 40-60
				else if (((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video64"))) > 0) && ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video65"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getInitBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video65")))) * 20) / ((Double.parseDouble(pros.getValue("video64")) - (Double.parseDouble(pros.getValue("video65"))))))) * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 20-40
				else if (((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video65"))) > 0) && ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video66"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getInitBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video66")))) * 20) / ((Double.parseDouble(pros.getValue("video65")) - (Double.parseDouble(pros.getValue("video66"))))))) * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 0-20
				else if (((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video66"))) > 0) && ((videoList.get(i).getInitBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video67"))) <= 0)) {
					score += ((((videoList.get(i).getInitBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video67")))) * 20) / ((Double.parseDouble(pros.getValue("video66")) - (Double.parseDouble(pros.getValue("video67")))))) * (Double.parseDouble(pros.getValue("video61")));
				}
				//init_buffer_delay 0
				else {
					score += 0;
				}

				//load_delay 100
				if ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video72"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 80-100
				else if (((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video72"))) > 0) && ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video73"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getLoadDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video73")))) * 20) / ((Double.parseDouble(pros.getValue("video72")) - (Double.parseDouble(pros.getValue("video73"))))))) * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 60-80
				else if (((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video73"))) > 0) && ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video74"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getLoadDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video74")))) * 20) / ((Double.parseDouble(pros.getValue("video73")) - (Double.parseDouble(pros.getValue("video74"))))))) * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 40-60
				else if (((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video74"))) > 0) && ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video75"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getLoadDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video75")))) * 20) / ((Double.parseDouble(pros.getValue("video74")) - (Double.parseDouble(pros.getValue("video75"))))))) * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 20-40
				else if (((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video75"))) > 0) && ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video76"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getLoadDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video76")))) * 20) / ((Double.parseDouble(pros.getValue("video75")) - (Double.parseDouble(pros.getValue("video76"))))))) * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 0-20
				else if (((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video76"))) > 0) && ((videoList.get(i).getLoadDelay()).compareTo(Double.parseDouble(pros.getValue("video77"))) <= 0)) {
					score += ((((videoList.get(i).getLoadDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video77")))) * 20) / ((Double.parseDouble(pros.getValue("video76")) - (Double.parseDouble(pros.getValue("video77")))))) * (Double.parseDouble(pros.getValue("video71")));
				}
				//load_delay 0
				else {
					score += 0;
				}

				//total_buffer_delay 100
				if ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video82"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 80-100
				else if (((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video82"))) > 0) && ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video83"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getTotalBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video83")))) * 20) / ((Double.parseDouble(pros.getValue("video82")) - (Double.parseDouble(pros.getValue("video83"))))))) * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 60-80
				else if (((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video83"))) > 0) && ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video84"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getTotalBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video84")))) * 20) / ((Double.parseDouble(pros.getValue("video83")) - (Double.parseDouble(pros.getValue("video84"))))))) * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 40-60
				else if (((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video84"))) > 0) && ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video85"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getTotalBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video85")))) * 20) / ((Double.parseDouble(pros.getValue("video84")) - (Double.parseDouble(pros.getValue("video85"))))))) * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 20-40
				else if (((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video85"))) > 0) && ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video86"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getTotalBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video86")))) * 20) / ((Double.parseDouble(pros.getValue("video85")) - (Double.parseDouble(pros.getValue("video86"))))))) * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 0-20
				else if (((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video86"))) > 0) && ((videoList.get(i).getTotalBufferDelay()).compareTo(Double.parseDouble(pros.getValue("video87"))) <= 0)) {
					score += ((((videoList.get(i).getTotalBufferDelay().doubleValue()) - (Double.parseDouble(pros.getValue("video87")))) * 20) / ((Double.parseDouble(pros.getValue("video86")) - (Double.parseDouble(pros.getValue("video87")))))) * (Double.parseDouble(pros.getValue("video81")));
				}
				//total_buffer_delay 0
				else {
					score += 0;
				}

				//download_rate 100
				if ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video92"))) >= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 80-100
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video92"))) < 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video93"))) >= 0)) {
					score += (80 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video93")))) * 20) / ((Double.parseDouble(pros.getValue("video92")) - (Double.parseDouble(pros.getValue("video93"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 60-80
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video93"))) < 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video94"))) >= 0)) {
					score += (60 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video94")))) * 20) / ((Double.parseDouble(pros.getValue("video93")) - (Double.parseDouble(pros.getValue("video94"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 40-60
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video94"))) < 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video95"))) >= 0)) {
					score += (40 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video95")))) * 20) / ((Double.parseDouble(pros.getValue("video94")) - (Double.parseDouble(pros.getValue("video95"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 20-40
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video95"))) < 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video96"))) >= 0)) {
					score += (20 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video96")))) * 20) / ((Double.parseDouble(pros.getValue("video95")) - (Double.parseDouble(pros.getValue("video96"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 0-20
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video96"))) < 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video97"))) >= 0)) {
					score += ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video97")))) * 20) / ((Double.parseDouble(pros.getValue("video96")) - (Double.parseDouble(pros.getValue("video97")))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 0
				else {
					score += 0;
				}


				ScoreEntity finalScore = new ScoreEntity();
				finalScore.setId(i+1);
				finalScore.setCityId(videoList.get(i).getCityId());
				finalScore.setCityName(videoList.get(i).getCityName());
				finalScore.setCountyId(videoList.get(i).getCountyId());
				finalScore.setCountyName(videoList.get(i).getAreaName());
				finalScore.setProbeId(videoList.get(i).getProbeId());
				finalScore.setProbeName(videoList.get(i).getProbeName());
				finalScore.setServiceType(5);
				finalScore.setTargetId(videoList.get(i).getTargetId());
				finalScore.setTargetName(videoList.get(i).getTargetName());
				finalScore.setRecordDate(videoList.get(i).getRecordDate());
				finalScore.setRecordTime(videoList.get(i).getRecordTime());
				finalScore.setAccessLayer(videoList.get(i).getAccessLayer());
				finalScore.setPort(videoList.get(i).getPort());
				finalScore.setWebVideoDnsDelay(videoList.get(i).getDnsDelay());
				finalScore.setWebVideoWsConnDelay(videoList.get(i).getWsConnDelay());
				finalScore.setWebVideoWebPageDelay(videoList.get(i).getWebPageDelay());
				finalScore.setWebVideoSsConnDelay(videoList.get(i).getSsConnDelay());
				finalScore.setWebVideoAddressDelay(videoList.get(i).getAddressDelay());
				finalScore.setWebVideoMsConnDelay(videoList.get(i).getMsConnDelay());
				finalScore.setWebVideoHeadFrameDelay(videoList.get(i).getHeadFrameDelay());
				finalScore.setWebVideoInitBufferDelay(videoList.get(i).getInitBufferDelay());
				finalScore.setWebVideoLoadDelay(videoList.get(i).getLoadDelay());
				finalScore.setWebVideoTotalBufferDelay(videoList.get(i).getTotalBufferDelay());
				finalScore.setWebVideoDownloadRate(videoList.get(i).getDownloadRate());
				finalScore.setWebVideoBufferTime(videoList.get(i).getBufferTime());
				finalScore.setFail(videoList.get(i).getFail());
				finalScore.setTotal(videoList.get(i).getTotal());
				double fail = (double) finalScore.getFail()/finalScore.getTotal();
				finalScore.setScore(score*(1-fail));
				finalScore.setBase(Double.parseDouble(pros.getValue("videoweight")));
				connectionScore.add(finalScore);


			}

		}catch (IOException e){}

		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateLayer5(List<ScoreEntity> webPageList){
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
				scoreBase.setWebVideoDnsDelay(webPageList.get(i).getWebVideoDnsDelay());
				scoreBase.setWebVideoWsConnDelay(webPageList.get(i).getWebVideoWsConnDelay());
				scoreBase.setWebVideoWebPageDelay(webPageList.get(i).getWebVideoWebPageDelay());
				scoreBase.setWebVideoSsConnDelay(webPageList.get(i).getWebVideoSsConnDelay());
				scoreBase.setWebVideoAddressDelay(webPageList.get(i).getWebVideoAddressDelay());
				scoreBase.setWebVideoMsConnDelay(webPageList.get(i).getWebVideoMsConnDelay());
				scoreBase.setWebVideoHeadFrameDelay(webPageList.get(i).getWebVideoHeadFrameDelay());
				scoreBase.setWebVideoInitBufferDelay(webPageList.get(i).getWebVideoInitBufferDelay());
				scoreBase.setWebVideoLoadDelay(webPageList.get(i).getWebVideoLoadDelay());
				scoreBase.setWebVideoTotalBufferDelay(webPageList.get(i).getWebVideoTotalBufferDelay());
				scoreBase.setWebVideoDownloadRate(webPageList.get(i).getWebVideoDownloadRate());
				scoreBase.setWebVideoBufferTime(webPageList.get(i).getWebVideoBufferTime());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreLayer)) {

					connection.put(scoreLayer,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreLayer);
					scoreBase.setWebVideoDnsDelay(scoreBase.getWebVideoDnsDelay());
					scoreBase.setWebVideoWsConnDelay(scoreBase.getWebVideoWsConnDelay());
					scoreBase.setWebVideoWebPageDelay(scoreBase.getWebVideoWebPageDelay());
					scoreBase.setWebVideoSsConnDelay(scoreBase.getWebVideoSsConnDelay());
					scoreBase.setWebVideoAddressDelay(scoreBase.getWebVideoAddressDelay());
					scoreBase.setWebVideoMsConnDelay(scoreBase.getWebVideoMsConnDelay());
					scoreBase.setWebVideoHeadFrameDelay(scoreBase.getWebVideoHeadFrameDelay());
					scoreBase.setWebVideoInitBufferDelay(scoreBase.getWebVideoInitBufferDelay());
					scoreBase.setWebVideoLoadDelay(scoreBase.getWebVideoLoadDelay());
					scoreBase.setWebVideoTotalBufferDelay(scoreBase.getWebVideoTotalBufferDelay());
					scoreBase.setWebVideoDownloadRate(scoreBase.getWebVideoDownloadRate());
					scoreBase.setWebVideoBufferTime(scoreBase.getWebVideoBufferTime());
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
					finalScore.setServiceType(5);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setWebVideoDnsDelay(connection.get(ite).getWebVideoDnsDelay());
					finalScore.setWebVideoWsConnDelay(connection.get(ite).getWebVideoWsConnDelay());
					finalScore.setWebVideoWebPageDelay(connection.get(ite).getWebVideoWebPageDelay());
					finalScore.setWebVideoSsConnDelay(connection.get(ite).getWebVideoSsConnDelay());
					finalScore.setWebVideoAddressDelay(connection.get(ite).getWebVideoAddressDelay());
					finalScore.setWebVideoMsConnDelay(connection.get(ite).getWebVideoMsConnDelay());
					finalScore.setWebVideoHeadFrameDelay(connection.get(ite).getWebVideoHeadFrameDelay());
					finalScore.setWebVideoInitBufferDelay(connection.get(ite).getWebVideoInitBufferDelay());
					finalScore.setWebVideoLoadDelay(connection.get(ite).getWebVideoLoadDelay());
					finalScore.setWebVideoTotalBufferDelay(connection.get(ite).getWebVideoTotalBufferDelay());
					finalScore.setWebVideoDownloadRate(connection.get(ite).getWebVideoDownloadRate());
					finalScore.setWebVideoBufferTime(connection.get(ite).getWebVideoBufferTime());
					finalScore.setScore(connection.get(ite).getScore());
					finalScore.setBase(connection.get(ite).getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("videoweight")));
					connectionScore.add(finalScore);
				} catch (IOException e) {
				}
				id++;
			}
		}catch(IOException e){}

		return connectionScore;
	}

	@Override
	public List<ScoreEntity> calculateDate5(List<ScoreEntity> webPageList){
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
				scoreBase.setWebVideoDnsDelay(webPageList.get(i).getWebVideoDnsDelay());
				scoreBase.setWebVideoWsConnDelay(webPageList.get(i).getWebVideoWsConnDelay());
				scoreBase.setWebVideoWebPageDelay(webPageList.get(i).getWebVideoWebPageDelay());
				scoreBase.setWebVideoSsConnDelay(webPageList.get(i).getWebVideoSsConnDelay());
				scoreBase.setWebVideoAddressDelay(webPageList.get(i).getWebVideoAddressDelay());
				scoreBase.setWebVideoMsConnDelay(webPageList.get(i).getWebVideoMsConnDelay());
				scoreBase.setWebVideoHeadFrameDelay(webPageList.get(i).getWebVideoHeadFrameDelay());
				scoreBase.setWebVideoInitBufferDelay(webPageList.get(i).getWebVideoInitBufferDelay());
				scoreBase.setWebVideoLoadDelay(webPageList.get(i).getWebVideoLoadDelay());
				scoreBase.setWebVideoTotalBufferDelay(webPageList.get(i).getWebVideoTotalBufferDelay());
				scoreBase.setWebVideoDownloadRate(webPageList.get(i).getWebVideoDownloadRate());
				scoreBase.setWebVideoBufferTime(webPageList.get(i).getWebVideoBufferTime());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreDate)) {

					connection.put(scoreDate,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreDate);
					scoreBase.setWebVideoDnsDelay(scoreBase.getWebVideoDnsDelay());
					scoreBase.setWebVideoWsConnDelay(scoreBase.getWebVideoWsConnDelay());
					scoreBase.setWebVideoWebPageDelay(scoreBase.getWebVideoWebPageDelay());
					scoreBase.setWebVideoSsConnDelay(scoreBase.getWebVideoSsConnDelay());
					scoreBase.setWebVideoAddressDelay(scoreBase.getWebVideoAddressDelay());
					scoreBase.setWebVideoMsConnDelay(scoreBase.getWebVideoMsConnDelay());
					scoreBase.setWebVideoHeadFrameDelay(scoreBase.getWebVideoHeadFrameDelay());
					scoreBase.setWebVideoInitBufferDelay(scoreBase.getWebVideoInitBufferDelay());
					scoreBase.setWebVideoLoadDelay(scoreBase.getWebVideoLoadDelay());
					scoreBase.setWebVideoTotalBufferDelay(scoreBase.getWebVideoTotalBufferDelay());
					scoreBase.setWebVideoDownloadRate(scoreBase.getWebVideoDownloadRate());
					scoreBase.setWebVideoBufferTime(scoreBase.getWebVideoBufferTime());
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
					finalScore.setServiceType(5);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setWebVideoDnsDelay(connection.get(ite).getWebVideoDnsDelay());
					finalScore.setWebVideoWsConnDelay(connection.get(ite).getWebVideoWsConnDelay());
					finalScore.setWebVideoWebPageDelay(connection.get(ite).getWebVideoWebPageDelay());
					finalScore.setWebVideoSsConnDelay(connection.get(ite).getWebVideoSsConnDelay());
					finalScore.setWebVideoAddressDelay(connection.get(ite).getWebVideoAddressDelay());
					finalScore.setWebVideoMsConnDelay(connection.get(ite).getWebVideoMsConnDelay());
					finalScore.setWebVideoHeadFrameDelay(connection.get(ite).getWebVideoHeadFrameDelay());
					finalScore.setWebVideoInitBufferDelay(connection.get(ite).getWebVideoInitBufferDelay());
					finalScore.setWebVideoLoadDelay(connection.get(ite).getWebVideoLoadDelay());
					finalScore.setWebVideoTotalBufferDelay(connection.get(ite).getWebVideoTotalBufferDelay());
					finalScore.setWebVideoDownloadRate(connection.get(ite).getWebVideoDownloadRate());
					finalScore.setWebVideoBufferTime(connection.get(ite).getWebVideoBufferTime());
					finalScore.setScore(connection.get(ite).getScore());
					finalScore.setBase(connection.get(ite).getBase());
					finalScore.setBase(Double.parseDouble(pros.getValue("videoweight")));
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
		return recordHourWebVideoDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourWebVideoEntity recordHourWebVideo){
		recordHourWebVideoDao.save(recordHourWebVideo);
	}
	
	@Override
	public void update(RecordHourWebVideoEntity recordHourWebVideo){
		recordHourWebVideoDao.update(recordHourWebVideo);
	}
	
	@Override
	public void delete(Integer id){
		recordHourWebVideoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourWebVideoDao.deleteBatch(ids);
	}
	
}
