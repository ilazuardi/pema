package com.usu.pema.model;

public class Acara {

    String judul;
    String tipe;
    String detail;
    String gambarAcara;

    public Acara() {

    }

    public Acara(String judul, String tipe, String detail, String gambarAcara) {
        this.judul = judul;
        this.tipe = tipe;
        this.detail = detail;
        this.gambarAcara = gambarAcara;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGambarAcara() {
        return gambarAcara;
    }

    public void setGambarAcara(String gambarAcara) {
        this.gambarAcara = gambarAcara;
    }
}
