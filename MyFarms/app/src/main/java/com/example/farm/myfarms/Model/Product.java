package com.example.farm.myfarms.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.firebase.firestore.Exclude;

public class Product implements Serializable {


    @Exclude private String id;

    String dolno;
    String kujawsko;
    String lubelskie;
    String lubuskie;
    String mazowieckie;
    String malopolskie;
    String name;
    String opolskie;
    String podkar;
    String podlaskie;
    String pomorskie;
    String warminsko;
    String wielkopolskie;
    String zachodnio;
    String lodzkie;
    String slaskie;
    String swie;



    Map<String, String> wojewodztwa = new HashMap<String, String>();

    public Product() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDolno() {
        return dolno;
    }

    public void setDolno(String dolno) {
        this.dolno = dolno;
    }

    public String getKujawsko() {
        return kujawsko;
    }

    public void setKujawsko(String kujawsko) {
        this.kujawsko = kujawsko;
    }

    public String getLubelskie() {
        return lubelskie;
    }

    public void setLubelskie(String lubelskie) {
        this.lubelskie = lubelskie;
    }

    public String getLubuskie() {
        return lubuskie;
    }

    public void setLubuskie(String lubuskie) {
        this.lubuskie = lubuskie;
    }

    public String getMazowieckie() {
        return mazowieckie;
    }

    public void setMazowieckie(String mazowieckie) {
        this.mazowieckie = mazowieckie;
    }

    public String getMalopolskie() {
        return malopolskie;
    }

    public void setMalopolskie(String malopolskie) {
        this.malopolskie = malopolskie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpolskie() {
        return opolskie;
    }

    public void setOpolskie(String opolskie) {
        this.opolskie = opolskie;
    }

    public String getPodkar() {
        return podkar;
    }

    public void setPodkar(String podkar) {
        this.podkar = podkar;
    }

    public String getPodlaskie() {
        return podlaskie;
    }

    public void setPodlaskie(String podlaskie) {
        this.podlaskie = podlaskie;
    }

    public String getPomorskie() {
        return pomorskie;
    }

    public void setPomorskie(String pomorskie) {
        this.pomorskie = pomorskie;
    }

    public String getWarminsko() {
        return warminsko;
    }

    public void setWarminsko(String warminsko) {
        this.warminsko = warminsko;
    }

    public String getWielkopolskie() {
        return wielkopolskie;
    }

    public void setWielkopolskie(String wielkopolskie) {
        this.wielkopolskie = wielkopolskie;
    }

    public String getZachodnio() {
        return zachodnio;
    }

    public void setZachodnio(String zachodnio) {
        this.zachodnio = zachodnio;
    }

    public String getLodzkie() {
        return lodzkie;
    }

    public void setLodzkie(String lodzkie) {
        this.lodzkie = lodzkie;
    }

    public String getSlaskie() {
        return slaskie;
    }

    public void setSlaskie(String slaskie) {
        this.slaskie = slaskie;
    }

    public String getSwie() {
        return swie;
    }

    public void setSwie(String swie) {
        this.swie = swie;
    }

    public Map<String, String> getWojewodztwa() {

        wojewodztwa.put("dolnośląskie", getDolno());
        wojewodztwa.put("kujawsko_pomorskie" , getKujawsko());
        wojewodztwa.put("lubelskie" , getLubelskie());
        wojewodztwa.put("lubuskie" , getLubuskie());
        wojewodztwa.put("mazowieckie" , getMazowieckie());
        wojewodztwa.put("małopolskie" , getMalopolskie());
        wojewodztwa.put("name" , getName());
        wojewodztwa.put("opolskie" , getOpolskie());
        wojewodztwa.put("podkarpackie" , getPodkar());
        wojewodztwa.put("podlaskie" , getPodlaskie());
        wojewodztwa.put("pomorskie" , getPomorskie());
        wojewodztwa.put("warmińsko_mazurskie" , getWarminsko());
        wojewodztwa.put("wielkopolskie" , getWielkopolskie());
        wojewodztwa.put("zachodniopomorskie" , getZachodnio());
        wojewodztwa.put("łódzkie", getLodzkie());
        wojewodztwa.put("śląskie", getSlaskie());
        wojewodztwa.put("świętokrzyskie" , getSwie());

        return wojewodztwa;
    }




}
