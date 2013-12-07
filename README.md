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
	取得HandlerAdapter对象-->因为Controller对象中的方法签名各有不同，所以需要将Controller对象进行适配以得到能够调用的Controller中的方法。
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



 