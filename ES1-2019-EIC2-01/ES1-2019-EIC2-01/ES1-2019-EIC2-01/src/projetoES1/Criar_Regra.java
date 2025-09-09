package projetoES1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * Criar_Regra é a interface para criar uma regra e definesse o conjunto de
 * métricas que são dadas e compará-las
 *
 */
public class Criar_Regra extends Observable {

	private JPanel panel;
	private JPanel input_panel;
	private JPanel bottom_panel;

	private String panelName = "Criar Regra";

	private JTextField input;
	private JTextField input2;
	private JTextField rule_name;
	private boolean mais_pressed = true;

	private String[] l = new String[] { "LOC", "ATFD" };
	private String[] l1 = new String[] { "<=", ">=" };
	private String[] l2 = new String[] { "AND", "OR" };
	private int times_button_was_clicked = 0;
	private JComboBox<String> box1;
	private JComboBox<String> box2;
	private JComboBox<String> box3;
	private JComboBox<String> box4;
	private JComboBox<String> box5;
	private JLabel labelMaiorOuIgual1;
	private JLabel labelMaiorOuIgual2;

	/**
	 * So para as regras que foram lidas a partir de um ficheiro
	 */
	private LinkedList<Regra> regras_carregadas;

	public Criar_Regra() {
		init();
	}

	/**
	 * Inicia a interface gráfica através da qual o utilizador pode adicionar
	 * regras
	 */

	private void init() {
		regras_carregadas = new LinkedList<Regra>();

		loadRulesOnStart();

		panel = new JPanel();
		input_panel = new JPanel();
		bottom_panel = new JPanel();

		panel.setLayout(new BorderLayout(10, 10));
		input_panel.setLayout(new GridLayout(1, 2));
		bottom_panel.setLayout(new GridLayout(2, 1));

		JButton save = new JButton("Save");
		JButton load = new JButton("Load");

		input = new JTextField("Input a number");
		rule_name = new JTextField("Insert rule name");

		addBoxes();

		panel.add(rule_name, BorderLayout.NORTH);
		input_panel.add(input, BorderLayout.NORTH);

		bottom_panel.add(save, BorderLayout.SOUTH);
		bottom_panel.add(load, BorderLayout.SOUTH);

		panel.add(input_panel, BorderLayout.CENTER);
		panel.add(bottom_panel, BorderLayout.SOUTH);

		box3 = new JComboBox<String>();
		box3.addItem(filter((String) box1.getSelectedItem()));
		labelMaiorOuIgual1 = new JLabel(">=", SwingConstants.CENTER);
		input2 = new JTextField("Input a number");
		input_panel.add(box3, BorderLayout.CENTER);
		input_panel.add(labelMaiorOuIgual1, BorderLayout.CENTER);
		input_panel.add(input2, BorderLayout.CENTER);
		panel.add(input_panel, BorderLayout.CENTER);

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveRules();
			}
		});

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadTheRules();
			}
		});

		box1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = box1.getSelectedItem();
				box3.removeAllItems();
				box3.addItem(filter((String) box1.getSelectedItem()));
				if (((String) box1.getSelectedItem()).equals("ATFD")) {
					labelMaiorOuIgual1.setText("<=");
				} else {
					labelMaiorOuIgual1.setText(">=");
				}

			}
		});
	}

	/**
	 * Este método permite criar um ficheiro na pasta do repositório se o
	 * ficheiro ainda não existir, e com esse ficheiro vamos conseguir guardar
	 * as regras com as métricas dadas
	 */
	protected void saveRules() {
		try {
			File file = new File("regras.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			String s1 = rule_name.getText();
			String s2 = (String) box1.getSelectedItem();
			String s3 = labelMaiorOuIgual2.getText();
			String s4 = (String) input.getText();
			String s5 = (String) box5.getSelectedItem();
			String s6 = (String) box3.getSelectedItem();
			String s7 = labelMaiorOuIgual1.getText();
			String s8 = (String) input2.getText();
			double news8 = Double.parseDouble(s8);

			int news4 = Integer.parseInt(s4);
			if (s2 != null && s3 != null && s4 != null && s5 != null && s6 != null && s7 != null && s8 != null) {
				PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
				pw.println(
						s1 + ", " + s2 + " " + s3 + " " + news4 + " " + s5 + " " + s6 + " " + s7 + " " + news8 + " ");
				pw.close();
				Regra regra;
				if (Avaliar_Defeitos.getBoxDefeitos().getSelectedItem() == "is_long_method") {
					regra = new Regra(s1, s2, s3, s5, s6, s7, news4, (int) news8);
					setChanged();
					notifyObservers(regra);
				} else {
					regra = new Regra(s1, s2, s3, s5, s6, s7, news4, news8);
					setChanged();
					notifyObservers(regra);
				}
				regras_carregadas.add(regra);
				addRegra(regra);
				JOptionPane.showMessageDialog(panel, "The rule " + regra.toString() + " was created successfuly.");
				panel.updateUI();
				// TODO add regra to dropdown menu
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel, "There is a string instead of a number! Please correct it.");
		}

	}

	/**
	 * Este metodo permite carregar o ficheiro com regras quando o programa é
	 * lançado Se o ficheiro com regras não existir (no caso do 1º lançamento ou
	 * no caso do utilizador apagar o ficheiro) será criado um novo ficheiro
	 * onde que vão ser guardadas as regras
	 */
	private void loadRulesOnStart() {
		File file = new File("regras.txt");
		if (!file.exists()) {
			try {
				// System.out.println("Ficheiro com regras não encontrado");
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			// System.out.println("Ficheiro com regras encontrado");
			readFile(file);
		}
	}

	/**
	 * Este método permite adicionar mais combo boxes ao painel
	 */
	protected void moreBoxes() {
		// TODO Auto-generated method stub
		mais_pressed = true;
		if (times_button_was_clicked < 1) {
			box3 = new JComboBox<String>();
			box3.addItem(filter((String) box1.getSelectedItem()));
			labelMaiorOuIgual1 = new JLabel(">=");
			input2 = new JTextField("Input a number");
			input_panel.add(box3, BorderLayout.CENTER);
			input_panel.add(labelMaiorOuIgual1, BorderLayout.CENTER);
			input_panel.add(input2, BorderLayout.CENTER);
			panel.add(input_panel, BorderLayout.CENTER);
			times_button_was_clicked++;
		}
		panel.updateUI();
	}

	/**
	 * Este método permite dizer se a string for igual ao LOC ele returna a
	 * métrica CYCLO e se não for igual returna métrica LAA
	 * 
	 * @param string
	 * @return
	 */
	public String filter(String string) {
		if (string.equals("LOC")) {
			return "CYCLO";
		}
		return "LAA";
	}

	/**
	 * Creates boxes that are used to create rules
	 */
	private void addBoxes() {

		box1 = new JComboBox<String>(l);
		labelMaiorOuIgual2 = new JLabel(">=", SwingConstants.CENTER);
		box5 = new JComboBox<String>(l2);

		input_panel.add(box1, BorderLayout.CENTER);
		input_panel.add(labelMaiorOuIgual2, BorderLayout.CENTER);

		panel.add(box5, BorderLayout.EAST);

	}

	/**
	 * Este metodo serve para o caso em que o utilizador desejar carregar o seu
	 * próprio ficheiro com regras Aparece um pop-up através do qual é possível
	 * escolher o ficheiro do utilizador
	 */
	protected void loadTheRules() {

		JFileChooser file = new JFileChooser(".");
		file.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = file.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			readFile(file.getSelectedFile());
		}

	}

	/**
	 * 
	 * Este metodo serve para ler o ficheiro e é usado de 2 formas -O ficheiro é
	 * lido quando a aplicação é lançada -O ficheiro é lido no caso do
	 * utilizador desejar carregar o seu próprio ficheiro com regras
	 * 
	 * @param file,
	 *            este file é que é lido por este metodo
	 */

	private void readFile(File file) {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file);
			int line = 1;
			if (!scanner.hasNext())
				return;
			while (scanner.hasNextLine()) {

				String regra_raw = scanner.nextLine();
				String[] regras_partes = regra_raw.split(",");

				String nome = regras_partes[0];
				String[] part2 = regras_partes[1].trim().split(" ");

				if (part2.length == 7) {
					String box1 = part2[0];
					String box2 = part2[1];
					int number = Integer.parseInt(part2[2]);

					String box4 = part2[3];
					String box5 = part2[4];
					String box6 = part2[5];
					Regra regra;
					if (box4.equals("CYCLO")) {
						int number2 = Integer.parseInt(part2[6]);
						regra = new Regra(nome, box1, box2, box4, box5, box6, number, number2);
					} else {
						double number2 = Double.parseDouble(part2[6]);
						regra = new Regra(nome, box1, box2, box4, box5, box6, number, number2);
					}
					regras_carregadas.add(regra);
				} else {
					JOptionPane.showMessageDialog(panel,
							"There is a problem with rule on the line:" + line + ", please correct it");
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* return the panel name don´t touch */
	public String getName() {
		return panelName;
	}

	/* return the panel don´t touch */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Este metodo devolve o nome que o user da a uma regra nova
	 * 
	 * @return
	 */

	public String getRuleName() {
		return rule_name.getName();
	}

	/**
	 * Este metodo devolve o valor que o user indica
	 * 
	 * @return
	 */

	public String getInputValue() {
		return input.getName();
	}

	/**
	 * Este painel devolve as combo boxes, inputs e nome da regra
	 * 
	 * @return
	 */

	public JPanel getPanel1() {
		return input_panel;
	}

	/**
	 * Atualiza a lista de regras e notifica o Observer para fazer update na
	 * interface gráfica do JTableSample
	 * 
	 * @param regra,
	 *            é a regra que acabou de ser criada e guardada pelo utilizador
	 */

	public void addRegra(Regra regra) {
		regras_carregadas.add(regra);
		setChanged();
		notifyObservers();
	}

	/**
	 * Este metodo serve para devolver a lista de regras que foram carregadas
	 * 
	 * @return regras_carregadas
	 */

	public LinkedList<Regra> getRegras_carregadas() {
		return regras_carregadas;
	}

}
