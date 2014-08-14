package com.demo.controller.front.blog;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Intercept;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;

import com.demo.inteceptor.BBBBB;

@Path("/demo")
@Intercept(BBBBB.class)
public class DemoRest extends Controller{
	
    private Logger logger = Logger.getLogger(DemoRest.class);
    
    @Autowired
    private IndexController a;
    
	public void index(){
	    logger.info("index");
	    this.render("index");
	}
	
	@Action("/demo/blog/{id}")
	public void blog(){
	    logger.info(this.getPara());
		this.setAttr("blog_id", this.getPara());
		this.render("blog");
	}
	
	@Action("add")
	public void edit(){
	    logger.info(this.getPara());
	    this.render("edit");
	}
	
	@Action("/qq/mytest")
	public void test2(){
	    logger.info("/qq/mytest");
	    this.render("test2.html");
	}
	
}
