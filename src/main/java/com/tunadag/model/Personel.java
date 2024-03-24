package com.tunadag.model;

public abstract class Personel {
    private String isim;
    private String soyisim;
    private double saatlikUcret;

    public Personel(String isim, String soyisim, double saatlikUcret) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.saatlikUcret = saatlikUcret;
    }

    public abstract double maasHesapla(int calismaSaati);

    public String getIsim() {
        return isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSaatlikUcret(double saatlikUcret) {
        this.saatlikUcret = saatlikUcret;
    }

    public double getSaatlikUcret() {
        return saatlikUcret;
    }
}