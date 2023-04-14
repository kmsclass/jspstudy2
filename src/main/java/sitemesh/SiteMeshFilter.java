package sitemesh;
/*
 * 1. sitemesh 패키지 생성
 * 2. SiteMeshFilter class 생성
 * 
 * sitemesh 기능:화면에 layout을 코딩한 jsp를 페이지에 적용 
 * 
 * addDecoratorPath("/member/*","/layout/kiclayout.jsp")
 *    url : /member/ 로시작하는 모든 요청 url
 *    layout : 응답시 layout 페이지 용으로 사용되는 jsp 페이지 설정
 *    
 *    addExcludedPath("/member/id*")
 *     url : /member/id 로 시작하는 모든 요청시 layout 제외
 */
import javax.servlet.annotation.WebFilter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration
	                    (SiteMeshFilterBuilder builder) {
	  builder.addDecoratorPath("/member/*","/layout/kiclayout.jsp")
	  .addExcludedPath("/member/id*")
	  .addExcludedPath("/member/pw*")
	  .addExcludedPath("/member/password*")
	  .addExcludedPath("/member/picture*");
	  
	  builder.addDecoratorPath("/board/*","/layout/boardlayout.jsp");
	  
	}
}
