package aproject.vo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.CallableStatement;
import com.shinhan.dbutil.OracleUtil;

import aproject.vo.EmpVO;

//DAO(Data Access Object):DB업무 ..CRUD..Insert,Select,Update,Delete 
public class EmpDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst;// ?지원
	java.sql.CallableStatement cst;//SP지원
	ResultSet rs;
	int resultCount;// insert, update, delete건수

	public List<EmpVO> selectAll() {
		String sql = "select * from employees order by 1";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}
	
	//SP 호출
		public EmpVO getSalary(int empid) {
			String sql = "{ call sp_salary(?,?,?) }";
			conn = OracleUtil.getConnection();
			EmpVO emp = new EmpVO();
			try {
					cst = conn.prepareCall(sql);
					cst.setInt(1, empid);
					cst.registerOutParameter(2, Types.DOUBLE);
					cst.registerOutParameter(3, Types.VARCHAR);
					cst.execute(); 		//resultset있으면 true이고 없으면 false
					//executeQuery(), executeUpdate()
					emp.setSalary(cst.getDouble(2));
					emp.setFirst_name(cst.getString(3));
				} catch (SQLException e) {
					e.printStackTrace();
					} finally {
						OracleUtil.dbDisconnect(null, cst, conn);
				
					}
			return emp;
			
		}
		
	//자신의 속한 부서의 평균 급여보다 더 적은 급여를 받는 직원들을 조회하시오
	public List<EmpVO> selectLAB() {
		String sql = """
				select employee_id, first_name, salary, employees.department_id
				from employees, 
                                 (select department_id, avg(salary) sal
                                from employees
                                group by  department_id )  inlineview_emp
				where employees.department_id = inlineview_emp.department_id
				and employees.salary < inlineview_emp.sal
				""";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpVO emp = makeEmp2(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}
	
	
	// 특정직원 조회
	public EmpVO selectById(int empid) {
		EmpVO emp = null;
		String sql = "select * from employees where employee_id = " + empid;
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emp;
	}

	// 특정부서의 직원조회
	public List<EmpVO> selectByDept(int deptid) {
		String sql = "select * from employees where department_id = " + deptid;
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}

	// 특정부서, jobid, salary이상 직원조회
	public List<EmpVO> selectByCondition(int deptid, String jobid, double salary) {
		String sql = "select * "
				+ " from employees "
				+ " where department_id = ? "
				+ " and job_id = ? "
				+ " and salary >= ? ";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid);
			pst.setString(2, jobid);
			pst.setDouble(3, salary);
			rs = pst.executeQuery();
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		return emplist;
	}

	//신규직원등록 (insert)
	public int empInsert(EmpVO emp) {
		String sql="""
				insert into employees 
				values(seq_employee.nextval,?,?,?,?,?,?,?,?,?,?)
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, emp.getFirst_name() );
			pst.setString(2, emp.getLast_name() );
			pst.setString(3, emp.getEmail() );
			pst.setString(4, emp.getPhone_number() );
			pst.setDate(5, emp.getHire_date() );
			pst.setString(6, emp.getJob_id() );
			pst.setDouble(7, emp.getSalary());
			pst.setDouble(8, emp.getCommission_pct() );
			pst.setInt(9, emp.getManager_id() );
			pst.setInt(10, emp.getDepartment_id() );

			resultCount = pst.executeUpdate();//DML문장실행한다.영향받은건수가 return 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 OracleUtil.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//직원정보 수정하기 
	public int empUpdate(EmpVO emp) {
		String sql = """
				update employees
				set EMAIL = ?, DEPARTMENT_ID=?, JOB_ID=?, SALARY=?
				where EMPLOYEE_ID = ?
				
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, emp.getEmail() );
			pst.setString(3, emp.getJob_id() );
			pst.setDouble(4, emp.getSalary());
			pst.setInt(2, emp.getDepartment_id() );
			pst.setInt(5, emp.getEmployee_id() );

			resultCount = pst.executeUpdate();//DML문장실행한다.영향받은건수가 return 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 OracleUtil.dbDisconnect(null, pst, conn);
		}
		System.out.println("update결과:" + resultCount);
		return resultCount;
		
	}
	
	
	//한건의 직원을 삭제하기
	public int empDelete(int empid) {
		String sql = """
				delete from employees
				where EMPLOYEE_ID = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, empid);

			resultCount = pst.executeUpdate();//DML문장실행한다.영향받은건수가 return 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultCount = -1;
			e.printStackTrace();
		} finally {
			 OracleUtil.dbDisconnect(null, pst, conn);
		}
		System.out.println("delete결과:" + resultCount);
		return resultCount;
		
	}
	
	
	private EmpVO makeEmp(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();
		emp.setCommission_pct(rs.getDouble("Commission_pct"));
		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmail(rs.getString("Email"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setHire_date(rs.getDate("Hire_date"));
		emp.setJob_id(rs.getString("Job_id"));
		emp.setLast_name(rs.getString("Last_name"));
		emp.setManager_id(rs.getInt("Manager_id"));
		emp.setPhone_number(rs.getString("Phone_number"));
		emp.setSalary(rs.getDouble("Salary"));

		return emp;
	}
	
	private EmpVO makeEmp2(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();
		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setSalary(rs.getDouble("Salary"));

		return emp;
	}
	
	
}