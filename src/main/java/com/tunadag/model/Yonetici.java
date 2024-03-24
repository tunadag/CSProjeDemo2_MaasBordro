package com.tunadag.model;

public class Yonetici extends Personel {
    private double bonus;

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Yonetici(String isim, String soyisim, double saatlikUcret, double bonus) {
        super(isim, soyisim, Math.max(saatlikUcret, 500));
        this.bonus = bonus;
    }

    @Override
    public double maasHesapla(int calismaSaati) {
        return super.getSaatlikUcret() * calismaSaati + bonus;
    }
}
