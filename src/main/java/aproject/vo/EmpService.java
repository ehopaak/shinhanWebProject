package aproject.vo;

import java.util.List;

import aproject.vo.EmpVO;

//Service : 업무로직 담당 
public class EmpService {

	EmpDAO empDao = new EmpDAO();
	
	//SP 호출
	public EmpVO getSalary(int empid) {
		return empDao.getSalary(empid);
		
	}
	
	public List<EmpVO> selectAll() {
		return empDao.selectAll();
	}
	
	public EmpVO selectById (int empid) {
		return empDao.selectById(empid);
	}
	
	public List<EmpVO> selectByDept(int deptid) {
		return empDao.selectByDept(deptid);
	}
	
	public List<EmpVO> selectByCondition (int deptid,String jobid,double salary) {
		return empDao.selectByCondition(deptid, jobid, salary);
	}
	
	//자신의 속한 부서의 평균 급여보다 더 적은 급여를 받는 직원들을 조회하시오
	public List<EmpVO> selectLAB() {
		 List<EmpVO> empList = empDao.selectLAB();
		 System.out.println("[empservice] 실행건수" + empList.size());
		 return empList;
	}
	
	//신규직원등록 (insert)
	public String empInsert(EmpVO emp) {
		int result = empDao.empInsert (emp);
		return result>0?"입력성공":"입력실패";
			
	}
	
	public String empUpdate(EmpVO emp) {
		int result = empDao.empUpdate(emp);
		return result>0?"update 성공":"update 실패";
	}
	
	//한건의 직원을 삭제하기
		public String empDelete(int empid) {
			int result = empDao.empDelete(empid);
			return result>0?"Delete 성공":"Delete 실패";
		}
}
