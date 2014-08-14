package com.demo.controller.front.blog;

import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;

import com.demo.model.Book;
import com.demo.service.BookService;

@Path("/book")
public class BookController extends Controller {
    
    @Autowired
    private BookService bookService;
    
	public void index(){
		setAttr("title", "图书列表");
		//setAttr("books",  bookService.findBooks());
		render("index.html");
	}

	public void newPage(){
		setAttr("title", "新增图书");
		setAttr("book",  new Book());
		render("new");
	}

}
