package controller;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
//http://localhost:8080/jspstudy2/board/writeForm
@WebServlet(urlPatterns= {"/board/*"},
initParams= {@WebInitParam(name="view",value="/view/")})
public class BoardController extends MskimRequestMapping {
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
}
