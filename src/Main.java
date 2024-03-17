import java.util.Random;

// Bölge ödüllerini temsil eden enum
enum BolgeOdulu {
    FOOD, FIREWOOD, WATER, PARA, SILAH, ZIRH
}

// Canavar türlerini temsil eden enum
enum CanavarTuru {
    YILAN
}

// Eşya türlerini temsil eden enum
enum EşyaTürü {
    SILAH, TÜFEK, KILIC, TABANCA, ZIRH, AGIR_ZIRH, ORTA_ZIRH, HAFIF_ZIRH, PARA
}

// MaceraOyunu sınıfı
public class Main {
    // Random nesnesi
    private static Random rand = new Random();

    // Bolge sınıfı
    static class Bolge {
        BolgeOdulu odul;

        Bolge(BolgeOdulu odul) {
            this.odul = odul;
        }
    }

    // Canavar sınıfı
    static class Canavar {
        CanavarTuru turu;
        int hasar;
        int saglik;

        Canavar(CanavarTuru turu, int hasar, int saglik) {
            this.turu = turu;
            this.hasar = hasar;
            this.saglik = saglik;
        }
    }

    // Macera başladığında çağrılan metod
    public static void main(String[] args) {
        Bolge[] savasBolgeleri = {new Bolge(BolgeOdulu.FOOD), new Bolge(BolgeOdulu.FIREWOOD), new Bolge(BolgeOdulu.WATER),
                new Bolge(BolgeOdulu.PARA), new Bolge(BolgeOdulu.SILAH), new Bolge(BolgeOdulu.ZIRH)};
        Bolge maden = new Bolge(BolgeOdulu.PARA);

        // Oyuncunun envanteri
        boolean[] oduller = new boolean[6];

        // Oyuncu savaş bölgelerini ziyaret ediyor
        for (Bolge bolge : savasBolgeleri) {
            oyuncuBolgeyiZiyaretEder(bolge, oduller);
        }

        // Oyuncu madene gidiyor
        oyuncuBolgeyiZiyaretEder(maden, oduller);

        // Tüm ödüller toplandı mı kontrol ediliyor
        if (tumOdullerToplandiMi(oduller)) {
            System.out.println("Tebrikler! Tüm ödülleri toplayarak güvenli eve döndünüz. Oyunu kazandınız!");
        } else {
            System.out.println("Maalesef tüm ödülleri toplayamadınız. Oyunu kaybettiniz.");
        }
    }

    // Oyuncunun bir bölgeyi ziyaret ettiği metot
    public static void oyuncuBolgeyiZiyaretEder(Bolge bolge, boolean[] oduller) {
        if (bolge.odul == BolgeOdulu.PARA || bolge.odul == BolgeOdulu.SILAH || bolge.odul == BolgeOdulu.ZIRH) {
            EşyaTürü kazanilanEsya = dusmanlaSavas(bolge);
            if (kazanilanEsya != null) {
                switch (kazanilanEsya) {
                    case SILAH, TÜFEK, KILIC, TABANCA, ZIRH, AGIR_ZIRH, ORTA_ZIRH, HAFIF_ZIRH, PARA -> {
                        oduller[kazanilanEsya.ordinal()] = true;
                        System.out.println("Bölgeden " + kazanilanEsya.name() + " kazandınız.");
                    }
                }
            }
        } else {
            oduller[bolge.odul.ordinal()] = true;
            System.out.println("Bölgeden " + bolge.odul.name() + " kazandınız.");
        }
    }

    // Düşmanla savaşı temsil eden metot
    public static EşyaTürü dusmanlaSavas(Bolge bolge) {
        Canavar dusman = new Canavar(CanavarTuru.YILAN, rand.nextInt(4) + 3, 12);
        int oyuncuHasar = rand.nextInt(2); // Oyuncunun hasarı

        // İlk hamleyi belirleme
        boolean ilkHamleOyuncudanMi = rand.nextBoolean();

        // İlk hamleyi yapma
        if (ilkHamleOyuncudanMi) {
            dusman.saglik -= oyuncuHasar;
            if (dusman.saglik <= 0) {
                return dusmandanEsyaKazanmaIhtimali();
            }
        }

        // Sıradaki hamleleri yapma
        while (true) {
            oyuncuHasar = rand.nextInt(2); // Oyuncunun hasarı
            dusman.saglik -= oyuncuHasar;
            if (dusman.saglik <= 0) {
                return dusmandanEsyaKazanmaIhtimali();
            }

            int dusmanHasar = rand.nextInt(dusman.hasar);
            // Oyuncunun sağlığı kontrol ediliyor
            if (dusmanHasar >= 10) {
                return null; // Oyuncu öldü
            }
        }
    }

    // Düşmandan eşya kazanma ihtimali
    public static EşyaTürü dusmandanEsyaKazanmaIhtimali() {
        int ihtimal = rand.nextInt(100);
        if (ihtimal < 15) {
            return EşyaTürü.SILAH;
        } else if (ihtimal < 35) {
            return EşyaTürü.TÜFEK;
        } else if (ihtimal < 65) {
            return EşyaTürü.KILIC;
        } else if (ihtimal < 115) {
            return EşyaTürü.TABANCA;
        } else if (ihtimal < 130) {
            return EşyaTürü.ZIRH;
        } else if (ihtimal < 150) {
            return EşyaTürü.AGIR_ZIRH;
        } else if (ihtimal < 180) {
            return EşyaTürü.ORTA_ZIRH;
        } else if (ihtimal < 230) {
            return EşyaTürü.HAFIF_ZIRH;
        } else if (ihtimal < 255) {
            return EşyaTürü.PARA;
        } else if (ihtimal < 275) {
            return EşyaTürü.PARA;
        } else if (ihtimal < 305) {
            return EşyaTürü.PARA;
        } else if (ihtimal < 355) {
            return EşyaTürü.PARA;
        } else {
            return null; // Hiçbir şey kazanılamadı
        }
    }

    // Tüm ödüller toplandı mı kontrolü
    public static boolean tumOdullerToplandiMi(boolean[] oduller) {
        for (boolean odul : oduller) {
            if (!odul) {
                return false;
            }
        }
        return true;
    }
}
