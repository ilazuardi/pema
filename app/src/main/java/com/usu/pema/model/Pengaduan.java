package com.usu.pema.model;

public class Pengaduan {

    private String nim;
    private String judulAduan;
    private String catatanPengaduan;

    public Pengaduan() {
    }

    public Pengaduan(String nim, String judulAduan, String catatanPengaduan) {
        this.nim = nim;
        this.judulAduan = judulAduan;
        this.catatanPengaduan = catatanPengaduan;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getJudulAduan() {
        return judulAduan;
    }

    public void setJudulAduan(String judulAduan) {
        this.judulAduan = judulAduan;
    }

    public String getCatatanPengaduan() {
        return catatanPengaduan;
    }

    public void setCatatanPengaduan(String catatanPengaduan) {
        this.catatanPengaduan = catatanPengaduan;
    }
}
