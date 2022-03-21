package kr.happyjob.study.sup.dao;

import java.util.List;
import java.util.Map;
import kr.happyjob.study.sup.model.AwardMgtModel;

public interface AwardMgtDao {

  /** 수상 목록 조회 */
  public List<AwardMgtModel> listAward(Map<String, Object> paramMap);

  /** 수상 목록 카운트 조회 */
  public int countListAward(Map<String, Object> paramMap);

  /** 상담 등록 */
  public void insertAward(Map<String, Object> paramMap);

  /** 상담 수정 */
  public void updateAward(Map<String, Object> paramMap);

  /** 상담 조회 */
  public AwardMgtModel selectAward(Map<String, Object> paramMap);

  //  /** 수강 학생 목록 조회 */
  //  public List<AdviceMgtModel> listUser(Map<String, Object> paramMap);
  //
  //  /** 수강 학생 목록 카운트 조회 */
  //  public int countListUser(Map<String, Object> paramMap);
  //
  //  /** 상담 목록 조회 */
  //  public List<AdviceMgtModel> listAdvice(Map<String, Object> paramMap);
  //  

  //
  //  /** 상담 목록 카운트 조회 */
  //  public int countListAdvice(Map<String, Object> paramMap);
  //

  //  
  //  /** 과정 상세 조회 */
  //  public CourseModel selectCourse(Map<String, Object> paramMap);
  //  
  //  
  //  /** 수강신청 인원 조회 */
  //  public int countListUser(Map<String, Object> paramMap);

}