package projetoES1;

public class Regra {

	private String nome;
	private String box1;
	private String box2;
	private String and_or;
	private String box4;
	private String box5;

	private int number1;
	private int number2;
	private double number2LAA;
	
	
	/**
	 * Primeiro Construtor de regras que serve para facilitar o trabalho com as mesmas
	 * @param nome, nome da regra
	 * @param box1, tem as seguintes escolhas "LOC" e "ATFD"
	 * @param box2, tem as seguintes possibilidades "=<" e ">="
	 * @param and_or, escolha que o utilizador fez entre "and" ou "or"
	 * @param box4, input que corresponde ao "CYCLO"
	 * @param box5, input que corresponde a caixa5
	 * @param number1, input que corresponde ao numero1
	 * @param number2, input que corresponde ao numero2
	 */
	public Regra(String nome, String box1, String box2, String and_or, String box4, String box5, int number1, int number2) {
		this.nome = nome;
		this.box1 = box1;
		this.box2 = box2;
		this.and_or = and_or;
		this.box4 = box4;
		this.box5 = box5;
		this.number1 = number1;
		this.number2 = number2;
	}
	/**
	 * Segundo Construtor de regras que serve para facilitar o trabalho com as mesmas
	 * @param nome, nome da regra
	 * @param box1, input que corresponde a caixa1
	 * @param box2, input que corresponde a caixa2
	 * @param and_or, escolha que o utilizador fez entre "and" ou "or"
	 * @param box4, input que corresponde a caixa4
	 * @param box5, input que corresponde a caixa5
	 * @param number1, input que corresponde ao numero1
	 * @param number2, input que corresponde ao numero2, neste caso é um double porque tem outro objetivo
	 */
	public Regra(String nome, String box1, String box2, String and_or, String box4, String box5, int number1, double number2) {
		this.nome = nome;
		this.box1 = box1;
		this.box2 = box2;
		this.and_or = and_or;
		this.box4 = box4;
		this.box5 = box5;
		this.number1 = number1;
		this.number2LAA = number2;
	}
	
	//Getters 
	/**
	 * Getter para receber o nome da regra
	 * @return nome da regra, value of String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Getter para receber o que o Utilizador escolheu na caixa1
	 * @return box1, value of String
	 */
	public String getBox1() {
		return box1;
	}

	/**
	 * Getter para receber o que o Utulizador escolheu na caixa2
	 * @return box2, value of String
	 */
	public String getBox2() {
		return box2;
	}
	
	/**
	 * Getter para receber o que o Utilizador escolheu entre AND e OR
	 * @return and_or, value of String
	 */
	public String getAnd_Or() {
		return and_or;
	}

	/**
	 * Getter para receber o que o Utilizador escolheu
	 * @return box4, value of String
	 */
	public String getBox4() {
		return box4;
	}

	/**
	 * Getter para receber o que o Utilizador escolheu
	 * @return box5, value of String
	 */
	public String getBox5() {
		return box5;
	}

	
	/**
	 * Devolve o numero que o Utilizador escreveu na caixa de input para o primeiro numero
	 * @return number1, value of int
	 */
	public int getNumber1() {
		return number1;
	}
	
	/**
	 * Devolve o numero que o Utilizador escreveu na caixa de input para o segundo numero
	 * @return number2, value of int
	 */
	public int getNumber2() {
		return number2;
	}
	
	/**
	 * Devolve o numero que o Utilizador escreveu para LAA
	 * @return number2LAA, value of double
	 */
	public double getNumber2LAA(){
		return number2LAA;
	}

	/**
	 * Devolve o nome da regra
	 */
	@Override
	public String toString() {
		return  nome;
	}
	
	
	
}

