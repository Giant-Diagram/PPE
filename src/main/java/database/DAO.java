package database;

import database.Connector;
import dataclassesHib.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @version 2.0
 * <p>
 * DAO class for the DB, all methods are in here
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 */


public class DAO {
    //private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("POf");

    public void insertFavorite(int apprenticeId, int ppId) {
        EntityManager entityManager = Connector.getInstance().open();
        Practiceplace practiceplace;
        Apprentice apprentice;
        try {
            entityManager.getTransaction().begin();
            practiceplace = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", ppId).getSingleResult();
            apprentice = entityManager.createQuery("from Apprentice p where p.id = :id", Apprentice.class).setParameter("id", apprenticeId).getSingleResult();
            apprentice.getFavorites().add(practiceplace);
            entityManager.merge(apprentice);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public static ArrayList<Practiceplace> selectAllPP() {
        EntityManager entityManager = Connector.getInstance().open();
        ArrayList<Practiceplace> practiceplaces = null;

        try {
            entityManager.getTransaction().begin();
            practiceplaces = (ArrayList<Practiceplace>) entityManager.createQuery("from Practiceplace", Practiceplace.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
        return practiceplaces;
    }

    public static ArrayList<User> selectAllUser() {
        EntityManager entityManager = Connector.getInstance().open();
        ArrayList<User> users = null;

        try {
            entityManager.getTransaction().begin();
            users = (ArrayList<User>) entityManager.createQuery("from User", User.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
        return users;
    }

    public static ArrayList<Admin> selectAllAdmins() {
        EntityManager entityManager = Connector.getInstance().open();
        ArrayList<Admin> admins = null;

        try {
            entityManager.getTransaction().begin();
            admins = (ArrayList<Admin>) entityManager.createQuery("from Admin", Admin.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
        return admins;
    }

    public void deleteUserById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        User user = null;
        List<Notification> notifications = null;

        try {
            entityManager.getTransaction().begin();

            user = entityManager.createQuery("from User u where u.id = :id", User.class).setParameter("id", id).getSingleResult();
            notifications = entityManager.createQuery("from Notification n where n.apprentice.id = :id or n.user.id = :id", Notification.class).setParameter("id", id).getResultList();

            for (Notification notification : notifications)
                entityManager.remove(notification);
            entityManager.remove(user);

            if (user.getRole().equalsIgnoreCase("educator")) {
                for (Practiceplace p : ((Educator) user).getPracticeplaces()) {
                    entityManager.remove(p);
                    for (Apprentice a : p.getFavoritesApprenticePP()) {
                        a.getFavorites().remove(p);
                    }
                }
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

    }


    public void deletePracticeplaceById(int ppId) {
        EntityManager entityManager = Connector.getInstance().open();
        Practiceplace practiceplace = null;
        try {
            entityManager.getTransaction().begin();
            practiceplace = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", ppId).getSingleResult();

            entityManager.remove(practiceplace);

            for (Apprentice a : practiceplace.getFavoritesApprenticePP()) {
                a.getFavorites().remove(practiceplace);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void deleteApplicationById(int apid) {
        EntityManager entityManager = Connector.getInstance().open();
        Application application = null;
        try {
            entityManager.getTransaction().begin();
            application = entityManager.createQuery("from Application a where a.id = :id", Application.class).setParameter("id", apid).getSingleResult();

            entityManager.remove(application);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }


    public void deleteFavorite(int apprenticeId, int ppId) {
        EntityManager entityManager = Connector.getInstance().open();
        Apprentice apprentice;
        try {
            entityManager.getTransaction().begin();
            apprentice = entityManager.createQuery("from Apprentice ap where ap.id = :id", Apprentice.class).setParameter("id", apprenticeId).getSingleResult();
            for (Practiceplace p : apprentice.getFavorites()) {
                if (p.getId() == ppId) {
                    apprentice.getFavorites().remove(p);
                    break;
                }
            }

            entityManager.merge(apprentice);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }


    public ArrayList<Practiceplace> selectAllValid() {
        EntityManager entityManager = Connector.getInstance().open();
        ArrayList<Practiceplace> result = null;
        try {
            entityManager.getTransaction().begin();
            LocalDate now = LocalDate.now();            
            String nowSt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            result = (ArrayList<Practiceplace>) entityManager.createQuery("from Practiceplace e WHERE e.end >= :ende", Practiceplace.class).setParameter("ende", LocalDate.of(now.getYear(),  now.getMonth(),  now.getDayOfMonth())).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return result;
    }

    public Educator selectEducatorById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        Educator educator = null;

        try {
            entityManager.getTransaction().begin();
            educator = entityManager.createQuery("from Educator e where e.id = :id", Educator.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return educator;
    }

    public User selectUserById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        User user = null;

        try {
            entityManager.getTransaction().begin();
            user = entityManager.createQuery("from User u where u.id = :id", User.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return user;
    }

    public Apprentice selectApprenticeById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        Apprentice apprentice = null;

        try {
            entityManager.getTransaction().begin();
            apprentice = entityManager.createQuery("from Apprentice ap where ap.id = :id", Apprentice.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return apprentice;
    }


    public Admin selectAdminById(int id) {
        EntityManager entityManager = Connector.getInstance().open();

        Admin admin = null;

        try {
            entityManager.getTransaction().begin();
            admin = entityManager.createQuery("from Admin ad where ad.id = :id", Admin.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return admin;
    }

    public void updateEducator(Educator educator) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            Educator updatedEdu = entityManager.merge(educator);
            entityManager.persist(updatedEdu);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void updateApprentice(Apprentice apprentice) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            Apprentice updatedApp = entityManager.merge(apprentice);
            entityManager.persist(updatedApp);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void updateAdmin(Admin admin) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            Admin updateedAdmin = entityManager.merge(admin);
            entityManager.persist(updateedAdmin);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public Practiceplace insertRT(int id, Practiceplace pp) {
        Practiceplace oldPP = null;

        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            oldPP = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();

            if (pp.getTechnologies() != null && !Arrays.equals(oldPP.getTechnologies().toArray(), pp.getTechnologies().toArray())) {
                if (oldPP.getTechnologies().size() > pp.getTechnologies().size())
                    for (int i = oldPP.getTechnologies().size() - 1; i > pp.getTechnologies().size() - 1; i--)
                        oldPP.getTechnologies().set(i, null);


                for (int i = 0; i < pp.getTechnologies().size(); i++) {
                    try {
                        entityManager.persist(pp.getTechnologies().get(i));
                        oldPP.getTechnologies().set(i, pp.getTechnologies().get(i));
                    } catch (IndexOutOfBoundsException ex) {
                        entityManager.persist(pp.getTechnologies().get(i));
                        oldPP.getTechnologies().add(pp.getTechnologies().get(i));
                    }
                }
            }

            if (pp.getRequirements() != null && !Arrays.equals(oldPP.getRequirements().toArray(), pp.getRequirements().toArray())) {
                if (oldPP.getRequirements().size() > pp.getRequirements().size())
                    for (int i = oldPP.getRequirements().size() - 1; i > pp.getRequirements().size() - 1; i--)
                        oldPP.getRequirements().set(i, null);


                for (int i = 0; i < pp.getRequirements().size(); i++) {
                    try {
                        entityManager.persist(pp.getRequirements().get(i));
                        oldPP.getRequirements().set(i, pp.getRequirements().get(i));
                    } catch (IndexOutOfBoundsException ex) {
                        entityManager.persist(pp.getRequirements().get(i));
                        oldPP.getRequirements().add(pp.getRequirements().get(i));
                    }
                }
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return oldPP;
    }

    public void insertT(Technology t) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(t);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }
    public void insertL(Language l) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(l);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void removeImgByPPId(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            Practiceplace pp = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();

            pp.setImage(null);
            pp.setImageType(null);

            Practiceplace updatedPP = entityManager.merge(pp);

            entityManager.persist(updatedPP);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void updatePPRotationsites(int id, int rotationsites) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            Practiceplace pp = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();

            pp.setRotationsites(rotationsites);

            Practiceplace updatedPP = entityManager.merge(pp);

            entityManager.persist(updatedPP);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void updatePPById(int id, Practiceplace pp) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();

            Practiceplace oldPP = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();

            if (pp.getName() != null && !pp.getName().equals(oldPP.getName()))
                oldPP.setName(pp.getName());

            if (pp.getDescription() != null && !pp.getDescription().equals(oldPP.getDescription()))
                oldPP.setDescription(pp.getDescription());


            if (pp.getRotationsites() != oldPP.getRotationsites())
                oldPP.setRotationsites(pp.getRotationsites());

            if (pp.getSubject() != null && !pp.getSubject().equals(oldPP.getSubject()))
                oldPP.setSubject(pp.getSubject());

            if (pp.getTechnologies() != null) {
                int listSize = oldPP.getTechnologies().size();
                for (int i = listSize - 1; i > -1; i--) {
                    entityManager.remove(oldPP.getTechnologies().get(i));
                    oldPP.getTechnologies().remove(i);
                }

                for (int i = 0; i < pp.getTechnologies().size(); i++) {
                    Technology t = new Technology();
                    t.setTechnology(pp.getTechnologies().get(i).getTechnology());
                    t.setPracticeplace(oldPP);

                    entityManager.persist(t);

                    oldPP.getTechnologies().add(t);
                }
            }

            if (pp.getLanguages() != null) {
                int listSize = oldPP.getLanguages().size();
                for (int i = listSize - 1; i > -1; i--) {
                    entityManager.remove(oldPP.getLanguages().get(i));
                    oldPP.getLanguages().remove(i);
                }

                for (int i = 0; i < pp.getLanguages().size(); i++) {
                    Language l = new Language();
                    l.setLanguage(pp.getLanguages().get(i).getLanguage());
                    l.setPracticeplace(oldPP);

                    entityManager.persist(l);

                    oldPP.getLanguages().add(l);
                }
            }

            if (pp.getRequirements() != null) {
                int listSize = oldPP.getRequirements().size();

                for (int i = listSize - 1; i > -1; i--) {
                    entityManager.remove(oldPP.getRequirements().get(i));
                    oldPP.getRequirements().remove(i);
                }

                for (int i = 0; i < pp.getRequirements().size(); i++) {
                    Requirement r = new Requirement();
                    r.setRequirement(pp.getRequirements().get(i).getRequirement());
                    r.setPracticeplace(oldPP);

                    entityManager.persist(r);

                    oldPP.getRequirements().add(r);
                }
            }

            if (pp.getStreet() != null && !pp.getStreet().equals(oldPP.getStreet()))
                oldPP.setStreet(pp.getStreet());

            if (pp.getStreetNr() != null && !pp.getStreetNr().equals(oldPP.getStreetNr()))
                oldPP.setStreetNr(pp.getStreetNr());

            if (pp.getZip() != null && !pp.getZip().equals(oldPP.getZip()))
                oldPP.setZip(pp.getZip());

            if (pp.getKindOfDeployment() != null && !pp.getKindOfDeployment().equals(oldPP.getKindOfDeployment()))
                oldPP.setKindOfDeployment(pp.getKindOfDeployment());

            if (pp.getPlace() != null && !pp.getPlace().equals(oldPP.getPlace()))
                oldPP.setPlace(pp.getPlace());

            if (pp.getStart() != null && !pp.getStart().equals(oldPP.getStart()))
                oldPP.setStart(pp.getStart());

            if (pp.getEnd() != null && !pp.getEnd().equals(oldPP.getEnd()))
                oldPP.setEnd(pp.getEnd());

            if (pp.getApprenticeYears() != null && !Arrays.equals(pp.getApprenticeYears().toArray(), oldPP.getApprenticeYears().toArray())) {

                if (oldPP.getApprenticeYears().size() > pp.getApprenticeYears().size())
                    for (int i = oldPP.getApprenticeYears().size() - 1; i > pp.getApprenticeYears().size() - 1; i--)
                        oldPP.getApprenticeYears().set(i, null);


                for (int i = 0; i < pp.getApprenticeYears().size(); i++) {
                    try {
                        oldPP.getApprenticeYears().set(i, pp.getApprenticeYears().get(i));
                    } catch (IndexOutOfBoundsException ex) {
                        oldPP.getApprenticeYears().add(pp.getApprenticeYears().get(i));
                    }
                }
            }

            if (pp.getImage() != null && !Arrays.equals(pp.getImage(), oldPP.getImage())) {
                oldPP.setImage(pp.getImage());
                oldPP.setImageType(pp.getImageType());
            }

            Practiceplace updatedPP = entityManager.merge(oldPP);

            entityManager.persist(updatedPP);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public Practiceplace selectPPById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        Practiceplace pp = null;
        try {
            entityManager.getTransaction().begin();
            pp = entityManager.createQuery("from Practiceplace p where p.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
        return pp;
    }

    public List<User> selectUserByEmail(String email) {
        EntityManager entityManager = Connector.getInstance().open();
        List<User> users = null;
        try {
            entityManager.getTransaction().begin();
            users = entityManager.createQuery("from User u where u.email = :email", User.class).setParameter("email", email).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
        return users;
    }

    public Application selectApplicationById(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        Application application = null;

        try {
            entityManager.getTransaction().begin();
            application = entityManager.createQuery("from Application ap where ap.id = :id", Application.class).setParameter("id", id).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }

        return application;
    }

    public void updateconfirmed(int userid) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            User result = entityManager.createQuery("select e from User e WHERE e.id = :id", User.class).setParameter("id", userid).getSingleResult();
            if (result.isIsconfirmed()) {
                result.setIsconfirmed(false);
            } else {
                result.setIsconfirmed(true);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void insertPPRT(Practiceplace pp) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            for (Requirement r : pp.getRequirements()) {
                entityManager.persist(r);
            }
            for (Technology t : pp.getTechnologies()) {
                entityManager.persist(t);
            }
            for (Language l : pp.getLanguages()) {
                entityManager.persist(l);
            }
            entityManager.persist(pp);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }

    public void insertNewapplication(Application b) {
        EntityManager entityManager = Connector.getInstance().open();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(b);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            Connector.getInstance().close();
        }
    }


    /**
     * updateApplication number 1
     */

    public void updateApplicationStatus(int id, String status) {

/** UPDATE-Method */

        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();

        Application result = entityManager.createQuery("from Application app where app.id = :id", Application.class).setParameter("id", id).getSingleResult();
        result.setStatus(status);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateNotificationStatus(int id, boolean status) {

/** UPDATE-Method */

        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();

        Notification result = entityManager.createQuery("from Notification no where no.id = :id", Notification.class).setParameter("id", id).getSingleResult();
        result.setHasbeenseen(status);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 1st update Method for the UserAccount (email changing)
     */

    public static void updateUserAccountEmail(int id, String email) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        User result = entityManager.createQuery("from User user where user.id = :id", User.class).setParameter("id", id).getSingleResult();
        result.setEmail(email);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 2nd update Method for the UserAccount (firstname changing)
     */

    public static void updateUserAccountFirstname(int id, String firstname) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        User result = entityManager.createQuery("from User user where user.id = :id", User.class).setParameter("id", id).getSingleResult();
        result.setFirstname(firstname);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 3rd update Method for the UserAccount (lastname changing) {GPN cant be changed}
     */

    public static void updateUserAccountLastname(int id, String lastname) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        User result = entityManager.createQuery("from User user where user.id = :id", User.class).setParameter("id", id).getSingleResult();
        result.setLastname(lastname);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 4th update Method for the UserAccount (password changing) {cant be changed if your forgot it, only just if you want a better password}
     */

    public static void updateUserAccountPassword(int id, String password) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        User result = entityManager.createQuery("from User user where user.id = :id", User.class).setParameter("id", id).getSingleResult();
        result.setPassword(password);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 5th update Method for the UserAccount (role changing)
     */

    public static void updateUserAccountRole(int id, String role) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        User result = entityManager.createQuery("from User user where user.id = :id", User.class).setParameter("id", id).getSingleResult();
        result.setRole(role);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 16th update Method for the Practiceplace (years changing)
     */

    public static void updatePracticeplaceYears(int id, String description) {
        EntityManager entityManager = Connector.getInstance().open();
        entityManager.getTransaction().begin();
        Practiceplace result = entityManager.createQuery("from Practiceplace practiceplace where practiceplace.id = :id", Practiceplace.class).setParameter("id", id).getSingleResult();
        result.setDescription(description);

        entityManager.merge(result);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
