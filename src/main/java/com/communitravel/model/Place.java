package com.communitravel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Place - used to save the city of a room
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {

    public enum Country {
        DE, //Deutschland
        AT, //Österreich
        CH, //Schweiz
        IT, //Italien
        BE, //Belgien
        FR, //Frankreich
        NL, //Niederlande
        DK, //Dänemark
        SE, //Schweden
        GB, //Großbrittanien
        ES, //Spanien
        BA, //Bosnien & Herzegowina
        BG, //Bulgarien
        EE, //Estland
        FO, //Färöer
        FI, //Finnland
        GR, //Griechenland
        IE, //Irland
        IS, //Island
        HR, //Kroatien
        LV, //Lettland
        LI, //Liechtenstein
        LT, //Litauen
        LU, //Luxemburg
        MT, //Malta
        MK, //Mazedonien
        MC, //Monaco
        NO, //Norwegen
        PL, //Polen
        RO, //Rumänien
        RU, //Russland
        SM, //San Marino
        CS, //Serbien Montenegro
        SK, //Slowakei
        SI, //Slowenien
        CZ, //Tschechien
        HU, //Ungarn
        UA  //Ukraine
    }

    private String name;
    private String address;
    private Country country;

    public Place() {}

    public Place(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public  String getName() {
        return name;
    }

    public String toString() {
        return "[ Name: " + name + ", Address: " + address + ", Country: " + country + "]";
    }
}
