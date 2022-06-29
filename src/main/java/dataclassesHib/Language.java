package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String language;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_practiceplace", foreignKey = @ForeignKey(name = "FK__Languages__id_pr__5CD6CB2B"))
    private Practiceplace practiceplace;

    public Language() {
    }

    public Language(String language, Practiceplace practiceplace) {
        this.language = language;
        this.practiceplace = practiceplace;
        practiceplace.getLanguages().add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
        Language that = (Language) o;
        return Objects.equals(language, that.language) && Objects.equals(practiceplace, that.practiceplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, practiceplace);
    }

}