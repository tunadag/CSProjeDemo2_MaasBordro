package com.tunadag;

import com.tunadag.model.Memur;
import com.tunadag.model.Personel;
import com.tunadag.service.DosyaOku;
import com.tunadag.service.MaasBordro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // JSON dosyasından personel bilgilerini oku
        List<Personel> personelListesi = DosyaOku.personelBilgileriniOku("src/main/java/com/tunadag/utils/personel.json");

        // Bordro tarihini kullanıcıdan al
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bordro tarihini giriniz (örneğin MART 2024): ");
        String bordroTarihi = scanner.nextLine();

        // 150 saatten az çalışanları tutacak bir liste oluştur
        List<Personel> azCalisanlar = new ArrayList<>();

        // Personel listesindeki her bir personel için maaş hesapla ve dosyaya kaydet
        for (Personel personel : personelListesi) {
            System.out.print(personel.getIsim() + " " + personel.getSoyisim() + " için çalışma saati giriniz: ");
            int calismaSaati = scanner.nextInt();
            scanner.nextLine(); // Boş satırı oku

            // 150 saatten az çalışanları kontrol et ve listeye ekle
            if (calismaSaati < 150) {
                azCalisanlar.add(personel);
            }

            MaasBordro.maasHesapla(personel, calismaSaati, bordroTarihi);
        }

        // Personel listesindeki her bir personelin raporunu ekrana yazdır
        for (Personel personel : personelListesi) {
            System.out.println(personel.getIsim() + " " + personel.getSoyisim() + " için maaş raporu:");
            String maasDosyaAdi = "src/main/java/com/tunadag/utils/maaslar/" + personel.getIsim() + "_" + bordroTarihi.replace(" ", "_") + ".json";
            try {
                DosyaOku.maasRaporunuYazdir(maasDosyaAdi);
            } catch (Exception e) {
                System.out.println("Maaş raporu bulunamadı.");
            }
            System.out.println();
        }

        // 150 saatten az çalışan personelleri ekrana yazdır
        System.out.println("150 saatten az çalışan personeller:");
        for (Personel azCalisan : azCalisanlar) {
            System.out.println(azCalisan.getIsim() + " " + azCalisan.getSoyisim());
        }
    }
}