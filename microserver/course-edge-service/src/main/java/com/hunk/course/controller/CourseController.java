package com.hunk.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hunk.course.dto.CourseDTO;
import com.hunk.course.service.ICourseService;
import com.hunk.thrift.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    @Reference
    private  ICourseService courseService;

    @RequestMapping(value = "/courseList", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseDTO> courseList(HttpServletRequest request) {

        UserDTO user = (UserDTO)request.getAttribute("user");
        log.info(user.toString());

        return courseService.courseList();
    }
}
