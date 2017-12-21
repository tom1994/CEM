package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.entity.ScoreBaseEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.entity.ScoreTargetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import io.cem.modules.cem.dao.RecordHourTracertDao;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.service.RecordHourTracertService;



@Service("recordHourTracertService")
public class RecordHourTracertServiceImpl implements RecordHourTracertService {
	@Autowired
	private RecordHourTracertDao recordHourTracertDao;
	
	@Override
	public RecordHourTracertEntity queryObject(Integer id){
		return recordHourTracertDao.queryObject(id);
	}
	
	@Override
	public List<RecordHourTracertEntity> queryList(Map<String, Object> map){
		return recordHourTracertDao.queryList(map);
	}

	@Override
	public List<RecordHourTracertEntity> queryTracertList(Map<String, Object> map){
		return recordHourTracertDao.queryTracertList(map);
	}

	@Override
	public List<RecordHourTracertEntity> queryDayList(Map<String, Object> map){
		return recordHourTracertDao.queryDayList(map);
	}

	@Override
	public List<ScoreEntity> calculateService0(List<ScoreEntity> connection, List<ScoreEntity> quality, List<ScoreEntity> broswer, List<ScoreEntity> download, List<ScoreEntity> video, List<ScoreEntity> game){
		List<ScoreEntity> finalScore = new ArrayList<>();

		Map<ScoreTargetEntity, ScoreBaseEntity> score = new HashMap<>();

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
			ScoreBaseEntity scoreBase = new ScoreBaseEntity();
			scoreBase.setScore((connection.get(i).getScore()) * (connection.get(i).getBase()));
			scoreBase.setBase(connection.get(i).getBase());
			score.put(scoreTarget, scoreBase);
		}

		score=putMap(quality,score);
		score=putMap(broswer,score);
		score=putMap(download,score);
		score=putMap(video,score);
		score=putMap(game,score);

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
			lastScore.setScore(score.get(ite).getScore() / score.get(ite).getBase());
			lastScore.setBase(score.get(ite).getBase());
			finalScore.add(lastScore);
			id++;
		}



		return finalScore;
	}

	public Map<ScoreTargetEntity,ScoreBaseEntity> putMap(List<ScoreEntity> list,Map<ScoreTargetEntity,ScoreBaseEntity> map){
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

			if (!map.containsKey(scoreTarget)) {
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase(list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
			} else {
				ScoreBaseEntity scoreBaseDul = map.get(scoreTarget);
				ScoreBaseEntity scoreBase = new ScoreBaseEntity();
				scoreBase.setScore((scoreBaseDul.getScore()) + (list.get(i).getScore()) * (list.get(i).getBase()));
				scoreBase.setBase((scoreBaseDul.getBase()) + list.get(i).getBase());

				map.put(scoreTarget, scoreBase);
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
