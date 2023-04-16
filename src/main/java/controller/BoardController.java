package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Board;
import model.BoardDao;
//http://localhost:8080/jspstudy2/board/writeForm
@WebServlet(urlPatterns= {"/board/*"},
initParams= {@WebInitParam(name="view",value="/view/")})
public class BoardController extends MskimRequestMapping {
	private BoardDao dao = new BoardDao();
	@RequestMapping("writeForm")
	public String writeForm(HttpServletRequest request,
			HttpServletResponse response) {
		String boardid =(String)request.getSession().getAttribute("boardid");
		if(boardid == null) boardid="1";
		String login =(String)request.getSession().getAttribute("login");
		if(boardid.equals("1")) {
			if(login == null || !login.equals("admin")) {
				request.setAttribute
				       ("msg", "관리자만 공지사항 글쓰기가 가능합니다.");
				request.setAttribute("url",
		request.getContextPath()+"/board/list?boardid="+boardid);
				return "alert";
			}
		}
		return "board/writeForm";
	}
	@RequestMapping("write")
	public String write(HttpServletRequest request,
			HttpServletResponse response) {
	   String path=request.getServletContext().getRealPath("/")+"/upload/";
	   File f = new File(path);
	   if(!f.exists()) f.mkdirs();
	   int size=10*1024*1024;
	   MultipartRequest multi = null;
	   try {
		   multi = new MultipartRequest(request,path,size,"UTF-8");
	   } catch(IOException e) {
		   e.printStackTrace();
	   } 
	   Board board = new Board();
	   board.setWriter(multi.getParameter("writer"));
	   board.setPass(multi.getParameter("pass"));
	   board.setTitle(multi.getParameter("title"));	   
	   board.setContent(multi.getParameter("content"));
	   board.setFile1(multi.getFilesystemName("file1"));
	   String boardid = 
			   (String)request.getSession().getAttribute("boardid");
	   if(boardid==null) boardid="1";
	   board.setBoardid(boardid);
	   if(board.getFile1()==null) board.setFile1("");
	   int num = dao.maxnum();
	   board.setNum(++num);
	   board.setGrp(num);
	   String msg = "게시물 등록 실패";
	   String url = "writeForm";
	   if(dao.insert(board)) { 
		   return "redirect:list?boardid="+boardid;
	   }
	   request.setAttribute("msg", msg);
	   request.setAttribute("url", url);
	   return "alert";
	}
}
