package dataclassesHib;
/*

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Languages")
public class Languages {
    //Data

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String language;

    //Relationship

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "practiceplace_id", foreignKey = @ForeignKey(name = "REQUIREMENTS_PRACTICEPLACE_ID_FK"))
    private Practiceplace practiceplace;

    //Constructor

    public Language() {
    }

    public Language(String language, Practiceplace practiceplace) {
        this.language  = language;
        this.practiceplace = practiceplace;
        practiceplace.getLanguages().add(this);
    }

    public Language(String language) {
        this.language = language;
    }

    //Getter & Setter


    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public Practiceplace getPracticeplace() {
        return practiceplace;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPracticeplace(Practiceplace practiceplace) {
        this.practiceplace = practiceplace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return Objects.equals(language, that.language) && Objects.equals(practiceplace, that.practiceplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Language, practiceplace);
    }
}
*/
