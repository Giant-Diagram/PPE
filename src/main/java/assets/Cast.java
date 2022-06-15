package assets;

import dataclassesHib.Practiceplace;
import dataclassesHib.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cast {
    public static ArrayList<Practiceplace> convertObjectToPPList(Object obj) {
        ArrayList<Practiceplace> practiceplaces = new ArrayList<>();

        List<?> list;
        if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);

            for (Object o : list){
                if (o instanceof Practiceplace)
                    practiceplaces.add(((Practiceplace) o));
            }
        }

        return practiceplaces;
    }

    public static ArrayList<User> convertObjectToUserList(Object obj) {
        ArrayList<User> users = new ArrayList<>();

        List<?> list;
        if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);

            for (Object o : list){
                if (o instanceof User)
                    users.add(((User) o));
            }
        }

        return users;
    }

        }
