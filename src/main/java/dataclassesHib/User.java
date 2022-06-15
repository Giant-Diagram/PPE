package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @version 1.0
 * <p>
 * Fatherdataclass which contains the attributes of USERS and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-28
 */

@Entity
@Table(name = "UserAccount")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    //Data

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String gpn;
    private String email;
    private byte[] password;
    private String role;
    private boolean isconfirmed;

    //Relations

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> notifications = new ArrayList<>();


    //Constructor

    public User() {
    }

    public User(String firstname, String lastname, String gpn, String email, String password, String role, boolean isconfirmed, List<Notification> notifications) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gpn = gpn;
        this.email = email;

        //Password Encryption
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.password = md.digest(password.getBytes());
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }

        this.role = role;
        this.isconfirmed = isconfirmed;
        this.notifications = notifications;
        for (Notification notification : notifications){
            notification.setUser(this);
        }
    }

    //Getter & Setter

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGpn() {
        return gpn;
    }

    public void setGpn(String gpn) {
        this.gpn = gpn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Password Encryption
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.password = md.digest(password.getBytes());
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIsconfirmed() {
        return isconfirmed;
    }

    public void setIsconfirmed(boolean isconfirmed) {
        this.isconfirmed = isconfirmed;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    //Overrided equals & hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(gpn, user.gpn) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(isconfirmed, user.isconfirmed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, gpn, email, password, role, isconfirmed);
    }

    //Overrided toString


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gpn='" + gpn + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", isconfirmed='" + isconfirmed + '\'' +
                '}';
    }


}