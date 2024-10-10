package code.doston.service;


import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.entity.Course;
import code.doston.exceptions.DataNotFoundException;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;


    public CourseResponseDTO createCourse(CourseCreationDTO courseCreationDTO) {

        // Set the created date if it is null
        if (courseCreationDTO.getCreatedDate() == null) {
            courseCreationDTO.setCreatedDate(LocalDate.now());
        }
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



    public String deleteCourse(Long id) {

        // Check if the course exists
        idNotExists(id);

        // Delete the course from the database
        courseRepository.deleteById(id);
        return "Course deleted successfully";
    }

    public String updateCourse(Long id, @Valid CourseCreationDTO courseCreationDTO) {

        // Check if the course exists
        idNotExists(id);

        // Update the course in the database
        Course course = modelMapper.map(courseCreationDTO, Course.class);
        course.setId(id);
        courseRepository.save(course);
        return "Course updated successfully";
    }

    public List<CourseResponseDTO> getCoursesByName(String name) {

        // Check if the course exists
        boolean isExist = courseRepository.existsByName(name);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with name: " + name);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.findByName(name);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getByPrice(BigDecimal price) {

        // Check if the course exists
        boolean isExist = courseRepository.existsByPrice(price);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with price: " + price);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.findByPrice(price);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getByDuration(Integer duration) {

        // Check if the course exists
        boolean isExist = courseRepository.existsByDuration(duration);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with duration: " + duration);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.findByDuration(duration);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getByPriceRange(BigDecimal min, BigDecimal max) {


        // Check if the minimum price is less than the maximum price
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Minimum price must be less than or equal to maximum price");
        }

        // Check if the course exists
        boolean isExist = courseRepository.existsByPriceBetween(min, max);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with price between: " + min + " and " + max);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.findByPriceBetween(min, max);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesByCreatedDateBetween(LocalDate start, LocalDate end) {

        // Check if the course exists
        boolean isExist = courseRepository.existsByCreatedDateBetween(start, end);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with createdDate between: " + start + " and " + end);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.findByCreatedDateBetween(start, end);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void idNotExists(Long id) {
        boolean isExist = courseRepository.existsById(id);
        if (!isExist) {
            throw new IdExistsException("Course not found with id: ", id);
        }
    }


}
