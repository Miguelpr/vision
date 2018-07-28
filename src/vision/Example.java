/**
 * 
 */
package vision;

/**
 * @author M
 *
 */

class Dog{
    public void bark(){
        System.out.println("woof ");
    }
}
class Hound extends Dog{
    public void sniff(){
        System.out.println("sniff ");
    }
 
    public void bark(){
        System.out.println("bowl");
    }
}
public class Example {

	public Example() {

	}

	
	 

	
	public String printDash(int length) {
		StringBuilder s = new StringBuilder();
		s.append("+");
		for (int i=0; i<length; i++){
			s.append("-");
		}
		
		return s.toString();
		
	}
	
	public String printNumber(int length, int number) {

		
		return "|"+String.format("%"+length+"s", ""+number);
		
	}

    public void solution(int[] A, int K) {
        // write your code in Java SE 8
        
        
        
        int longestN=1;
        for (int i=0; i<A.length; i++){
        	String n=""+A[i];
        	if (n.length()>longestN){
        		longestN=n.length();
        	}
        	
        }

        String dash=printDash(longestN);
        
        int numLines=A.length/K;
        int mod= A.length%K;
        StringBuilder lineN;
        StringBuilder  lineD = new StringBuilder();
        for (int j=0; j<numLines; j++){
        
        	lineN = new StringBuilder();
	       	
        	
	        for (int i=0; i<K; i++){
	        	if (j==0){
	        		lineD.append(dash);
	        	}
	        	lineN.append(printNumber(longestN,A[i+(j*K)]));
	        	
	        }
	        if (j==0){
        		lineD.append("+");
        	}
	        lineN.append("|");
	        System.out.println(lineD.toString());
	        System.out.println(lineN.toString());
	        
	        
        }
        if(numLines>0){
        System.out.println(lineD.toString());
        }
        lineN = new StringBuilder();
        lineD = new StringBuilder();
        for (int i=0; i<mod; i++){
        	lineN.append(printNumber(longestN,A[(numLines*K)+i]));
        	lineD.append(dash);
        }
        if(mod>0){
        lineN.append("|");
        lineD.append("+");
        }
        if(numLines==0){
        	System.out.println(lineD.toString());
        }
       
        if(mod>0){
        	System.out.println(lineN.toString());
        
        System.out.println(lineD.toString());
        }
    }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Example myExample = new Example();

				int[] A={4, 35, 80, 123, 12345, 44, 8, 5, 24, 3, 22, 35};
				

				
		myExample.solution(A, 4);
		
		 Dog dog = new Dog();
	        dog.bark();
	}

}
