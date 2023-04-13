package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

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
//===================================================	
	public String loginCheck(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String login=(String)request.getSession().getAttribute("login");
		if(login==null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		}
		return null;
	}
	//로그인 검증. id파라미터와 로그인정보 검증
	public String loginIdCheck(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
	public String loginAdminCheck(HttpServletRequest request,
			HttpServletResponse response) {
		String login=(String)request.getSession().getAttribute("login");
		if(login==null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if (!login.equals("admin")) {
			request.setAttribute("msg", "관리자만 거래 가능합니다.");
			request.setAttribute("url", "main");
			return "alert";			
		}
		return null;
	}
//=======================================================	
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
	  if (id.equals("admin")) {  //
		  request.setAttribute("msg", "관리자는 탈퇴 못합니다.");
		  request.setAttribute("url", "list");
		  return "alert";
	  }
	  MemberDao dao = new MemberDao();
	  Member dbMem = dao.selectOne(login); //로그인된 사용자의 비밀번호로 검증
	  if(!pass.equals(dbMem.getPass())) {
	  	  request.setAttribute("msg", "비밀번호 오류"); 
	  	  request.setAttribute("url", "deleteForm?id="+id);
	  	  return  "alert";
	  }
	  //비밀번호 일치=>고객정보삭제
	  if(dao.delete(id)) {  //삭제 성공
		msg=id +"고객님 탈퇴성공";
	   	if(login.equals("admin"))   url = "list";
	   	else {  //일반사용자
	   	  request.getSession().invalidate(); //로그아웃
	   	  url = "loginForm";
	   	}
     } else {  //삭제 실패
		msg=id +"고객님 탈퇴시 오류 발생. 탈퇴 실패";
	   	if(login.equals("admin"))   url = "list";
	   	else                        url = "info?id="+id;
	 }
	 request.setAttribute("msg", msg);
	 request.setAttribute("url", url);
     return "alert";
   }
	/*
  1. 관리자만 사용가능 페이지=> 검증
    - 로그아웃상태 : 로그인이 필요합니다. 메세지 출력. loginForm.jsp 페이지이동
    - 로그인 상태 : 일반사용자 로그인시 "관리자만 가능합니다." 메세지 출력 main.jsp 페이지 이동\
  2. db에서 모든 회원정보를 조회. List<Member> 객체로 리턴
      List<Member> MemberDao.list()
  3. list 화면 전달 	  
	 */
   @RequestMapping("list")
   @MSLogin("loginAdminCheck")
   public String list(HttpServletRequest request,
		   HttpServletResponse response) {
	   List<Member> list = dao.list();
	   request.setAttribute("list", list);
	   return "member/list";
   }
   /*
   1. 이미지파일 업로드. request 객체 사용 불가
           이미지파일 업로드의 위치 : 현재URL/picture 폴더로 설정
   2. opener 화면에 결과 전달 =>javascript
   3. 현재 화면 close() =>javascript
    */
   @RequestMapping("picture")
   public String picture(HttpServletRequest request,
		   HttpServletResponse response) {
	   //request.getServletContext() : application 객체
	   //request.getServletContext().getRealPath("/") 
	   //  : 실제 웹어플리케이션 경로.
	   String path = request.getServletContext().getRealPath("/") 
			             + "/picture/";
	   String fname = null;
	   File f = new File(path);
	   if(!f.exists()) {   f.mkdirs();   }  //업로드 폴더가 없는 경우 폴더 생성
	   MultipartRequest multi=null;
	   try {
		   //request : 요청객체. 파라미터,파일의내용,파일이름
		   //path    : 업로드된 파일이 저장될 폴더
		   //10*1024*1024 : 업로드 파일의 최대 크기 바이트수. 10MB최대크기
		   //utf-8    : 인코딩 코드
		multi = new MultipartRequest(request,path,10*1024*1024,"utf-8");
	   } catch (IOException e) {
		  e.printStackTrace();
	   }
	   //fname : 업로드된 파일 이름
	   fname = multi.getFilesystemName("picture"); //업로드된 파일의 이름
	   request.setAttribute("fname", fname);
	   return "member/picture";
   }
   /*
    */
   @RequestMapping("idchk")
   public String idchk(HttpServletRequest request,
			   HttpServletResponse response) {
		String id= request.getParameter("id");
	    Member mem = new MemberDao().selectOne(id);
	    String msg = null;
	    boolean able = true; 
		if(mem == null) {
			msg = "사용가능한 아이디 입니다.";
		} else {
			msg = "사용 중인 아이디 입니다.";
		    able = false;
		}
	    request.setAttribute("able", able);
	    request.setAttribute("msg", msg);
	    return "member/idchk";
   }
   /*
   1. 파라미터값 저장(email,tel)
   2. db에서 두개의 파라미터를 이용하여 id값 리턴해주는 함수
      id MemberDao.idSearch(email,tel)
   3. id 검증
       -id 존재 :화면에 뒤쪽 2자만 ** 표시하여 화면에 출력하기.
                아이디전송 버튼을 클릭하면 opener 윈도우에 id값 전달. 현재 화면 닫기
       -id 없음 : id가 없습니다. 메세지 출력후 현재화면을 idForm.jsp 페이지 이동     */
   @RequestMapping("id")
   public String id(HttpServletRequest request,
			   HttpServletResponse response) {
	   String email = request.getParameter("email");
	   String tel = request.getParameter("tel");
	   MemberDao dao = new MemberDao();
	   String id = dao.idSearch(email,tel); 
	   if(id != null) { //id 찾은 경우   
		   String showId = id.substring(0,id.length()-2);
		   request.setAttribute("id", showId);
		   return "member/id";
	   } else {
		   request.setAttribute("msg", "아이디를 찾을 수 없습니다.");
		   request.setAttribute("url", "idForm");
		   return "alert";
	   }
   }
   /*
  1. 파라미터 저장.
  2. db에서 id,email과 tel 을 이용하여 pass값을 리턴
       pass = MemberDao.pwSearch(id,email,tel)
  3. 비밀번호 검증 
     비밀번호 찾은 경우 :화면에 앞 두자리는 **로 표시하여 화면에 출력. 닫기버튼 클릭시 
                     현재 화면 닫기
     비밀번호 못찾은 경우: 정보에 맞는 비밀번호를 찾을 수 없습니다.  메세지 출력후
                     현재 페이지를 pwForm.jsp로 페이지 이동. 
    */
   @RequestMapping("pw")
   public String pw(HttpServletRequest request,
			   HttpServletResponse response) {
	   String id = request.getParameter("id");
	   String email = request.getParameter("email");
	   String tel = request.getParameter("tel");
	   MemberDao dao = new MemberDao();
	   String pass = dao.pwSearch(id,email,tel);
	   if(pass != null) {
		   request.setAttribute("pass", pass.substring(2,pass.length()));
	       return "member/pw";
	   } else {
		   request.setAttribute("msg", "비밀번호를 찾을 수 없습니다.");
		   request.setAttribute("url", "pwForm");
		   return "alert";
	   }
   }
   @RequestMapping("passwordForm")
   @MSLogin("loginCheck")
   public String passwordForm(HttpServletRequest request,
		   HttpServletResponse response) {
	   return "member/passwordForm";
   }

   /*
   1. 파라미터 저장 (pass,chgpass)
   2. 로그인한 사용자의 비밀번호 변경만 가능.=> 로그인부분 검증
      로그아웃상태 : 로그인 하세요 메세지 출력후 
                  opener 창을 loginForm.jsp 페이지로 이동. 현재페이지 닫기
   3. 비밀번호 검증 : 현재비밀번호로 비교
      비밀번호 오류 : 비밀번호 오류 메세지 출력 후 현재페이지를 passwordForm.jsp로 이동                
   4. db에 비밀번호 수정
       boolean MemberDao.updatePass(id,변경비밀번호)
       - 수정성공 : 성공메세지 출력 후
                  (로그아웃되었습니다. 변경된 비밀번호로 다시로그인 하세요) 
                // opener 페이지 info.jsp로 이동.현재 페이지 닫기
                  로그아웃 후 opener 페이지 loginForm.jsp로 이동.현재 페이지 닫기     
       - 수정실패 : 실패메세지 출력 후 opener 페이지 updateForm.jsp로 이동.
                  현재 페이지 닫기      
    */
   @RequestMapping("password")
   public String password(HttpServletRequest request,
		   HttpServletResponse response) {
	   String pass = request.getParameter("pass");
	   String chgpass = request.getParameter("chgpass");
	   String login = (String)request.getSession().getAttribute("login");
	   String msg = null;
	   String url = null;
	   boolean opener = true;
	   if(login == null) {
		   msg = "로그인하세요";
		   url = "loginForm.jsp";
		   opener = true;
	   } else { 
		   MemberDao dao = new MemberDao();
		   Member dbmem = dao.selectOne(login);
		   if(pass.equals(dbmem.getPass())) {
			   if(dao.updatePass(login,chgpass)) {
//				   msg = "비밀번호가 변경되었습니다";
//				   url = "info.jsp?id=" + login;
	               msg = "비밀번호가 변경되었습니다 \\n  다시로그인 하세요";
	  	           request.getSession().invalidate();
	  	           url = "loginForm";
				   opener = true;
			   } else {
				   msg = "비밀번호가 변경시 오류발생";
				   url = "updateForm?id=" + login;
				   opener = true;
			   }
		   } else {  //비밀번호 오류
			 opener = false;
		     msg = "비밀번호 오류입니다.";
		     url = "passwordForm";
		   }
	   }
	   request.setAttribute("msg", msg);
	   request.setAttribute("url", url);
	   request.setAttribute("opener", opener);
	   return "member/password";
   }
}

