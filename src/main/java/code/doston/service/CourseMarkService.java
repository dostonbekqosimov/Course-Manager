package code.doston.service;

import code.doston.dtos.*;
import code.doston.entity.Course;
import code.doston.entity.Student;
import code.doston.entity.CourseMark;
import code.doston.exceptions.DataNotFoundException;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.CourseRepository;
import code.doston.repository.CourseMarkRepository;
import code.doston.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseMarkService {

    private final CourseMarkRepository courseMarkRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;




        public CourseMarkResponseDTO createStudentCourseMark(@Valid CourseMarkCreationDTO courseMarkCreationDTO) {

            //Check if courseMark exists
            if (courseMarkRepository.existsByStudentIdAndCourseId(courseMarkCreationDTO.getStudentId(), courseMarkCreationDTO.getCourseId())) {
                throw new IdExistsException("CourseMark already exists");
            }

            //Get the student and course from the database
            Student student = studentRepository.findById(courseMarkCreationDTO.getStudentId()).get();
            Course course = courseRepository.findById(courseMarkCreationDTO.getCourseId()).get();

            //Create the courseMark
            CourseMark courseMark = modelMapper.map(courseMarkCreationDTO, CourseMark.class);


            //Set the created date if it is null
            if (courseMarkCreationDTO.getCreatedDate() == null) {
                courseMark.setCreatedDate(LocalDate.now());
            }


            //Set the student and course
            courseMark.setStudent(student);
            courseMark.setCourse(course);
            courseMarkRepository.save(courseMark);


            // Map the CourseMark to CourseMarkResponseDTO using ModelMapper
            return modelMapper.map(courseMark, CourseMarkResponseDTO.class);
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

            CourseMarkResponseWithDetailDTO responseDTO = new CourseMarkResponseWithDetailDTO();
            responseDTO.setId(id);
            responseDTO.setStudent(student);
            responseDTO.setCourse(course);
            responseDTO.setMark(courseMark.getMark());
            responseDTO.setCreatedDate(courseMark.getCreatedDate());

            // Custom mapping for detailed response

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
                throw new IdExistsException("CourseMark not found with id: ", id);
            }
        }
    }


