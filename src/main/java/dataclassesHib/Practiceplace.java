package dataclassesHib;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @version 2.0
 * <p>
 * Dataclass which contains the attributes of an PRACTICEPLACE and also the annotations for hibernate
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 */

@Entity
@Table(name = "Practiceplace")
public class Practiceplace {
    //Data

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String subject;
    private String zip;
    private int rotationsites;

    @Column(name = "designation")
    private String place;

    @Column(name = "startDate")
    private LocalDate start;

    @Column(name = "endDate")
    private LocalDate end;

    @Column(length = 4000)
    private String description;
    private String tempDescription = "";
    private String street;
    private String streetNr;
    private String imageType;

    private String kindOfDeployment;

    @Column(columnDefinition = "image")
    private byte[] image;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> apprenticeYears = new ArrayList<>();

    //Relationships

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "educator_id", foreignKey = @ForeignKey(name = "PRACTICEPLACE_EDUCATOR_ID_FK"))
    private Educator educator;

    @OneToMany(mappedBy = "practiceplace", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Requirement> requirements = new ArrayList<>();

    @OneToMany(mappedBy = "practiceplace", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Technology> technologies = new ArrayList<>();

    @OneToMany(mappedBy = "practiceplace", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany(mappedBy = "favorites", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Apprentice> favoritesApprenticePP = new ArrayList<>();

    //Constructor

    public Practiceplace() {
    }

    public Practiceplace(String name, String subject, String zip, String place, LocalDate start, LocalDate end, String description, String street, String streetNr, byte[] image, Educator educator, List<Integer> apprenticeYears, List<Requirement> requirements, List<Technology> technologies, List<Application> applications, List<Apprentice> favoritesApprenticePP, int rotationsites, String kindOfDeployment) {
        this.name = name;
        this.subject = subject;
        this.zip = zip;
        this.place = place;
        this.start = start;
        this.end = end;
        this.description = description;
        this.kindOfDeployment = kindOfDeployment;

        int maxChars = 100;

        if (maxChars > description.length())
            maxChars = description.length();

        for (int i = 0;i < maxChars; i++) {
            if (description.toCharArray()[i] == '<' && description.toCharArray()[i + 1] == 'b' && description.toCharArray()[i + 2] == 'r' && description.toCharArray()[i + 3] == '>') {
                i += 3;
                this.tempDescription += " ";

            } else {
                this.tempDescription += description.toCharArray()[i];
            }
        }

        this.street = street;
        this.streetNr = streetNr;
        this.image = image;

        this.educator = educator;
        educator.getPracticeplaces().add(this);

        this.apprenticeYears = apprenticeYears;

        this.requirements = requirements;
        for (Requirement r : requirements) {
            r.setPracticeplace(this);
        }

        this.technologies = technologies;
        for (Technology t : technologies) {
            t.setPracticeplace(this);
        }

        this.applications = applications;
        for (Application a : applications) {
            a.setPracticeplace(this);
        }

        this.favoritesApprenticePP = favoritesApprenticePP;
        for (Apprentice a : favoritesApprenticePP) {
            a.getFavorites().add(this);
        }

        this.rotationsites = rotationsites;
    }

//Getter & Setter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String designation) {
        this.place = designation;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

        this.tempDescription = "";
        int maxChars = 100;

        if (maxChars > description.length())
            maxChars = description.length();

        for (int i = 0;i < maxChars; i++) {
            if (description.toCharArray()[i] == '<' && description.toCharArray()[i + 1] == 'b' && description.toCharArray()[i + 2] == 'r' && description.toCharArray()[i + 3] == '>') {
                i += 3;
                this.tempDescription += " ";

            } else {
                this.tempDescription += description.toCharArray()[i];
            }
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Educator getEducator() {
        return educator;
    }

    public void setEducator(Educator educator) {
        this.educator = educator;
    }

    public List<Integer> getApprenticeYears() {
        return apprenticeYears;
    }

    public void setApprenticeYears(List<Integer> apprenticeYears) {
        this.apprenticeYears = apprenticeYears;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Apprentice> getFavoritesApprenticePP() {
        return favoritesApprenticePP;
    }

    public void setFavoritesApprenticePP(List<Apprentice> favoritesPP) {
        this.favoritesApprenticePP = favoritesPP;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public String getTempDescription() {
        return tempDescription;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getRotationsites() {
        return rotationsites;
    }

    public void setRotationsites(int rotationsites) {
        this.rotationsites = rotationsites;
    }

    public String getKindOfDeployment() {
        return kindOfDeployment;
    }

    public void setKindOfDeployment(String kindOfDeployment) {
        this.kindOfDeployment = kindOfDeployment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Practiceplace that = (Practiceplace) o;
        return zip == that.zip && Objects.equals(name, that.name) && Objects.equals(subject, that.subject) && Objects.equals(place, that.place) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(description, that.description) && Objects.equals(street, that.street) && Objects.equals(streetNr, that.streetNr) && Objects.equals(image, that.image) && Objects.equals(apprenticeYears, that.apprenticeYears) && Objects.equals(educator, that.educator) && Objects.equals(requirements, that.requirements) && Objects.equals(technologies, that.technologies) && Objects.equals(applications, that.applications) && Objects.equals(favoritesApprenticePP, that.favoritesApprenticePP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subject, zip, place, start, end, description, street, streetNr, image, apprenticeYears, educator, requirements, technologies, applications, favoritesApprenticePP);
    }

    public void setEnd(Date date) {
    }

}