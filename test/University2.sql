Select Student.StudentName, Grade.Grade
From Course, Professor, Student, Grade
Where Course.ID = Grade.CourseID
And Course.ProfessorID = Professor.ID
And Grade.StudentID = Student.ID
And Professor.qName = "John Doe"
And Course.CourseName = "Introduction to Algorithms"