-----------demo1----------------
1.配置一个servlet，所有的/example/* 将会被提交到spring mvc框架中
2.这个servlet会去加载 /WEB-INF/example-servlet.xml这个spring配置文件
3.spring配置文件中配置了一个名为 /hello.action的 Controller，完整的URL是 /example/hello.action
4.发出请求 /example/hello.action 后，Controller中的代码会被执行
5.Controller类实现了Controller接口，该接口中有一个方法handleRequest 返回ModelAndView，它包含了视图要显示的model和视图，这个示例代码中返回的是JSP的完整路径
6.框架最终请求转发到指定的视图上

#demo2

DispatcherServlet中的几个默认部件：

doDispatch（request,response）
    找到HandlerMapping对象，调用getHandler(request)，得到HandlerExecutionChain，此时HandlerExecutionChain 中已经包含了Controller对象
	取得HandlerAdapter对象-->因为Controller的类型各有不同，比如有实现Controller接口的，有使用annotation的，所以需要将Controller对象进行适配以得到能够调用的Controller中的方法。
    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());即执行	HandlerAdapter的handle方法，返回ModelAndView 
    执行render方法，将ModelAndView进行渲染，渲染的时候需要取得ViewResolver对象，依靠这个对象得到一个View对象，最终调用View对象的 render方法，执行render方法的时候，将ModelAndView中的model传入进行视图渲染
    

1.org.springframework.web.servlet.HandlerMapping ，框架默认使用的实现类是：
	org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,
	org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping   功能是： 根据请求，找到控制器实例对象。

BeanNameUrlHandlerMapping 是根据请求的url来查找 Spring Ioc中的 Controller Bean，此时拿Bean的名称来与请求的url来进行对比。
注意bean name如果以“/”开头，则 “/”相对于请求路径的最后一个“/”

DefaultAnnotationHandlerMapping ： 如果这个Controller中使用了annotation,则根据 annotation来找到要调用的Controller的实例对象


2.springframework.web.servlet.HandlerAdapter ,默认的实现类
	org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,
	org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\ 所有实现了org.springframework.web.servlet.mvc.Controller接口的Bean可以作为Spring Web MVC中的处理器
	org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter
	
3.org.springframework.web.servlet.ViewResolver 默认实现 
	org.springframework.web.servlet.view.InternalResourceViewResolver 用于支持Servlet、JSP视图解析

----------------------------------------------------------------------------------------------
理解流程之后，现在将HandlerMapping 启用为DefaultAnnotationHandlerMapping ，HandlerAdapter 启用为AnnotationMethodHandlerAdapter ，它们默认就是启用的，所以不用添加任何额外的配置，只需要在Controller
上加入annotation即可。


#demo3
@Controller :我们编写的Controller类如果使用@Controller来标识，那么这个类不需要继承（或者实现）某些类。当然如果需要的话，也可以使用
@RequestMapping： 可以修饰类也可以修饰方法。如果修饰类，那么所有修饰方法的url都相对于修饰类的


#demo4
@Controller 可以找到处理器对象，以下考虑处理器中的功能处理方法，那么请求如何路由到功能处理方法，先从HTTP协议开始：
http请求信息包含六部分信息：
1. ①请求方法，如GET或POST，表示提交的方式；
2. ②URL，请求的地址信息；
3. ③协议及版本；
4. ④请求头信息（包括Cookie信息）；
5. ⑤回车换行（CRLF）；
6. ⑥请求内容区（即请求的内容或数据），如表单提交时的参数数据、URL请求参数（?abc=123 ？后边的）等。
	那此处我们可以看到有①、②、④、⑥一般是可变的，因此我们可以这些信息进行请求到处理器的功能处理方法的映射，因此请求的映射分为如下几种：
* URL路径映射：使用URL映射请求到处理器的功能处理方法；
* 请求方法映射限定：如限定功能处理方法只处理GET请求；
* 请求参数映射限定：如限定只处理包含“abc”请求参数的请求；
* 请求头映射限定：如限定只处理“Accept=application/json”的请求。

##例子
* 多个映射
```java
	@RequestMapping(value={"/m_1","/m1"},method={RequestMethod.GET,RequestMethod.POST})
```
* URI Template Patterns
```java
	@RequestMapping(value="/m2/{id}",method=RequestMethod.GET)
	public ModelAndView m2(@PathVariable String id,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id=>"+id);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
```
```java
	@RequestMapping(value="/m3/{id}/{name}",method=RequestMethod.GET)
	public ModelAndView m3(@PathVariable String id,@PathVariable String name,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id=>"+id+"   "+name);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
```
* URI Template Patterns with Regular Expressions  语法 ``{varName:regex}`` 
```java
	@RequestMapping(value="/m4/{id:\\d{3}}",method=RequestMethod.GET) //id必须是3位数字,否则无法匹配出现404
	public ModelAndView m4(@PathVariable int id,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("msg","id:"+id);
	
		return new ModelAndView("/WEB-INF/views/example/hello.jsp") ;
	}
```
* Matrix Variables
 要启用该功能，首先要配置
 ```xml
 <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
		<property name="removeSemicolonContent" value="false"></property>
	</bean> 
	<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>
 ```
```java

```
 







 