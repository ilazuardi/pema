package com.usu.pema.model;

public class Lostfound {

    String judul;
    String kontakpenemu;
    String detail;
    String gambarBarang;
    String tanggalPost;

    public Lostfound() {}

    public Lostfound(String judul, String kontakpenemu, String detail, String gambarBarang, String tanggalPost) {
        this.judul = judul;
        this.kontakpenemu = kontakpenemu;
        this.detail = detail;
        this.gambarBarang = gambarBarang;
        this.tanggalPost = tanggalPost;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKontakpenemu() {
        return kontakpenemu;
    }

    public void setKontakpenemu(String kontakpenemu) {
        this.kontakpenemu = kontakpenemu;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGambarBarang() {
        return gambarBarang;
    }

    public void setGambarBarang(String gambarBarang) {
        this.gambarBarang = gambarBarang;
    }

    public String getTanggalPost() {
        return tanggalPost;
    }

    public void setTanggalPost(String tanggalPost) {
        this.tanggalPost = tanggalPost;
    }
}
