public class Ok implements Student // Class Ok is being used to implement student interface and interact with it
{
    private String fname, lname;
    private int grade; //variable declarations
    
    public Ok(String fname, String lname, int grade) //method  to get names and grades of Ok students
    {
        this.fname = fname; // reference declaration for first name of student
        this.lname = lname;// reference declaration for last name of student
        this.grade = grade;// reference declaration for grade of student
    }

    public void info() //method to print Ok students when called
    {
        System.out.println(fname + " " + lname + "\t" + grade);//prints students name and grades
    }
}
