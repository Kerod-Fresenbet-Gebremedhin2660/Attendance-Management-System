/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityModels;

/**
 *
 * @author Henok Gelaneh
 */
public class Employee {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String sex;
    private int leavedays;
    private String profilepic;

    public Employee(int id, String name, String mobile ,String email, String sex, int leavedays,String profilepic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.leavedays = leavedays;
        this.profilepic = profilepic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLeavedays() {
        return leavedays;
    }

    public void setLeavedays(int leavedays) {
        this.leavedays = leavedays;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

   
}
