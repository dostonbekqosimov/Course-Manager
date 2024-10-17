package code.doston.service;


import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.dtos.CustomFilterResponseDTO;
import code.doston.dtos.filterDTOs.CourseFilterDTO;
import code.doston.dtos.filterDTOs.StudentFilterDTO;
import code.doston.entity.Course;
import code.doston.exceptions.DataNotFoundException;
import code.doston.exceptions.DataExistsException;
import code.doston.repository.CourseRepository;
import code.doston.repository.filterRepository.CustomCourseFilterRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final CustomCourseFilterRepository filterRepository;


    public CourseResponseDTO createCourse(CourseCreationDTO courseCreationDTO) {

        // Check if the course already exists
        boolean isExist = courseRepository.existsByName(courseCreationDTO.getName());
        if (isExist) {
            throw new DataExistsException("Course already exists with name: " + courseCreationDTO.getName());
        }

        // Save the course in the database
        Course course = modelMapper.map(courseCreationDTO, Course.class);
        courseRepository.save(course);

        // Map the Course to CourseResponseDTO using ModelMapper
        return modelMapper.map(course, CourseResponseDTO.class);
    }

    public PageImpl<CourseResponseDTO> getAllCourses(Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());


        // Get all courses
        Page<Course> courses = courseRepository.getAll(pageRequest);

        // Check if the list is empty
        if (courses.isEmpty()) {
            throw new DataNotFoundException("No courses found");
        }

        // Map each Course to CourseResponseDTO using ModelMapper
        List<CourseResponseDTO> courseList = courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(courseList, pageRequest, courses.getTotalElements());

    }

    public CourseResponseDTO getCourse(Long id) {

        // Check if course exists
        idNotExists(id);

        // Get the course from the database
        Course course = courseRepository.getById(id);

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

    public String updateCourse(Long id, CourseCreationDTO courseCreationDTO) {

        // Check if the course exists
        idNotExists(id);

        // Get the course from the database
        Course course = courseRepository.findById(id).get();

        // Update the course properties
        modelMapper.map(courseCreationDTO, course);


        // Update the course in the database
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
        List<Course> courses = courseRepository.getAllByName(name);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PageImpl<CourseResponseDTO> getByPrice(BigDecimal price, Integer page, Integer size) {


        // Check if the course exists
        boolean isExist = courseRepository.existsByPrice(price);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with price: " + price);
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());


        // Get the course from the database
        Page<Course> courses = courseRepository.getAllByPrice(price, pageRequest);

        List<CourseResponseDTO> courseList = courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(courseList, pageRequest, courses.getTotalElements());

    }

    public List<CourseResponseDTO> getByDuration(Integer duration) {

        // Check if the course exists
        boolean isExist = courseRepository.existsByDuration(duration);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with duration: " + duration);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.getByDuration(duration);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PageImpl<CourseResponseDTO> getByPriceRange(BigDecimal min, BigDecimal max, Integer page, Integer size) {


        // Check if the minimum price is less than the maximum price
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Minimum price must be less than or equal to maximum price");
        }

        // Check if the course exists
        boolean isExist = courseRepository.existsByPriceBetween(min, max);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with price between: " + min + " and " + max);
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());


        // Get the course from the database

        // Get the course from the database
        Page<Course> courses = courseRepository.getAllBetweenPrice(min, max, pageRequest);


        List<CourseResponseDTO> courseList = courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(courseList, pageRequest, courses.getTotalElements());

    }

    public List<CourseResponseDTO> getCoursesByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {

        // Check if the start date is before the end date
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        LocalDateTime fromDate = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(endDate, LocalTime.MAX);

        // Check if the course exists
        boolean isExist = courseRepository.existsByCreatedDateBetween(fromDate, toDate);
        if (!isExist) {
            throw new DataNotFoundException("Course not found with createdDate between: " + startDate + " and " + endDate);
        }

        // Get the course from the database
        List<Course> courses = courseRepository.getAllBetweenDates(fromDate, toDate);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PageImpl<CourseResponseDTO> filter(CourseFilterDTO filter, int page, Integer size) {

        CustomFilterResponseDTO<Course> result = filterRepository.filter(filter, page, size);

        List<CourseResponseDTO> responseDTOS = result.getResult().stream().map(course -> modelMapper.map(course, CourseResponseDTO.class)).toList();

        return new PageImpl<>(responseDTOS, PageRequest.of(page, size), result.getTotalCount());


    }

    public Course getCourseEntityById(Long id) {

        // Check if the course exists
        idNotExists(id);

        // Get the course from the database
        return courseRepository.findById(id).get();
    }

    public void idNotExists(Long id) {
        boolean isExist = courseRepository.existsById(id);
        if (!isExist) {
            throw new DataExistsException("Course not found with id: ", id);
        }
    }


}
