package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordWebVideoDao;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.RecordWebVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourWebVideoDao;
import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.cem.service.RecordHourWebVideoService;



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
				if ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 80-100
				else if (((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video42"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video43"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Integer.parseInt(pros.getValue("video43")))) * 20) / ((Integer.parseInt(pros.getValue("video42")) - (Integer.parseInt(pros.getValue("video43"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 60-80
				else if (((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video43"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video44"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Integer.parseInt(pros.getValue("video44")))) * 20) / ((Integer.parseInt(pros.getValue("video43")) - (Integer.parseInt(pros.getValue("video44"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 40-60
				else if (((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video44"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video45"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Integer.parseInt(pros.getValue("video45")))) * 20) / ((Integer.parseInt(pros.getValue("video44")) - (Integer.parseInt(pros.getValue("video45"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 20-40
				else if (((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video45"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video46"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getBufferTime().doubleValue()) - (Integer.parseInt(pros.getValue("video46")))) * 20) / ((Integer.parseInt(pros.getValue("video45")) - (Integer.parseInt(pros.getValue("video46"))))))) * (Double.parseDouble(pros.getValue("video41")));
				}
				//buffer_time 0-20
				else if (((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video46"))) > 0) && ((videoList.get(i).getBufferTime()).compareTo(Integer.parseInt(pros.getValue("video47"))) <= 0)) {
					score += ((((videoList.get(i).getBufferTime().doubleValue()) - (Integer.parseInt(pros.getValue("video47")))) * 20) / ((Integer.parseInt(pros.getValue("video46")) - (Integer.parseInt(pros.getValue("video47")))))) * (Double.parseDouble(pros.getValue("video41")));
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
				if ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video92"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 80-100
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video92"))) > 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video93"))) <= 0)) {
					score += (80 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video93")))) * 20) / ((Double.parseDouble(pros.getValue("video92")) - (Double.parseDouble(pros.getValue("video93"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 60-80
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video93"))) > 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video94"))) <= 0)) {
					score += (60 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video94")))) * 20) / ((Double.parseDouble(pros.getValue("video93")) - (Double.parseDouble(pros.getValue("video94"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 40-60
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video94"))) > 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video95"))) <= 0)) {
					score += (40 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video95")))) * 20) / ((Double.parseDouble(pros.getValue("video94")) - (Double.parseDouble(pros.getValue("video95"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 20-40
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video95"))) > 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video96"))) <= 0)) {
					score += (20 + ((((videoList.get(i).getDownloadRate().doubleValue()) - (Double.parseDouble(pros.getValue("video96")))) * 20) / ((Double.parseDouble(pros.getValue("video95")) - (Double.parseDouble(pros.getValue("video96"))))))) * (Double.parseDouble(pros.getValue("video91")));
				}
				//download_rate 0-20
				else if (((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video96"))) > 0) && ((videoList.get(i).getDownloadRate()).compareTo(Double.parseDouble(pros.getValue("video97"))) <= 0)) {
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
				finalScore.setScore(score*(1-(videoList.get(i).getFail()/videoList.get(i).getTotal())));
				finalScore.setBase(Double.parseDouble(pros.getValue("videoweight")));
				connectionScore.add(finalScore);


			}

		}catch (IOException e){}

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
