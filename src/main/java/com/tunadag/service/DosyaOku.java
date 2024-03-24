package com.tunadag.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tunadag.model.Derece;
import com.tunadag.model.Memur;
import com.tunadag.model.Personel;
import com.tunadag.model.Yonetici;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DosyaOku {
    public static List<Personel> dosyadanOku(String dosyaYolu) {
        List<Personel> personelListesi = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            File dosya = new File(dosyaYolu);
            PersonelDosya[] personelDosyaDizisi = mapper.readValue(dosya, PersonelDosya[].class);

            for (PersonelDosya pd : personelDosyaDizisi) {
                if (pd.getRole().equals("Yonetici")) {
                    personelListesi.add(new Yonetici(pd.getName(), pd.getSurname(), 500, 0));
                } else if (pd.getRole().equals("Memur")) {
                    personelListesi.add(new Memur(pd.getName(), pd.getSurname(), Derece.JUNIOR));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return personelListesi;
    }

    // JSON dosyasındaki yapıya uygun iç içe sınıf
    private static class PersonelDosya {
        private String name;
        private String surname;
        private String role;

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getRole() {
            return role;
        }
    }

    public static void tumRaporlariOku() {
        File maaslarKlasoru = new File(MaasBordro.MAAS_DIZINI);
        File[] dosyaListesi = maaslarKlasoru.listFiles();

        if (dosyaListesi != null) {
            List<Personel> azCalisanlar = new ArrayList<>(); // 150 saatten az çalışanları tutacak liste
            for (File dosya : dosyaListesi) {
                if (dosya.isFile() && dosya.getName().endsWith(".json")) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        PersonelRapor rapor = mapper.readValue(dosya, PersonelRapor.class);
                        int calismaSaati = rapor.getPersonel().getCalismaSaati();
                        if (calismaSaati < 150) { // 150 saatten az çalışan memurları tespit et
                            azCalisanlar.add(new Memur(rapor.getPersonel().getIsmi(), "", Derece.JUNIOR));
                        }

                        System.out.println("");
                        System.out.println("Maaş Raporu:");
                        System.out.println("Bordro Tarihi: " + rapor.getBordro());
                        System.out.println("Personel İsmi: " + rapor.getPersonel().getIsmi());
                        System.out.println("Çalışma Saati: " + calismaSaati);
                        System.out.println("Ana Ödeme: " + rapor.getPersonel().getOdemeDetaylari().getAnaOdeme());
                        if (rapor.getPersonel().getOdemeDetaylari().getMesai() != null) {
                            System.out.println("Mesai: " + rapor.getPersonel().getOdemeDetaylari().getMesai());
                        } else if (rapor.getPersonel().getOdemeDetaylari().getBonus() != null) {
                            System.out.println("Bonus: " + rapor.getPersonel().getOdemeDetaylari().getBonus());
                        }
                        System.out.println("Toplam Ödeme: " + rapor.getPersonel().getOdemeDetaylari().getToplamOdeme());
                        System.out.println("----------------------------------");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (!azCalisanlar.isEmpty()) { // 150 saatten az çalışan varsa bilgilerini yazdır
                System.out.println("\n150 saatten az çalışan personeller:");
                for (Personel personel : azCalisanlar) {
                    System.out.println("İsim: " + personel.getIsim() + personel.getSoyisim());
                }
                System.out.println("");
            }
        } else {
            System.out.println("Maaş raporu bulunamadı.");
        }
    }

    private static class PersonelRapor {
        private String bordro;
        private PersonelBilgisi personel;

        public String getBordro() {
            return bordro;
        }

        public PersonelBilgisi getPersonel() {
            return personel;
        }
    }

    private static class PersonelBilgisi {
        private String ismi;
        private int calismaSaati;
        private OdemeDetaylari odemeDetaylari;

        public String getIsmi() {
            return ismi;
        }

        public int getCalismaSaati() {
            return calismaSaati;
        }

        public OdemeDetaylari getOdemeDetaylari() {
            return odemeDetaylari;
        }
    }

    private static class OdemeDetaylari {
        private String anaOdeme;
        private String mesai;
        private String bonus;
        private String toplamOdeme;

        public String getAnaOdeme() {
            return anaOdeme;
        }

        public String getMesai() {
            return mesai;
        }

        public String getBonus() { // Bonus getter ekleniyor
            return bonus;
        }

        public String getToplamOdeme() {
            return toplamOdeme;
        }
    }
}
