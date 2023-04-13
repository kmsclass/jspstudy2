package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Book;
import model.BookDao;

@WebServlet(urlPatterns= {"/book/*"},
initParams= {@WebInitParam(name="view",value="/view/")})
public class BookController extends MskimRequestMapping{
	BookDao dao = new BookDao();
	@RequestMapping("testForm")
	public String testForm(HttpServletRequest request ,
			HttpServletResponse response ) {
		return "book/testForm";
	}
	@RequestMapping("test1")
	public String test1(HttpServletRequest request ,
			HttpServletResponse response ) {
	    try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    Book book = new Book();
	    book.setWriter(request.getParameter("writer"));
	    book.setTitle(request.getParameter("title"));
	    book.setContent(request.getParameter("content"));
	    if(dao.insert(book)) {
	    	Book dbBook = dao.selectOne(book.getWriter());
		    request.setAttribute("book", dbBook);
			return "book/test1";
	    } else {
	    	request.setAttribute("msg", "방명록 등록시 오류 발생");
	    	request.setAttribute("url", "testForm");
	    	return "alert";
	    }
	}
}
