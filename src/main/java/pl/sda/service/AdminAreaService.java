package pl.sda.service;

import pl.sda.dao.AdminAreaDAO;
import pl.sda.entity.AdminArea;

import java.util.ArrayList;
import java.util.List;

public class AdminAreaService {

    AdminAreaDAO aad;
    AccuWeatherService aws;

    public AdminAreaService(AdminAreaDAO aad, AccuWeatherService aws) {
        this.aad=aad;
        this.aws=aws;
    }

    public List<AdminArea> getAvailableAdminAreas() {
        return aad.getAvailableAdminAreas();
    }

    public void updateAvailableLocations() {
        List<AdminArea> newAdminAreas = new ArrayList<>();
        aad.saveNewAvailableLocations(newAdminAreas);
    }
}
