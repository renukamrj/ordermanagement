package com.example.ordermanagement.ordermanagement.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ordermanagement.ordermanagement.sample.SampleDemo;

@RunWith(SpringRunner.class)// this runs SampleDemoTest as a part of junit
public class SampleDemoTest {
	
	SampleDemo sampleDemo = new SampleDemo();
	
	@Test // it will consider this method as a test case
	public void findSumTestPositive() {
		int sum = sampleDemo.findSum(5, 2);
		assertEquals(7, sum);
	}
	
	@Test // it will consider this method as a test case
	public void findSumTestNegative() {
		int sum = sampleDemo.findSum(5, 2);
		assertNotEquals(5, sum);
	}
	
	@Test // it will consider this method as a test case
	public void findDiffTestPositive() {
		int diff = sampleDemo.findDiff(5, 2);
		assertEquals(3, diff);
	}
	
	@Test // it will consider this method as a test case
	public void findDiffTestNegative() {
		int diff = sampleDemo.findDiff(5, 2);
		assertNotEquals(2, diff);
	}
	
	@Test // it will consider this method as a test case
	public void findMulPositive() {
		int mul = sampleDemo.findMul(5, 2);
		assertEquals(10, mul);
	}
	
	@Test // it will consider this method as a test case
	public void findMulTestNegative() {
		int mul = sampleDemo.findMul(5, 2);
		assertNotEquals(5, mul);
	}
	
	@Test // it will consider this method as a test case
	public void findDivTestPositive() {
		int div = sampleDemo.findDiv(10, 2);
		assertEquals(5, div);
	}
	
	@Test // it will consider this method as a test case
	public void findDivTestNegative() {
		int div = sampleDemo.findDiv(10, 2);
		assertNotEquals(2, div);
	}
	
	@Test
	public void testAll() {
		int sum = sampleDemo.findSum(5, 2);
		assertEquals(7, sum);
		int diff = sampleDemo.findDiff(5, 2);
		assertEquals(3, diff);
		int mul = sampleDemo.findMul(5, 2);
		assertEquals(10, mul);
		int div = sampleDemo.findDiv(10, 2);
		assertEquals(5, div);
		
	}

}

