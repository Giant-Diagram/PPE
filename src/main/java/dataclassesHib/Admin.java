package dataclassesHib;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @version 1.0
 * <p>
 * Dataclass which contains the attributes of ADMIN and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-28
 */

@Entity
@Table(name = "Admin")
public class Admin extends User {
    //Data

    //Relationships

    public Admin() {
        super.setIsconfirmed(true);
        super.setRole("ADMIN");
    }

    public Admin(String firstname, String lastname, String gpn, String email, String password, boolean isconfirmed, List<Notification> notifications) {
        super(firstname, lastname, gpn, email, password, "ADMIN", isconfirmed,notifications);
    }
}
