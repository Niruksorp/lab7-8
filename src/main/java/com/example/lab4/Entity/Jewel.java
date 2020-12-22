package com.example.lab4.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@Builder
@Entity
public class Jewel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brandName;

    @Enumerated(EnumType.STRING)
    private Collection collection;

    private Integer cost;

    private Integer warranty;

    @Override
    public String toString() {
        return "Jewel{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", collection='" + collection + '\'' +
                ", cost=" + cost +
                ", warranty=" + warranty +
                '}';
    }
}
