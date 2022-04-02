package kr.happyjob.study.scm.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.scm.model.WorkOrderModel;
import kr.happyjob.study.scm.model.dailyOrderHistoryModel;
import kr.happyjob.study.scm.model.warehouseModel;

public interface dailyOrderHistoryDao {

	// list 조회
	public List<dailyOrderHistoryModel> listdailyOrderHistory(Map<String, Object> paramMap) throws Exception;
	// 총 값
	public int total(Map<String, Object> paramMap) throws Exception;
	// 단일 조회
	public WorkOrderModel onedailyOrderHistory(Map<String, Object> paramMap) throws Exception;
	// 창로 list 조회
	public List<warehouseModel> warehouse() throws Exception;
	//창고 제고 조회
		public warehouseModel whcnt(Map<String,Object> paramMap) throws Exception;
}