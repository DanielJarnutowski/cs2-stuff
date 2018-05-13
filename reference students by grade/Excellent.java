public class Excellent implements Student //Excellent used to implement Student interface and interact with it
{
    private String fname, lname; // variable declarations 
    private int grade;

    public Excellent(String fname, String lname, int grade) // method used to define name and grades of Excellent students
    {
        this.fname = fname;// reference declaration for first name of student
        this.lname = lname; // reference declaration for last name of student
        this.grade = grade; //reference decleration for grade of each student
    }

    public void info() // method used to print Excellent students when called
    {
        System.out.println(fname + " " + lname + "\t" + grade); //prints students and grades
    }
}
