package com.wq.web.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hellos")
public class HelloWorldController{

	@RequestMapping(method=RequestMethod.GET) // /example/hellos+GET
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","index!");
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	
	@RequestMapping(value="/{id}/show",method=RequestMethod.GET) // /example/hellos/1/show +GET
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","show!");
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
}

