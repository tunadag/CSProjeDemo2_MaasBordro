package com.tunadag;

import com.tunadag.model.Personel;
import com.tunadag.service.DosyaOku;
import com.tunadag.service.MaasBordro;

import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Aylık Maaş Bordrosu Oluşturma");
            System.out.println("2. Maaş Raporu Görüntüleme");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminizi yapınız: ");
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            if (secim == 1) {
                List<Personel> personelListesi = DosyaOku.dosyadanOku("src/main/java/com/tunadag/utils/personel.json");
                System.out.print("Bordro tarihini giriniz (örneğin MART 2024): ");
                String bordroTarihi = scanner.nextLine();

                for (Personel personel : personelListesi) {
                    MaasBordro.maasHesapla(personel, bordroTarihi);
                }
            } else if (secim == 2) {
                System.out.println("Maaş Raporları:");
                DosyaOku.tumRaporlariOku();
            } else if (secim == 3) {
                System.out.println("Programdan çıkılıyor...");
                break;
            } else {
                System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        }
    }
}
