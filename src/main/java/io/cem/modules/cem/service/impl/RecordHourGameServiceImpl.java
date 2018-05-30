package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.SpringContextUtils;
import io.cem.modules.cem.dao.RecordGameDao;
import io.cem.modules.cem.dao.RecordHourGameDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.RecordFailService;
import io.cem.modules.cem.service.RecordHourGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;



@Service("recordHourGameService")
public class RecordHourGameServiceImpl implements RecordHourGameService {
	@Autowired
	private RecordHourGameDao recordHourGameDao;
	@Autowired
	private RecordGameDao recordGameDao;
	
	@Override
	public RecordHourGameEntity queryObject(Integer id){
		return recordHourGameDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourGameEntity> queryList(Map<String, Object> map){
		return recordHourGameDao.queryList(map);
	}

	@Override
	public List<RecordHourGameEntity> queryGame(Map<String, Object> map){
		return recordGameDao.queryGame(map);
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryGameList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryGameList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryExitList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryDayExitList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryDayExitList(map));
	}
	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryGameAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryGameAreaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryGameTargetList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryGameTargetList(map));
	}



	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryGameRankList(Map<String, Object> map) {
		return new AsyncResult<>
				(recordHourGameDao.queryGameRankList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryDayList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryDayList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryDayAreaList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryDayAreaList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryDayTargetList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryDayTargetList(map));
	}

	@Override
	@Async
	public Future<List<RecordHourGameEntity>> queryDayRankList(Map<String, Object> map){
		return new AsyncResult<> (recordHourGameDao.queryDayRankList(map));
	}
	
	@Override
	public List<ScoreEntity> calculateService6(List<RecordHourGameEntity> gameList,Map<String,Object> map){
		RecordFailService recordFailService= (RecordFailService) SpringContextUtils.getBean("recordFailService");
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
			RecordFailEntity recordFail = recordFailService.queryFail(map);
			double fail = (double)recordFail.getFail()/recordFail.getTotal();
			for(int i=0;i<gameList.size();i++){
				double score=0;
				//conn_delay 100
				if ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game12"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 80-100
				else if (((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game12"))) > 0) && ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game13"))) <= 0)) {
					score += (80 + ((((gameList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game13")))) * 20) / ((Double.parseDouble(pros.getValue("game12")) - (Double.parseDouble(pros.getValue("game13"))))))) * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 60-80
				else if (((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game13"))) > 0) && ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game14"))) <= 0)) {
					score += (60 + ((((gameList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game14")))) * 20) / ((Double.parseDouble(pros.getValue("game13")) - (Double.parseDouble(pros.getValue("game14"))))))) * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 40-60
				else if (((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game14"))) > 0) && ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game15"))) <= 0)) {
					score += (40 + ((((gameList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game15")))) * 20) / ((Double.parseDouble(pros.getValue("game14")) - (Double.parseDouble(pros.getValue("game15"))))))) * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 20-40
				else if (((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game15"))) > 0) && ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game16"))) <= 0)) {
					score += (20 + ((((gameList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game16")))) * 20) / ((Double.parseDouble(pros.getValue("game15")) - (Double.parseDouble(pros.getValue("game16"))))))) * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 0-20
				else if (((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game16"))) > 0) && ((gameList.get(i).getConnDelay()).compareTo(Double.parseDouble(pros.getValue("game17"))) <= 0)) {
					score += ((((gameList.get(i).getConnDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game17")))) * 20) / ((Double.parseDouble(pros.getValue("game16")) - (Double.parseDouble(pros.getValue("game17")))))) * (Double.parseDouble(pros.getValue("game11")));
				}
				//conn_delay 0
				else {
					score += 0;
				}

				//packet_delay 100
				if ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game22"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 80-100
				else if (((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game22"))) > 0) && ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game23"))) <= 0)) {
					score += (80 + ((((gameList.get(i).getPacketDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game23")))) * 20) / ((Double.parseDouble(pros.getValue("game22")) - (Double.parseDouble(pros.getValue("game23"))))))) * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 60-80
				else if (((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game23"))) > 0) && ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game24"))) <= 0)) {
					score += (60 + ((((gameList.get(i).getPacketDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game24")))) * 20) / ((Double.parseDouble(pros.getValue("game23")) - (Double.parseDouble(pros.getValue("game24"))))))) * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 40-60
				else if (((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game24"))) > 0) && ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game25"))) <= 0)) {
					score += (40 + ((((gameList.get(i).getPacketDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game25")))) * 20) / ((Double.parseDouble(pros.getValue("game24")) - (Double.parseDouble(pros.getValue("game25"))))))) * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 20-40
				else if (((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game25"))) > 0) && ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game26"))) <= 0)) {
					score += (20 + ((((gameList.get(i).getPacketDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game26")))) * 20) / ((Double.parseDouble(pros.getValue("game25")) - (Double.parseDouble(pros.getValue("game26"))))))) * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 0-20
				else if (((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game26"))) > 0) && ((gameList.get(i).getPacketDelay()).compareTo(Double.parseDouble(pros.getValue("game27"))) <= 0)) {
					score += ((((gameList.get(i).getPacketDelay().doubleValue()) - (Double.parseDouble(pros.getValue("game27")))) * 20) / ((Double.parseDouble(pros.getValue("game26")) - (Double.parseDouble(pros.getValue("game27")))))) * (Double.parseDouble(pros.getValue("game21")));
				}
				//packet_delay 0
				else {
					score += 0;
				}

				//packet_jitter 100
				if ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game32"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 80-100
				else if (((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game32"))) > 0) && ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game33"))) <= 0)) {
					score += (80 + ((((gameList.get(i).getPacketJitter().doubleValue()) - (Double.parseDouble(pros.getValue("game33")))) * 20) / ((Double.parseDouble(pros.getValue("game32")) - (Double.parseDouble(pros.getValue("game33"))))))) * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 60-80
				else if (((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game33"))) > 0) && ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game34"))) <= 0)) {
					score += (60 + ((((gameList.get(i).getPacketJitter().doubleValue()) - (Double.parseDouble(pros.getValue("game34")))) * 20) / ((Double.parseDouble(pros.getValue("game33")) - (Double.parseDouble(pros.getValue("game34"))))))) * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 40-60
				else if (((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game34"))) > 0) && ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game35"))) <= 0)) {
					score += (40 + ((((gameList.get(i).getPacketJitter().doubleValue()) - (Double.parseDouble(pros.getValue("game35")))) * 20) / ((Double.parseDouble(pros.getValue("game34")) - (Double.parseDouble(pros.getValue("game35"))))))) * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 20-40
				else if (((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game35"))) > 0) && ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game36"))) <= 0)) {
					score += (20 + ((((gameList.get(i).getPacketJitter().doubleValue()) - (Double.parseDouble(pros.getValue("game36")))) * 20) / ((Double.parseDouble(pros.getValue("game35")) - (Double.parseDouble(pros.getValue("game36"))))))) * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 0-20
				else if (((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game36"))) > 0) && ((gameList.get(i).getPacketJitter()).compareTo(Double.parseDouble(pros.getValue("game37"))) <= 0)) {
					score += ((((gameList.get(i).getPacketJitter().doubleValue()) - (Double.parseDouble(pros.getValue("game37")))) * 20) / ((Double.parseDouble(pros.getValue("game36")) - (Double.parseDouble(pros.getValue("game37")))))) * (Double.parseDouble(pros.getValue("game31")));
				}
				//packet_jitter 0
				else {
					score += 0;
				}

				//loss_rate 100
				Double lossRate = gameList.get(i).getLossRate()*100;
				if (lossRate.compareTo(Double.parseDouble(pros.getValue("game42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 80-100
				else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("game42"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("game43"))) <= 0)) {
					score += (80 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("game43")))) * 20) / ((Double.parseDouble(pros.getValue("game42")) - (Double.parseDouble(pros.getValue("game43"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 60-80
				else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("game43"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("game44"))) <= 0)) {
					score += (60 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("game44")))) * 20) / ((Double.parseDouble(pros.getValue("game43")) - (Double.parseDouble(pros.getValue("game44"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 40-60
				else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("game44"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("game45"))) <= 0)) {
					score += (40 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("game45")))) * 20) / ((Double.parseDouble(pros.getValue("game44")) - (Double.parseDouble(pros.getValue("game45"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 20-40
				else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("game45"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("game46"))) <= 0)) {
					score += (20 + ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("game46")))) * 20) / ((Double.parseDouble(pros.getValue("game45")) - (Double.parseDouble(pros.getValue("game46"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 0-20
				else if ((lossRate.compareTo(Double.parseDouble(pros.getValue("game46"))) > 0) && (lossRate.compareTo(Double.parseDouble(pros.getValue("game47"))) <= 0)) {
					score += ((((lossRate.doubleValue()) - (Double.parseDouble(pros.getValue("game47")))) * 20) / ((Double.parseDouble(pros.getValue("game46")) - (Double.parseDouble(pros.getValue("game47")))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 0
				else {
					score += 0;
				}


				ScoreEntity finalScore = new ScoreEntity();
				finalScore.setId(i+1);
				finalScore.setCityId(gameList.get(i).getCityId());
				finalScore.setCityName(gameList.get(i).getCityName());
				finalScore.setCountyId(gameList.get(i).getCountyId());
				finalScore.setCountyName(gameList.get(i).getAreaName());
				finalScore.setProbeId(gameList.get(i).getProbeId());
				finalScore.setProbeName(gameList.get(i).getProbeName());
				finalScore.setServiceType(6);
				finalScore.setTargetId(gameList.get(i).getTargetId());
				finalScore.setTargetName(gameList.get(i).getTargetName());
				finalScore.setAccessLayer(gameList.get(i).getAccessLayer());
				finalScore.setPort(gameList.get(i).getPort());
				finalScore.setRecordDate(gameList.get(i).getRecordDate());
				finalScore.setRecordTime(gameList.get(i).getRecordTime());
				finalScore.setGameDnsDelay(gameList.get(i).getDnsDelay());
				finalScore.setGameConnDelay(gameList.get(i).getConnDelay());
				finalScore.setGamePacketDelay(gameList.get(i).getPacketDelay());
				finalScore.setGamePacketJitter(gameList.get(i).getPacketJitter());
				finalScore.setGameLossRate(gameList.get(i).getLossRate());
				finalScore.setFail(gameList.get(i).getFail());
				finalScore.setTotal(gameList.get(i).getTotal());
				map.put("service_type",50);
				finalScore.setScore(score*(1-fail));
				finalScore.setBase(Double.parseDouble(pros.getValue("gameweight")));
				connectionScore.add(finalScore);
				
				
			}
			
		}catch(IOException e){}
		
		return connectionScore;
	}


	@Override
	public List<ScoreEntity> calculateLayer6(List<ScoreEntity> webPageList){
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
				scoreBase.setGameDnsDelay(webPageList.get(i).getGameDnsDelay());
				scoreBase.setGameConnDelay(webPageList.get(i).getGameConnDelay());
				scoreBase.setGamePacketDelay(webPageList.get(i).getGamePacketDelay());
				scoreBase.setGamePacketJitter(webPageList.get(i).getGamePacketJitter());
				scoreBase.setGameLossRate(webPageList.get(i).getGameLossRate());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreLayer)) {

					connection.put(scoreLayer,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreLayer);
					if(scoreBase.getGameDnsDelay()!=null&scoreBaseDul.getGameDnsDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameDnsDelay()+scoreBaseDul.getGameDnsDelay())/2);
					}else if(scoreBase.getGameDnsDelay()!=null&&scoreBaseDul.getGameDnsDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameDnsDelay());
					} else if(scoreBase.getGameDnsDelay()==null&&scoreBaseDul.getGameDnsDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameDnsDelay());
					}
					if(scoreBase.getGameConnDelay()!=null&scoreBaseDul.getGameConnDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameConnDelay()+scoreBaseDul.getGameConnDelay())/2);
					}else if(scoreBase.getGameConnDelay()!=null&&scoreBaseDul.getGameConnDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameConnDelay());
					} else if(scoreBase.getGameConnDelay()==null&&scoreBaseDul.getGameConnDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameConnDelay());
					}
					if(scoreBase.getGamePacketDelay()!=null&scoreBaseDul.getGamePacketDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGamePacketDelay()+scoreBaseDul.getGamePacketDelay())/2);
					}else if(scoreBase.getGamePacketDelay()!=null&&scoreBaseDul.getGamePacketDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGamePacketDelay());
					} else if(scoreBase.getGamePacketDelay()==null&&scoreBaseDul.getGamePacketDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGamePacketDelay());
					}
					if(scoreBase.getGamePacketJitter()!=null&scoreBaseDul.getGamePacketJitter()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGamePacketJitter()+scoreBaseDul.getGamePacketJitter())/2);
					}else if(scoreBase.getGamePacketJitter()!=null&&scoreBaseDul.getGamePacketJitter()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGamePacketJitter());
					} else if(scoreBase.getGamePacketJitter()==null&&scoreBaseDul.getGamePacketJitter()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGamePacketJitter());
					}
					if(scoreBase.getGameLossRate()!=null&scoreBaseDul.getGameLossRate()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameLossRate()+scoreBaseDul.getGameLossRate())/2);
					}else if(scoreBase.getGameLossRate()!=null&&scoreBaseDul.getGameLossRate()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameLossRate());
					} else if(scoreBase.getGameLossRate()==null&&scoreBaseDul.getGameLossRate()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameLossRate());
					}

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
					finalScore.setServiceType(6);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setGameDnsDelay(connection.get(ite).getGameDnsDelay());
					finalScore.setGameConnDelay(connection.get(ite).getGameConnDelay());
					finalScore.setGamePacketDelay(connection.get(ite).getGamePacketDelay());
					finalScore.setGamePacketJitter(connection.get(ite).getGamePacketJitter());
					finalScore.setGameLossRate(connection.get(ite).getGameLossRate());
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
	public List<ScoreEntity> calculateDate6(List<ScoreEntity> webPageList){
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
				scoreBase.setGameDnsDelay(webPageList.get(i).getGameDnsDelay());
				scoreBase.setGameConnDelay(webPageList.get(i).getGameConnDelay());
				scoreBase.setGamePacketDelay(webPageList.get(i).getGamePacketDelay());
				scoreBase.setGamePacketJitter(webPageList.get(i).getGamePacketJitter());
				scoreBase.setGameLossRate(webPageList.get(i).getGameLossRate());
				scoreBase.setScore(webPageList.get(i).getScore());
				scoreBase.setBase(webPageList.get(i).getBase());
				if (!connection.containsKey(scoreDate)) {

					connection.put(scoreDate,scoreBase);

				} else {
					ScoreBaseEntity scoreBaseDul = connection.get(scoreDate);
					if(scoreBase.getGameDnsDelay()!=null&scoreBaseDul.getGameDnsDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameDnsDelay()+scoreBaseDul.getGameDnsDelay())/2);
					}else if(scoreBase.getGameDnsDelay()!=null&&scoreBaseDul.getGameDnsDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameDnsDelay());
					} else if(scoreBase.getGameDnsDelay()==null&&scoreBaseDul.getGameDnsDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameDnsDelay());
					}
					if(scoreBase.getGameConnDelay()!=null&scoreBaseDul.getGameConnDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameConnDelay()+scoreBaseDul.getGameConnDelay())/2);
					}else if(scoreBase.getGameConnDelay()!=null&&scoreBaseDul.getGameConnDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameConnDelay());
					} else if(scoreBase.getGameConnDelay()==null&&scoreBaseDul.getGameConnDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameConnDelay());
					}
					if(scoreBase.getGamePacketDelay()!=null&scoreBaseDul.getGamePacketDelay()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGamePacketDelay()+scoreBaseDul.getGamePacketDelay())/2);
					}else if(scoreBase.getGamePacketDelay()!=null&&scoreBaseDul.getGamePacketDelay()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGamePacketDelay());
					} else if(scoreBase.getGamePacketDelay()==null&&scoreBaseDul.getGamePacketDelay()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGamePacketDelay());
					}
					if(scoreBase.getGamePacketJitter()!=null&scoreBaseDul.getGamePacketJitter()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGamePacketJitter()+scoreBaseDul.getGamePacketJitter())/2);
					}else if(scoreBase.getGamePacketJitter()!=null&&scoreBaseDul.getGamePacketJitter()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGamePacketJitter());
					} else if(scoreBase.getGamePacketJitter()==null&&scoreBaseDul.getGamePacketJitter()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGamePacketJitter());
					}
					if(scoreBase.getGameLossRate()!=null&scoreBaseDul.getGameLossRate()!=null){
						scoreBase.setIcmpPingScore((scoreBase.getGameLossRate()+scoreBaseDul.getGameLossRate())/2);
					}else if(scoreBase.getGameLossRate()!=null&&scoreBaseDul.getGameLossRate()==null){
						scoreBase.setIcmpPingScore(scoreBase.getGameLossRate());
					} else if(scoreBase.getGameLossRate()==null&&scoreBaseDul.getGameLossRate()!=null){
						scoreBase.setIcmpPingScore(scoreBaseDul.getGameLossRate());
					}

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
					finalScore.setServiceType(6);
					finalScore.setTargetId(ite.getTargetId());
					finalScore.setTargetName(ite.getTargetName());
					finalScore.setAccessLayer(ite.getAccessLayer());
					finalScore.setPort(ite.getPort());
					finalScore.setRecordTime(ite.getRecordTime());
					finalScore.setRecordDate(ite.getRecordDate());
					finalScore.setGameDnsDelay(connection.get(ite).getGameDnsDelay());
					finalScore.setGameConnDelay(connection.get(ite).getGameConnDelay());
					finalScore.setGamePacketDelay(connection.get(ite).getGamePacketDelay());
					finalScore.setGamePacketJitter(connection.get(ite).getGamePacketJitter());
					finalScore.setGameLossRate(connection.get(ite).getGameLossRate());
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
		return recordHourGameDao.queryTotal(map);
	}
	
	@Override
	public void save(RecordHourGameEntity recordHourGame){
		recordHourGameDao.save(recordHourGame);
	}
	
	@Override
	public void update(RecordHourGameEntity recordHourGame){
		recordHourGameDao.update(recordHourGame);
	}
	
	@Override
	public void delete(Integer id){
		recordHourGameDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		recordHourGameDao.deleteBatch(ids);
	}
	
}
