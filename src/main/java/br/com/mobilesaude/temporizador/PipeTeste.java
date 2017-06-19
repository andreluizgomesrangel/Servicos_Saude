package br.com.mobilesaude.temporizador;

public class PipeTeste implements Runnable {

	private int i;

	public PipeTeste(int i) {
		// TODO Auto-generated constructor stub
		this.i = i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.print(" " + i);
	}

	public void f1() {
		System.out.println("Funcao 1");
	}

	public void f2() {
		System.out.println("Funcao 2");
	}

	public void f3() {
		System.out.println("Funcao 3");
	}

	public void f4() {
		System.out.println("Funcao 4");
	}

}
