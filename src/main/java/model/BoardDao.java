package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
	public int maxnum() {
		Connection conn = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery
					 ("select ifnull(max(num),0)  from board");
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, stmt, rs);
		}
		return 0;
	}
    public boolean insert(Board board) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into board"
			+ " (num, writer,pass,title,content,file1,regdate,"
			+ " readcnt, grp,grplevel,grpstep,boardid)"
			+ " values (?,?,?,?,?,?,now(),0,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getNum());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getPass());
			pstmt.setString(4, board.getTitle());
			pstmt.setString(5, board.getContent());
			pstmt.setString(6, board.getFile1());
			pstmt.setInt(7, board.getGrp());
			pstmt.setInt(8, board.getGrplevel());
			pstmt.setInt(9, board.getGrpstep());
			pstmt.setString(10, board.getBoardid());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		}
		return false;
    }
    //게시판 종류별 등록된 게시물건수 리턴
	public int boardCount(String boardid) { 
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//select count(*) from board : board 테이블에 등록된 레코드 건수
//select count(*) from board where boardid=1 : 공지사항 등록 건수			
//select count(*) from board where boardid=2 : 자유게시판 등록 건수			
			pstmt = conn.prepareStatement
				       ("select count(*) from board where boardid=?");
			pstmt.setString(1,boardid);
			rs = pstmt.executeQuery();
			if (rs.next())	return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, rs);
		}
		return 0;
	}
	public List<Board> list(String boardid,int pageNum,int limit) {
		//boardid : 게시판 종류
		//pageNum : 현재 페이지 번호
		//limit   : 페이지당 출력할 게시물 건수
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<Board>();
/*
		 order by grp desc : grp 값의 내림차순 정렬 => 1차정렬
		       grpstep asc : grpstep의 오름차순 정렬 => 2차정렬
		  limit 시작레코드번호,갯수 : 오라클에서는 사용불가.
		  현재페이지  시작레코드번호
		      1          0 
		      2         10
		      
		  => 조회되는 페이지에 맞는 레코드를 저장    
 */
		String sql ="select * from board where boardid=? "
				  + " order by grp desc, grpstep asc limit ?,?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardid);
			pstmt.setInt(2, (pageNum -1) * limit); //0이면 첫번째 레코드
			pstmt.setInt(3, limit); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setNum(rs.getInt("num"));
				b.setWriter(rs.getString("writer"));
				b.setPass(rs.getString("pass"));
				b.setTitle(rs.getString("title"));
				b.setContent(rs.getString("content"));
				b.setFile1(rs.getString("file1"));
				b.setGrp(rs.getInt("grp"));
				b.setGrplevel(rs.getInt("grplevel"));
				b.setGrpstep(rs.getInt("grpstep"));
				b.setReadcnt(rs.getInt("readcnt"));
				b.setRegdate(rs.getTimestamp("regdate"));
				b.setBoardid(rs.getString("boardid"));
				list.add(b);
			}
//			System.out.println(list);
			return list;
		} catch (SQLException e) {	
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, rs);
		}
		return null;		
	}    
	public Board selectOne(int num) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement
					 ("select * from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			  Board b = new Board();
			  b.setNum(rs.getInt("num"));
			  b.setWriter(rs.getString("writer"));
			  b.setPass(rs.getString("pass"));
			  b.setTitle(rs.getString("title"));
			  b.setContent(rs.getString("content"));
			  b.setFile1(rs.getString("file1"));
			  b.setGrp(rs.getInt("grp"));
			  b.setGrplevel(rs.getInt("grplevel"));
			  b.setGrpstep(rs.getInt("grpstep"));
			  b.setReadcnt(rs.getInt("readcnt"));
			  b.setRegdate(rs.getTimestamp("regdate"));
			  b.setBoardid(rs.getString("boardid"));
			  return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, rs);
		}
		return null;
	}
	public void readcntAdd(int num) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement
		("update board set readcnt = readcnt + 1 where num=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		}
	}
	public void grpStepAdd(int grp, int grpstep) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement
("update board set grpstep = grpstep + 1 where grp=? and grpstep>?");
			pstmt.setInt(1, grp);
			pstmt.setInt(2, grpstep);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		}
	}
	public boolean update(Board board) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql ="update  board "
		+ " set writer=?,title=?,content=?,file1=? where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getWriter());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFile1());
			pstmt.setInt(5, board.getNum());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		} return false;
	}
	public boolean delete(int num) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql ="delete from board where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		}
		return false;
	}
	
} //클래스 종료 
