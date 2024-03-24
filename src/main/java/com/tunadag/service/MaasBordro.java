package com.tunadag.service;

import com.tunadag.model.Derece;
import com.tunadag.model.Memur;
import com.tunadag.model.Personel;
import com.tunadag.model.Yonetici;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class MaasBordro {
    public static final String MAAS_DIZINI = "src/main/java/com/tunadag/utils/maaslar/";

    public static void maasHesapla(Personel personel, String bordroTarihi) {
        double anaOdeme = 0;
        double mesai = 0;
        double toplamOdeme = 0;

        if (personel instanceof Memur) {
            Memur memur = (Memur) personel;
            Scanner scanner = new Scanner(System.in);
            System.out.println(personel.getIsim() + " " + personel.getSoyisim() + " için çalışma saati giriniz: ");
            int calismaSaati = scanner.nextInt();
            scanner.nextLine();
            System.out.println(personel.getIsim() + " için memur derecesini giriniz (1: JUNIOR, 2: MID, 3: SENIOR): ");
            int dereceInput = scanner.nextInt();
            scanner.nextLine();

            Derece derece;
            switch (dereceInput) {
                case 1:
                    derece = Derece.JUNIOR;
                    break;
                case 2:
                    derece = Derece.MID;
                    break;
                case 3:
                    derece = Derece.SENIOR;
                    break;
                default:
                    System.out.println("Geçersiz derece seçimi. Varsayılan olarak JUNIOR atanacak.");
                    derece = Derece.JUNIOR;
            }
            memur.setDerece(derece);
            toplamOdeme = memur.maasHesapla(calismaSaati);
            mesai = calismaSaati > 180 ? (calismaSaati - 180) * memur.getSaatlikUcret() * 1.5 : 0;
            anaOdeme = toplamOdeme - mesai;
            kaydet(personel.getIsim(), personel.getSoyisim(), bordroTarihi, calismaSaati, anaOdeme, mesai, toplamOdeme, null);
        } else if (personel instanceof Yonetici) {
            Yonetici yonetici = (Yonetici) personel;
            Scanner scanner = new Scanner(System.in);
            System.out.println(personel.getIsim() + " " + personel.getSoyisim() + " için saatlik ücreti giriniz (500'den az olamaz): ");
            double saatlikUcret = scanner.nextDouble();
            scanner.nextLine();
            System.out.println(personel.getIsim() + " " + personel.getSoyisim() + " için çalışma saati giriniz: ");
            int calismaSaati = scanner.nextInt();
            scanner.nextLine();
            System.out.println(personel.getIsim() + " " + personel.getSoyisim() + " için bonus değerini giriniz: ");
            double bonus = scanner.nextDouble();
            scanner.nextLine();
            yonetici.setSaatlikUcret(Math.max(saatlikUcret, 500));
            yonetici.setBonus(bonus);
            toplamOdeme = yonetici.maasHesapla(calismaSaati);
            anaOdeme = toplamOdeme - bonus;
            kaydet(personel.getIsim(), personel.getSoyisim(), bordroTarihi, calismaSaati, anaOdeme, null, toplamOdeme, bonus);
        }
    }

    private static void kaydet(String isim, String soyisim, String bordroTarihi, int calismaSaati, double anaOdeme, Double mesai, double toplamOdeme, Double bonus) {
        try {
            File maaslarKlasoru = new File(MAAS_DIZINI);
            if (!maaslarKlasoru.exists()) {
                maaslarKlasoru.mkdirs();
            }

            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
            DecimalFormat decimalFormat = (DecimalFormat) format;
            decimalFormat.setMaximumFractionDigits(2);

            String maasDosyaAdi = MAAS_DIZINI + isim + "_" + bordroTarihi.replace(" ", "_") + ".json";
            try (FileWriter fileWriter = new FileWriter(maasDosyaAdi)) {
                fileWriter.write("{\n");
                fileWriter.write("\"bordro\": \"" + bordroTarihi + "\",\n");
                fileWriter.write("\"personel\": {\n");
                fileWriter.write("\"ismi\": \"" + isim + " " + soyisim + "\",\n");
                fileWriter.write("\"calismaSaati\": \"" + calismaSaati + "\",\n");
                fileWriter.write("\"odemeDetaylari\": {\n");
                fileWriter.write("\"anaOdeme\": \"" + format.format(anaOdeme) + "\",\n");
                if (bonus != null) {
                    fileWriter.write("\"bonus\": \"" + format.format(bonus) + "\",\n");
                }
                if (mesai != null) {
                    fileWriter.write("\"mesai\": \"" + format.format(mesai) + "\",\n");
                }
                fileWriter.write("\"toplamOdeme\": \"" + format.format(toplamOdeme) + "\"\n");
                fileWriter.write("}\n}\n}");
            }

            System.out.println(isim + " için maaş bilgileri başarıyla kaydedildi.");
        } catch (IOException e) {
            System.out.println("Hata: Maaş bilgileri kaydedilirken bir sorun oluştu.");
            e.printStackTrace();
        }
    }
}