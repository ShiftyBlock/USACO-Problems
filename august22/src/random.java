public class random {
    public static void main(String[] args){
        rando a= new rando();
        ((hello)a).printa();


    }
}
class rando{
    public void printa(){System.out.println("a");}
}
class hello extends rando {


    @Override
    public void printa(){
        System.out.println("b");
    }


}