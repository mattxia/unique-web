package com.demo.controller.front.blog;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;


@Path("/demo")
public class DemoRest extends Controller{
	
    private Logger logger = Logger.getLogger(DemoRest.class);
    
    @Autowired
    private IndexController a;
    
	public void index(){
	    logger.info("index");
	    System.out.println("index......................");
	    this.render("index");
	}
	
	@Action("/demo/blog/{1}/{2}")
	public void blog(){
	    logger.info(this.getPara());
	    System.out.println("{1} : " + this.getPara(0));
	    System.out.println("{2} : " + this.getPara(1));
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
