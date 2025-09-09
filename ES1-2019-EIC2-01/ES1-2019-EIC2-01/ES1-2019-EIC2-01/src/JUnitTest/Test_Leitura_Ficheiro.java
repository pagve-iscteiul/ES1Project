package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projetoES1.Leitura_Ficheiro;

class Test_Leitura_Ficheiro {

	Leitura_Ficheiro lf;
	
	@BeforeEach
	void setUp() throws Exception {
		lf = new Leitura_Ficheiro();
	}

	@Test
	final void testGetName() {
		assertEquals("file", lf.getName());
	}

	@Test
	final void testGetPanel() {
		JPanel panel = lf.getPanel();
		assertEquals(panel, lf.getPanel());
	}
	
	@Test
	final void testremoveAllLine() {
		
		assertEquals(true, lf.removeAllLine());
	}

}
