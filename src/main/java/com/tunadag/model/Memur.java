package com.tunadag.model;

public class Memur extends Personel {
    private Derece derece;

    public Memur(String isim, String soyisim, Derece derece) {
        super(isim, soyisim, saatlikUcret(derece));
        this.derece = derece;
    }

    public void setDerece(Derece derece) {
        this.derece = derece;
        setSaatlikUcret(saatlikUcret(derece));
    }

    private static double saatlikUcret(Derece derece) {
        switch (derece) {
            case JUNIOR:
                return 500;
            case MID:
                return 600;
            case SENIOR:
                return 700;
            default:
                return 500;
        }
    }

    @Override
    public double maasHesapla(int calismaSaati) {
        double aylikMaas;
        if (calismaSaati <= 180) {
            aylikMaas = calismaSaati * getSaatlikUcret();
        } else {
            aylikMaas = (180 * getSaatlikUcret()) + ((calismaSaati - 180) * 1.5 * getSaatlikUcret());
        }
        return aylikMaas;
    }
}

