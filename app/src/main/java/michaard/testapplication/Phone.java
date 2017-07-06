package michaard.testapplication;

public class Phone {
    private int id;
    private String name;
    private String brand;
    public Phone(int id,String brand,String name){
        this.id=id;
        this.name=name;
        this.brand=brand;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }
}