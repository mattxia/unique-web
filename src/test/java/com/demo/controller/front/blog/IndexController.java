package com.demo.controller.front.blog;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.tx.annotation.Transaction;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Intercept;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;

import com.demo.service.A;

/**
 * 首页
 * 
 * @author：rex
 * @create_time：2014-6-24 下午5:52:15
 * @version：V1.0
 */
@Path("/index")
public class IndexController extends Controller {

    private Logger logger = Logger.getLogger(IndexController.class);
    
    @Autowired
    private A a;

    public void index() {
        redirect("/book/index.html");
    }

    @Transaction
    public void request() {
        render("request");
    }

    public void users() {
        List<Map<String, Object>> list = CollectionUtil.newArrayList();
        Map<String, Object> m1 = CollectionUtil.newHashMap();
        m1.put("name", "张三");
        m1.put("id", 22);
        m1.put("email", "zhangsan@qq.com");
        Map<String, Object> m2 = CollectionUtil.newHashMap();
        m2.put("name", "里斯");
        m2.put("id", 21);
        m2.put("email", "list@qq.com");
        list.add(m1);
        list.add(m2);
        this.setAttr("userList", list);
        render("users");
    }

    @Action(value = "books/{id}")
    public void books() {
        Integer uid = this.getParaToInt();
        logger.info(uid);
        this.setAttr("bookList", null);
        render("books");
    }

    public void layout() {
        render("layout");
    }

}
