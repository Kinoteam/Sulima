package register;

import java.util.*;

public class converter {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		char again = 'y';

		while (again=='y' || again == 'Y') {
			System.out.println("Enter a value");
			float value = scan.nextFloat();
			float result = 0;
			int choice = 0;
			System.out.println("1: convert from C to F\n2: convert from F to C");
			choice = scan.nextInt();

			if (choice == 1) {
				result = (9 * value / 5) + 32;
				System.out.println(value + "C = " + result + "F");
			}

			else if (choice == 2) {
				result = (value - 32) * 5 / 9;
				System.out.println(value + "F = " + result + "C");
			}

			else {
				System.out.println("1 or 2");
			}
			
			System.out.println("wanna do it again?");
			scan.nextLine();
		
			String str = scan.nextLine();
			again = str.charAt(0);
			scan.close();
		} 
		
		return;
	}

}
