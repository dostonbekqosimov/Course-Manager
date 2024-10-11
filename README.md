# Student Course Management System

This project implements a comprehensive Student Course Management System with various features for managing students, courses, and their marks.

## Entities

### 1. Student
Attributes: id, name, surname, level, age, Gender, createdDate

Operations:
1. Create student
2. Get Student List (return all)
3. Get student by id
4. Update student
5. Delete Student by id
6. Get methods by each field (getByName, getBySurname, getByLevel, getByAge, getByGender)
7. Get StudentList by Given Date
8. Get StudentList between given dates
9. Student Pagination
10. Student Pagination by given Level (sorted by id)
11. Pagination by given gender (sorted by createdDate)
12. Filter with pagination (id, name, surname, level, age, Gender, createdDateFrom, createdDateTo)

### 2. Course
Attributes: id, name, price, duration, createdDate

Operations:
1. Create Course
2. Get Course by id
3. Get Course list (return all)
4. Update Course
5. Delete Course
6. Get methods by each field (getByName, getByPrice, getByDuration)
7. Get Course list between given 2 prices
8. Get Course list between 2 createdDates
9. Course Pagination
10. Course Pagination (sorted by createdDate)
11. Course Pagination by price (sorted by createdDate)
12. Course Pagination by price between (sorted by createdDate)
13. Filter with pagination (id, name, price, duration, createdDateFrom, createdDateTo)

### 3. StudentCourseMark
Attributes: id, studentId, courseId, mark, createdDate

Operations:
1. Create StudentCourseMark
2. Update StudentCourseMark
3. Get StudentCourseMark by id
4. Get StudentCourseMark by id with detail
5. Delete StudentCourseMark by id
6. Get List of StudentCourseMark (return all)
7. Get student's mark for a given day
8. Get student's marks between two given dates
9. Get all marks of a student sorted by time in descending order
10. Get student's marks for a given course sorted by time in descending order
11. Get student's latest mark and course name
12. Get student's top 3 highest marks
13. Get student's first mark
14. Get student's first mark for a given course
15. Get student's highest mark for a given course
16. Get student's average marks
17. Get student's average mark for a given course
18. Count student's marks higher than a given mark
19. Get highest mark for a given course
20. Get average mark for a given course
21. Count marks for a given course
22. StudentCourseMark pagination
23. StudentCourseMark pagination by given studentId (sorted by createdDate)
24. StudentCourseMark pagination by given courseId (sorted by createdDate)
25. Get list of marks for a given studentId (with course details)
26. Get list of marks for a given courseId (with student details)
27. Get all mark details (student and course information)
28. Filter with pagination (studentId, courseId, markFrom, markTo, createdDateFrom, createdDateTo, studentName, courseName)

## Implementation Phases

### Exercise 1
Implement the following:
- Student (operations 1-8)
- Course (operations 1-8)
- StudentCourseMark (operations 1-21)

### Exercise 2
Implement the following:
- Student (operations 9-11)
- Course (operations 11-12)
- StudentCourseMark (operations 22-24)

Use @Query annotations where appropriate.

### Exercise 3
Implement filtering functionality.

## Additional Resources
For more information on building Telegram bots using Java, refer to [this gitbook](https://monsterdeveloper.gitbooks.io/writing-telegram-bots-on-java/content/).
