package com.modelo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.modelo.dao.UserDao;
import com.modelo.users.model.User;

@Controller
public class MainController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		//webDataBinder.registerCustomEditor(requiredType, propertyEditor);
	}
	
	@RequestMapping(value={"/welcome**"}, method = RequestMethod.GET)
	public ModelAndView welcomePage(Authentication authentication) {
		UserDetails usuarioLogado = (UserDetails) authentication.getPrincipal();
		return new ModelAndView(AliasPaginasSistema.WELCOME)
				.addObject("usuarioLogado", usuarioLogado);
	}
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		return new ModelAndView(AliasPaginasSistema.ADMIN);
	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		ModelAndView model = new ModelAndView(AliasPaginasSistema.LOGIN);
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		return model;
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Usuário e/ou Senha estão errados!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

	
	@RequestMapping(value = "/clientSignUp", method = RequestMethod.POST)
	public ModelAndView clientSignUp(@Valid @ModelAttribute("client") User user, BindingResult result,
			RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		if(!result.hasErrors()) {
			String password = user.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
			user.setPassword(encodedPassword);
			this.userDao.insert(user);
			redirectAttributes.addFlashAttribute("success", "Cadastro efetuado com sucesso!");
		} else {
			redirectAttributes.addFlashAttribute("error", "Não foi possível cadastrar! Tente novamente mais tarde");
		}
		
		model.setViewName(AliasPaginasSistema.LOGIN_REDIRECT);
		return model;
	}
}