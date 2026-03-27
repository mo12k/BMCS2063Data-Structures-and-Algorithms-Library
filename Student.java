package entity;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentID;
    private String studentName;
    
    public Student(){
    }
    
    public Student(String studentID, String studentName){
        this.studentID = studentID;
        this.studentName = studentName;
    }
    
    public String getStudentID(){
        return studentID;
    }
    
    public void setStudentID(String studentID){
        this.studentID = studentID;
    }
    
    public String getStudentName(){
        return studentName;
    }
    
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(studentID);
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }
    
    @Override
    public String toString(){
        return String.format("%s - %s", studentID, studentName);
    }
}
