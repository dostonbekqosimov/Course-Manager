package code.doston.service;


import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.dtos.StudentResponseDTO;
import code.doston.entity.Course;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;


    public CourseResponseDTO createCourse(CourseCreationDTO courseCreationDTO) {

        Course course = modelMapper.map(courseCreationDTO, Course.class);
        courseRepository.save(course);

        return modelMapper.map(course, CourseResponseDTO.class);
    }

    public List<CourseResponseDTO> getAllCourses() {

        // Get all courses
        List<Course> courses = courseRepository.findAll();

        // Map each Course to CourseResponseDTO using ModelMapper
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getCourse(Long id) {

        // Check if course exists
        idNotExists(id);

        // Get the course from the database
        Course course = courseRepository.findById(id).get();

        // Map the Course to CourseResponseDTO using ModelMapper
        return modelMapper.map(course, CourseResponseDTO.class);
    }

    public void idNotExists(Long id) {
        boolean isExist = courseRepository.existsById(id);
        if (!isExist) {
            throw new IdExistsException("Course not found with id: ", id);
        }
    }
}
