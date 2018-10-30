// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name:xincheng
// UT Student #:1004068518
// Author:Chengyu Xin
//
// Student2: 
// UTORID user_name: mihao
// UT Student #: 1004418203
// Author: Hao Mi
//
// Student3:
// UTORID user_name: wangy809
// UT Student #: 1004042617
// Author: Yunfei Wang
//
// Student4:
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

// import what we need
import java.util.Arrays;// for testing
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import a2.*;

public class JShell {

  // a boolean variable to control the status of JShell
  private boolean terminate;
  // a dictionary that maps String as key to Command as value
  private Map<String, Command> map;
  private FileSystem fs;
  private Command com;
  private PrintDirectory pwd;
  private Mkdir mkdir;
  private ChangeDirectory cd;
  private History history;
  private Ls ls;

  // constructor
  JShell() {
    // JShell will be closed when terminate is true
    terminate = false;
    // create a FileSystem with SingletonDesignPattern
    fs = FileSystem.createInstanceOfFileSystem();
    // for testing, delete this after!!!
    Directory a = fs.getRoot();
    a.setSub(new Directory("user",a));
    Directory b = (Directory)a.getSub().get(0);
    b.setSub(new Directory("Application",b));
    b.setSub(new Directory("Desktop",b));
    Directory c = (Directory)b.getSub().get(0);
    c.setSub(new Directory("Music",c));
    
    // creating comment objects
    com = new Command(fs);
    pwd = new PrintDirectory(fs);
    cd = new ChangeDirectory(fs);
    mkdir = new Mkdir(fs);
    ls = new Ls(fs);
    history = new History(fs);
    // create the dictionary that maps String as key to Command as value
    map = new HashMap<String, Command>();
    // put corresponding Command by String
    map.put("pwd", pwd);
    map.put("mkdir", mkdir);
    map.put("cd", cd);
    map.put("ls", ls);
    map.put("history", history);
  }

  // main class of JShell
  public static void main(String[] args) {
    // Generates a JShell object
    JShell jShell = new JShell();
    // create a scanner for user inputs
    Scanner in = new Scanner(System.in);
    // keep running until terminate is true
    while (!jShell.terminate) {
      // print statement to ask for user's input
      System.out.print("/#: ");
      // trim the extra spaces at the start and end of user input
      String userInput = in.nextLine().trim();
      // turns user input into array's element by spaces between
      String input[] = userInput.split("\\s+");
      // for testing, delete this after!!!
      System.out.println(Arrays.toString(input));
      // execute the input
      jShell.execute(input);
    }
    // close the scanner
    in.close();
  }

  public void execute(String[] input) {
    // save the first word of user input
    String command = input[0];
    // check if the first word is a valid command
    if (com.isValid(command)) {
      // if the command is exit
      if (command.equals("exit")) {
        // change the state of terminate to close JShell
        terminate = true;
      // else execute the command
      } else {
        // save the command in history
        history.addHistory(input);
        // execute the corresponding command by user input
        (map.get(input[0])).run(input);
      }
    // else it is not a valid command
    } else {
      // save the input in history
      history.addHistory(input);
      // print the message
      System.out.println(input[0] + ": command not found");
    }
  }
}
