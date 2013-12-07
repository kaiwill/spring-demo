-----------demo1----------------
1.配置一个servlet，所有的/example/* 将会被提交到spring mvc框架中
2.这个servlet会去加载 /WEB-INF/example-servlet.xml这个spring配置文件
3.spring配置文件中配置了一个名为 /hello.action的 Controller，完整的URL是 /example/hello.action
4.发出请求 /example/hello.action 后，Controller中的代码会被执行
5.Controller类实现了Controller接口，该接口中有一个方法handleRequest 返回ModelAndView，它包含了视图要显示的model和视图，这个示例代码中返回的是JSP的完整路径
6.框架最终请求转发到指定的视图上
