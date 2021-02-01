package level2.lesson3;

import java.util.ArrayList;

public class People {
    private String firstName;
    private String lastName;
    //Array list создан для создания под одной Фамилий двух разных телефона
    private ArrayList<String> phone = new ArrayList<>();

    public People(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone.add(phone);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone.toString();
    }



    @Override
    public String toString() {
        return "Имя:" + firstName  +
                ", Фамилия:" + lastName  +
                ", Телефон:" + phone  ;
    }
}
