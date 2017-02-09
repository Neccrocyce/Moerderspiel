package shuffle;

import game.Person;

public class MergeShuffle {
	
	public static void shuffle (Person[] o, boolean[] seed) {
		mergeShuffle(o, 0, o.length - 1, seed);
	}
	
	public static void sort (int[] o) {
		mergeSort(o, 0, o.length - 1);
	}
	
	private static void mergeShuffle (Person[] o, int l, int r, boolean[] seed) {
		if (l == r) {
			return; 
		}
		int m = (r + l) / 2; 
		mergeShuffle(o, l, m, seed); 
		mergeShuffle(o, m + 1, r, seed); 
		int j = l;
		int k = m + 1;
		Person[] b = new Person[o.length];
		for (int i = 0; i <= r - l; i++) {
			if (j > m) {
				b[i] = o[k];
				k++;  
			}
			else if (k > r) {
				b[i] = o[j];
				j++; 
			}
			else if (seed[0]) {
				b[i] = o[j];
				j++;
				booleanRotateLeft(seed, 1);
			}
			else {
				b[i] = o[k];
				k++;
				booleanRotateLeft(seed, 1);
			}
		}
		for (int i = 0; i <= r - l; i++) {
			o[l + i] = b[i]; 
		}
	}
	
	private static void mergeSort (int[] o, int l, int r) {
		if (l == r) {
			return; 
		}
		int m = (r + l) / 2; 
		mergeSort(o, l, m); 
		mergeSort(o, m + 1, r); 
		int j = l;
		int k = m + 1;
		int[] b = new int[o.length];
		for (int i = 0; i <= r - l; i++) {
			if (j > m) {
				b[i] = o[k];
				k++;  
			}
			else if (k > r) {
				b[i] = o[j];
				j++; 
			}
			else if (o[j] <= o[k]) {
				b[i] = o[j];
				j++;
			}
			else {
				b[i] = o[k];
				k++;
			}
		}
		for (int i = 0; i <= r - l; i++) {
			o[l + i] = b[i]; 
		}
	}
	
	private static void booleanRotateLeft (boolean[] b, int rotations) {
		for (int j = 0; j < rotations; j++) {
			boolean temp = b[0];
			for (int i = 0; i < b.length - 1; i++) {
				b[i] = b[i + 1];
			}
			b[b.length - 1] = temp;
		}
	}
	
	
	
}

