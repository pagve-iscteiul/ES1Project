package auxAvaliarDefeitos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import projetoES1.App;
import projetoES1.Avaliar_Defeitos;
import projetoES1.Criar_Regra;
import projetoES1.Regra;

public class JTableSample implements Observer {
	private JFrame mainFrame;
	private JTable table;

	private JPanel panel;
	private JScrollPane scrollPane;
	private DefaultTableModel defaultTableModel;
	private JButton newColumn;
	private JButton addIPlasma;
	private JButton addPMD;
	private int numOfColumns;
	private final int MAX_NUM_COLUMNS = 5;
	private JComboBox rulesBox;

	public int counter = 1;

	public JTableSample() {
		initTable();
	}

	
	/**
	 * inicializa a jtable, jscrollpane e defaulttablemodel
	 * adiciona uma coluna à tabela com os indicadores DCI,DII,ADCI,ADII
	 * e cria um botão "add column"
	 */
	public void initTable() {
		mainFrame = new JFrame("JTableSample");
		numOfColumns = 1;
		defaultTableModel = new DefaultTableModel();
		table = new JTable(defaultTableModel);
		scrollPane = new JScrollPane(table);

		Vector indicatorNames = new Vector();
		indicatorNames.add(null);
		indicatorNames.add("DCI");
		indicatorNames.add("DII");
		indicatorNames.add("ADCI");
		indicatorNames.add("ADII");
		defaultTableModel.addColumn("Indicadores", indicatorNames);

		table.validate();
		panel = new JPanel();
		newColumn = new JButton("AddColumn");

		newColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTableColumn();
			}
		});

		rulesBox = new JComboBox();

		panel.add(rulesBox);
		panel.add(newColumn);

	}

	/**
	 * inicializa a frame
	 */
/*
	private void open() {
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.add(scrollPane, BorderLayout.CENTER);
		mainFrame.add(panel, BorderLayout.NORTH);
		mainFrame.pack();
		mainFrame.setLocation(150, 150);
		mainFrame.setVisible(true);
	}
*/
	/**
	 * Caso seja a tabela do defeito is_long_method
	 * adiciona os botoes PMD e iPlasma
	 * que permitem adicionar uma coluna à tabela com os 
	 * respetivos parametros
	 */
	public void addButtonsPMDandIPlasma() {
		if (Avaliar_Defeitos.getJTables()[0] == this) {
			addIPlasma = new JButton("Add iPlasma");
			addPMD = new JButton("Add PMD");

			addIPlasma.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addPMDorIPlasma("iPlasma");
				}
			});

			addPMD.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addPMDorIPlasma("PMD");
				}
			});

			panel.add(addIPlasma);
			panel.add(addPMD);
		}
	}

	public JPanel getPanel() {
		return panel;

	}

	/**
	 * funcao que decide qual das apps comparar
	 * PMD se parametro nome = PMD
	 * iPlasma se parametro nome = iPlasma
	 * e adiciona à tabela
	 * @param nome
	 */
	public void addPMDorIPlasma(String nome) {
		if (App.file == null) {
			JOptionPane.showMessageDialog(panel, "File not loaded");
			return;
		}
		if (numOfColumns <= MAX_NUM_COLUMNS) {
			TableColumn colX = new TableColumn();
			Vector indicadores = new Vector();
			indicadores.add(null);
			indicadores.add(0);
			indicadores.add(0);
			indicadores.add(0);
			indicadores.add(0);

			// nr da coluna do is_long_method
			int indexReal = 8;
			int indexPMDorIPlasma;
			if (nome.equals("PMD")) {
				indexPMDorIPlasma = 10;
				colX.setHeaderValue("PMD");
				colX.setIdentifier("PMD");
			} else {
				indexPMDorIPlasma = 9;
				colX.setHeaderValue("iPlasma");
				colX.setIdentifier("iPlasma");
			}
			try {
				if (App.file == null)
					return;
				FileInputStream in = new FileInputStream(App.file);
				XSSFWorkbook wb = new XSSFWorkbook(App.file);

				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row;

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					boolean avaliacaoReal;
					boolean avaliacaoPMD;

					// System.out.println("Real: " +
					// row.getCell(8).getBooleanCellValue() + " PMD ou iPlasma:
					// " +
					// row.getCell(indexPMDorIPlasma).getBooleanCellValue());

					avaliacaoPMD = row.getCell(indexPMDorIPlasma).getBooleanCellValue();
					avaliacaoReal = row.getCell(indexReal).getBooleanCellValue();
					indicadores = comparaRealComRegra(indicadores, avaliacaoReal, avaliacaoPMD);

				}

				wb.close();
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}

			defaultTableModel.addColumn(colX.getHeaderValue(), indicadores);
			numOfColumns++;
		}
	}

	public JScrollPane getScroll() {
		return scrollPane;
	}

	public JFrame getFrame() {
		return mainFrame;
	}

	/**
	 * Verifica qual a regra selecionada na combobox 
	 * e compara essa regra.
	 * adiciona uma coluna à tabela
	 * com os valores obtidos na comparação
	 */
	public void addTableColumn() {
		if (App.file == null) {
			JOptionPane.showMessageDialog(panel, "File not loaded");
			return;
		}
		if (numOfColumns <= MAX_NUM_COLUMNS) {
			TableColumn colX = new TableColumn();
			Regra regra = (Regra) rulesBox.getSelectedItem();
			if (regra == null)
				return;
			Vector indicadores = new Vector();
			indicadores = getRuleIndicators(regra);
			colX.setHeaderValue(regra.toString());
			colX.setIdentifier(regra.toString());
			defaultTableModel.addColumn(regra.toString(), indicadores);
			numOfColumns++;
		}

	}

	/**
	 * lê ficheiro 
	 * recebe uma regra e devolve o vetor
	 * com os valores dos parametros para essa regra
	 * 
	 * @param regra
	 * @return
	 */
	public Vector getRuleIndicators(Regra regra) {
		Vector indicadores = new Vector();
		// Deixa a primeira celula em branco
		indicadores.add(null);
		indicadores.add(0);
		indicadores.add(0);
		indicadores.add(0);
		indicadores.add(0);
		int indexParametro1;
		int indexParametro2;
		int indexColunaReal;

		/*
		 * Se estiver a comparar para o indicador is long method vai apenas
		 * comparar as colunas do loc e do cyclo se não compara as colunas do
		 * atfd e laa
		 */
		if (Avaliar_Defeitos.getBoxDefeitos().getSelectedItem() == "is_long_method") {
			indexParametro1 = 4; // LOC
			indexParametro2 = 5; // CYCLO
			indexColunaReal = 8;
		} else {
			indexParametro1 = 6; // ATFD
			indexParametro2 = 7; // LAA
			indexColunaReal = 11;
		}

		// ROW 4 - 5 - 6 - 7 8 11
		// LOC - CYCLO - ATFD - LAA is_long_method is_feature_envy
		try {
			if (App.file == null)
				return null;
			FileInputStream in = new FileInputStream(App.file);
			XSSFWorkbook wb = new XSSFWorkbook(App.file);

			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				boolean regraAcertou = true;
				double a = 0.0;
				double b = 0.0;
				// PARAMETRO 1
				if (row.getCell(indexParametro1).getCellType() == CellType.NUMERIC) {
					a = row.getCell(indexParametro1).getNumericCellValue();

				}
				// PARAMETRO 2
				if (row.getCell(indexParametro2).getCellType() == CellType.NUMERIC) {
					b = row.getCell(indexParametro2).getNumericCellValue();
				}
				if (row.getCell(indexParametro2).getCellType() == CellType.STRING) {
					b = Double.parseDouble(row.getCell(indexParametro2).getStringCellValue());
				}
				boolean avaliacaoDaRegra = false;

				if (Avaliar_Defeitos.getBoxDefeitos().getSelectedItem() == "is_long_method") {
					avaliacaoDaRegra = getRuleEvaluation(regra, (int) a, (int) b);
				} else {
					avaliacaoDaRegra = getRuleEvaluation(regra, (int) a, b);
				}
				boolean avaliacaoReal = row.getCell(indexColunaReal).getBooleanCellValue();
				indicadores = comparaRealComRegra(indicadores, avaliacaoReal, avaliacaoDaRegra);
			}

			wb.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

		return indicadores;
	}

	/**
	 *	incrementa contadores do vetor 
	 *dependendo dos seus defeitos
	 */
	public Vector comparaRealComRegra(Vector v, boolean real, boolean regra) {
		// 1 - DCI
		// 2 - DII
		// 3 - ADCI FALSE - FALSE
		// 4 - ADII
		if (isDCI(real, regra)) {
			int incrementa = (int) v.get(1) + 1;
			v.set(1, (Object) incrementa);
		} else if (isDII(real, regra)) {
			int incrementa = (int) v.get(2) + 1;
			v.set(2, (Object) incrementa);
		} else if (isADCI(real, regra)) {
			int incrementa = (int) v.get(3) + 1;
			v.set(3, (Object) incrementa);
		} else if (isADII(real, regra)) {
			int incrementa = (int) v.get(4) + 1;
			v.set(4, (Object) incrementa);
		}
		return v;
	}

	/**
	 * decide se é DCI consoante os valores booleanos real e regra 
	 * @param real
	 * @param regra
	 * @return
	 */
	public boolean isDCI(boolean real, boolean regra) {
		if (real && regra)
			return true;
		return false;
	}

	/**
	 * decide se é DII consoante os valores booleanos real e regra 
	 * @param real
	 * @param regra
	 * @return
	 */
	public boolean isDII(boolean real, boolean regra) {
		if (!real && regra)
			return true;
		return false;
	}

	/**
	 * decide se é ADCI consoante os valores booleanos real e regra 
	 * @param real
	 * @param regra
	 * @return
	 */
	public boolean isADCI(boolean real, boolean regra) {
		if (!real && !regra)
			return true;
		return false;
	}

	/**
	 * decide se é ADII consoante os valores booleanos real e regra 
	 * @param real
	 * @param regra
	 * @return
	 */
	public boolean isADII(boolean real, boolean regra) {
		if (real && !regra)
			return true;
		return false;
	}

	
	/**
	 * ao receber a regra LOC, e os dois valores lidos do ficheiro
	 * decide se a regra acertou ou não
	 * @param regra
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean getRuleEvaluation(Regra regra, int a, int b) {
		if (Avaliar_Defeitos.getBoxDefeitos().getSelectedItem() == "is_long_method") {
			if (regra.getAnd_Or().equals("AND")) {
				int valorLOC = regra.getNumber1();
				int valorCYCLO = regra.getNumber2();
				if (a < valorLOC) {
					return false;
				} else if (b < valorCYCLO) {
					return false;
				}
				return true;
			}
			// SE FOR OR
			else {
				int valorLOC = regra.getNumber1();
				int valorCYCLO = regra.getNumber2();
				if (a < valorLOC && b < valorCYCLO) {
					return false;
				}
				return true;
			}
		}
		return false;

	}

	/**
	 * ao receber a regra ATFD, e os dois valores lidos do ficheiro
	 * decide se a regra acertou ou não
	 * @param regra
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean getRuleEvaluation(Regra regra, int a, double b) {
		if (Avaliar_Defeitos.getBoxDefeitos().getSelectedItem() == "is_feature_envy") {
			if (regra.getAnd_Or().equals("AND")) {
				int valorATFD = regra.getNumber1();
				double valorLAA = regra.getNumber2LAA();
				if (a < valorATFD) {
					return false;
				} else if (b > valorLAA) {
					return false;
				}
				return true;
			}
			// SE FOR OR
			else {
				int valorATFD = regra.getNumber1();
				double valorLAA = regra.getNumber2LAA();
				if (a < valorATFD && b > valorLAA) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				JTableSample jts = new JTableSample();
				jts.open();
				;
			}
		});
	}
*/
	/**
	 * fica à espera de regras criadas
	 * se uma nova regra for criada adiciona à combobox da tabela correspondente
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Regra regra = (Regra) arg1;
		if (regra != null) {
			if (Avaliar_Defeitos.getJTables()[0] == this) {
				if (regra.getBox1().equals("LOC")) {
					rulesBox.addItem(regra);
				}
			}
		}
		if (Avaliar_Defeitos.getJTables()[1] == this) {
			if (regra != null) {
				if (regra.getBox1().equals("ATFD")) {
					rulesBox.addItem(regra);
				}
			}
		}

	}

	/**
	 * adiciona uma regra à combobox 
	 * @param regra
	 */
	public void addRegraToComboBox(Regra regra) {
		rulesBox.addItem(regra);
	}

}