package dataclassesHib;

import javax.persistence.*;
import java.util.Objects;

/**
 *
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-27
 * @version 2.0
 *
 * Dataclass which contains the attributes of an TECHNOLOGY and also the annotations for hibernate
 *
 * */

@Entity
@Table(name = "Technology")
public class Technology {

        //Data

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String technology;

        //Relationship

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "practiceplace_id", foreignKey = @ForeignKey(name = "TECHNOLOGIES_PRACTICEPLACE_ID_FK"))
        private Practiceplace practiceplace;

        //Constructor

        public Technology() {
        }

        public Technology(String technology, Practiceplace practiceplace) {
            this.technology = technology;
            this.practiceplace = practiceplace;
            practiceplace.getTechnologies().add(this);
        }

        public Technology(String requirement) {
            this.technology = requirement;
        }

        //Getter & Setter

        public int getId() {
            return id;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
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
        Technology that = (Technology) o;
        return Objects.equals(technology, that.technology) && Objects.equals(practiceplace, that.practiceplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technology, practiceplace);
    }
}
