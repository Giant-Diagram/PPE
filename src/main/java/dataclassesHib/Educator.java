package dataclassesHib;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @version 1.0
 * <p>
 * Dataclass which contains the attributes of EDUCATOR and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-28
 */

@Entity
@Table(name = "Educator")
public class Educator extends User {
    //Data
    //Relationships
    @OneToMany(mappedBy = "educator", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Practiceplace> practiceplaces = new ArrayList<>();

    public Educator() {
        super.setRole("EDUCATOR");
        super.setIsconfirmed(true);
    }

    public Educator(String firstname, String lastname, String gpn, String email, String password, List<Practiceplace> practiceplaces, boolean isconfirmed, List<Notification> notifications) {
        super(firstname, lastname, gpn, email, password, "educator", isconfirmed,notifications);
        this.practiceplaces = practiceplaces;
        for (Practiceplace p : practiceplaces) {
            p.setEducator(this);
        }

    }

    //Getter & Setter

    public List<Practiceplace> getPracticeplaces() {
        return practiceplaces;
    }

    public void setPracticeplaces(List<Practiceplace> practiceplaces) {
        this.practiceplaces = practiceplaces;
    }


    //Overrided equals & hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Educator educator = (Educator) o;
        return Objects.equals(practiceplaces, educator.practiceplaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), practiceplaces);
    }

    //Overided toString


    @Override
    public String toString() {
        return "Educator{" +
                "practiceplaces=" + practiceplaces +
                '}';
    }


}
