package com.example.lab4;

import com.example.lab4.Entity.Jewel;
import com.example.lab4.Service.CRUDJewel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/jewels/")
public class ApplicationController {
    private final CRUDJewel crudJewel;

    public ApplicationController(CRUDJewel crudJewel) {
        this.crudJewel = crudJewel;
    }

    @GetMapping("")
    List<Jewel> getAllJewels() {
        return crudJewel.findAll();
    }
    @GetMapping("{brand}")
    List<Jewel> getCustom(@PathVariable("brand") String brandName){
        return crudJewel.findJewelsByBrandName(brandName);
    }

    @DeleteMapping("{id}")
    public void deleter(@PathVariable("id") String id) {
        crudJewel.deleteById(Long.valueOf(id));
    }

    @PostMapping()
    public Jewel create(@RequestBody Jewel jewel) {
        return crudJewel.add(jewel);
    }

    @PutMapping()
    public Jewel update(@RequestBody Jewel jewel) {
        crudJewel.update(jewel);
        return jewel;
    }
}
