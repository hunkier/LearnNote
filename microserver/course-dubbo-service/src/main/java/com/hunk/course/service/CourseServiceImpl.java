package com.hunk.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hunk.course.dto.CourseDTO;
import com.hunk.course.mapper.CourseMapper;
import com.hunk.thrift.user.UserInfo;
import com.hunk.thrift.user.dto.TeacherDTO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Properties;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courseDTOS = courseMapper.listCourse();
        if (courseDTOS!=null){
            for (CourseDTO courseDTO : courseDTOS) {
                Integer teacherId = courseMapper.getCourseTeacher(courseDTO.getId());
                if (null!=teacherId){

                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        courseDTO.setTeacher(trans2Teacher(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return courseDTOS;
    }

    private TeacherDTO trans2Teacher(UserInfo userInfo){
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(userInfo,teacherDTO);
        return teacherDTO;
    }
}
