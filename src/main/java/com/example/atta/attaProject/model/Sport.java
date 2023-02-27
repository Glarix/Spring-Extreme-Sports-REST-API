package com.example.atta.attaProject.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sport")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_id")
    private long id;

    @Column(name = "sport_name")
    private String sportName;

    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY)
    private Set<CitySport> citySports;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
