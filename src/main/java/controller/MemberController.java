package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Member;
import model.MemberDao;
//  /member/* => http://localhost:8080/jspstudy2/member/이후의 어떤 요청이
//               들어와도 MemberController 서블릿이 호출됨.
// /view/ =>  /jspstudy2/src/main/webapp/view/ 폴더
@WebServlet(urlPatterns= {"/member/*"},
   initParams= {@WebInitParam(name="view",value="/view/")})
public class MemberController extends MskimRequestMapping{
	private MemberDao dao = new MemberDao();
	
	//로그인 검증. id파라미터와 로그인정보 검증
	public String loginIdCheck(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String login=(String)request.getSession().getAttribute("login");
		if(login==null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if (!login.equals("admin") && !id.equals(login)) {
			request.setAttribute("msg", "본인만 거래 가능합니다.");
			request.setAttribute("url", "main");
			return "alert";			
		}
		return null;
	}
	//http://localhost:8080/jspstudy2/member/loginForm
	@RequestMapping("loginForm")
	public String loginForm(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/loginForm"; //view 선택 
		//  /view/member/loginForm.jsp => view 이름
	}
	@RequestMapping("login")
	public String login(HttpServletRequest request,
			HttpServletResponse response) {
		//1. 파라미터 변수 저장하기
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		//2. 비밀번호 검증
		Member mem = dao.selectOne(id);
		String msg = null;
		String url = null;
		if(mem == null) {
			msg = "아이디를 확인하세요";
			url = "loginForm";
		} else if (!pass.equals(mem.getPass())) { //아이디 존재. 비밀번호검증
			msg = "비밀번호가 틀립니다.";
			url = "loginForm";
		} else {  //정상적인 로그인
			request.getSession().setAttribute("login", id);//로그인정보등록
			msg="반갑습니다." + mem.getName() +"님";
			url = "main";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert"; //view 이름 : /view/alert.jsp
	}
	@RequestMapping("main")
	public String main(HttpServletRequest request,
			HttpServletResponse response) {
		//request.getSession() : session 객체
		String login=(String)request.getSession().getAttribute("login");
		if(login == null) {  //로그아웃상태
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "alert";  // /view/alert.jsp 페이지 forward 됨
		}
		return "member/main";  // /view/member/main.jsp 페이지로 forward됨
	}
	/*
	 * 1. session에 등록된 로그인 정보 제거
	 * 2. loginForm 으로 페이지 이동
	 */
	@RequestMapping("logout")
	public String logout
	   (HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:loginForm"; 
	}
	/*
  1. id 파라미터값을 조회
  2. 로그인상태 검증
     - 로그아웃상태 : '로그인하세요' 메세지 출력 후 loginForm.jsp 페이지 호출
     - 로그인 상태 
         - 다른 id 조회(관리자제외) : 
                      '내정보 조회만 가능합니다.' 메세지 출력 후  main.jsp 호출
  3. db에서 id에 해당하는 데이터 조회하기                    
  4. view로 데이터를 전송 => request 객체의 속성등록
	 */
	@RequestMapping("info")
	@MSLogin("loginIdCheck")
	public String info
	   (HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		request.setAttribute("mem", mem);
		return "member/info"; 
	}
	/*
   1. 파라미터 정보를 Member 객체에 저장. 인코딩 필요
   2. Member 객체를 이용하여 db에 insert (member 테이블) 저장
   3. 가입성공 : 성공 메세지 출력 후 loginForm 페이지 이동
      가입실패 : 실패 메세지 출력 후 joinForm 페이지 이동
 	 */
	@RequestMapping("join")
	public String join(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setGender(Integer.parseInt(request.getParameter("gender")));
		mem.setTel(request.getParameter("tel"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));
		if(dao.insert(mem)) {
			request.setAttribute
			         ("msg", mem.getName()+"님 회원 가입 되었습니다.");
			request.setAttribute("url", "loginForm");
		} else {
			request.setAttribute
	         ("msg", mem.getName()+"님 회원가입시 오류가 발생되었습니다.");
 	        request.setAttribute("url", "joinForm");
		}
		return "alert";
	}	
	/*
  1. id 파라미터값을 조회
  2. 로그인상태 검증
     - 로그아웃상태 : '로그인하세요' 메세지 출력 후 loginForm 페이지 호출
     - 로그인 상태 
         - 다른 id 수정(관리자제외) : 
                      '내정보만 수정이 가능합니다.' 메세지 출력 후  main 호출
  3. db에서 id에 해당하는 데이터 조회하기                    
  4. 조회된 내용 화면 출력하기 => 이전데이터를 화면 출력. 수정전화면 출력 
	 */
	@RequestMapping("updateForm")
	@MSLogin("loginIdCheck")
	public String updateForm(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		request.setAttribute("mem", mem);
		return "member/updateForm";
	}
/*
   1. 모든 파라미터를 Member 객체에 저장하기
   2. 입력된 비밀번호와 db에 저장된 비밀번호 비교.
       관리자인 경우 관리자비밀번호로 비교
       불일치 : '비밀번호 오류' 메세지 출력후 updateForm 페이지 이동 
   3. Member 객체의 내용으로 db를 수정하기
       - 성공 : 회원정보 수정 완료 메세지 출력후 info로 페이지 이동
       - 실패 : 회원정보 수정 실패 메세지 출력 후 updateForm 페이지 이동  */
	@RequestMapping("update")
	@MSLogin("loginIdCheck")
	public String update (HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setGender(Integer.parseInt(request.getParameter("gender")));
		mem.setTel(request.getParameter("tel"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));
		String login = 
				(String)request.getSession().getAttribute("login");
		Member dbMem = dao.selectOne(login);
		String msg = "비밀번호가 틀렸습니다.";
		String url = "updateForm?id="+mem.getId();
		if(mem.getPass().equals(dbMem.getPass())) {
			if(dao.update(mem)) {
				msg = "회원 정보 수정 완료";
				url = "info?id="+mem.getId();
			} else {
				msg = "회원 정보 수정 실패";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	/*
  1. id 파라미터 저장
  2. 로그인 정보 검증
      - 로그아웃 상태 : 로그인하세요 메세지 출력. loginForm로 페이지 이동
      - 관리자제외. 다른사용자 탈퇴 불가
         본인만 탈퇴 가능합니다. 메세지 출력. main 페이지로 이동
  3. deleteForm.jsp 페이지 호출      
	 */
	@RequestMapping("deleteForm")
	@MSLogin("loginIdCheck")
	public String deleteForm(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/deleteForm";
	}
/*
   1. 파라미터 정보 저장
   2. 로그인정보 검증
       - 로그아웃상태 : 로그인하세요 메세지 출력 후 loginForm로 페이지 이동
       - 다른사람 탈퇴(관리자 제외) : 본인만 탈퇴 가능 메세지 출력 main 페이지 이동
   3. 관리자 탈퇴는 검증
       - 관리자 정보 탈퇴 : 관리자는 탈퇴 불가. list 페이지 이동
   4. 비밀번호 검증
       로그인 정보로 비밀번호 검증
       - 비밀번호 불일치 : 비밀번호 오류 메세지 출력. deleteForm로 페이지 이동        
   5. db에서 delete 실행
         boolean MemberDao.delete(id)
       - 탈퇴성공 : 
         - 일반사용자 : 로그아웃 실행. 탈퇴 완료 메세지 출력 후 loginForm로 페이지 이동 
         - 관리자    : 탈퇴 완료 메세지 출력 후 list로 페이지 이동 
       - 탈퇴실패      
         - 일반사용자 : 탈퇴 실패 메세지 출력 후 info로 페이지 이동 
         - 관리자    : 탈퇴 실패 메세지 출력 후 list로 페이지 이동 
 */
	@RequestMapping("delete")
	@MSLogin("loginIdCheck")
	public String delete(HttpServletRequest request,
			HttpServletResponse response) {
	  String id = request.getParameter("id");
	  String pass = request.getParameter("pass");
	  String login =(String)request.getSession().getAttribute("login");
	  String msg = null;
	  String url = null;
	  if (id.equals("admin")) {
		  request.setAttribute("msg", msg);
		  request.setAttribute("url", url);
		  return "alert";
	  }
	  MemberDao dao = new MemberDao();
	  Member dbMem = dao.selectOne(login);
	  if(!pass.equals(dbMem.getPass())) {
	  	  request.setAttribute("msg", "비밀번호 오류"); 
	  	  request.setAttribute("url", "deleteForm?id="+id);
	  	  return  "alert";
	  }
	  if(dao.delete(id)) {
		msg=id +"고객님 탈퇴성공";
	   	if(login.equals("admin")) {
	   	  url = "list";
	   	} else {
	   	  request.getSession().invalidate();
	   	  url = "loginForm";
	   	}
     } else {
		msg=id +"고객님 탈퇴시 오류 발생. 탈퇴 실패";
	   	if(login.equals("admin")) {
	   	  url = "list";
	   	} else {
	   	  url = "info?id="+id;
	   	}
	 }
	 request.setAttribute("msg", msg);
	 request.setAttribute("url", url);
     return "alert";
   }
}

