package com.wq.web.controllers;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	/**
	 * example/hellos/m_1 + get 或者post
	 * example/hellos/m1 + get  或者 post 
	 *     
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/m_1","/m1"},method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView m1(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","m1");
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	@RequestMapping(value="/m2/{id}",method=RequestMethod.GET)
	public ModelAndView m2(@PathVariable String id,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id=>"+id);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	@RequestMapping(value="/m3/{id}/{name}",method=RequestMethod.GET)
	public ModelAndView m3(@PathVariable String id,@PathVariable String name,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id=>"+id+"   "+name);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	@RequestMapping(value="/m4/{id:\\d{3}}",method=RequestMethod.GET) //id必须是3位数字
	public ModelAndView m4(@PathVariable int id,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id:"+id);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	@RequestMapping(value="/m5/{id}",method=RequestMethod.GET) // /m5/123;year=2013;month=02
	public ModelAndView m5(@MatrixVariable String year,@MatrixVariable String month, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","m5 year="+year+"  month="+month);
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
	
	

	
	
	
}

