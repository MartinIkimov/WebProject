package com.example.EverExpanding.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/planets")
public class PlanetController {

    @GetMapping("/sun")
    public String sun() {
        return "sun";
    }

    @GetMapping("/mercury")
    public String mercury() {
        return "mercury";
    }

    @GetMapping("/venus")
    public String venus() {
        return "venus";
    }

    @GetMapping("/earth")
    public String earth() {
        return "earth";
    }

    @GetMapping("/theMoon")
    public String moon() {
        return "theMoon";
    }
    @GetMapping("/mars")
    public String mars() {
        return "mars";
    }

    @GetMapping("/jupiter")
    public String jupiter() {
        return "jupiter";
    }

    @GetMapping("/saturn")
    public String saturn() {
        return "saturn";
    }

    @GetMapping("/uranus")
    public String uranus() {
        return "uranus";
    }

    @GetMapping("/neptune")
    public String neptune() {
        return "neptune";
    }

}
