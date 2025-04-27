package com.example.aranankanbulundu.model;

public class KanIlanModel {
    private String ilanId;
    private String userId;
    private String ad;
    private String soyad;
    private String kanGrubu;
    private String hastane;
    private String poliklinik;
    private String il;
    private String ilce;
    private String telefon;

    // Boş constructor (Firebase için lazım)
    public KanIlanModel() {
    }

    // Dolu constructor
    public KanIlanModel(String ilanId, String userId, String ad, String soyad, String kanGrubu,
                        String hastane, String poliklinik, String il, String ilce, String telefon) {
        this.ilanId = ilanId;
        this.userId = userId;
        this.ad = ad;
        this.soyad = soyad;
        this.kanGrubu = kanGrubu;
        this.hastane = hastane;
        this.poliklinik = poliklinik;
        this.il = il;
        this.ilce = ilce;
        this.telefon = telefon;
    }

    // Getter ve Setter'lar
    public String getIlanId() {
        return ilanId;
    }

    public void setIlanId(String ilanId) {
        this.ilanId = ilanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getKanGrubu() {
        return kanGrubu;
    }

    public void setKanGrubu(String kanGrubu) {
        this.kanGrubu = kanGrubu;
    }

    public String getHastane() {
        return hastane;
    }

    public void setHastane(String hastane) {
        this.hastane = hastane;
    }

    public String getPoliklinik() {
        return poliklinik;
    }

    public void setPoliklinik(String poliklinik) {
        this.poliklinik = poliklinik;
    }

    public String getIl() {
        return il;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public String getIlce() {
        return ilce;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
