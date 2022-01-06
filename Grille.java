

public class Grille {
	private int[][] grille;
	private int taille;
	private int ligne0;
	private int colonne0;

	public Grille() {
		super();
	}

	public Grille(int[][] grille) {
		super();
		this.grille = grille;
	}

	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {
		this.grille = grille;
	}

	public int getTaille() {
		this.taille = this.grille[0].length * this.grille.length;
		return taille;
	}

	public int getLigne0() {
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille.length; j++) {
				if (this.grille[i][j] == 0) {
					ligne0 = i;
				}
			}
		}
		return ligne0;
	}

	public int getColonne0() {
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille.length; j++) {
				if (this.grille[i][j] == 0) {
					colonne0 = j;
				}
			}
		}
		return colonne0;
	}

	public void setColonne0(int colonne0) {
		this.colonne0 = colonne0;
	}

	public int getValeur(int i, int j) {
		return grille[i][j];
	}

	public int[][] copier() {
		int[][] copie_grille = new int[grille.length][grille.length];
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille.length; j++) {
				copie_grille[i][j] = grille[i][j];
			}
		}
		return copie_grille;
	}

	public boolean equals(Grille g) {
		boolean flag = true;

		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille.length; j++) {
				if (this.grille[i][j] != g.getGrille()[i][j]) {
					flag = false;
				}
			}
		}
		return flag;
	}

	@Override
	public String toString() {
		String g = "";
		for (int[] is : grille) {
			for (int i : is) {
				g = g + " " + i;
			}

			g = g + "\n";
		}

		return "Grille=\n" + g;
	}

}
