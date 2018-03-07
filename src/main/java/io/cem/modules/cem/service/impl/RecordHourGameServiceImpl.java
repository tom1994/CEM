package io.cem.modules.cem.service.impl;

import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.RecordGameDao;
import io.cem.modules.cem.entity.ScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordHourGameDao;
import io.cem.modules.cem.entity.RecordHourGameEntity;
import io.cem.modules.cem.service.RecordHourGameService;



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
	public List<RecordHourGameEntity> queryGameList(Map<String, Object> map){
		return recordHourGameDao.queryGameList(map);
	}

	@Override
	public List<RecordHourGameEntity> queryDayList(Map<String, Object> map){
		return recordHourGameDao.queryDayList(map);
	}
	
	@Override
	public List<ScoreEntity> calculateService6(List<RecordHourGameEntity> gameList){
		List<ScoreEntity> connectionScore = new ArrayList<>();
		try {
			PropertiesUtils pros = new PropertiesUtils();
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
				if ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game42"))) <= 0) {
					score += 100 * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 80-100
				else if (((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game42"))) > 0) && ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game43"))) <= 0)) {
					score += (80 + ((((gameList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("game43")))) * 20) / ((Double.parseDouble(pros.getValue("game42")) - (Double.parseDouble(pros.getValue("game43"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 60-80
				else if (((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game43"))) > 0) && ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game44"))) <= 0)) {
					score += (60 + ((((gameList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("game44")))) * 20) / ((Double.parseDouble(pros.getValue("game43")) - (Double.parseDouble(pros.getValue("game44"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 40-60
				else if (((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game44"))) > 0) && ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game45"))) <= 0)) {
					score += (40 + ((((gameList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("game45")))) * 20) / ((Double.parseDouble(pros.getValue("game44")) - (Double.parseDouble(pros.getValue("game45"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 20-40
				else if (((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game45"))) > 0) && ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game46"))) <= 0)) {
					score += (20 + ((((gameList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("game46")))) * 20) / ((Double.parseDouble(pros.getValue("game45")) - (Double.parseDouble(pros.getValue("game46"))))))) * (Double.parseDouble(pros.getValue("game41")));
				}
				//loss_rate 0-20
				else if (((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game46"))) > 0) && ((gameList.get(i).getLossRate()).compareTo(Double.parseDouble(pros.getValue("game47"))) <= 0)) {
					score += ((((gameList.get(i).getLossRate().doubleValue()) - (Double.parseDouble(pros.getValue("game47")))) * 20) / ((Double.parseDouble(pros.getValue("game46")) - (Double.parseDouble(pros.getValue("game47")))))) * (Double.parseDouble(pros.getValue("game41")));
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
				finalScore.setScore(score);
				finalScore.setBase(Double.parseDouble(pros.getValue("gameweight")));
				connectionScore.add(finalScore);
				
				
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
