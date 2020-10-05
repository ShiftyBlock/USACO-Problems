class Artwork  implements Comparable<Artwork>{
    private String title;
    private int year;
    private boolean forSale;
    public Artwork(String t, int y, boolean fs){
        this.title=t;
        this.year=y;
        this.forSale=fs;
    }
    final static int DEFAULT_YEAR=2020;
    final static boolean DEFAULT_STATUS=true;
    public Artwork(String t){
        this.title=t;
        this.year=DEFAULT_YEAR;
        this.forSale= DEFAULT_STATUS;
    }

    public int compareTo(Artwork obj){
        if (obj==null) return 0;
        if (obj.getClass()==getClass()){
            if (((Artwork) obj).getTitle().equals(title)){
                return Integer.compare(((Artwork) obj).getYear(), year);
            }
            return ((Artwork) obj).getTitle().compareTo(title);

        }
        return 0;
    }



    public String getTitle() {return title;}
    public int getYear() {return year;}
    public boolean getStatus() {return forSale;}
    public void setTitle(String newtitle){this.title=newtitle;}
    public void setYear(int newyear){
        if (newyear<=2020) this.year=newyear;
    }
    public void setStatus(boolean newstatus){
        this.forSale= newstatus;
    }
    public String toString(){
        String sale= "";
        if (forSale) sale= "for sale";
        else sale= "not for sale";
        String res= String.format("Your artwork's name is %s, it was created in %i, and it is %s", title, year, sale);
        return res;
    }


}
enum Medium{
    METAL, STONE, WOOD, OTHER;
}
class Sculpture extends Artwork {
    private Medium material;
    public Sculpture(String t, int y, boolean fs, Medium mat) {
        super(t, y, fs);
        this.material=mat;
    }
    public String toString(){
        String sale= "";
        if (getStatus()) sale= "for sale";
        else sale= "not for sale";
        String res= String.format("Your artwork's name is %s, it was created in %i, and it is %s", getTitle(), getYear(), sale);
        res+= "\n It was created from "+ material;
        return res;
    }
    public boolean equals (Sculpture obj1, Sculpture obj2){
        if (obj1==null || obj2==null) return false;
        boolean sametitle= obj1.getTitle()== obj2.getTitle();
        boolean sameyear= obj1.getYear()== obj2.getYear();
        boolean samestatus= obj1.getStatus()== obj2.getStatus();
        boolean samemedium= obj1.material== obj2.material;
        return sametitle && sameyear&& samestatus && samemedium;
    }
}