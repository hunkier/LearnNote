package com.hunk.course.dto;

import com.hunk.thrift.user.dto.TeacherDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseDTO  implements Serializable {
    private int id;
    private String description;
    private TeacherDTO teacher;
}