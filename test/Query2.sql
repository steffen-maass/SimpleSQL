Select Student.ID, Student.Name, Grade.Grade
From Course, Student, Professor, Grade
Where Course.Department = "ECE"
And Professor.Department = "CS"
And Course.ProfessorID = Professor.ID
And Student.GraduationYear = "2005"
And Grade.CourseID = Course.ID
And Grade.StudentID = Student.ID