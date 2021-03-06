package com.estate.controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request , Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorPage ="error";
        String pageTitle = "Error";
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                pageTitle ="Page Not Found";
                errorPage = "error/404";
                LOGGER.error("Error 404");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTitle ="Internal Server Error ";
                errorPage = "error/500";
                LOGGER.error("Error 500");
            }
        }
        model.addAttribute("pageTitle",pageTitle);
        return errorPage;
    }

}