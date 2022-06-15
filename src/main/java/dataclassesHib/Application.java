package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @version 1.0
 * <p>
 * Dataclass which contains the attributes of APPRENTICE and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-28
 */

@Entity
@Table(name = "Application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "apprentice_id_application", foreignKey = @ForeignKey(name = "apprentice_id_application"))
    private Apprentice apprentice;

    @ManyToOne (cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "pp_id", foreignKey = @ForeignKey(name = "pp_id"))
    private Practiceplace practiceplace;

    private String status;

    private LocalDate created;

    public Application(){

    }

    public Application(int id, String description, Apprentice apprentice, List<Practiceplace> bewerbungen, Practiceplace practiceplace,String status, LocalDate created) {
        this.id = id;
        this.description = description;
        this.apprentice = apprentice;
        this.practiceplace = practiceplace;
        this.status = status;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Apprentice getApprentice() {
        return apprentice;
    }

    public void setApprentice(Apprentice apprentice) {
        this.apprentice = apprentice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Application that = (Application) o;
        return Objects.equals(description, that.description) && Objects.equals(apprentice, that.apprentice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, apprentice, apprentice);
    }

    public Practiceplace getPracticeplace() {
        return practiceplace;
    }

    public void setPracticeplace(Practiceplace practiceplace) {
        this.practiceplace = practiceplace;
    }

    public String  getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
