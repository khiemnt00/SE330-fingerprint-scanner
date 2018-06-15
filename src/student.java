/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tan
 */
public class student {
    private String ten;
    private String mssv;
    public student(){super();}
    public student(String ten, String mssv){
        this.ten = ten;
        this.mssv = mssv;
    }
    public String getten(){
        return this.ten;
    }
    public void setten(String ten){
        this.ten=ten;
    }
    public String getmssv(){
        return this.mssv;
    }
    public void setmssv(String mssv){
        this.mssv = mssv;
    }
    public Object[] toArray()
    {
        return new Object[]{this.ten, this.mssv};
    }
}
