

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	
	private Grille grille;
	private Noeud pere;
	private int g; //coût réel
	
	public Grille getGrille() {
		return grille;
	}
	
	public Noeud getPere() {
		return pere;
	}
	
	public int h() {
		switch (Solveur.heuristique) {
		case 1: {
			//Nombre de cases mal placées
			return h1();
		}
		case 2: {
			//La somme des distances des cases par rapport à leurs positions cibles
			return h2();
		}
		default:
			throw new IllegalArgumentException("Unexpected heuristique value: " + Solveur.heuristique);
		}
		
	}
	
	public int h1() {
		int h1 = 0;
		int [][] grille_result = grille_final();		
		for(int i=0; i<grille_result.length;i++) {
			for(int j=0;j<grille_result.length;j++) {
				if(i==grille_result.length-1 && j == grille_result.length-1) break; 
				if(grille.getGrille()[i][j] != grille_result[i][j]) {
					h1++;
				}
			}
		}
		return h1;
	}
	
	public int h2() {
		int h2 = 0;
		int [][] grille_result = grille_final();		
		for(int i=0; i<grille_result.length;i++) {
			for(int j=0;j<grille_result.length;j++) {
				if(i==grille_result.length-1 && j == grille_result.length-1) break; 
				h2 += get_distance(grille_result[i][j],i,j);
			}
		}
		return h2;
	}
	
	public int get_distance(int val,int i, int j) {
		int result = 0;
		int [][] grille_result = grille_final();		
		for(int i1=0; i1<grille_result.length;i1++) {
			for(int j1=0;j1<grille_result.length;j1++) {
				if(grille.getGrille()[i1][j1] == val) {
					result = Math.abs(i-i1)+Math.abs(j-j1);
					break;
				}
			}
		}
		return result;
	}
	
	public int g() {
		return g;
	}
	
	public int f() {
		return g()+h();
	}
	
	public boolean estUnEtatFinal() {
		return h() == 0 ? true : false;
	}
	
	public List<Noeud> successeurs(){
		
		int taille=grille.getGrille().length;
		List<Noeud> successeurs = new ArrayList<Noeud>();
		int i = this.grille.getLigne0();
		int j = this.grille.getColonne0();
		
		Noeud successeur;
		if (i < taille -1) {
			Grille myGrille = new Grille(grille.copier());
			myGrille.getGrille()[i][j] = myGrille.getGrille()[i+1][j];
			myGrille.getGrille()[i+1][j]=0;
			successeur = new Noeud(myGrille, this, g+1);

			successeurs.add(successeur);
		}
		
		if(i > 0) {
			Grille myGrille = new Grille(grille.copier());
			myGrille.getGrille()[i][j] = myGrille.getGrille()[i-1][j];
			myGrille.getGrille()[i-1][j]=0;
			successeur = new Noeud(myGrille, this, g+1);
			successeurs.add(successeur);
		}
		
		if(j > 0 ) {
			Grille myGrille = new Grille(grille.copier());
			myGrille.getGrille()[i][j] = myGrille.getGrille()[i][j-1];
			myGrille.getGrille()[i][j-1]=0;
			successeur = new Noeud(myGrille, this, g+1);
			successeurs.add(successeur);
		}
		
		if(j < taille- 1 ) {
			Grille myGrille = new Grille(grille.copier());
			myGrille.getGrille()[i][j] = myGrille.getGrille()[i][j+1];
			myGrille.getGrille()[i][j+1]=0;
			successeur = new Noeud(myGrille, this, g+1);
			successeurs.add(successeur);
			}
		return successeurs;
	}
	
	public Noeud(Grille grille, Noeud pere, int g) {
		super();
		this.grille = grille;
		this.pere = pere;
		this.g = g;
	}
	
	public int[][] grille_final(){
		int l = grille.getGrille().length;
		int [][] grille_final = new int[l][l];
		int val = 1;
		for(int i=0; i<l;i++) {
			for(int j=0;j<l;j++) {
				grille_final[i][j]=val;
				val++;
			}
		}
		grille_final[l-1][l-1] = 0;
		return grille_final;
	}

}
