package model;

public class User {
    private String userName;
    private String fullName;
    private int age;
    private String sex;
    private String address;

    public User(String userName, String fullName, int age, String sex, String address) {
        this.userName = userName;
        this.fullName = fullName;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
