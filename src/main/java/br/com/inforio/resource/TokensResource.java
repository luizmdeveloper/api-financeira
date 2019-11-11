package br.com.inforio.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokensResource {
		
	@DeleteMapping("/remove")
	public void excluir(HttpServletRequest request, HttpServletResponse response) {		
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); 
		cookie.setPath(request.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		response.addCookie(cookie);		
	}
}
