package com.bintime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Home App Controller, processing url app.</p>
 *
 * @author  Valerii Sukhov valerii.sukhov@gmail.com
 * @see     BaseController
 */
@Controller
public class HomeController extends BaseController{

    /**
     * Entry point, first view controller
     * @return /WEB-INF/views/index.jsp
     */
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String home() {
        return "index";
	}

    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String about() {
        return "about";
    }

}
