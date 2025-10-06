package org.example.rent.other;

import org.example.rent.entity.*;
import org.example.rent.entity.property.*;
import org.example.rent.repositories.interfaces.*;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseSeeder  implements CommandLineRunner {
    @Autowired private UserRepository userRepository;
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired private BuildingRepository buildingRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private ViewingRequestRepository viewingRequestRepository;

    @Override
    public void run(String... args) {
        if (!Arrays.asList(args).contains("seed")) return;

        // Users
        List<User> users = IntStream.range(1, 6)
                .mapToObj(i -> {
                    User u = new User();
                    u.setUsername("user" + i);
                    u.setPassword("pass" + i);
                    u.setEmail("user" + i + "@mail.com");
                    u.setPhone("123456789" + i);
                    u.setRole(Role.USER);
                    return u;
                }).toList();
        userRepository.saveAll(users);

        // Addresses
        List<Address> addresses = IntStream.range(1, 10)
                .mapToObj(i -> new Address("Київська", "Вулиця " + i, "Київ", "Оболонь", String.valueOf(100 + i), i))
                .toList();
        addressRepository.saveAll(addresses);

        // Buildings
        List<Building> buildings = IntStream.range(0, 3)
                .mapToObj(i -> {
                    Building b = new Building();
                    b.setBuildingName("Будинок " + i);
                    b.setStatus(BuildingStatus.ACTIVE);
                    b.setFloors(5 + i);
                    b.setInsideArea(100 + i * 10);
                    b.setOutsideArea(50 + i * 5);
                    b.setYear(2000 + i);
                    b.setLocation(new Location(50.45 + i * 0.01, 30.52 + i * 0.01, "Description " + i));
                    b.setAddress(addresses.get(i));
                    return b;
                }).toList();
        buildingRepository.saveAll(buildings);

        // Services
        List<Services> services = IntStream.range(0, 3)
                .mapToObj(i -> {
                    Services s = new Services();
                    s.setTitle("Послуга " + i);
                    s.setDescription("Опис послуги " + i);
                    s.setDate(LocalDateTime.now());
                    s.setStatus(ServicesStatus.SHOW);
                    return s;
                }).toList();
        servicesRepository.saveAll(services);

        // Properties
        List<Property> properties = new ArrayList<>();

        Apartment apt = new Apartment();
        apt.setTitle("Квартира 1");
        apt.setArea(BigDecimal.valueOf(55));
        apt.setPricePerSquareMeter(BigDecimal.valueOf(1000));
        apt.setTotalPrice(BigDecimal.valueOf(55000));
        apt.setDate(LocalDateTime.now());
        apt.setAddress(addresses.get(0));
        apt.setBuilding(buildings.get(0));
        apt.setBedrooms(2);
        apt.setFloor(3);
        properties.add(apt);

        Apartment apt2 = new Apartment();
        apt2.setTitle("Квартира 2");
        apt2.setArea(BigDecimal.valueOf(55));
        apt2.setPricePerSquareMeter(BigDecimal.valueOf(1000));
        apt2.setTotalPrice(BigDecimal.valueOf(55000));
        apt2.setDate(LocalDateTime.now());
        apt2.setAddress(addresses.get(1));
        apt2.setBuilding(buildings.get(0));
        apt2.setBedrooms(2);
        apt2.setFloor(3);
        properties.add(apt2);

        Apartment apt3 = new Apartment();
        apt3.setTitle("Квартира 3");
        apt3.setArea(BigDecimal.valueOf(55));
        apt3.setPricePerSquareMeter(BigDecimal.valueOf(1000));
        apt3.setTotalPrice(BigDecimal.valueOf(55000));
        apt3.setDate(LocalDateTime.now());
        apt3.setAddress(addresses.get(2));
        apt3.setBuilding(buildings.get(0));
        apt3.setBedrooms(2);
        apt3.setFloor(3);
        properties.add(apt3);

        House house = new House();
        house.setTitle("Будинок 1");
        house.setArea(BigDecimal.valueOf(120));
        house.setPricePerSquareMeter(BigDecimal.valueOf(800));
        house.setTotalPrice(BigDecimal.valueOf(96000));
        house.setDate(LocalDateTime.now());
        house.setAddress(addresses.get(3));
        house.setBuilding(buildings.get(1));
        house.setBedrooms(4);
        house.setFloors(2);
        house.setOutsideArea(BigDecimal.valueOf(40));
        properties.add(house);

        House house2 = new House();
        house2.setTitle("Будинок 2");
        house2.setArea(BigDecimal.valueOf(120));
        house2.setPricePerSquareMeter(BigDecimal.valueOf(800));
        house2.setTotalPrice(BigDecimal.valueOf(96000));
        house2.setDate(LocalDateTime.now());
        house2.setAddress(addresses.get(4));
        house2.setBedrooms(4);
        house2.setFloors(2);
        house2.setOutsideArea(BigDecimal.valueOf(40));
        properties.add(house2);

        Commercial com = new Commercial();
        com.setTitle("Комерція 1");
        com.setArea(BigDecimal.valueOf(200));
        com.setPricePerSquareMeter(BigDecimal.valueOf(1500));
        com.setTotalPrice(BigDecimal.valueOf(300000));
        com.setDate(LocalDateTime.now());
        com.setAddress(addresses.get(5));
        com.setBuilding(buildings.get(2));
        com.setFloor(1);
        properties.add(com);

        Commercial com2 = new Commercial();
        com2.setTitle("Комерція 2");
        com2.setArea(BigDecimal.valueOf(200));
        com2.setPricePerSquareMeter(BigDecimal.valueOf(1500));
        com2.setTotalPrice(BigDecimal.valueOf(300000));
        com2.setDate(LocalDateTime.now());
        com2.setAddress(addresses.get(6));
        com2.setBuilding(buildings.get(2));
        com2.setFloor(1);
        properties.add(com2);

        Commercial com3 = new Commercial();
        com3.setTitle("Комерція 3");
        com3.setArea(BigDecimal.valueOf(200));
        com3.setPricePerSquareMeter(BigDecimal.valueOf(1500));
        com3.setTotalPrice(BigDecimal.valueOf(300000));
        com3.setDate(LocalDateTime.now());
        com3.setAddress(addresses.get(7));
        com3.setBuilding(buildings.get(2));
        com3.setFloor(1);
        properties.add(com3);

        Plots plot = new Plots();
        plot.setTitle("Ділянка 1");
        plot.setArea(BigDecimal.valueOf(500));
        plot.setPricePerSquareMeter(BigDecimal.valueOf(100));
        plot.setTotalPrice(BigDecimal.valueOf(50000));
        plot.setDate(LocalDateTime.now());
        plot.setAddress(addresses.get(8));
        properties.add(plot);

        propertyRepository.saveAll(properties);

        // Viewing Requests
        List<ViewingRequest> requests = IntStream.range(0, 7)
                .mapToObj(i -> {
                    ViewingRequest vr = new ViewingRequest();
                    vr.setComments("Хочу подивитись об'єкт " + i);
                    vr.setStatus(i % 2 == 0 ? ViewingRequestStatus.NEW : ViewingRequestStatus.PROCESSED);
                    vr.setDate(LocalDateTime.now());
                    vr.setUser(users.get(i % users.size()));
                    vr.setProperty(properties.get(i % properties.size()));
                    return vr;
                }).toList();
        viewingRequestRepository.saveAll(requests);

        System.out.println("✅ Базу заповнено тестовими даними!");
    }
}
