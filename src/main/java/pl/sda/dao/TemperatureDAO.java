package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.entity.TemperatureMeasurement;

public class TemperatureDAO {

    SessionFactory sf;

    public TemperatureDAO(SessionFactory sf) {
        this.sf=sf;
    }

    public void save(TemperatureMeasurement tm) {
        try(Session s = sf.openSession()){
            Transaction tr = s.beginTransaction();
            s.save(tm);
            tr.commit();
        }
    }
}
