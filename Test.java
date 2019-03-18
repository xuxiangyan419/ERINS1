package array;

public class Test {
	public static void main(String[] args) {
		String s = "good";
		int index = s.lastIndexOf('.');
		System.out.println(s.lastIndexOf('.'));
		System.out.println(s.substring(index+1));
	}
}
