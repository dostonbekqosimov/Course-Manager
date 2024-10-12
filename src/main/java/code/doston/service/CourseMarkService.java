package code.doston.service;

import code.doston.dtos.*;
import code.doston.entity.Course;
import code.doston.entity.Student;
import code.doston.entity.CourseMark;
import code.doston.exceptions.DataNotFoundException;
import code.doston.exceptions.DataExistsException;
import code.doston.repository.CourseRepository;
import code.doston.repository.CourseMarkRepository;
import code.doston.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseMarkService {

    private final CourseMarkRepository courseMarkRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    // Service ga o'tkaz
    private final CourseService courseService;
    private final StudentService studentService;


    public CourseMarkResponseDTO createStudentCourseMark(CourseMarkCreationDTO courseMarkCreationDTO) {

        // Check if a course mark already exists for the student and course on the same day
        LocalDate today = LocalDate.now();
        LocalDateTime fromDate = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(today, LocalTime.MAX);


        if (courseMarkRepository.existsByStudentIdAndCourseIdAndCreatedDateBetween(courseMarkCreationDTO.getStudentId(),
                courseMarkCreationDTO.getCourseId(), fromDate, toDate)) {
            throw new DataExistsException("CourseMark already exists for this student and course on this day");
        }

        //Get the student and course from the database using services
        Student student = studentService.getStudentEntityById(courseMarkCreationDTO.getStudentId());
        Course course = courseService.getCourseEntityById(courseMarkCreationDTO.getCourseId());


        //Create the courseMark
        CourseMark courseMark = new CourseMark();
        courseMark.setStudent(student);
        courseMark.setCourse(course);
        courseMark.setMark(courseMarkCreationDTO.getMark());


        //Set the student and course
        courseMarkRepository.save(courseMark);


        // Custom mapping for detailed response
        CourseMarkResponseDTO responseDTO = new CourseMarkResponseDTO();
        responseDTO.setId(courseMark.getId());
        responseDTO.setStudentId(courseMark.getStudent().getId());
        responseDTO.setCourseId(courseMark.getCourse().getId());
        responseDTO.setMark(courseMark.getMark());

        return responseDTO;
    }

    public CourseMarkResponseDTO updateStudentCourseMark(Long id, Integer mark) {

        //Check if courseMark exists
        idNotExists(id);


        //Get the courseMark from the database
        CourseMark courseMark = courseMarkRepository.findById(id).get();

        //Update the mark
        courseMark.setMark(mark);

        //Save the courseMark
        courseMarkRepository.save(courseMark);

        // Map the CourseMark to CourseMarkResponseDTO using ModelMapper
        return modelMapper.map(courseMark, CourseMarkResponseDTO.class);
    }

    public CourseMarkResponseDTO getStudentCourseMarkById(Long id) {

        //Check if courseMark exists
        idNotExists(id);

        //Get the courseMark from the database
        CourseMark courseMark = courseMarkRepository.findById(id).get();

        // Map the CourseMark to CourseMarkResponseDTO using ModelMapper
        return modelMapper.map(courseMark, CourseMarkResponseDTO.class);
    }

    public CourseMarkResponseWithDetailDTO getStudentCourseMarkDetailById(Long id) {

        //Check if courseMark exists
        idNotExists(id);

        //Get the courseMark from the database
        CourseMark courseMark = courseMarkRepository.findById(id).get();

        // Map the CourseMark to CourseMarkResponseWithDetailDTO using ModelMapper
        StudentResponseDTO student = modelMapper.map(courseMark.getStudent(), StudentResponseDTO.class);
        CourseResponseDTO course = modelMapper.map(courseMark.getCourse(), CourseResponseDTO.class);

        // Custom mapping for detailed response
        CourseMarkResponseWithDetailDTO responseDTO = new CourseMarkResponseWithDetailDTO();
        responseDTO.setId(id);
        responseDTO.setStudent(student);
        responseDTO.setCourse(course);
        responseDTO.setMark(courseMark.getMark());
        responseDTO.setCreatedDate(courseMark.getCreatedDate());


        return responseDTO;
    }

    public void deleteStudentCourseMarkById(Long id) {
        idNotExists(id);
        courseMarkRepository.deleteById(id);
    }

    public List<CourseMarkResponseDTO> getAllStudentCourseMarks() {
        List<CourseMark> courseMarks = courseMarkRepository.findAll();

        //Check if empty
        if (courseMarks.isEmpty()) {
            throw new DataNotFoundException("CourseMarks not found");
        }

        // Map the CourseMarks to CourseMarkResponseDTO using ModelMapper
        return courseMarks.stream()
                .map(courseMark -> modelMapper.map(courseMark, CourseMarkResponseDTO.class))
                .collect(Collectors.toList());
    }

    private void idNotExists(Long id) {
        if (!courseMarkRepository.existsById(id)) {
            throw new DataExistsException("CourseMark not found with id: ", id);
        }
    }

    public List<MarkResponseWithDetailDTO> getStudentCourseMarksByCreatedDate(Long studentId, LocalDate date) {

        // Make sure the date is in the correct format
        LocalDateTime fromDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(date, LocalTime.MAX);
        //Get the courseMark from the database
        List<CourseMark> courseMarks = courseMarkRepository.findByStudentIdAndCreatedDateBetween(studentId, fromDate, toDate);

        //Check if empty
        if (courseMarks.isEmpty()) {
            throw new DataNotFoundException("No marks found for student with id: " + studentId + " on date: " + date);
        }


        // Mapping
        return getMarkResponseWithDetailDTOS(courseMarks);
    }


    public List<MarkResponseWithDetailDTO> getStudentCourseMarksByCreatedDateRange(Long studentId, LocalDate start, LocalDate end) {

        // Make sure the date is in the correct format
        LocalDateTime fromDate = LocalDateTime.of(start, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(end, LocalTime.MAX);
        //Get the courseMark from the database
        List<CourseMark> courseMarks = courseMarkRepository.findByStudentIdAndCreatedDateBetween(studentId, fromDate, toDate);

        //Check if empty
        if (courseMarks.isEmpty()) {
            throw new DataNotFoundException("No marks found for student with id: " + studentId + " between dates: " + start + " and " + end);
        }

        // Mapping
        return getMarkResponseWithDetailDTOS(courseMarks);
    }


    public List<MarkResponseWithDetailDTO> getStudentCourseMarksSortedByDate(Long studentId, String sort) {

        // Check if student exists
        notFoundException(studentId);

        // Check if sort is asc or desc
        List<CourseMark> courseMarks;
        if ("asc".equalsIgnoreCase(sort)) {
            courseMarks = courseMarkRepository.findAllByStudentIdOrderByCreatedDateAsc(studentId);

        } else {
            courseMarks = courseMarkRepository.findAllByStudentIdOrderByCreatedDateDesc(studentId);
        }

        // Mapping and return
        return getMarkResponseWithDetailDTOS(courseMarks);
    }


    public List<MarkResponseWithDetailDTO> getSortedMarksByStudentAndCourse(Long studentId, Long courseId, String sort) {

        // Check if student exists
        notFoundException(studentId);

        //Check if course exists
        if (courseMarkRepository.findAllByCourseId(courseId).isEmpty()) {
            throw new DataNotFoundException("No marks  found with course with id: " + courseId);
        }

        // Check if sort is asc or desc
        List<CourseMark> courseMarks;
        if ("asc".equalsIgnoreCase(sort)) {
            courseMarks = courseMarkRepository.findAllByStudentIdAndCourseIdOrderByCreatedDateAsc(studentId, courseId);

        } else {
            courseMarks = courseMarkRepository.findAllByStudentIdAndCourseIdOrderByCreatedDateDesc(studentId, courseId);
        }

        // Mapping and return
        return getMarkResponseWithDetailDTOS(courseMarks);
    }

    public MarkResponseWithDetailDTO getStudentLastMark(Long studentId) {

        // Check if student exists
        notFoundException(studentId);

        // Get the last mark from the database
        CourseMark courseMark = courseMarkRepository.findTop1ByStudentIdOrderByCreatedDateDesc(studentId);

        // Mapping and return
        return getMarkResponseWithDetailDTO(courseMark);
    }

    public List<MarkResponseWithDetailDTO> getStudentLastThreeBiggestMarks(Long studentId) {

        // Check if student exists
        notFoundException(studentId);


        // Get the last three biggest marks from the database
        List<CourseMark> courseMarks = courseMarkRepository.findTop3ByStudentIdOrderByCreatedDateDescMarkDesc(studentId);
        // Mapping and return
        return getMarkResponseWithDetailDTOS(courseMarks);


    }

    public List<MarkResponseWithDetailDTO> getStudentFirstMarkByCourse(Long studentId, Long courseId) {

        // Check if student exist
        notFoundException(studentId);

        // Check if course exists
        if (courseMarkRepository.findAllByCourseId(courseId).isEmpty()) {
            throw new DataNotFoundException("No marks found for course with id: " + courseId);
        }
        // Get the first mark from the database
        CourseMark courseMark = courseMarkRepository.findTop1ByStudentIdAndCourseIdOrderByCreatedDateAsc(studentId, courseId);
        // Mapping and return
        return getMarkResponseWithDetailDTOS(List.of(courseMark));
    }


    public MarkResponseWithDetailDTO getStudentFirstMark(Long studentId) {

        // Check if student exists
        notFoundException(studentId);
        // Get the first mark from the database
        CourseMark courseMark = courseMarkRepository.findTop1ByStudentIdOrderByCreatedDateAsc(studentId);
        // Mapping and return
        return getMarkResponseWithDetailDTO(courseMark);

    }

    public MarkResponseWithDetailDTO getStudentBiggestMarkByCourse(Long studentId, Long courseId) {

        // Check if student exists
        notFoundException(studentId);

        // Check if course exists
        if (courseMarkRepository.findAllByCourseId(courseId).isEmpty()) {
            throw new DataNotFoundException("No marks found for course with id: " + courseId);
        }
        // Get the biggest mark from the database
        CourseMark courseMark = courseMarkRepository.findTop1ByStudentIdAndCourseIdOrderByMarkDescCreatedDateDesc(studentId, courseId);
        // Mapping and return
        return getMarkResponseWithDetailDTO(courseMark);
    }


    // Get student's average marks
    public Double getStudentAverageMarks(Long studentId) {
        List<CourseMark> marks = courseMarkRepository.findAllByStudentId(studentId);
        if (marks.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (CourseMark mark : marks) {
            total += mark.getMark();
        }
        return total / marks.size();
    }

    // Get student's average marks by course
    public Double getStudentAverageMarksByCourse(Long studentId, Long courseId) {
        List<CourseMark> marks = courseMarkRepository.findAllByStudentIdAndCourseId(studentId, courseId);
        if (marks.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (CourseMark mark : marks) {
            total += mark.getMark();
        }
        return total / marks.size();
    }

    // Count student's marks greater than a given value
    public Long countMarksGreaterThan(Long studentId, Double threshold) {
        List<CourseMark> marks = courseMarkRepository.findAllByStudentId(studentId);
        long count = 0;

        for (CourseMark mark : marks) {
            if (mark.getMark() > threshold) {
                count++;
            }
        }
        return count;
    }



    // Get the highest mark for a given course
    public Double getHighestMarkByCourse(Long courseId) {
        CourseMark highestMark = courseMarkRepository.findTop1ByCourseIdOrderByMarkDesc(courseId);
        return highestMark != null ? highestMark.getMark() : 0.0; // Return 0 if no marks found
    }

    // Get the average mark for a given course
    public Double getAverageMarkByCourse(Long courseId) {
        List<CourseMark> marks = courseMarkRepository.findAllByCourseId(courseId);
        if (marks.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (CourseMark mark : marks) {
            total += mark.getMark();
        }
        return total / marks.size();
    }

    // Count marks for a given course
    public Long countMarksByCourse(Long courseId) {
        return (long) courseMarkRepository.findAllByCourseId(courseId).size();
    }




    // Custom methods
    private List<MarkResponseWithDetailDTO> getMarkResponseWithDetailDTOS(List<CourseMark> courseMarks) {

        List<MarkResponseWithDetailDTO> dtoList = new ArrayList<>();
        for (CourseMark courseMark : courseMarks) {
            MarkResponseWithDetailDTO responseDTO = new MarkResponseWithDetailDTO();
            responseDTO.setStudentName(courseMark.getStudent().getName());
            responseDTO.setCourseName(courseMark.getCourse().getName());
            responseDTO.setMark(courseMark.getMark());
            responseDTO.setCreatedDate(courseMark.getCreatedDate());
            dtoList.add(responseDTO);
        }
        return dtoList;
    }

    private MarkResponseWithDetailDTO getMarkResponseWithDetailDTO(CourseMark courseMark) {

        MarkResponseWithDetailDTO responseDTO = new MarkResponseWithDetailDTO();
        responseDTO.setStudentName(courseMark.getStudent().getName());
        responseDTO.setCourseName(courseMark.getCourse().getName());
        responseDTO.setMark(courseMark.getMark());
        responseDTO.setCreatedDate(courseMark.getCreatedDate());
        return responseDTO;
    }

    private void notFoundException(Long studentId) {

        // Check if student exists
        if (studentService.getStudentEntityById(studentId) == null) {
            throw new DataNotFoundException("Student not found with id: " + studentId);

        }

        //Check if empty
        if (courseMarkRepository.findAllByStudentId(studentId).isEmpty()) {
            throw new DataNotFoundException("No marks found for student with id: " + studentId);
        }
    }



}


