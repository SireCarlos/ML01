package learningcheckers;

import java.util.ArrayList;
import java.util.List;

public class LinearSelectorLearner {
    
    
    public LinearSelectorLearner() {}
    
    
    
    /**
     * Lernt bessere Parameter für einen LinearSelector.
     * Dabei werden Testspiele erzeugt und sowohl Spielstände sowohl mit dem teacher als auch
     * mit dem zu lernenden Selektor bewertet. Der zu lernende Selektor lernt also
     * wie der teacher zu spielen.
     *   
     * @param rounds Anzahl der Testspiele die maximal durchgeführt werden.
     * @param goalFraction Der Lernalgorithmus bricht ab, wenn der gelernte 
     *                     Selektor mindestens diesen Bruchteil an Testspielen
     *                     gegen den gegebenen base-Selektor gewinnt. 
     * @param base Der Selektor mit dessen Parametern der Lernvorgang beginnt
     *             und der als Vergleich zum Ermitteln des Lernerfolgs herangezogen wird.
     * @param teacher Der Selektor von dem gelernt werden soll.
     * @return Den gelernten Selektor.
     */
    public LinearSelector learnSupervised(int rounds, double goalFraction, LinearSelector base, LinearSelector teacher) {
    	LinearSelector learner = (LinearSelector)base.clone();
    	for (int round = 0; round < rounds; round++) {
    		Game g = new Game(learner, learner, false);
            g.run(false);
            g.closeGameWindow();
            List<Board> li = g.getHistory();
            trainSupervised(learner, li, teacher);
            double result = fractionOfGamesWon(base, learner, 100);
            if(result > goalFraction) {
            	System.out.println("Round: " + round + " base vs learner: " + result +" Status: finished");
            	System.out.println("");
            	return learner;
            }
            else {
            	System.out.println("Round: " + round + " base vs learner: " + result);
            }
    	}
    	System.out.println("Status: failed");
    	System.out.println("");
        return learner;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s, wobei s sich an den Bewertungen von teacher orientiert.
     */
    private void trainSupervised(LinearSelector s, List<Board> li, LinearSelector teacher) {    		
	   	double c = 0.000015;
    	double factBasis = s.getFactBasis();
	    double factNrPiecesSelf = s.getFactNrPiecesSelf();
	    double factNrPiecesOther = s.getFactNrPiecesOther();
	    double factNrKingsSelf = s.getFactNrKingsSelf();
	    double factNrKingsOther = s.getFactNrKingsOther();
	    double factNrThreatenedPiecesSelf = s.getFactNrThreatenedPiecesSelf();
	    double factNrThreatenedPiecesOther = s.getFactNrThreatenedPiecesOther();
	    double factNrStuckSelfPieces = s.getFactNrStuckSelfPieces();
	    double factNrStuckOtherPieces = s.getFactNrStuckOtherPieces();
		for(Board b : li) {
		   	double fehler = teacher.evaluate(b, 2) - s.evaluate(b, 2);
		   	factNrPiecesSelf = factNrPiecesSelf + c * fehler * b.getNumberOfPiecesFor(2);
		   	factNrPiecesOther = factNrPiecesOther + c * fehler * b.getNumberOfPiecesFor(1);
		   	factNrKingsSelf = factNrKingsSelf + c * fehler * b.getNumberOfKingsFor(2);
		   	factNrKingsOther = factNrKingsOther + c * fehler * b.getNumberOfKingsFor(1);
		   	factNrThreatenedPiecesSelf = factNrThreatenedPiecesSelf + c * fehler * s.piecesThreatened(b, 2);
		   	factNrThreatenedPiecesOther = factNrThreatenedPiecesOther + c * fehler * s.piecesThreatened(b, 1);
		   	factNrStuckSelfPieces = factNrStuckSelfPieces + c * fehler * s.piecesStuck(b, 2);
		   	factNrStuckOtherPieces = factNrStuckOtherPieces + c * fehler * s.piecesStuck(b, 1);		   	
		}
		s.setFactBasis(factBasis);
		s.setFactNrPiecesSelf(factNrPiecesSelf);
		s.setFactNrPiecesOther(factNrPiecesOther);
		s.setFactNrKingsSelf(factNrKingsSelf);
		s.setFactNrKingsOther(factNrKingsOther);
		s.setFactNrThreatenedPiecesSelf(factNrThreatenedPiecesSelf);
		s.setFactNrThreatenedPiecesOther(factNrThreatenedPiecesOther);
		s.setFactNrStuckSelfPieces(factNrStuckSelfPieces);
		s.setFactNrStuckOtherPieces(factNrStuckOtherPieces);
    }
    
    
    
    /**
     * Lernt selbsständig bessere Parameter für einen LinearSelector.
     * Dabei werden Trainingsdaten automatisch über Testspiele erzeugt.
     *   
     * @param rounds Anzahl der Testspiele die maximal durchgeführt werden.
     * @param goalFraction Der Lernalgorithmus bricht ab, wenn der gelernte 
     *                     Selektor mindestens diesen Bruchteil an Testspielen
     *                     gegen den gegebenen base-Selektor gewinnt. 
     * @param base Der Selektor mit dessen Parametern der Lernvorgang beginnt
     *             und der als Vergleich zum Ermitteln des Lernerfolgs herangezogen wird.
     * @return Den gelernten Selektor.
     */
    public LinearSelector learnUnsupervised(int rounds, double goalFraction, LinearSelector base) {
    	LinearSelector learner = (LinearSelector)base.clone();
    	for (int round = 0; round < rounds; round++) {
    		Game g = new Game(learner, learner, false);
            g.run(false);
            g.closeGameWindow();
            List<Board> li = g.getHistory();
            trainUnsupervised(learner, li);
            double result = fractionOfGamesWon(base, learner, 100);
            if(result > goalFraction) {
            	System.out.println("Round: " + round + " base vs learner: " + result +" Status: finished");
            	System.out.println("");
            	return learner;
            }
            else {
            	System.out.println("Round: " + round + " base vs learner: " + result);
            }
    	}
    	System.out.println("Status: failed");
    	System.out.println("");
        return learner;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s. 
     */
    private void trainUnsupervised(LinearSelector s, List<Board> li) {
        //TODO: Bitte Implementieren!
    	double c = 0.00015;
    	double factBasis = s.getFactBasis();
	    double factNrPiecesSelf = s.getFactNrPiecesSelf();
	    double factNrPiecesOther = s.getFactNrPiecesOther();
	    double factNrKingsSelf = s.getFactNrKingsSelf();
	    double factNrKingsOther = s.getFactNrKingsOther();
	    double factNrThreatenedPiecesSelf = s.getFactNrThreatenedPiecesSelf();
	    double factNrThreatenedPiecesOther = s.getFactNrThreatenedPiecesOther();
	    double factNrStuckSelfPieces = s.getFactNrStuckSelfPieces();
	    double factNrStuckOtherPieces = s.getFactNrStuckOtherPieces();
		for(int i = 0; i<li.size()-2; i++) {
	    	double fehler = s.evaluate(li.get(i), 2) - s.evaluate(li.get(i+2), 2);
		   	factNrPiecesSelf = factNrPiecesSelf + c * fehler * li.get(i).getNumberOfPiecesFor(2);
		   	factNrPiecesOther = factNrPiecesOther + c * fehler * li.get(i).getNumberOfPiecesFor(1);
		   	factNrKingsSelf = factNrKingsSelf + c * fehler * li.get(i).getNumberOfKingsFor(2);
		   	factNrKingsOther = factNrKingsOther + c * fehler * li.get(i).getNumberOfKingsFor(1);
		   	factNrThreatenedPiecesSelf = factNrThreatenedPiecesSelf + c * fehler * s.piecesThreatened(li.get(i), 2);
		   	factNrThreatenedPiecesOther = factNrThreatenedPiecesOther + c * fehler * s.piecesThreatened(li.get(i), 1);
		   	factNrStuckSelfPieces = factNrStuckSelfPieces + c * fehler * s.piecesStuck(li.get(i), 2);
		   	factNrStuckOtherPieces = factNrStuckOtherPieces + c * fehler * s.piecesStuck(li.get(i), 1);		   	
		}
		s.setFactBasis(factBasis);
		s.setFactNrPiecesSelf(factNrPiecesSelf);
		s.setFactNrPiecesOther(factNrPiecesOther);
		s.setFactNrKingsSelf(factNrKingsSelf);
		s.setFactNrKingsOther(factNrKingsOther);
		s.setFactNrThreatenedPiecesSelf(factNrThreatenedPiecesSelf);
		s.setFactNrThreatenedPiecesOther(factNrThreatenedPiecesOther);
		s.setFactNrStuckSelfPieces(factNrStuckSelfPieces);
		s.setFactNrStuckOtherPieces(factNrStuckOtherPieces);
    }
    
    
    /**
     * Führt numberOfGames Damespiele durch, wobei ein Spieler
     * seine Züge mittels base-Selektor ausführt und der andere
     * spieler mittels learned-Selector.
     * @return Den Bruchteil der Spiele, der von learned gewonnen wurde.
     */
    private double fractionOfGamesWon(LinearSelector base, LinearSelector learned, int numberOfGames){
	    	double gamesWon = 0;
	    	double noTie = 0;
	    	for (int i = 0; i < numberOfGames; i++) {
	    		Game g = new Game(base, learned, false);
	    		int winner = g.run(false);
	    		if (winner == 2) {
            		gamesWon++;
            		noTie++;
	    		}
	    		else if(winner == 1) {
            		noTie++;
	    		}
	    		g.closeGameWindow();
	    	}
	    //System.out.println(gamesWon/noTie);
	    return gamesWon/noTie;
    }
    
    

    
    public static void main(String[] args) {
        LinearSelectorLearner learner = new LinearSelectorLearner();
        //LinearSelector base = new LinearSelector(8);
        LinearSelector base = new LinearSelector(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
        HumanIntuitionLinearSelector human = new HumanIntuitionLinearSelector();
        System.out.println("base vs supervised_tester(=learner)");
        LinearSelector supervised_tester = learner.learnSupervised(1000, 0.98, base, human);
        System.out.println("base vs unsupervised_tester(=learner)");
        LinearSelector unsupervised_tester = learner.learnUnsupervised(1000, 0.80, base);
        
        System.out.println("base vs base " + learner.fractionOfGamesWon(base, base, 1000));
        System.out.println("human vs base_tester " + learner.fractionOfGamesWon(human, base, 1000));
        System.out.println("base vs supervised_tester(=learner) " + learner.fractionOfGamesWon(base, supervised_tester, 1000));
        System.out.println("human vs supervised_tester(=learner) " + learner.fractionOfGamesWon(base, supervised_tester, 1000));
        System.out.println("base vs unsupervised_tester(=learner) " + learner.fractionOfGamesWon(base, unsupervised_tester, 1000));
    }
}


