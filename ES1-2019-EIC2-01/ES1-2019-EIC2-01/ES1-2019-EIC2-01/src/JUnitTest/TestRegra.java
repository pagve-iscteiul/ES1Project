package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projetoES1.Regra;

class TestRegra {
	
	Regra regra1 ;
	Regra regra2;


	@BeforeEach
	void setUp() throws Exception {
		regra1 = new Regra("Regra1", "LOC", ">=", "and","CYCLO" , ">=", 10, 20);
		regra2 = new Regra("Regra2", "ATFD", ">=", "and","LAA" , "<=", 10, 20.0);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testGetNome() {
		assertEquals("Regra1", regra1.getNome());
	}

	@Test
	final void testGetBox1() {
		assertEquals("LOC", regra1.getBox1());
	}

	@Test
	final void testGetBox2() {
		assertEquals(">=", regra1.getBox2());
	}

	@Test
	final void testGetAnd_Or() {
		assertEquals("and", regra1.getAnd_Or());
	}

	@Test
	final void testGetBox4() {
		assertEquals("CYCLO", regra1.getBox4());
	}

	@Test
	final void testGetBox5() {
		assertEquals(">=", regra1.getBox5());
	}

	@Test
	final void testGetNumber1() {
		assertEquals(10, regra1.getNumber1());
	}

	@Test
	final void testGetNumber2() {
		assertEquals(20, regra1.getNumber2());
	}

	@Test
	final void testGetNumber2LAA() {
		assertEquals(20.0, regra2.getNumber2LAA());
	}

	@Test
	final void testToString() {
		assertEquals("Regra1", regra1.toString());
	}

}
