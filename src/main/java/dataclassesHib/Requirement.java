package dataclassesHib;

import javax.persistence.*;
import java.util.Objects;

/**
 *
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 * @version 2.0
 *
 * Dataclass which contains the attributes of an REQUIREMENTS and also the annotations for hibernate
 *
 * */

@Entity
@Table(name = "Requirements")
public class Requirement {
    //Data

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String requirement;

    //Relationship

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "practiceplace_id", foreignKey = @ForeignKey(name = "REQUIREMENTS_PRACTICEPLACE_ID_FK"))
    private Practiceplace practiceplace;

    //Constructor

    public Requirement() {
    }

    public Requirement(String requirement, Practiceplace practiceplace) {
        this.requirement = requirement;
        this.practiceplace = practiceplace;
        practiceplace.getRequirements().add(this);
    }

    public Requirement(String requirement) {
        this.requirement = requirement;
    }

    //Getter & Setter

    public int getId() {
        return id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Practiceplace getPracticeplace() {
        return practiceplace;
    }

    public void setPracticeplace(Practiceplace practiceplace) {
        this.practiceplace = practiceplace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return Objects.equals(requirement, that.requirement) && Objects.equals(practiceplace, that.practiceplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, practiceplace);
    }
}
