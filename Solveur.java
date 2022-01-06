

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solveur {
	static int heuristique = 1;
	private List<Noeud> liste_noeuds_ouverts = new ArrayList<Noeud>();
	private List<Noeud> liste_noeuds_fermes = new ArrayList<Noeud>();

	public Solveur() {

	}

	public Grille chargerFichier(String nomFichier) throws IOException {

		BufferedReader reader;
		reader = new BufferedReader(new FileReader("src\\puzzles\\" + nomFichier + ".txt"));

		String grilleDimension = reader.readLine();
		String[] split = grilleDimension.split(" ");
		int grilleX = Integer.parseInt(split[0]);
		int grilleY = Integer.parseInt(split[0]);

		int[][] grille = new int[grilleX][grilleY];

		for (int i = 0; i < grilleX; i++) {
			String[] line = reader.readLine().split(" ");
			int n = 0;
			for (int j = 0; j < line.length; j++) {
				if (line[j] == "")
					continue;
				grille[i][n] = Integer.parseInt(line[j]);
				n++;
			}

		}
		reader.readLine();
		Grille g = new Grille(grille);
		return g;
	}

	public Noeud algoStar(Grille initial) {

//		On initiale le noued avec le grille initial
		Noeud noeudCourant = new Noeud(initial, null, 0);
		Noeud noeudCourant1 = new Noeud(initial, null, 0);
		liste_noeuds_ouverts.add(noeudCourant);

		while (!noeudCourant.estUnEtatFinal() && !liste_noeuds_ouverts.isEmpty()) {
			noeudCourant = meilleurNoeud();

//    		On ajoute noeudCourant à la liste fermée et on le retire de la liste ouverte
			liste_noeuds_fermes.add(noeudCourant);
			liste_noeuds_ouverts.remove(noeudCourant);

//    		On génère tous les noeuds successeurs du noeudCourant 
			List<Noeud> successeurs = noeudCourant.successeurs();
			int ls = successeurs.size();
			for (int i = 0; i < ls; i++) {
				if (!noeudInListFerme(successeurs.get(i))) {
					Noeud s = successeurs.get(i);
					Noeud n = noeudInListOuvert(s); // la grille de s exist dans la liste ouvert
					if (n == null)
						liste_noeuds_ouverts.add(s);
					else {
						if (s.f() < n.f()) {
							int index = liste_noeuds_ouverts.indexOf(n);
							liste_noeuds_ouverts.set(index, s); // on remplace n par s
						}
					}
				}
			}
		}

		return noeudCourant;

	}

	public List<Grille> cheminComplet(Noeud noeud) {

		ArrayList<Grille> noeuds_chemin = new ArrayList<>();
		noeuds_chemin.add(noeud.getGrille());
		System.out.println("\nLe nombre de mouvement: " + noeud.g());
		System.out.println();
		while (noeud.getPere() != null) {
			noeud = noeud.getPere();
			noeuds_chemin.add(noeud.getGrille());
		}
		return noeuds_chemin;
	}

	public Noeud meilleurNoeud() {
		int l = liste_noeuds_ouverts.size();
		Noeud min = liste_noeuds_ouverts.get(0);
		for (int i = 1; i < l; i++) {
			if (liste_noeuds_ouverts.get(i).f() < min.f())
				min = liste_noeuds_ouverts.get(i);
		}

		return min;
	}

	public Noeud noeudInListOuvert(Noeud n) {
		Noeud result = null;
		int l = liste_noeuds_ouverts.size();
		for (int i = 0; i < l; i++) {
			if (liste_noeuds_ouverts.get(i).getGrille().equals(n.getGrille())) {
				result = liste_noeuds_ouverts.get(i);
				break;
			}
		}
		return result;

	}

	public boolean noeudInListFerme(Noeud n) {
		Noeud result = null;
		int l = liste_noeuds_fermes.size();
		for (int i = 0; i < l; i++) {
			if (liste_noeuds_fermes.get(i).getGrille().equals(n.getGrille())) {
				result = liste_noeuds_fermes.get(i);
				break;
			}
		}
		return result != null ? true : false;

	}

	public static void show_chemin(List<Grille> chemin) {
		int size = chemin.size();
		for (int i = size - 1; i >= 0; i--) {
			System.out.println("****************** " + (size - i) + " ******************");
			System.out.println(chemin.get(i).toString());
		}

	}

	public static void main(String[] args) {

		String file = "";
		int h = 0;
		Solveur solveur = new Solveur();
		Scanner sc = new Scanner(System.in);
		Grille g = null;
		while (true) {
			try {
				while (file == "") {
					System.out.println("Entrer le nom du fichier:");
					file = sc.nextLine();
				}

				g = solveur.chargerFichier(file);
				break;
			} catch (IOException e) {
				file = "";
				System.err.println("Error: " + e.getMessage());
				continue;
			}

		}

		System.out.println("Choisir la fonction heuristique: ");
		System.out.println("-1- Nombre de cases mal placées.");
		System.out.println("-2- La somme des distances des cases par rapport à leurs positions cibles.");
		h = sc.nextInt();
		while (h != 1 && h != 2) {
			System.out.println("Taper 1 ou 2 s'il vous plait.");
			h = sc.nextInt();
		}

		heuristique = h;

		System.err.println("Grille initiale: ");
		System.out.println(g);
		Noeud noeudFinal = solveur.algoStar(g);
		if (noeudFinal.estUnEtatFinal()) {
			System.out.println("L'algorithme a trouvé une solution (*_*) : ");

			System.out.println(noeudFinal.getGrille().toString());

			System.out.println("Chemin complet:");
			show_chemin(solveur.cheminComplet(noeudFinal));
		} else
			System.out.println("Le puzzle n'a pas de solution :( ");
	}

}
