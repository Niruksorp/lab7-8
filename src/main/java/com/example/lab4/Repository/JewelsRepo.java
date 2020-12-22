package com.example.lab4.Repository;

import com.example.lab4.Entity.Jewel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface JewelsRepo extends JpaRepository<Jewel, Long> {

    ArrayList<Jewel> findJewelsByBrandName(String name);
}
