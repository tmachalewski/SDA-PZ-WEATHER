package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.sda.entity.AdminArea;

import java.util.List;

public class AdminAreaDAO {

    SessionFactory sf;

    public AdminAreaDAO(SessionFactory sf) {
        this.sf = sf;
    }

    public List<AdminArea> getAvailableAdminAreas() {
        try(Session s = sf.openSession()){
            return List.of(new AdminArea("AAN1"));
        }

    }

    public void saveNewAvailableLocations(List<AdminArea> newAdminAreas) {

    }
}
