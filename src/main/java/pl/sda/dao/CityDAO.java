package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    SessionFactory sf;
    public CityDAO(SessionFactory sf) {
        this.sf = sf;
    }

    public List<City> getAvailableCities() {
        List<City> cities = new ArrayList<>();
        try(Session s = sf.openSession()){
            cities = s.createQuery("FROM City", City.class).getResultList();
        }
        return cities;
    }

    public void save(City c) {
        try(Session s = sf.openSession()){
            Transaction tr = s.beginTransaction();
            s.save(c);
            tr.commit();
        }
    }
}
