package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import shuffle.MergeShuffle;

public class TestMergeSort {

	@Test
	public void test() {
		int[] a = new int[10];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		MergeShuffle.sort(a);
		testArray(new int[] {0,1,2,3,4,5,6,7,8,9}, a);
	}
	
	@Test
	public void test2() {
		//
		int n = Math.abs(Integer.MIN_VALUE - 1);
		int n2 = -432 % 10;
		//
		List<Integer> a = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			a.add(i);
		}
		a.remove(5);
		a.add(5);
		
		for (int j = 0; j < 100000; j++) {
			Collections.sort(a);
			Integer[] ai = a.toArray(new Integer[10]);
			int[] aii = new int[ai.length];
			for (int i = 0; i < ai.length; i++) {
				aii[i] = ai[i].intValue();
			}
			MergeShuffle.sort(aii);
			testArray(new int[] {0,1,2,3,4,5,6,7,8,9}, aii);
		}
	}
	
	public void testArray (int[] exp, int[] act) {
		if (exp.length != act.length) {
			fail("different length");
		}
		for (int i = 0; i < exp.length; i++) {
			assertEquals(exp[i], act[i]);
		}
	}
	
	public void testArray (int[] exp, List<Integer> act) {
		if (exp.length != act.size()) {
			fail("different length");
		}
		for (int i = 0; i < exp.length; i++) {
			assertEquals(exp[i], act.get(i).intValue());
		}
	}

}
