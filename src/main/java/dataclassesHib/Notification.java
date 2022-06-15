package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;
    private boolean hasbeenseen;


    @ManyToOne(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id"))
    private User user;
    private LocalDate applicationcreated;

    @ManyToOne(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "apprentice_id_notification", foreignKey = @ForeignKey(name = "apprentice_id_notification"))
    private Apprentice apprentice;

    public Notification() {

    }

    public Notification(String text, boolean hasbeenseen, User user, LocalDate applicationcreated, Apprentice apprentice) {
        this.text = text;
        this.hasbeenseen = hasbeenseen;
        this.applicationcreated = applicationcreated;
        this.user = user;
        this.apprentice = apprentice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getHasbeenseen() {
        return hasbeenseen;
    }

    public void setHasbeenseen(boolean hasbeenseen) {
        this.hasbeenseen = hasbeenseen;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getApplicationcreated() {
        return applicationcreated;
    }

    public void setApplicationcreated(LocalDate applicationcreated) {
        this.applicationcreated = applicationcreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(text, that.text) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, user);
    }

    public Apprentice getApprentice() {
        return apprentice;
    }

    public void setApprentice(Apprentice apprentice) {
        this.apprentice = apprentice;
    }
}
