public class Test {
    @org.junit.Test

    public int fn(int n){
        if(n==20){
            return 1;
        }else if (n==21){
            return 4;
        }else{
            return fn(n+2)-2*fn(n+1);
        }
    }
    @org.junit.Test
    public void out(){
        int a = fn(10);
        System.out.println(a);
    }
}

