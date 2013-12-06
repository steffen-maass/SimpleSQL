Select Course.CourseName, Professor.ProfessorName
From Course, Professor
Where Course.ProfessorID = Professor.ID
And Course.Department = "CS"
And Professor.Department = "CS"