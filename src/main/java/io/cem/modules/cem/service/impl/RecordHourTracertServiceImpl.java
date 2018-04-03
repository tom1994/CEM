package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordTracertDao;
import io.cem.modules.cem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

import io.cem.modules.cem.dao.RecordHourTracertDao;
import io.cem.modules.cem.service.RecordHourTracertService;



@Service("recordHourTracertService")
public class RecordHourTracertServiceImpl implements RecordHourTracertService {
	@Autowired
	private RecordHourTracertDao recordHourTracertDao;
	@Autowired
	private RecordTracertDao recordTracertDao;
	
	@Override
	public RecordHourTracertEntity queryObject(Integer id){
		return recordHourTracertDao.queryObject(id);
	}

	@Override
	public List<RecordHourTracertEntity> queryTracert(Map<String,Object> map) {return recordTracertDao.queryTracert(map);}
	@Override
	public List<RecordHourTracertEntity> queryList(Map<String, Object> map){
		return recordHourTracertDao.queryList(map);
	}

	@Override
	@Async
	public Future<List<RecordHourTracertEntity>> queryTracertList(Map<String, Object> map) {
		return new AsyncResult<>(recordHourTracertDao.queryTracertList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourTracertEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourTracertDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourTracertEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourTracertDao.queryDayExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourTracertEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourTracertDao.queryDayList(map));
	}

	@Override
	public List<ScoreEntity> calculateService0(List<ScoreEntity> connection, List<ScoreEntity> quality, List<ScoreEntity> broswer, List<ScoreEntity> download, List<ScoreEntity> video, List<ScoreEntity> game){
		List<ScoreEntity> finalScore = new ArrayList<>();
		Map<ScoreTargetEntity,Map<String,ScoreBaseEntity>> score= new HashMap<>();

		for (int i = 0; i < connection.size(); i++) {
			ScoreTargetEntity scoreTarget = new ScoreTargetEntity();
			scoreTarget.setCityId(connection.get(i).getCityId());
			scoreTarget.setCountyId(connection.get(i).getCountyId());
			scoreTarget.setProbeId(connection.get(i).getProbeId());
			scoreTarget.setTargetId(connection.get(i).getTargetId());
			scoreTarget.setCityName(connection.get(i).getCityName());
			scoreTarget.setCountyName(connection.get(i).getCountyName());
			scoreTarget.setProbeName(connection.get(i).getProbeName());
			scoreTarget.setTargetName(connection.get(i).getTargetName());
			scoreTarget.setRecordDate(connection.get(i).getRecordDate());
			scoreTarget.setRecordTime(connection.get(i).getRecordTime());
			scoreTarget.setAccessLayer(connection.get(i).getAccessLayer());
			scoreTarget.setPort(connection.get(i).getPort());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			scoreBase.setPingIcmpDelay(connection.get(i).getPingIcmpDelay());
			scoreBase.setPingIcmpDelayStd(connection.get(i).getPingIcmpDelayStd());
			scoreBase.setPingIcmpDelayVar(connection.get(i).getPingIcmpDelayVar());
			scoreBase.setPingIcmpJitter(connection.get(i).getPingIcmpJitter());
			scoreBase.setPingIcmpJitterStd(connection.get(i).getPingIcmpJitterStd());
			scoreBase.setPingIcmpJitterVar(connection.get(i).getPingIcmpJitterVar());
			scoreBase.setPingIcmpLossRate(connection.get(i).getPingIcmpLossRate());
			scoreBase.setPingTcpDelay(connection.get(i).getPingTcpDelay());
			scoreBase.setPingTcpDelayStd(connection.get(i).getPingTcpDelayStd());
			scoreBase.setPingTcpDelayVar(connection.get(i).getPingTcpDelayVar());
			scoreBase.setPingTcpJitter(connection.get(i).getPingTcpJitter());
			scoreBase.setPingTcpJitterStd(connection.get(i).getPingTcpJitterStd());
			scoreBase.setPingTcpJitterVar(connection.get(i).getPingTcpJitterVar());
			scoreBase.setPingTcpLossRate(connection.get(i).getPingTcpLossRate());
			scoreBase.setPingUdpDelay(connection.get(i).getPingUdpDelay());
			scoreBase.setPingUdpDelayStd(connection.get(i).getPingUdpDelayStd());
			scoreBase.setPingUdpDelayVar(connection.get(i).getPingUdpDelayVar());
			scoreBase.setPingUdpJitter(connection.get(i).getPingUdpJitter());
			scoreBase.setPingUdpJitterStd(connection.get(i).getPingUdpJitterStd());
			scoreBase.setPingUdpJitterVar(connection.get(i).getPingUdpJitterVar());
			scoreBase.setPingUdpLossRate(connection.get(i).getPingUdpLossRate());
			scoreBase.setTracertIcmpDelay(connection.get(i).getTracertIcmpDelay());
			scoreBase.setTracertIcmpDelayStd(connection.get(i).getTracertIcmpDelayStd());
			scoreBase.setTracertIcmpDelayVar(connection.get(i).getTracertIcmpDelayVar());
			scoreBase.setTracertIcmpJitter(connection.get(i).getTracertIcmpJitter());
			scoreBase.setTracertIcmpJitterStd(connection.get(i).getTracertIcmpJitterStd());
			scoreBase.setTracertIcmpJitterVar(connection.get(i).getTracertIcmpJitterVar());
			scoreBase.setTracertIcmpLossRate(connection.get(i).getTracertIcmpLossRate());
			scoreBase.setTracertTcpDelay(connection.get(i).getTracertTcpDelay());
			scoreBase.setTracertTcpDelayStd(connection.get(i).getTracertTcpDelayStd());
			scoreBase.setTracertTcpDelayVar(connection.get(i).getTracertTcpDelayVar());
			scoreBase.setTracertTcpJitter(connection.get(i).getTracertTcpJitter());
			scoreBase.setTracertTcpJitterStd(connection.get(i).getTracertTcpJitterStd());
			scoreBase.setTracertTcpJitterVar(connection.get(i).getTracertTcpJitterVar());
			scoreBase.setTracertTcpLossRate(connection.get(i).getTracertTcpLossRate());
			scoreBase.setConnectionScore(connection.get(i).getScore());
			scoreBase.setScore(connection.get(i).getScore());
			scoreBase.setBase(connection.get(i).getBase());
			Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
			ping1.put("connection",scoreBase);
			score.put(scoreTarget,ping1 );
		}

		score=putMap(quality,score,"quality");
		score=putMap(broswer,score,"broswer");
		score=putMap(download,score,"download");
		score=putMap(video,score,"video");
		score=putMap(game,score,"game");

		System.out.println("MAP:"+score);

		Set<ScoreTargetEntity> key = score.keySet();
		Iterator<ScoreTargetEntity> iterator = key.iterator();
		int id = 1;
		while (iterator.hasNext()) {
			ScoreTargetEntity ite = iterator.next();
			ScoreEntity lastScore = new ScoreEntity();
			lastScore.setId(id);
			lastScore.setCityId(ite.getCityId());
			lastScore.setCityName(ite.getCityName());
			lastScore.setCountyId(ite.getCountyId());
			lastScore.setCountyName(ite.getCountyName());
			lastScore.setProbeId(ite.getProbeId());
			lastScore.setProbeName(ite.getProbeName());
			lastScore.setServiceType(0);
			lastScore.setTargetId(ite.getTargetId());
			lastScore.setTargetName(ite.getTargetName());
			lastScore.setAccessLayer(ite.getAccessLayer());
			lastScore.setRecordTime(ite.getRecordTime());
			lastScore.setRecordDate(ite.getRecordDate());
			lastScore.setPort(ite.getPort());
			lastScore.setAccessLayer(ite.getAccessLayer());
			lastScore.setScore(0.0);
			lastScore.setBase(0.0);
			Map<String, ScoreBaseEntity> map1 = score.get(ite);
			Set<String> keyType = map1.keySet();
			Iterator<String> iterator1 = keyType.iterator();
			int i=1;
			while(iterator1.hasNext()) {
				String typ = iterator1.next();
				if (typ.equals("connection")) {
					lastScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
					lastScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
					lastScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
					lastScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
					lastScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
					lastScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
					lastScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
					lastScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
					lastScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
					lastScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
					lastScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
					lastScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
					lastScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
					lastScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
					lastScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
					lastScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
					lastScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
					lastScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
					lastScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
					lastScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
					lastScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
					lastScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
					lastScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
					lastScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
					lastScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
					lastScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
					lastScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
					lastScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
					lastScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
					lastScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
					lastScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
					lastScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
					lastScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
					lastScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
					lastScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
					lastScore.setConnectionScore(map1.get(typ).getConnectionScore());
				}else if (typ.equals("quality")){
					lastScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
					lastScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
					lastScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
					lastScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
					lastScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
					lastScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
					lastScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
					lastScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
					lastScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
					lastScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
					lastScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
					lastScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
					lastScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
					lastScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
					lastScore.setDnsDelay(map1.get(typ).getDnsDelay());
					lastScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
					lastScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
					lastScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
					lastScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
					lastScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
					lastScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
					lastScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
					lastScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
					lastScore.setQualityScore(map1.get(typ).getQualityScore());
				}else if (typ.equals("broswer")){
					lastScore.setWebpageDnsDelay(map1.get(typ).getWebpageDnsDelay());
					lastScore.setWebpageConnDelay(map1.get(typ).getWebpageConnDelay());
					lastScore.setWebpageHeadbyteDelay(map1.get(typ).getWebpageHeadbyteDelay());
					lastScore.setWebpagePageFileDelay(map1.get(typ).getWebpagePageFileDelay());
					lastScore.setWebpageRedirectDelay(map1.get(typ).getWebpageRedirectDelay());
					lastScore.setWebpageAboveFoldDelay(map1.get(typ).getWebpageAboveFoldDelay());
					lastScore.setWebpagePageElementDelay(map1.get(typ).getWebpagePageElementDelay());
					lastScore.setWebpageDownloadRate(map1.get(typ).getWebpageDownloadRate());
					lastScore.setBroswerScore(map1.get(typ).getBroswerScore());
				}else if(typ.equals("download")){
					lastScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
					lastScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
					lastScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
					lastScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
					lastScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
					lastScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
					lastScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
					lastScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
					lastScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
					lastScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
					lastScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
					lastScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
					lastScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
					lastScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
					lastScore.setDownloadScore(map1.get(typ).getDownloadScore());
				}else if(typ.equals("video")){
					lastScore.setWebVideoDnsDelay(map1.get(typ).getWebVideoDnsDelay());
					lastScore.setWebVideoWsConnDelay(map1.get(typ).getWebVideoWsConnDelay());
					lastScore.setWebVideoWebPageDelay(map1.get(typ).getWebVideoWebPageDelay());
					lastScore.setWebVideoSsConnDelay(map1.get(typ).getWebVideoSsConnDelay());
					lastScore.setWebVideoAddressDelay(map1.get(typ).getWebVideoAddressDelay());
					lastScore.setWebVideoMsConnDelay(map1.get(typ).getWebVideoMsConnDelay());
					lastScore.setWebVideoHeadFrameDelay(map1.get(typ).getWebVideoHeadFrameDelay());
					lastScore.setWebVideoInitBufferDelay(map1.get(typ).getWebVideoInitBufferDelay());
					lastScore.setWebVideoLoadDelay(map1.get(typ).getWebVideoLoadDelay());
					lastScore.setWebVideoTotalBufferDelay(map1.get(typ).getWebVideoTotalBufferDelay());
					lastScore.setWebVideoDownloadRate(map1.get(typ).getWebVideoDownloadRate());
					lastScore.setWebVideoBufferTime(map1.get(typ).getWebVideoBufferTime());
					lastScore.setVideoScore(map1.get(typ).getVideoScore());
				}else if(typ.equals("game")){
					lastScore.setGameDnsDelay(map1.get(typ).getGameDnsDelay());
					lastScore.setGameConnDelay(map1.get(typ).getGameConnDelay());
					lastScore.setGamePacketDelay(map1.get(typ).getGamePacketDelay());
					lastScore.setGamePacketJitter(map1.get(typ).getGamePacketJitter());
					lastScore.setGameLossRate(map1.get(typ).getGameLossRate());
					lastScore.setGameScore(map1.get(typ).getGameScore());
				}else{}
				lastScore.setScore(lastScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
				lastScore.setBase(lastScore.getBase()+map1.get(typ).getBase());
				i++;
			}
			lastScore.setScore(lastScore.getScore() /lastScore.getBase());
			lastScore.setBase(lastScore.getBase());
			finalScore.add(lastScore);
			id++;
		}



		return finalScore;
	}

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
			scoreTarget.setRecordTime(list.get(i).getRecordTime());
			scoreTarget.setRecordDate(list.get(i).getRecordDate());
			scoreTarget.setAccessLayer(list.get(i).getAccessLayer());
			scoreTarget.setPort(list.get(i).getPort());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("quality")){
				scoreBase.setSlaTcpDelay(list.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(list.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(list.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(list.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(list.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(list.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(list.get(i).getSlaTcpLossRate());
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setQualityScore(list.get(i).getScore());
			}else if(type.equals("broswer")){
				scoreBase.setWebpageDnsDelay(list.get(i).getWebpageDnsDelay());
				scoreBase.setWebpageConnDelay(list.get(i).getWebpageConnDelay());
				scoreBase.setWebpageHeadbyteDelay(list.get(i).getWebpageHeadbyteDelay());
				scoreBase.setWebpagePageFileDelay(list.get(i).getWebpagePageFileDelay());
				scoreBase.setWebpageRedirectDelay(list.get(i).getWebpageRedirectDelay());
				scoreBase.setWebpageAboveFoldDelay(list.get(i).getWebpageAboveFoldDelay());
				scoreBase.setWebpagePageElementDelay(list.get(i).getWebpagePageElementDelay());
				scoreBase.setWebpageDownloadRate(list.get(i).getWebpageDownloadRate());
				scoreBase.setBroswerScore(list.get(i).getScore());
			}else if(type.equals("download")){
				scoreBase.setWebDownloadDnsDelay(list.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(list.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(list.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(list.get(i).getWebDownloadDownloadRate());
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setDownloadScore(list.get(i).getScore());
			}else if(type.equals("video")){
				scoreBase.setWebVideoDnsDelay(list.get(i).getWebVideoDnsDelay());
				scoreBase.setWebVideoWsConnDelay(list.get(i).getWebVideoWsConnDelay());
				scoreBase.setWebVideoWebPageDelay(list.get(i).getWebVideoWebPageDelay());
				scoreBase.setWebVideoSsConnDelay(list.get(i).getWebVideoSsConnDelay());
				scoreBase.setWebVideoAddressDelay(list.get(i).getWebVideoAddressDelay());
				scoreBase.setWebVideoMsConnDelay(list.get(i).getWebVideoMsConnDelay());
				scoreBase.setWebVideoHeadFrameDelay(list.get(i).getWebVideoHeadFrameDelay());
				scoreBase.setWebVideoInitBufferDelay(list.get(i).getWebVideoInitBufferDelay());
				scoreBase.setWebVideoLoadDelay(list.get(i).getWebVideoLoadDelay());
				scoreBase.setWebVideoTotalBufferDelay(list.get(i).getWebVideoTotalBufferDelay());
				scoreBase.setWebVideoDownloadRate(list.get(i).getWebVideoDownloadRate());
				scoreBase.setWebVideoBufferTime(list.get(i).getWebVideoBufferTime());
				scoreBase.setVideoScore(list.get(i).getScore());
			}else if(type.equals("game")){
				scoreBase.setGameDnsDelay(list.get(i).getGameDnsDelay());
				scoreBase.setGameConnDelay(list.get(i).getGameConnDelay());
				scoreBase.setGamePacketDelay(list.get(i).getGamePacketDelay());
				scoreBase.setGamePacketJitter(list.get(i).getGamePacketJitter());
				scoreBase.setGameLossRate(list.get(i).getGameLossRate());
				scoreBase.setGameScore(list.get(i).getScore());
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

	@Override
	public List<ScoreEntity> calculateArea0(List<ScoreEntity> connection, List<ScoreEntity> quality, List<ScoreEntity> broswer, List<ScoreEntity> download, List<ScoreEntity> video, List<ScoreEntity> game){
		List<ScoreEntity> finalScore = new ArrayList<>();
		Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> score= new HashMap<>();

		for (int i = 0; i < connection.size(); i++) {
			ScoreAreaEntity scoreArea = new ScoreAreaEntity();
			scoreArea.setCityId(connection.get(i).getCityId());
			scoreArea.setCountyId(connection.get(i).getCountyId());
			scoreArea.setProbeId(connection.get(i).getProbeId());
			scoreArea.setTargetId(connection.get(i).getTargetId());
			scoreArea.setCityName(connection.get(i).getCityName());
			scoreArea.setCountyName(connection.get(i).getCountyName());
			scoreArea.setProbeName(connection.get(i).getProbeName());
			scoreArea.setTargetName(connection.get(i).getTargetName());
			scoreArea.setRecordDate(connection.get(i).getRecordDate());
			scoreArea.setRecordTime(connection.get(i).getRecordTime());
			scoreArea.setAccessLayer(connection.get(i).getAccessLayer());
			scoreArea.setPort(connection.get(i).getPort());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			scoreBase.setPingIcmpDelay(connection.get(i).getPingIcmpDelay());
			scoreBase.setPingIcmpDelayStd(connection.get(i).getPingIcmpDelayStd());
			scoreBase.setPingIcmpDelayVar(connection.get(i).getPingIcmpDelayVar());
			scoreBase.setPingIcmpJitter(connection.get(i).getPingIcmpJitter());
			scoreBase.setPingIcmpJitterStd(connection.get(i).getPingIcmpJitterStd());
			scoreBase.setPingIcmpJitterVar(connection.get(i).getPingIcmpJitterVar());
			scoreBase.setPingIcmpLossRate(connection.get(i).getPingIcmpLossRate());
			scoreBase.setPingTcpDelay(connection.get(i).getPingTcpDelay());
			scoreBase.setPingTcpDelayStd(connection.get(i).getPingTcpDelayStd());
			scoreBase.setPingTcpDelayVar(connection.get(i).getPingTcpDelayVar());
			scoreBase.setPingTcpJitter(connection.get(i).getPingTcpJitter());
			scoreBase.setPingTcpJitterStd(connection.get(i).getPingTcpJitterStd());
			scoreBase.setPingTcpJitterVar(connection.get(i).getPingTcpJitterVar());
			scoreBase.setPingTcpLossRate(connection.get(i).getPingTcpLossRate());
			scoreBase.setPingUdpDelay(connection.get(i).getPingUdpDelay());
			scoreBase.setPingUdpDelayStd(connection.get(i).getPingUdpDelayStd());
			scoreBase.setPingUdpDelayVar(connection.get(i).getPingUdpDelayVar());
			scoreBase.setPingUdpJitter(connection.get(i).getPingUdpJitter());
			scoreBase.setPingUdpJitterStd(connection.get(i).getPingUdpJitterStd());
			scoreBase.setPingUdpJitterVar(connection.get(i).getPingUdpJitterVar());
			scoreBase.setPingUdpLossRate(connection.get(i).getPingUdpLossRate());
			scoreBase.setTracertIcmpDelay(connection.get(i).getTracertIcmpDelay());
			scoreBase.setTracertIcmpDelayStd(connection.get(i).getTracertIcmpDelayStd());
			scoreBase.setTracertIcmpDelayVar(connection.get(i).getTracertIcmpDelayVar());
			scoreBase.setTracertIcmpJitter(connection.get(i).getTracertIcmpJitter());
			scoreBase.setTracertIcmpJitterStd(connection.get(i).getTracertIcmpJitterStd());
			scoreBase.setTracertIcmpJitterVar(connection.get(i).getTracertIcmpJitterVar());
			scoreBase.setTracertIcmpLossRate(connection.get(i).getTracertIcmpLossRate());
			scoreBase.setTracertTcpDelay(connection.get(i).getTracertTcpDelay());
			scoreBase.setTracertTcpDelayStd(connection.get(i).getTracertTcpDelayStd());
			scoreBase.setTracertTcpDelayVar(connection.get(i).getTracertTcpDelayVar());
			scoreBase.setTracertTcpJitter(connection.get(i).getTracertTcpJitter());
			scoreBase.setTracertTcpJitterStd(connection.get(i).getTracertTcpJitterStd());
			scoreBase.setTracertTcpJitterVar(connection.get(i).getTracertTcpJitterVar());
			scoreBase.setTracertTcpLossRate(connection.get(i).getTracertTcpLossRate());
			scoreBase.setConnectionScore(connection.get(i).getScore());
			scoreBase.setScore(connection.get(i).getScore());
			scoreBase.setBase(connection.get(i).getBase());
			Map<String,ScoreBaseEntity> ping1 = new HashMap<>();
			ping1.put("connection",scoreBase);
			score.put(scoreArea,ping1 );
		}

		score=putAreaMap(quality,score,"quality");
		score=putAreaMap(broswer,score,"broswer");
		score=putAreaMap(download,score,"download");
		score=putAreaMap(video,score,"video");
		score=putAreaMap(game,score,"game");

		System.out.println("MAP:"+score);

		Set<ScoreAreaEntity> key = score.keySet();
		Iterator<ScoreAreaEntity> iterator = key.iterator();
		int id = 1;
		while (iterator.hasNext()) {
			ScoreAreaEntity ite = iterator.next();
			ScoreEntity lastScore = new ScoreEntity();
			lastScore.setId(id);
			lastScore.setCityId(ite.getCityId());
			lastScore.setCityName(ite.getCityName());
			lastScore.setCountyId(ite.getCountyId());
			lastScore.setCountyName(ite.getCountyName());
			lastScore.setProbeId(ite.getProbeId());
			lastScore.setProbeName(ite.getProbeName());
			lastScore.setServiceType(0);
			lastScore.setTargetId(ite.getTargetId());
			lastScore.setTargetName(ite.getTargetName());
			lastScore.setAccessLayer(ite.getAccessLayer());
			lastScore.setRecordTime(ite.getRecordTime());
			lastScore.setRecordDate(ite.getRecordDate());
			lastScore.setPort(ite.getPort());
			lastScore.setAccessLayer(ite.getAccessLayer());
			lastScore.setScore(0.0);
			lastScore.setBase(0.0);
			Map<String, ScoreBaseEntity> map1 = score.get(ite);
			Set<String> keyType = map1.keySet();
			Iterator<String> iterator1 = keyType.iterator();
			int i=1;
			while(iterator1.hasNext()) {
				String typ = iterator1.next();
				if (typ.equals("connection")) {
					lastScore.setPingIcmpDelay(map1.get(typ).getPingIcmpDelay());
					lastScore.setPingIcmpDelayStd(map1.get(typ).getPingIcmpDelayStd());
					lastScore.setPingIcmpDelayVar(map1.get(typ).getPingIcmpDelayVar());
					lastScore.setPingIcmpJitter(map1.get(typ).getPingIcmpJitter());
					lastScore.setPingIcmpJitterStd(map1.get(typ).getPingIcmpJitterStd());
					lastScore.setPingIcmpJitterVar(map1.get(typ).getPingIcmpJitterVar());
					lastScore.setPingIcmpLossRate(map1.get(typ).getPingIcmpLossRate());
					lastScore.setPingTcpDelay(map1.get(typ).getPingTcpDelay());
					lastScore.setPingTcpDelayStd(map1.get(typ).getPingTcpDelayStd());
					lastScore.setPingTcpDelayVar(map1.get(typ).getPingTcpDelayVar());
					lastScore.setPingTcpJitter(map1.get(typ).getPingTcpJitter());
					lastScore.setPingTcpJitterStd(map1.get(typ).getPingTcpJitterStd());
					lastScore.setPingTcpJitterVar(map1.get(typ).getPingTcpJitterVar());
					lastScore.setPingTcpLossRate(map1.get(typ).getPingTcpLossRate());
					lastScore.setPingUdpDelay(map1.get(typ).getPingUdpDelay());
					lastScore.setPingUdpDelayStd(map1.get(typ).getPingUdpDelayStd());
					lastScore.setPingUdpDelayVar(map1.get(typ).getPingUdpDelayVar());
					lastScore.setPingUdpJitter(map1.get(typ).getPingUdpJitter());
					lastScore.setPingUdpJitterStd(map1.get(typ).getPingUdpJitterStd());
					lastScore.setPingUdpJitterVar(map1.get(typ).getPingUdpJitterVar());
					lastScore.setPingUdpLossRate(map1.get(typ).getPingUdpLossRate());
					lastScore.setTracertIcmpDelay(map1.get(typ).getTracertIcmpDelay());
					lastScore.setTracertIcmpDelayStd(map1.get(typ).getTracertIcmpDelayStd());
					lastScore.setTracertIcmpDelayVar(map1.get(typ).getTracertIcmpDelayVar());
					lastScore.setTracertIcmpJitter(map1.get(typ).getTracertIcmpJitter());
					lastScore.setTracertIcmpJitterStd(map1.get(typ).getTracertIcmpJitterStd());
					lastScore.setTracertIcmpJitterVar(map1.get(typ).getTracertIcmpJitterVar());
					lastScore.setTracertIcmpLossRate(map1.get(typ).getTracertIcmpLossRate());
					lastScore.setTracertTcpDelay(map1.get(typ).getTracertTcpDelay());
					lastScore.setTracertTcpDelayStd(map1.get(typ).getTracertTcpDelayStd());
					lastScore.setTracertTcpDelayVar(map1.get(typ).getTracertTcpDelayVar());
					lastScore.setTracertTcpJitter(map1.get(typ).getTracertTcpJitter());
					lastScore.setTracertTcpJitterStd(map1.get(typ).getTracertTcpJitterStd());
					lastScore.setTracertTcpJitterVar(map1.get(typ).getTracertTcpJitterVar());
					lastScore.setTracertTcpLossRate(map1.get(typ).getTracertTcpLossRate());
					lastScore.setConnectionScore(map1.get(typ).getConnectionScore());
				}else if (typ.equals("quality")){
					lastScore.setSlaTcpDelay(map1.get(typ).getSlaTcpDelay());
					lastScore.setSlaTcpGDelay(map1.get(typ).getSlaTcpGDelay());
					lastScore.setSlaTcpRDelay(map1.get(typ).getSlaTcpRDelay());
					lastScore.setSlaTcpJitter(map1.get(typ).getSlaTcpJitter());
					lastScore.setSlaTcpGJitter(map1.get(typ).getSlaTcpGJitter());
					lastScore.setSlaTcpRJitter(map1.get(typ).getSlaTcpRJitter());
					lastScore.setSlaTcpLossRate(map1.get(typ).getSlaTcpLossRate());
					lastScore.setSlaUdpDelay(map1.get(typ).getSlaUdpDelay());
					lastScore.setSlaUdpGDelay(map1.get(typ).getSlaUdpGDelay());
					lastScore.setSlaUdpRDelay(map1.get(typ).getSlaUdpRDelay());
					lastScore.setSlaUdpJitter(map1.get(typ).getSlaUdpJitter());
					lastScore.setSlaUdpGJitter(map1.get(typ).getSlaUdpGJitter());
					lastScore.setSlaUdpRJitter(map1.get(typ).getSlaUdpRJitter());
					lastScore.setSlaUdpLossRate(map1.get(typ).getSlaUdpLossRate());
					lastScore.setDnsDelay(map1.get(typ).getDnsDelay());
					lastScore.setDnsSuccessRate(map1.get(typ).getDnsSuccessRate());
					lastScore.setDhcpDelay(map1.get(typ).getDhcpDelay());
					lastScore.setDhcpSuccessRate(map1.get(typ).getDhcpSuccessRate());
					lastScore.setPppoeDelay(map1.get(typ).getPppoeDelay());
					lastScore.setPppoeDropRate(map1.get(typ).getPppoeDropRate());
					lastScore.setPppoeSuccessRate(map1.get(typ).getPppoeSuccessRate());
					lastScore.setRadiusDelay(map1.get(typ).getRadiusDelay());
					lastScore.setRadiusSuccessRate(map1.get(typ).getRadiusSuccessRate());
					lastScore.setQualityScore(map1.get(typ).getQualityScore());
				}else if (typ.equals("broswer")){
					lastScore.setWebpageDnsDelay(map1.get(typ).getWebpageDnsDelay());
					lastScore.setWebpageConnDelay(map1.get(typ).getWebpageConnDelay());
					lastScore.setWebpageHeadbyteDelay(map1.get(typ).getWebpageHeadbyteDelay());
					lastScore.setWebpagePageFileDelay(map1.get(typ).getWebpagePageFileDelay());
					lastScore.setWebpageRedirectDelay(map1.get(typ).getWebpageRedirectDelay());
					lastScore.setWebpageAboveFoldDelay(map1.get(typ).getWebpageAboveFoldDelay());
					lastScore.setWebpagePageElementDelay(map1.get(typ).getWebpagePageElementDelay());
					lastScore.setWebpageDownloadRate(map1.get(typ).getWebpageDownloadRate());
					lastScore.setBroswerScore(map1.get(typ).getBroswerScore());
				}else if(typ.equals("download")){
					lastScore.setWebDownloadDnsDelay(map1.get(typ).getWebDownloadDnsDelay());
					lastScore.setWebDownloadConnDelay(map1.get(typ).getWebDownloadConnDelay());
					lastScore.setWebDownloadHeadbyteDelay(map1.get(typ).getWebDownloadHeadbyteDelay());
					lastScore.setWebDownloadDownloadRate(map1.get(typ).getWebDownloadDownloadRate());
					lastScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
					lastScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
					lastScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
					lastScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
					lastScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
					lastScore.setFtpDownloadDnsDelay(map1.get(typ).getFtpDownloadDnsDelay());
					lastScore.setFtpDownloadConnDelay(map1.get(typ).getFtpDownloadConnDelay());
					lastScore.setFtpDownloadLoginDelay(map1.get(typ).getFtpDownloadLoginDelay());
					lastScore.setFtpDownloadHeadbyteDelay(map1.get(typ).getFtpDownloadHeadbyteDelay());
					lastScore.setFtpDownloadDownloadRate(map1.get(typ).getFtpDownloadDownloadRate());
					lastScore.setDownloadScore(map1.get(typ).getDownloadScore());
				}else if(typ.equals("video")){
					lastScore.setWebVideoDnsDelay(map1.get(typ).getWebVideoDnsDelay());
					lastScore.setWebVideoWsConnDelay(map1.get(typ).getWebVideoWsConnDelay());
					lastScore.setWebVideoWebPageDelay(map1.get(typ).getWebVideoWebPageDelay());
					lastScore.setWebVideoSsConnDelay(map1.get(typ).getWebVideoSsConnDelay());
					lastScore.setWebVideoAddressDelay(map1.get(typ).getWebVideoAddressDelay());
					lastScore.setWebVideoMsConnDelay(map1.get(typ).getWebVideoMsConnDelay());
					lastScore.setWebVideoHeadFrameDelay(map1.get(typ).getWebVideoHeadFrameDelay());
					lastScore.setWebVideoInitBufferDelay(map1.get(typ).getWebVideoInitBufferDelay());
					lastScore.setWebVideoLoadDelay(map1.get(typ).getWebVideoLoadDelay());
					lastScore.setWebVideoTotalBufferDelay(map1.get(typ).getWebVideoTotalBufferDelay());
					lastScore.setWebVideoDownloadRate(map1.get(typ).getWebVideoDownloadRate());
					lastScore.setWebVideoBufferTime(map1.get(typ).getWebVideoBufferTime());
					lastScore.setVideoScore(map1.get(typ).getVideoScore());
				}else if(typ.equals("game")){
					lastScore.setGameDnsDelay(map1.get(typ).getGameDnsDelay());
					lastScore.setGameConnDelay(map1.get(typ).getGameConnDelay());
					lastScore.setGamePacketDelay(map1.get(typ).getGamePacketDelay());
					lastScore.setGamePacketJitter(map1.get(typ).getGamePacketJitter());
					lastScore.setGameLossRate(map1.get(typ).getGameLossRate());
					lastScore.setGameScore(map1.get(typ).getGameScore());
				}else{}
				lastScore.setScore(lastScore.getScore() + (map1.get(typ).getScore()) * (map1.get(typ).getBase()));
				lastScore.setBase(lastScore.getBase()+map1.get(typ).getBase());
				i++;
			}
			lastScore.setScore(lastScore.getScore() /lastScore.getBase());
			lastScore.setBase(lastScore.getBase());
			finalScore.add(lastScore);
			id++;
		}



		return finalScore;
	}

	public Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> putAreaMap(List<ScoreEntity> list,Map<ScoreAreaEntity,Map<String,ScoreBaseEntity>> map,String type){
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
			scoreArea.setRecordTime(list.get(i).getRecordTime());
			scoreArea.setRecordDate(list.get(i).getRecordDate());
			scoreArea.setAccessLayer(list.get(i).getAccessLayer());
			scoreArea.setPort(list.get(i).getPort());
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			if(type.equals("quality")){
				scoreBase.setSlaTcpDelay(list.get(i).getSlaTcpDelay());
				scoreBase.setSlaTcpGDelay(list.get(i).getSlaTcpGDelay());
				scoreBase.setSlaTcpRDelay(list.get(i).getSlaTcpRDelay());
				scoreBase.setSlaTcpJitter(list.get(i).getSlaTcpJitter());
				scoreBase.setSlaTcpGJitter(list.get(i).getSlaTcpGJitter());
				scoreBase.setSlaTcpRJitter(list.get(i).getSlaTcpRJitter());
				scoreBase.setSlaTcpLossRate(list.get(i).getSlaTcpLossRate());
				scoreBase.setSlaUdpDelay(list.get(i).getSlaUdpDelay());
				scoreBase.setSlaUdpGDelay(list.get(i).getSlaUdpGDelay());
				scoreBase.setSlaUdpRDelay(list.get(i).getSlaUdpRDelay());
				scoreBase.setSlaUdpJitter(list.get(i).getSlaUdpJitter());
				scoreBase.setSlaUdpGJitter(list.get(i).getSlaUdpGJitter());
				scoreBase.setSlaUdpRJitter(list.get(i).getSlaUdpRJitter());
				scoreBase.setSlaUdpLossRate(list.get(i).getSlaUdpLossRate());
				scoreBase.setDnsDelay(list.get(i).getDnsDelay());
				scoreBase.setDnsSuccessRate(list.get(i).getDnsSuccessRate());
				scoreBase.setDhcpDelay(list.get(i).getDhcpDelay());
				scoreBase.setDhcpSuccessRate(list.get(i).getDhcpSuccessRate());
				scoreBase.setPppoeDelay(list.get(i).getPppoeDelay());
				scoreBase.setPppoeDropRate(list.get(i).getPppoeDropRate());
				scoreBase.setPppoeSuccessRate(list.get(i).getPppoeSuccessRate());
				scoreBase.setRadiusDelay(list.get(i).getRadiusDelay());
				scoreBase.setRadiusSuccessRate(list.get(i).getRadiusSuccessRate());
				scoreBase.setQualityScore(list.get(i).getScore());
			}else if(type.equals("broswer")){
				scoreBase.setWebpageDnsDelay(list.get(i).getWebpageDnsDelay());
				scoreBase.setWebpageConnDelay(list.get(i).getWebpageConnDelay());
				scoreBase.setWebpageHeadbyteDelay(list.get(i).getWebpageHeadbyteDelay());
				scoreBase.setWebpagePageFileDelay(list.get(i).getWebpagePageFileDelay());
				scoreBase.setWebpageRedirectDelay(list.get(i).getWebpageRedirectDelay());
				scoreBase.setWebpageAboveFoldDelay(list.get(i).getWebpageAboveFoldDelay());
				scoreBase.setWebpagePageElementDelay(list.get(i).getWebpagePageElementDelay());
				scoreBase.setWebpageDownloadRate(list.get(i).getWebpageDownloadRate());
				scoreBase.setBroswerScore(list.get(i).getScore());
			}else if(type.equals("download")){
				scoreBase.setWebDownloadDnsDelay(list.get(i).getWebDownloadDnsDelay());
				scoreBase.setWebDownloadConnDelay(list.get(i).getWebDownloadConnDelay());
				scoreBase.setWebDownloadHeadbyteDelay(list.get(i).getWebDownloadHeadbyteDelay());
				scoreBase.setWebDownloadDownloadRate(list.get(i).getWebDownloadDownloadRate());
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setFtpDownloadDnsDelay(list.get(i).getFtpDownloadDnsDelay());
				scoreBase.setFtpDownloadConnDelay(list.get(i).getFtpDownloadConnDelay());
				scoreBase.setFtpDownloadLoginDelay(list.get(i).getFtpDownloadLoginDelay());
				scoreBase.setFtpDownloadHeadbyteDelay(list.get(i).getFtpDownloadHeadbyteDelay());
				scoreBase.setFtpDownloadDownloadRate(list.get(i).getFtpDownloadDownloadRate());
				scoreBase.setDownloadScore(list.get(i).getScore());
			}else if(type.equals("video")){
				scoreBase.setWebVideoDnsDelay(list.get(i).getWebVideoDnsDelay());
				scoreBase.setWebVideoWsConnDelay(list.get(i).getWebVideoWsConnDelay());
				scoreBase.setWebVideoWebPageDelay(list.get(i).getWebVideoWebPageDelay());
				scoreBase.setWebVideoSsConnDelay(list.get(i).getWebVideoSsConnDelay());
				scoreBase.setWebVideoAddressDelay(list.get(i).getWebVideoAddressDelay());
				scoreBase.setWebVideoMsConnDelay(list.get(i).getWebVideoMsConnDelay());
				scoreBase.setWebVideoHeadFrameDelay(list.get(i).getWebVideoHeadFrameDelay());
				scoreBase.setWebVideoInitBufferDelay(list.get(i).getWebVideoInitBufferDelay());
				scoreBase.setWebVideoLoadDelay(list.get(i).getWebVideoLoadDelay());
				scoreBase.setWebVideoTotalBufferDelay(list.get(i).getWebVideoTotalBufferDelay());
				scoreBase.setWebVideoDownloadRate(list.get(i).getWebVideoDownloadRate());
				scoreBase.setWebVideoBufferTime(list.get(i).getWebVideoBufferTime());
				scoreBase.setVideoScore(list.get(i).getScore());
			}else if(type.equals("game")){
				scoreBase.setGameDnsDelay(list.get(i).getGameDnsDelay());
				scoreBase.setGameConnDelay(list.get(i).getGameConnDelay());
				scoreBase.setGamePacketDelay(list.get(i).getGamePacketDelay());
				scoreBase.setGamePacketJitter(list.get(i).getGamePacketJitter());
				scoreBase.setGameLossRate(list.get(i).getGameLossRate());
				scoreBase.setGameScore(list.get(i).getScore());
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





	@Override
	public int queryTotal(Map<String, Object> map){
		return recordHourTracertDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourTracertEntity recordHourTracert){
		recordHourTracertDao.save(recordHourTracert);
	}
	
	@Override
	public void update(RecordHourTracertEntity recordHourTracert){
		recordHourTracertDao.update(recordHourTracert);
	}
	
	@Override
	public void delete(Integer id){
		recordHourTracertDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourTracertDao.deleteBatch(ids);
	}
	
}
