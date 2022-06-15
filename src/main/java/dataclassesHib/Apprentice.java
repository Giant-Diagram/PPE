package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.security.MessageDigest;

/**
 * @version 1.0
 * <p>
 * Dataclass which contains the attributes of APPRENTICE and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-28
 */

@Entity
@Table(name = "Apprentice")
public class Apprentice extends User {
    //Data

    private LocalDate startApprenticeship;
    private String subject;

    //Relationships
    @OneToMany(mappedBy = "apprentice", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Practiceplace> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "apprentice", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> notifications = new ArrayList<>();

    //Constructor

    public Apprentice() {
        super.setRole("APPRENTICE");
        super.setIsconfirmed(true);
    }

    public Apprentice(String firstname, String lastname, String gpn, String email, String password, LocalDate startApprenticeship, String subject, List<Practiceplace> favorites, List<Application> applications, boolean isconfirmed, List<Notification> notifications) {
        super(firstname, lastname, gpn, email, password, "APPRENTICE", isconfirmed, notifications);
        this.startApprenticeship = startApprenticeship;
        this.subject = subject;
        this.favorites = favorites;
        this.applications = applications;
    }

    //Getter & Setter

    public LocalDate getStartApprenticeship() {
        return startApprenticeship;
    }

    public void setStartApprenticeship(LocalDate startApprenticeship) {
        this.startApprenticeship = startApprenticeship;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Practiceplace> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Practiceplace> favorites) {
        this.favorites = favorites;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }


    //Overrided equals & hashcode

    @Override
    public String toString() {
        return "Apprentice{" +
                "startApprenticeship=" + startApprenticeship +
                ", subject='" + subject + '\'' +
                ", applications=" + Arrays.toString(applications.toArray()) +
                ", favorites=" + Arrays.toString(favorites.toArray()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Apprentice that = (Apprentice) o;
        return Objects.equals(startApprenticeship, that.startApprenticeship) && Objects.equals(subject, that.subject) && Objects.equals(favorites, that.favorites) && Objects.equals(applications, that.applications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startApprenticeship, subject, favorites, applications);
    }
}
