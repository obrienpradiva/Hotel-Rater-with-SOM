
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Data {
    
    int jumlahFasilitas;
    int jumlahPengunjung;
    int jumlahRoom;
    int loc; //Jawa Barat, Jawa Timur, Jawa Tengah, Jakarta, Bali
    String lokasi;
    double[] vector = new double[4];
    String[] data = new String[4];
    public Data(int jmlRoom, int jmlFasilitas, int jmlPengunjung, String loc)
    {
        this.jumlahFasilitas = jmlFasilitas;
        this.jumlahRoom = jmlRoom;
        this.jumlahPengunjung = jmlPengunjung;
        switch (loc.toLowerCase())
        {
            case "jawa barat":
                this.loc = 50;
                break;
            case "jawa timur":
                this.loc = 100;
                break;
            case "jawa tengah":
                this.loc = 200;
                break;
            case "jakarta":
                this.loc = 50;
                break;
            case "bali":
                this.loc = 5;
                break;
        }
        this.lokasi = loc;
        this.createVector();
        this.createData();
    }
    
    public void createVector()
    {
        this.vector[0] = this.jumlahRoom;
        this.vector[1] = 5-this.jumlahFasilitas;
        this.vector[2] = (this.jumlahRoom - this.jumlahPengunjung)*this.loc;
        this.vector[3] = this.loc;
    }
    
    public void createData()
    {
        this.data[0] = this.jumlahRoom+"";
        this.data[1] = this.jumlahFasilitas+"";
        this.data[2] = this.jumlahPengunjung+"";
        this.data[3] = this.lokasi;
    }
    
    public double[] getVector()
    {
        return this.vector;
    }
    
    public String[] getData()
    {
        return this.data;
    }
}
