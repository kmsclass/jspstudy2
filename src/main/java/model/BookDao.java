package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	public boolean insert(Book book) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
		 pstmt = conn.prepareStatement
				("insert into book (writer,title,content) values(?,?,?)");
		 pstmt.setString(1, book.getWriter());
		 pstmt.setString(2, book.getTitle());
		 pstmt.setString(3, book.getContent());
		 int cnt = pstmt.executeUpdate();
		 return cnt > 0;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, null);
		}
		return false;
	}
	public Book selectOne(String writer) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from book where writer=?");
			pstmt.setString(1, writer);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Book book = new Book();
				book.setWriter(rs.getString("writer"));
				book.setTitle(rs.getString("title"));
				book.setContent(rs.getString("content"));
				return book;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, rs);
		}
		return null;		
	}
	public List<Book> list() {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select * from book");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setWriter(rs.getString("writer"));
				book.setTitle(rs.getString("title"));
				book.setContent(rs.getString("content"));
				list.add(book);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(conn, pstmt, rs);
		}
		return null;		
	}
}
