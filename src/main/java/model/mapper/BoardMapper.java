package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Board;

public interface BoardMapper {
	@Select("select ifnull(max(num),0)  from board")
	int maxnum();
	String sql = "insert into board"
			+ " (num, writer,pass,title,content,file1,regdate,"
			+ " readcnt, grp,grplevel,grpstep,boardid)"
			+ " values (#{num},#{writer},#{pass},#{title},"
			+ "#{content},#{file1},now(),0,"
			+ "#{grp},#{grplevel},#{grpstep},#{boardid})";
 
	@Insert(sql)
	int insert(Board board);

	String sqlcol = "<if test='column != null'>" 		
	+"<if test='col1 != null'> and (${col1} like '%${find}%'</if>"
	+"<if test='col2 == null'> ) </if>"
	+"<if test='col2 != null'> or ${col2} like '%${find}%'</if>"
	+"<if test='col2 != null and col3==null'> ) </if>"
	+"<if test='col3 != null'> or ${col3} like '%${find}%')</if></if>";
	@Select({"<script>",
	"select count(*) from board where boardid=#{boardid}"+sqlcol,
	"</script>"})
	int boardCount(Map<String, Object> map);
	@Select({"<script>" ,"SELECT *,"
	+ " (SELECT COUNT(*) FROM comment c WHERE c.num = b.num) commcnt"
	+ " FROM board b where boardid=#{boardid}  " + sqlcol
	+ " order by grp desc, grpstep asc limit #{start},#{limit}",
	"</script>"})
	List<Board> selectlist(Map<String, Object> map);
	
	@Select("select * from board where num = #{value}")
	Board selectone(int num);
	@Update("update board set readcnt = readcnt + 1 where num=#{value}")
	void readcntAdd(int num);
	@Update("update board set grpstep = grpstep + 1 "
			+ " where grp=#{grp} and grpstep>#{grpstep}")
	void grpStepAdd(@Param("grp")int grp, @Param("grpstep")int grpstep);
	@Update("update  board "
		+ " set writer=#{writer},title=#{title},content=#{content},"
		+ "file1=#{file1} where num=#{num}")
	int update(Board board);
	@Delete("delete from board where num=#{value}")
	int delete(int num);

	// [{"writer":"홍길동","cnt":6},{"writer":"김삿갓","cnt":5}...]
	@Select("select writer, count(*) cnt from board group by writer "
			+ "having count(*) > 1 order by cnt desc")
	List<Map<String, Object>> graph();
	//[{regdate=2023-05-08,cnt=8},...]
	@Select("SELECT date_format(regdate,'%Y-%m-%d') regdate,"
			+ " COUNT(*) cnt FROM board "
			+ "	GROUP BY date_format(regdate,'%Y-%m-%d') "
			+ "	ORDER BY 1 desc"
			+ "	LIMIT 0,7")
	List<Map<String, Object>> graph2();
	
}
