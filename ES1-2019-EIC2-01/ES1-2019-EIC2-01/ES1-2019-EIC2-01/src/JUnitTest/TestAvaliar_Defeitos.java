package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import auxAvaliarDefeitos.JTableSample;
import projetoES1.Avaliar_Defeitos;

class TestAvaliar_Defeitos {

	Avaliar_Defeitos av;;
	@BeforeEach
	void setUp() throws Exception {
		av = new Avaliar_Defeitos();
	}

	@Test
	final void testGetJTableSample() {
		JTableSample tb = av.getJTableSample(0);
		assertEquals(tb, av.getJTableSample(0));
	}

	@Test
	final void testGetName() {
		assertEquals("Avaliar_Defeitos", av.getName());
	}

	@Test
	final void testGetPanel() {
		JPanel panel = av.getPanel();
		assertEquals(panel, av.getPanel());
	}

	@Test
	final void testGetBoxDefeitos() {
		JComboBox jcb = av.getBoxDefeitos();
		assertEquals(jcb, av.getBoxDefeitos());
	}

	@Test
	final void testGetJTables() {
		JTableSample[] jt = av.getJTables();
		assertEquals(jt,av.getJTables());
		
	}

}
