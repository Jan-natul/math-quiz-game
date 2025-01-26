/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math_quiz_game;

/**
 *
 * @author HP
 */
public class player {
   private String Name;
   private String Gender;
   private Integer Age;

    public player(String Name, String Gender, Integer Age) {
        this.Name = Name;
        this.Gender = Gender;
        this.Age = Age;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public Integer getAge() {
        return Age;
    }
   
   
}
