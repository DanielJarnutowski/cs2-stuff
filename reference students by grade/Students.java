/*
Daniel Jarnutowski
email: naruto016@live.com
*/
import java.util.Scanner; // Scanner class that reads input//
import java.io.*; //class that reads text files
import java.util.ArrayList; //class that creates and uses array list

public class Students  //class
{
    public static void main (String[] args) throws IOException //main method
    {   String first_name, last_name;
        int grade ; //variable declarations

        Scanner fileInput = new Scanner(new File("students.txt")); ////Instantiate a Scanner object  for reading text file
        ArrayList<Student> st = new ArrayList(); //creates array list
        while (fileInput.hasNext()) // loop to get names and grades from text file
        {
            first_name = fileInput.next(); //reads the first text input of line as first name of student
            last_name = fileInput.next(); //reads second input of  line at last name of student
            grade = fileInput.nextInt(); // reads last input of line as grade of student

            if (grade>89) // if statement to differentiate Ok and Excellent students
                st.add (new Excellent(first_name, last_name, grade)); // add grades higher than 89 into Excellent object in arraylist
            else 
                st.add(  new Ok(first_name, last_name, grade)); // add grades 89 or lower into Ok object in arraylist

        }

        Scanner scan = new Scanner(System.in); //Instantiate a Scanner object for command inputs
        String command = ""; // uses string command as input

        while (!command.equals("end")) //loop to accept input commands and print the correct students and grades
        {

            System.out.println("enter a command"); //asks user for a command to enter
            command = scan.nextLine();  //scans next input as command

            if (command.equals("all")) //if statement for printing all students
                for (int i = 0; i<st.size();i++) //initializes i to be within arraylist
                { 
                    st.get(i).info(); // print all students grades
                }
            if (command.equals("excellent")) //if statement for printing excellent students

                for (int i = 0; i<st.size();i++) //initializes i to be within arraylist
                {    
                    if (st.get(i) instanceof Excellent) //gets instance of I from excellent grades only 
                        st.get(i).info(); // prints excellent grades
                }
            if (command.equals("ok")) // if statement for printing Ok students

                for (int i = 0; i<st.size();i++) //initializes i to be within arraylist
                {    
                    if (st.get(i) instanceof Ok) //gets instance of I from Ok grades only 
                        st.get(i).info(); //prints ok grades

                }  
        }
    }
}
