package com.example.lab4.Service;

import com.example.lab4.Entity.Jewel;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public interface CRUDJewelInt {
    Jewel add(@NotNull Jewel jewel);
    ArrayList<Jewel> findAll();
    void update(@NotNull Jewel jewel);
    void deleteById(@NotNull Long id);
    Optional<Jewel> findById(Long id);
    ArrayList<Jewel> findJewelsByBrandName(String name);
}
